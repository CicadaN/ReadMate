package app.ReadMate.service;


import app.ReadMate.dto.UserRequestDto;
import app.ReadMate.dto.UserResponseDto;
import app.ReadMate.dto.UserUpdateDto;
import app.ReadMate.mapper.UserMapper;
import app.ReadMate.model.User;
import app.ReadMate.repository.UserRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponseDto save(@NotNull @Valid UserRequestDto userRequestDto) {
        if (userRepository.findByUsername(userRequestDto.getUsername()).isPresent()) {
            throw new RuntimeException("Username is already taken");
        }
        if (userRepository.findByEmail(userRequestDto.getEmail()).isPresent()) {
            throw new RuntimeException("Email is already registered");
        }

        User user = userMapper.toEntity(userRequestDto);
        System.out.println("Raw password before encoding: " + user.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAge(userRequestDto.getAge());
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    public UserResponseDto update(@NotNull @Valid UserUpdateDto userUpdateDto, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(()->new RuntimeException("User not found"));

        if (!user.getUsername().equals(userUpdateDto.getUsername())) {
            boolean usernameExists = userRepository.findByUsername(userUpdateDto.getUsername()).isPresent();
            if (usernameExists) {
                throw new RuntimeException("Username already exists");
            }
        }
        user.setEmail(userUpdateDto.getEmail());
        user.setUsername(userUpdateDto.getUsername());
        user.setAge(userUpdateDto.getAge());
        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Username already exists");
        }        return userMapper.toDto(user);
    }


    public List<UserResponseDto> findAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    public UserResponseDto findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User with ID " + id + " not found"));
        return userMapper.toDto(user);
    }

//    public UserResponseDto findByUsername(String username) {
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new RuntimeException("User with username " + username + " not found"));
//        return userMapper.toDto(user);
//    }

    // добавил новый метод, что бы возврашать USER а е DTO и пока убрал прошлую версию
    public User findEntityByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User with username " + username + " not found"));
    }

    public UserResponseDto findByUsername(String username) {
        log.info("Fetching user by username: {}", username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        log.info("Fetched user entity: {}", user);

        return userMapper.toDto(user);
    }

    public void deleteById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User with ID " + id + " not found");
        }
        userRepository.deleteById(id);
    }
}
