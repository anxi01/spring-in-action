package spring.mvc.tacos.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.mvc.tacos.domain.model.User;
import spring.mvc.tacos.domain.repository.UserRepository;
import spring.mvc.tacos.request.RegistrationRequest;
import spring.mvc.utils.JwtUtils;
import spring.mvc.utils.JwtUtils.Payload;

import java.util.Set;

@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegistrationController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @PostMapping
    public ResponseEntity<String> processRegistration(@RequestBody RegistrationRequest request) {
        User user = userRepository.save(request.toUser(passwordEncoder));

        Set<Payload> payloads = Set.of(
                new Payload("userId", user.getId().toString()),
                new Payload("username", user.getUsername())
        );
        String accessToken = jwtUtils.createAccessToken(payloads);
        return ResponseEntity.ok(accessToken);
    }
}
