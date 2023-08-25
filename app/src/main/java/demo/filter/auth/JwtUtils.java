package demo.filter.auth;


import com.auth0.jwt.JWT;
import demo.filter.customer.enums.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@Component
public class JwtUtils {

    private static final long JWT_TOKEN_VALIDITY = 24 * 60 * 60;

    @Value("${jwt.secret}")
    private String secret;

    public String generateJwtToken(Long id, List<Role> roles) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", id);
        claims.put("roles", Role.toStringList(roles));
        return JWT.create()
                .withSubject(id.toString())
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .withClaim("claims", claims)
                .sign(HMAC512(secret.getBytes()));
    }

    @SuppressWarnings("unchecked")
    public RequestCustomer parseJwtToken(String token) {
        Map<String, Object> claims = JWT.require(HMAC512(secret.getBytes()))
                .build()
                .verify(token)
                .getClaims()
                .get("claims")
                .asMap();
        return new RequestCustomer(
                Long.parseLong(claims.get("id").toString()),
                (List<String>) claims.get("roles")
        );
    }
}