package spring.springinaction.tacos.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import spring.springinaction.tacos.domain.repository.UserRepository;
import spring.springinaction.tacos.request.RegistrationRequest;

@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegistrationController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<Void> processRegistration(@RequestBody RegistrationRequest request) {
        userRepository.save(request.toUser(passwordEncoder));
        return ResponseEntity.ok().build();
    }
}
