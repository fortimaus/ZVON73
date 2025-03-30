package com.example.zvon73.service;

import com.example.zvon73.entity.Enums.Role;
import com.example.zvon73.entity.Temple;
import com.example.zvon73.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CheckUserRole {

    private final UserService userService;

    public boolean checkForAdminOrRingerTemple(Temple temple){
        User user = userService.getCurrentUser();
        return temple.checkRinger(user.getId()) || user.getRole().equals(Role.ADMIN);
    }


}
