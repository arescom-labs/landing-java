package kr.arescom.landing.core.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtTokenProvider {

    // JWT Secret Key
    private final String secretKey = "landing_project_jwt_secret_key_2026_secure";

    private SecretKey key;

    // 토큰 유효기간: 1시간
    private final long tokenValidityInMilliseconds = 1000L * 60 * 60;

    @PostConstruct
    protected void init() {
        this.key = Keys.hmacShaKeyFor(
                secretKey.getBytes(StandardCharsets.UTF_8)
        );
    }

    // 토큰 생성
    public String generateToken(String username) {

        Date now = new Date();

        Date validity = new Date(
                now.getTime() + tokenValidityInMilliseconds
        );

        return Jwts.builder()
                .subject(username)
                .claim("role", "ROLE_ADMIN")
                .issuedAt(now)
                .expiration(validity)
                .signWith(key)
                .compact();
    }

    // 토큰 검증 및 정보 조회
    public Claims validateToken(String token) {

        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // 토큰 유효기간
    public long getExpiration() {
        return tokenValidityInMilliseconds;
    }
}