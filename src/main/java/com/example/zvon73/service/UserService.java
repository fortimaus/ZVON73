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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
    public List<UserDto> getUserListForAdmin(PageRequest request){
        Page<User> userPage = userRepository.findAll(request);
        return userPage.getContent().stream().map(UserDto::new).collect(Collectors.toList());
    }
    @Transactional
    public User update(UserDto userDto){
        User currentUser = getCurrentUser();

        if (currentUser.getPhone() == null | !currentUser.getPhone().equals(userDto.getPhone()))
            currentUser.setPhone(userDto.getPhone());

        return userRepository.save(currentUser);
    }

    @Transactional
    public MessageResponse updateRole(RoleRequest request){
        try{
            User updateUser = getCurrentUser();
            Role newRole = Role.valueOf(request.getRole());
            if(updateUser.getRole() == Role.RINGER && (newRole != Role.RINGER || request.getTemples().isEmpty()) )
            {
                List<Temple> userTemples = templeRepository.findTemplesByRingersId(updateUser.getId());
                for (Temple temple : userTemples)
                    temple.deleteRinger(updateUser);
                templeRepository.saveAll(userTemples);
            }
            if(newRole == Role.RINGER && !request.getTemples().isEmpty()){
                List<Temple> userTemples = templeRepository.findTemplesByRingersId(updateUser.getId());
                if(userTemples == null )
                    userTemples = new ArrayList<>();
                List<Temple> newUserTemples = new ArrayList<>();
                List<Temple> updatedTemples = new ArrayList<>();

                for(String newTempleId : request.getTemples()){
                    newUserTemples.add(templeRepository.findById(UUID.fromString(newTempleId))
                            .orElseThrow(() -> new NullPointerException("Такого храма нет")));
                }

                for(Temple userTemple : userTemples){
                    if(!newUserTemples.contains(userTemple))
                        userTemple.deleteRinger(updateUser);
                    updatedTemples.add(userTemple);
                }

                for(Temple userTemple : newUserTemples){
                    if(!userTemples.contains(userTemple))
                        userTemple.addRinger(updateUser);
                    updatedTemples.add(userTemple);
                }

                templeRepository.saveAll(updatedTemples);
            }

            updateUser.setRole(newRole);
            userRepository.save(updateUser);
            return new MessageResponse("Роль изменена", "");
        }catch (Exception ex)
        {
            return new MessageResponse("", ex.getMessage());
        }

    }

    public User getByEmail(String username) {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

    }
    public User findByEmail(String email){
        var user = userRepository.findByEmail(email);
        return user.orElse(null);
    }
    public UserDetailsService userDetailsService() {
        return this::getByEmail;
    }

    public User getCurrentUser() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByEmail(username);
    }
    public User findById(){
        return getCurrentUser();
    }
    public void deleteUser(User user){
        userRepository.delete(user);
    }

}