package com.example.zvon73.DTO;

import com.example.zvon73.entity.Enums.Role;
import com.example.zvon73.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private UUID id;


    private String role;

    private String email;

    private String password;

    private String phone;

    public UserDto(User user){
        this.id = user.getId();
        this.role = user.getRole().toString();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.phone = user.getPhone();
    }

}
