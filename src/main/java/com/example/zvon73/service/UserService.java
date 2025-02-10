package com.example.zvon73.service;

import com.example.zvon73.DTO.UserDto;
import com.example.zvon73.controller.domain.MessageResponse;
import com.example.zvon73.controller.domain.RoleRequest;
import com.example.zvon73.controller.domain.TempleOperatorRequest;
import com.example.zvon73.controller.domain.VerifyRequest;
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

    private final TempleRepository templeRepository;

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

                Temple oldTemple = templeRepository.findByUser(updateUser);
                if(oldTemple != null)
                {
                    oldTemple.setUser(null);
                    templeRepository.save(oldTemple);
                }
                Temple newTemple = templeRepository.findById(UUID.fromString(request.getTemple())).orElseThrow();
                newTemple.setUser(updateUser);
                templeRepository.save(newTemple);
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
    public MessageResponse verifyEmail(VerifyRequest request) {
        Optional<User> userOpt = findByEmail(request.getEmail());
        if (userOpt.isPresent()) {
            var user = userOpt.get();
            if (user.getRole() != Role.NOT_CONFIRMED){
                return new MessageResponse("", "Пользователь уже подтвержден");
            }
            if (user.getTokenExpiryDate() != null && user.getTokenExpiryDate().isBefore(LocalDateTime.now())) {
                return new MessageResponse("", "Токен истёк. Пожалуйста, запросите новый.");
            }else if(user.getTokenExpiryDate() != null){
                if(user.getVerificationToken().equals(request.getToken())){
                    user.setRole(Role.USER);
                    user.setVerificationToken(null);
                    user.setTokenExpiryDate(null);
                    userRepository.save(user);

                    return new MessageResponse("Почта успешно подтверждена", "");
                }else{
                    return new MessageResponse("","Токен недействительный.");
                }
            }else{
                return new MessageResponse("", "Неизвестная ошибка!");
            }
        }else{
            return new MessageResponse("", "Пользователя с такой почтой не существует.");
        }
    }
}