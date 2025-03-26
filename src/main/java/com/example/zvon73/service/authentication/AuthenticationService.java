package com.example.zvon73.service.authentication;

import com.example.zvon73.controller.domain.*;
import com.example.zvon73.entity.Enums.Role;
import com.example.zvon73.entity.User;
import com.example.zvon73.service.UserService;
import com.example.zvon73.service.email.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final ResourceLoader resourceLoader;
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;
    private static final int TOKEN_VALIDITY_MINUTES = 15;

    @Transactional
    public MessageResponse signUp(SignUpRequest request) {

        try {
            var checkUser = userService.findByEmail(request.getEmail());
            if(checkUser != null && checkUser.getRole() == Role.NOT_CONFIRMED){
                userService.deleteUser(checkUser);
            }
            var user = User.builder()
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.NOT_CONFIRMED)
                    .build();
            generateVerificationToken(user);
            userService.create(user);

            Thread emailSendThread = new Thread(new Runnable()
            {
                public void run()
                {
                    try {
                        sendVerificationEmail(user, "Подтверждение регистрации");
                    } catch (MessagingException e) {

                    }
                }
            });
            emailSendThread.start();
            return new MessageResponse("Письмо с подтверждением регистрации отправлена на почту", "");
        }catch (Exception e){
            return new MessageResponse("", e.getMessage());
        }
    }

    public TokenResponse signIn(SignInRequest request) {
        try {
            var checkUser = userService.getByEmail(request.getEmail());
            if(checkUser.getRole() == Role.NOT_CONFIRMED){
                throw new UsernameNotFoundException("Пользователь с такой почтой не найден");
            }
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword()
            ));

            var user = userService.userDetailsService()
                    .loadUserByUsername(request.getEmail());

            var jwt = jwtService.generateToken(user);
            return new TokenResponse(jwt, "");
        } catch (Exception e) {
            return new TokenResponse("", e.getMessage());
        }
    }
    public MessageResponse verifyEmail(VerifyRequest request) {
        try {
            var user = userService.getByEmail(request.getEmail());
            checkUserSecurityToken(request.getToken(), user);
            user.setRole(Role.USER);
            user.setVerificationToken(null);
            user.setTokenExpiryDate(null);
            userService.save(user);

            return new MessageResponse("Почта успешно подтверждена", "");
        }catch(Exception e){
            return new MessageResponse("", e.getMessage());
        }
    }
    public MessageResponse sendSecurityToken(VerifyRequest request){
        try {
            User user = userService.getByEmail(request.getEmail());
            generateVerificationToken(user);
            userService.save(user);
            Thread emailSendThread = new Thread(new Runnable()
                {
                    public void run()
                    {
                        try {
                            sendVerificationEmail(user, "Код безопасности");
                        } catch (MessagingException e) {

                        }
                    }
                });
                emailSendThread.start();
                return new MessageResponse("Письмо с кодом безопасности отправлено на почту","");
        }catch (Exception e){
            return new MessageResponse("", e.getMessage());
        }
    }
    public MessageResponse checkToken(String token, String email){
        try{
            User user = userService.getByEmail(email);
            return checkUserSecurityToken(token, user);
        }catch (Exception e){
            return new MessageResponse("", e.getMessage());
        }
    }
    public MessageResponse changePassword(ChangePasswordRequest request){
        try {
             User user = userService.getByEmail(request.getEmail());
             checkUserSecurityToken(request.getToken(), user);
             if(!request.getPassword().isEmpty()){
                 user.setPassword(passwordEncoder.encode(request.getPassword()));
                 user.setTokenExpiryDate(null);
                 user.setVerificationToken(null);
                 userService.save(user);
                 return new MessageResponse("Пароль успешно изменён", "");
             }else{
                 return new MessageResponse("", "Пароль не может быть установлен");
             }
        }catch (Exception e){
            return new MessageResponse("", e.getMessage());
        }
    }
    private MessageResponse checkUserSecurityToken(String token, User user){
            if (user.getTokenExpiryDate() != null && user.getTokenExpiryDate().isBefore(LocalDateTime.now())) {
                throw new RuntimeException("Токен истёк. Пожалуйста, запросите новый.");
            } else if (user.getTokenExpiryDate() != null) {
                if (user.getVerificationToken().equals(token)) {
                    return new MessageResponse(token, "");
                } else {
                    throw new RuntimeException("Токен недействительный.");
                }
            } else {
                throw new RuntimeException("Неизвестная ошибка!");
            }
    }
    private void generateVerificationToken(User user) {
        Random r = new Random( System.currentTimeMillis());
        Integer token = 10000 + r.nextInt(20000);
        user.setVerificationToken(token.toString());
        user.setTokenExpiryDate(LocalDateTime.now().plusMinutes(TOKEN_VALIDITY_MINUTES));
    }
    private void sendVerificationEmail(User user, String title) throws MessagingException {
        String htmlContent = buildEmail(user.getVerificationToken());
        emailService.sendHtmlEmail(user.getEmail(), title, htmlContent);
    }
    private String buildEmail(String verificationToken) {
        try {
            Resource resource = resourceLoader.getResource("classpath:templates/email-token-page.html");
            String htmlContent = Files.readString(Path.of(resource.getURI()), StandardCharsets.UTF_8);
            return htmlContent.replace("{{TOKEN}}", verificationToken);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при загрузке email-шаблона", e);
        }
    }
}
