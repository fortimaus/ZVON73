package com.example.zvon73.service;

import com.example.zvon73.DTO.UserDto;
import com.example.zvon73.controller.domain.MessageResponse;
import com.example.zvon73.controller.domain.RoleRequest;
import com.example.zvon73.controller.domain.TempleOperatorRequest;
import com.example.zvon73.entity.Enums.Role;
import com.example.zvon73.entity.Temple;
import com.example.zvon73.entity.User;
import com.example.zvon73.repository.TempleRepository;
import com.example.zvon73.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final TempleService templeService;

    public User save(User user) {
        return userRepository.save(user);
    }


    public User create(User user) {
        if (userRepository.existsByEmail(user.getUsername())) {
            throw new RuntimeException("Пользователь с таким именем уже существует");
        }
        return save(user);
    }
    @Transactional
    public User update(UserDto userDto){
        User currentUser = findById(UUID.fromString(userDto.getId()));

        if( !currentUser.getEmail().equals(userDto.getEmail()) )
            currentUser.setEmail(userDto.getEmail());
        if (!currentUser.getPhone().equals(userDto.getPhone()))
            currentUser.setPhone(userDto.getPhone());
        if (!currentUser.getPassword().equals(userDto.getPassword()))
            currentUser.setPassword(userDto.getPassword());

        return userRepository.save(currentUser);
    }

    @Transactional
    public MessageResponse updateRole(RoleRequest request){
        try{
            User updateUser = findById(UUID.fromString(request.getUser()));
            Role newRole = Role.valueOf(request.getRole());
            updateUser.setRole(newRole);
            if(!request.getTemple().isEmpty()){
                templeService.updateOperator(new TempleOperatorRequest(request.getTemple(), request.getUser()));
            }
            userRepository.save(updateUser);
            return new MessageResponse("Роль изменена", "");
        }catch (Exception ex)
        {
            return new MessageResponse("", ex.getMessage());
        }

    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User getByUsername(String username) {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

    }
    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    public User getCurrentUser() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }
    public User findById(UUID id){
        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }
    public void deleteUser(User user){
        userRepository.delete(user);
    }
    public Optional<User> verifyEmail(String token) {
        Optional<User> userOpt = userRepository.findByVerificationToken(token);

        if (userOpt.isPresent()) {
            User user = userOpt.get();

            // Проверка на истечение токена
            if (user.getTokenExpiryDate() != null && user.getTokenExpiryDate().isBefore(LocalDateTime.now())) {
                throw new RuntimeException("Токен истёк. Пожалуйста, запросите новый.");
            }

            user.setRole(Role.USER);
            user.setVerificationToken(null);
            user.setTokenExpiryDate(null);

            userRepository.save(user);
            return Optional.of(user);
        }
        return Optional.empty();
    }
}