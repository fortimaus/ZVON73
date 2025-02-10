package com.example.zvon73.controller;

import com.example.zvon73.controller.domain.SignInRequest;
import com.example.zvon73.controller.domain.SignUpRequest;
import com.example.zvon73.controller.domain.TokenResponse;
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
    public ResponseEntity<String> signUp(@RequestBody SignUpRequest request) {
        return ResponseEntity.ok(authenticationService.signUp(request));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<TokenResponse> signIn(@RequestBody SignInRequest request) {
        return ResponseEntity.ok(authenticationService.signIn(request));

    }
    @GetMapping("/verify")
    public ResponseEntity<String> verifyEmail(@RequestParam("token") String token) {
        Optional<User> userOpt = userService.verifyEmail(token);
        if (userOpt.isPresent()) {
            return ResponseEntity.ok("Почта успешно подтверждена!");
        } else {
            return ResponseEntity.badRequest().body("Неверный или истёкший токен.");
        }
    }
    @PostMapping("/resend-token")
    public ResponseEntity<String> resendToken(@RequestParam("email") String email) {
        Optional<User> userOpt = userService.findByEmail(email);
        if (userOpt.isPresent() && userOpt.get().getRole() == Role.NOT_CONFIRMED) {
            User user = userOpt.get();

            return ResponseEntity.ok(authenticationService.regenerateToken(user));
        } else {
            return ResponseEntity.badRequest().body("Пользователь не найден или уже подтвержден.");
        }
    }
}
