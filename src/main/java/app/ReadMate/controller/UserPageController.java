package app.ReadMate.controller;

import app.ReadMate.dto.UserResponseDto;
import app.ReadMate.dto.UserUpdateDto;
import app.ReadMate.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserPageController {

    private final UserService userService;

    @GetMapping("/profile")
    public String getProfilePage(Principal principal, Model model) {
        if (principal == null) {
            return "redirect:/login";
        }
        String username = principal.getName();
        log.info("Current user: {}", username);

        UserResponseDto userResponseDto = userService.findByUsername(username);
        log.info("User data: {}", userResponseDto);

        model.addAttribute("user", userResponseDto);
        model.addAttribute("title", "Мой профиль");
        model.addAttribute("childTemplate", "profile");
        return "layout";
    }

    @PutMapping("/profile")
    public String update(@ModelAttribute @Valid UserUpdateDto userUpdateDto, Principal principal, RedirectAttributes redirectAttributes) {
        String username = principal.getName();
        UserResponseDto updatedUser = userService.update(userUpdateDto, username);

        // сообщение о успехе (опционально)
        redirectAttributes.addFlashAttribute("successMessage", "Profile updated successfully!");

        return "redirect:/profile";
    }
}
