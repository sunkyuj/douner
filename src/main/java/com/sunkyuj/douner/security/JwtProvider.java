package com.sunkyuj.douner.security;

import com.sunkyuj.douner.errors.CustomException;
import com.sunkyuj.douner.errors.ErrorCode;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import static com.sunkyuj.douner.SecurityConstants.JWT_SECRET_KEY;


@NoArgsConstructor
@Log4j2
@Component
public class JwtProvider {

    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24; // 1일
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7; // 7일
    private static final String jwtKey = JWT_SECRET_KEY;

//    @Value("${jwt.token.secret}")
//    private String secretkey;   // application.yml에서 설정한 token 키의 값을 저장함

    public String getAccessTokenFromRequest(HttpServletRequest request) {
        return request.getHeader("access-token");
    }

    public String getRefreshTokenFromRequest(HttpServletRequest request) {
        return request.getHeader("refresh-token");
    }

    public Long getAccessTokenExpireTime() {
        return ACCESS_TOKEN_EXPIRE_TIME;
    }

    public Long getIdFromToken(String token) {
        return validateTokenAndReturnBody(token).get("userId", Long.class);
    }

    public String publishAccessToken(Long userId) {
        return generateNewToken(userId, ACCESS_TOKEN_EXPIRE_TIME);
    }

    public String publishRefreshToken(Long userId) {
        return generateNewToken(userId, REFRESH_TOKEN_EXPIRE_TIME);
    }

    public Boolean isTokenValid(String token) {
        Date expireDate = validateTokenAndReturnBody(token).getExpiration();
        return expireDate.before(new Date());
    }

    private Key getSignInKey(String secretKey) {
        byte[] byteKey = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(byteKey);
    }

    private String generateNewToken(Long id, Long tokenExpireTime) {
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .claim("userId", id)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpireTime))
                .signWith(getSignInKey(jwtKey), SignatureAlgorithm.HS256)
                .compact();
    }

    private Claims validateTokenAndReturnBody(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSignInKey(jwtKey))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e1) {
            log.error("Invalid or Illegal JWT Token used.");
            throw new CustomException(ErrorCode.INVALID_FORMAT_TOKEN);
        } catch (ExpiredJwtException e2) {
            log.error("Expired JWT Token used.");
            throw new CustomException(ErrorCode.EXPIRED_ACCESS_TOKEN);
        }
    }
}
