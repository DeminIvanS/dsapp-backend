package org.dance.dsappbackend.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * Компонент для работы с JWT-токенами.
 *
 * <p>Генерирует два вида токенов:
 * <ul>
 *   <li><b>Access token</b> — короткоживущий (15 мин). Передаётся в каждом запросе
 *       через заголовок {@code Authorization: Bearer <token>}.</li>
 *   <li><b>Refresh token</b> — долгоживущий (7 дней). Используется ТОЛЬКО для получения
 *       нового access-токена через {@code POST /api/auth/refresh}. Нигде не хранится
 *       на сервере — валидация происходит исключительно по подписи.</li>
 * </ul>
 *
 * <p>Оба токена подписываются одним и тем же HMAC-SHA256 секретом, но содержат
 * разный claim {@code type} ("access" / "refresh"), чтобы нельзя было использовать
 * refresh-токен как access и наоборот.
 */

@Component
public class JwtTokenProvider {
    private static final Logger log = LoggerFactory.getLogger(JwtTokenProvider.class);

    private static final String CLAIM_TYPE = "type";
    private static final String TYPE_ACCESS = "access";
    private static final String TYPE_REFRESH = "refresh";

    private final SecretKey secretKey;
    private final long accessTokenExpiration;
    private final long refreshTokenExpiration;

    public JwtTokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.access-token-expiration}") long accessTokenExpiration,
            @Value("${jwt.refresh-token-expiration}") long refreshTokenExpiration) {
        // Декодируем секрет из Base64 или используем байты строки напрямую
        byte[] keyBytes;
        try {
            keyBytes = Decoders.BASE64.decode(secret);
        } catch (Exception e) {
            keyBytes = secret.getBytes();
        }
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenExpiration = accessTokenExpiration;
        this.refreshTokenExpiration = refreshTokenExpiration;
    }

    public String generateAccessToken(String username) {
        return buildToken(username, TYPE_ACCESS, accessTokenExpiration);
    }

    public String generateRefreshToken(String username) {
        return buildToken(username, TYPE_REFRESH, refreshTokenExpiration);
    }

    private String buildToken(String username, String type, long expirationMs) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .subject(username)
                .claim(CLAIM_TYPE, type)
                .issuedAt(now)
                .expiration(expiry)
                .signWith(secretKey)
                .compact();
    }

    /**
     * Извлекает имя пользователя из токена. Не проверяет тип токена.
     */
    public String extractUsername(String token) {
        return parseClaims(token).getSubject();
    }

    /**
     * Проверяет, что токен валиден и имеет тип "access".
     */
    public boolean isAccessTokenValid(String token) {
        return isTokenValidWithType(token, TYPE_ACCESS);
    }

    /**
     * Проверяет, что токен валиден и имеет тип "refresh".
     */
    public boolean isRefreshTokenValid(String token) {
        return isTokenValidWithType(token, TYPE_REFRESH);
    }

    private boolean isTokenValidWithType(String token, String expectedType) {
        try {
            Claims claims = parseClaims(token);
            return expectedType.equals(claims.get(CLAIM_TYPE, String.class));
        } catch (ExpiredJwtException e) {
            log.debug("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.debug("JWT token is unsupported: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.debug("JWT token is malformed: {}", e.getMessage());
        } catch (SecurityException e) {
            log.debug("JWT signature validation failed: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.debug("JWT token is empty or null: {}", e.getMessage());
        }
        return false;
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
