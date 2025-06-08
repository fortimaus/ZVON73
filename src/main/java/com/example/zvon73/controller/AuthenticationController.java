package com.example.zvon73.controller;

import com.example.zvon73.controller.domain.*;
import com.example.zvon73.service.UserService;
import com.example.zvon73.service.authentication.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
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
        return ResponseEntity.ok(authenticationService.verifyEmail(request));
    }
    @PutMapping("/send-token")
    public ResponseEntity<MessageResponse> sendToken(@RequestBody VerifyRequest request) {
        return ResponseEntity.ok(authenticationService.sendSecurityToken(request));
    }
    @GetMapping("/check-token")
    public ResponseEntity<MessageResponse> checkToken(@RequestParam String token, @RequestParam String email){
        return ResponseEntity.ok(authenticationService.checkToken(token, email));
    }
    @PutMapping("/change-password")
    public ResponseEntity<MessageResponse> changePassword(@RequestBody ChangePasswordRequest request){
        return ResponseEntity.ok(authenticationService.changePassword(request));
    }
}
