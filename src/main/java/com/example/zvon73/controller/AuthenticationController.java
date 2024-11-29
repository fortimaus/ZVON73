package com.example.zvon73.controller;

import com.example.zvon73.controller.domain.SignInRequest;
import com.example.zvon73.controller.domain.SignUpRequest;
import com.example.zvon73.controller.domain.TokenResponse;
import com.example.zvon73.service.authentication.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/sign-up")
    public TokenResponse signUp(@RequestBody SignUpRequest request) {
        return authenticationService.signUp(request);
    }

    @PostMapping("/sign-in")
    public TokenResponse signIn(@RequestBody SignInRequest request) {
        return authenticationService.signIn(request);

    }
}
