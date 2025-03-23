package spring.springinaction.utils;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import spring.springinaction.utils.JwtUtils.Payload;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class JwtUtilsTest {

    private JwtUtils jwtUtils;

    @BeforeEach
    void setUp() {
        jwtUtils = new JwtUtils();

        ReflectionTestUtils.setField(jwtUtils, "issuer", "test-issuer");
        ReflectionTestUtils.setField(jwtUtils, "secret", "test-secret");
        ReflectionTestUtils.setField(jwtUtils, "accessTokenExpirySeconds", 60L);
    }

    @Test
    void createAccessToken_ShouldReturnValidToken() {
        // Given
        Set<Payload> payloads = Set.of(
                new Payload("userId", "12345"),
                new Payload("role", "USER")
        );

        // When
        String token = jwtUtils.createAccessToken(payloads);

        // Then
        System.out.println("token: " + token);
        assertThat(token).isNotNull().isNotBlank();
    }

    @Test
    void verify_ShouldReturnDecodedJWT_WhenTokenIsValid() {
        // Given
        Set<Payload> payloads = Set.of(
                new Payload("userId", "12345"),
                new Payload("role", "USER")
        );
        String token = jwtUtils.createAccessToken(payloads);

        // When
        DecodedJWT decodedJWT = jwtUtils.verify(token);

        // Then
        assertThat(decodedJWT).isNotNull();
        assertThat(decodedJWT.getIssuer()).isEqualTo("test-issuer");
        assertThat(decodedJWT.getClaim("userId").asString()).isEqualTo("12345");
        assertThat(decodedJWT.getClaim("role").asString()).isEqualTo("USER");
    }
}
