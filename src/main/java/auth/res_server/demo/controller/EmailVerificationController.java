package auth.res_server.demo.controller;

import auth.res_server.demo.dto.email.ResendVerificationRequest;
import auth.res_server.demo.dto.email.VerifyEmailRequest;
import auth.res_server.demo.service.EmailVerificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class EmailVerificationController {

    private final EmailVerificationService emailVerificationService;

    @GetMapping("/verify-email")
    public ResponseEntity<String> verifyEmail(@RequestParam String token) {
        emailVerificationService.verifyEmail(token);
        return ResponseEntity.ok("Email verified successfully");
    }

    @PostMapping("/verify-email")
    public ResponseEntity<String> verifyEmailPost(@Valid @RequestBody VerifyEmailRequest request) {
        emailVerificationService.verifyEmail(request.token());
        return ResponseEntity.ok("Email verified successfully");
    }

    @PostMapping("/resend-verification")
    public ResponseEntity<String> resendVerification(@Valid @RequestBody ResendVerificationRequest request) {
        emailVerificationService.resendVerificationEmail(request.email());
        return ResponseEntity.ok("If the email exists, a verification email has been sent");
    }
}
