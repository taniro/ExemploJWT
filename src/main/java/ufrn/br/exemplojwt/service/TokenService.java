package ufrn.br.exemplojwt.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class TokenService {

    private final Long expiration = 86400000L;
    private final String secret = "secret";

    public String generateToken(Authentication authentication) {

        UserDetails usuario = (UserDetails) authentication.getPrincipal();

        Date now = new Date();
        Date exp = new Date(now.getTime() + expiration);

        return Jwts.builder().setIssuer("ExemploJWT")
                .setSubject(usuario.getUsername())
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getTokenUsername(String token) {
        Claims body = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return body.getSubject();
    }

}
