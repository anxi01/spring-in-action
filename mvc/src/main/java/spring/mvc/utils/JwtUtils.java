package spring.mvc.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class JwtUtils {

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.accessTokenExpirySeconds}")
    private Long accessTokenExpirySeconds;

    public String createAccessToken(Set<Payload> payloads) {
        Map<String, String> payloadMap = new HashMap<>();
        payloads.forEach(payload -> payloadMap.put(payload.key, payload.value));
        return createJwt(payloadMap);
    }

    public DecodedJWT verify(String jwt) {
        Algorithm algorithm = getAlgorithm();
        JWTVerifier verifier = getVerifier(algorithm);

        try {
            return verifier.verify(jwt);
        } catch (Exception e){
            throw new JWTVerificationException("JWT verification failed", e);
        }
    }

    private JWTVerifier getVerifier(Algorithm algorithm) {
        return JWT.require(algorithm)
                .withIssuer(issuer)
                .build();
    }

    private String createJwt(Map<String, String> payload) {
        Algorithm algorithm = getAlgorithm();
        Instant expiresAt = Instant.now().plusSeconds(accessTokenExpirySeconds);

        return JWT.create()
                .withIssuer(issuer)
                .withPayload(payload)
                .withExpiresAt(expiresAt)
                .sign(algorithm);
    }

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(secret);
    }

    public record Payload (
            String key,
            String value
    ){
    }
}
