package com.example.zvon73.service.authentication;

import com.example.zvon73.Role;
import com.example.zvon73.controller.domain.SignInRequest;
import com.example.zvon73.controller.domain.SignUpRequest;
import com.example.zvon73.controller.domain.TokenResponse;
import com.example.zvon73.entity.User;
import com.example.zvon73.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public TokenResponse signUp(SignUpRequest request) {

        var user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userService.create(user);

        var jwt = jwtService.generateToken(user);
        return new TokenResponse(jwt, "");
    }

    public TokenResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        ));

        var user = userService.userDetailsService()
                .loadUserByUsername(request.getEmail());

        var jwt = jwtService.generateToken(user);
        return new TokenResponse(jwt, "");
    }
}
