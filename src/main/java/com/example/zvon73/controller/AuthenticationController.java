package com.example.zvon73.controller;

import com.example.zvon73.controller.domain.*;
import com.example.zvon73.entity.Enums.Role;
import com.example.zvon73.entity.User;
import com.example.zvon73.service.UserService;
import com.example.zvon73.service.authentication.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<MessageResponse> signUp(@RequestBody SignUpRequest request) {
        return ResponseEntity.ok(authenticationService.signUp(request));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<TokenResponse> signIn(@RequestBody SignInRequest request) {
        return ResponseEntity.ok(authenticationService.signIn(request));

    }
    @PutMapping("/verify")
    public ResponseEntity<MessageResponse> verifyEmail(@RequestBody VerifyRequest request) {
        return ResponseEntity.ok(userService.verifyEmail(request));
    }
    @PutMapping("/resend-token")
    public ResponseEntity<MessageResponse> resendToken(@RequestBody VerifyRequest request) {
        Optional<User> userOpt = userService.findByEmail(request.getEmail());
        if (userOpt.isPresent() && userOpt.get().getRole() == Role.NOT_CONFIRMED) {
            User user = userOpt.get();
            return ResponseEntity.ok(new MessageResponse(authenticationService.regenerateToken(user), ""));
        } else {
            return ResponseEntity.ok(new MessageResponse("", "Пользователь не найден или уже подтвержден."));
        }
    }
}
