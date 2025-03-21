package spring.springinaction.tacos.request;

import org.springframework.security.crypto.password.PasswordEncoder;
import spring.springinaction.tacos.domain.model.User;

public record RegistrationRequest(
        String username,
        String password,
        String fullname,
        String street,
        String city,
        String state,
        String zip,
        String phone
) {
    public User toUser(PasswordEncoder passwordEncoder) {
        return new User(username, passwordEncoder.encode(password), fullname, street, city, state, zip, phone);
    }
}
