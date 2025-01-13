package app.ReadMate.controller;

import app.ReadMate.dto.UserRequestDto;
import app.ReadMate.dto.UserResponseDto;
import app.ReadMate.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@AllArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<?> register(@ModelAttribute @Valid UserRequestDto userRequestDto) {
        System.out.println("Received DTO: " + userRequestDto);

        try {
            UserResponseDto userResponseDto = userService.save(userRequestDto);

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userRequestDto.getUsername(), userRequestDto.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return ResponseEntity.ok("User registered and authenticated successfully: " + userResponseDto.getUsername());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@ModelAttribute @Valid UserRequestDto userRequestDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userRequestDto.getUsername(), userRequestDto.getPassword()
                    )
            );

            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);

            return ResponseEntity.ok(Map.of("message", "Login successful", "redirectUrl", "/profile"));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid username or password"));
        }
    }


    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        return ResponseEntity.ok("Logout successful");
    }

    // авторизация с использованием JWT токено убрана из проекта, оставил для возможной доработки
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@ModelAttribute @Valid UserRequestDto userRequestDto) {
//        try {
//            User user = userService.findEntityByUsername(userRequestDto.getUsername());
//            if (!passwordEncoder.matches(userRequestDto.getPassword(), user.getPassword())) {
//                return ResponseEntity.badRequest().body("Invalid username or password");
//            }
//            String token = jwtUtils.generateToken(user.getUsername());
////            ResponseCookie cookie = ResponseCookie.from("jwt", token)
////                    .httpOnly(true)
////                    .secure(true)
////                    .path("/")
////                    .maxAge(Duration.ofHours(1))
////                    .build();
//
////            return ResponseEntity.ok(token);
////            return ResponseEntity.ok()
////                    .header(HttpHeaders.SET_COOKIE, cookie.toString())
////                    .body("Login successful");
//            return ResponseEntity.ok(Collections.singletonMap("token", "Bearer " + token));
//
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
//        }
//    }

}
