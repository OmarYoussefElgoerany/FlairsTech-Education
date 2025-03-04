package com.flairstech_education.auth;

import com.flairstech_education.exception.InvalidCredentialsException;
import com.flairstech_education.role.RoleRepository;
import com.flairstech_education.security.JwtService;
import com.flairstech_education.token.Token;
import com.flairstech_education.token.TokenRepository;
import com.flairstech_education.user.User;
import com.flairstech_education.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
//    private final EmailService emailService;
    private final TokenRepository tokenRepository;

//    @Value("${application.mailing.frontend.activation-url}")
//    private String activationUrl;

    public void register(RegistrationRequest request) {
        var userRole = roleRepository.findByName(request.getRole().toString())
                // todo - better exception handling
                .orElseThrow(() -> new IllegalStateException("ROLE USER was not initiated"));
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountLocked(false)
                .enabled(false)
                .createdBy(userRole.getName())
                .roles(List.of(userRole))
                .build();
        userRepository.save(user);
//        sendValidationEmail(user);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            var auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );


            var claims = new HashMap<String, Object>();
            var user = ((User) auth.getPrincipal());
            claims.put("fullName", user.fullName());

            var jwtToken = jwtService.generateToken(claims, (User) auth.getPrincipal());
            var token = tokenRepository.save(Token.builder()
                    .token(jwtToken).user(user).build());
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        }
        catch (BadCredentialsException e) {
            throw new InvalidCredentialsException("Invalid email or password");
        } catch (Exception e) {
            e.printStackTrace(); // Log the exact exception
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error occurred", e);
        }
    }

//    @Transactional
//    public void activateAccount(String token) throws MessagingException {
//        Token savedToken = tokenRepository.findByToken(token)
//                // todo exception has to be defined
//                .orElseThrow(() -> new RuntimeException("Invalid token"));
//        if (LocalDateTime.now().isAfter(savedToken.getExpiresAt())) {
//            sendValidationEmail(savedToken.getUser());
//            throw new RuntimeException("Activation token has expired. A new token has been send to the same email address");
//        }
//
//        var user = userRepository.findById(savedToken.getUser().getId())
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//        user.setEnabled(true);
//        userRepository.save(user);
//
//        savedToken.setValidatedAt(LocalDateTime.now());
//        tokenRepository.save(savedToken);
//    }

//    private String generateAndSaveActivationToken(User user) {
//        // Generate a token
//        String generatedToken = generateActivationCode(6);
//        var token = Token.builder()
//                .token(generatedToken)
//                .createdAt(LocalDateTime.now())
//                .expiresAt(LocalDateTime.now().plusMinutes(15))
//                .user(user)
//                .build();
//        tokenRepository.save(token);
//
//        return generatedToken;
//    }

//    private void sendValidationEmail(User user) throws MessagingException {
//        var newToken = generateAndSaveActivationToken(user);
//
//        emailService.sendEmail(
//                user.getEmail(),
//                user.getFullName(),
//                EmailTemplateName.ACTIVATE_ACCOUNT,
//                activationUrl,
//                newToken,
//                "Account activation"
//                );
//    }

//    private String generateActivationCode(int length) {
//        String characters = "0123456789";
//        StringBuilder codeBuilder = new StringBuilder();
//
//        SecureRandom secureRandom = new SecureRandom();
//
//        for (int i = 0; i < length; i++) {
//            int randomIndex = secureRandom.nextInt(characters.length());
//            codeBuilder.append(characters.charAt(randomIndex));
//        }
//
//        return codeBuilder.toString();
//    }
}
