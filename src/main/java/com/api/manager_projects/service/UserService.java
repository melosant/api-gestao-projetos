package com.api.manager_projects.service;

import com.api.manager_projects.dto.UserRequestDTO;
import com.api.manager_projects.dto.UserResponseDTO;
import com.api.manager_projects.entity.User;
import com.api.manager_projects.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public UserResponseDTO createUserService(UserRequestDTO dto) {
        User user = User.builder().name(dto.name()).build();
        User userSaved = userRepository.save(user);

        return toResponseDto(userSaved);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream().map(this::toResponseDto).toList();
    }

    @Transactional
    public UserResponseDTO updateUser(UUID userId, UserRequestDTO dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        user.setName(dto.name());
        User userUpdated = userRepository.save(user);

        return toResponseDto(userUpdated);
    }

    @Transactional
    public void deleteUser(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        userRepository.delete(user);
    }

    public UserResponseDTO toResponseDto(User entity) {
        return new UserResponseDTO(
                entity.getId(),
                entity.getName()
        );
    }
}
