package com.sunkyuj.douner.security;

import com.sunkyuj.douner.errors.CustomException;
import com.sunkyuj.douner.errors.ErrorCode;
import com.sunkyuj.douner.user.UserService;
import com.sunkyuj.douner.user.model.User;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Stream;

//import static com.sunkyuj.douner.SecurityConstants.JWT_SECRET_KEY;



@RequiredArgsConstructor
@Slf4j
@Component
public class JwtProvider {


    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24; // 1일
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7; // 7일
    @Value("${jwt.jwt-secret-key}")
    private String JWT_SECRET_KEY/* = JWT_SECRET_KEY*/;



    // 요청 헤더에서 액세스 토큰 가져오기
    public String getAccessTokenFromRequest(HttpServletRequest request) {
        return request.getHeader("access-token");
    }

    // 요청 헤더에서 리프레시 토큰 가져오기
    public String getRefreshTokenFromRequest(HttpServletRequest request) {
        return request.getHeader("refresh-token");
    }

    // 액세스 토큰 만료 시간 가져오기
    public Long getAccessTokenExpireTime() {
        return ACCESS_TOKEN_EXPIRE_TIME;
    }

    // 토큰에서 userId 가져오기
    public Long getIdFromToken(String token) {
        return validateTokenAndReturnBody(token).get("userId", Long.class);
    }

    // 액세스 토큰 발행
    public String publishAccessToken(Long userId) {
        return generateNewToken(userId, ACCESS_TOKEN_EXPIRE_TIME);
    }

    // 리프레시 토큰 발행
    public String publishRefreshToken(Long userId) {
        return generateNewToken(userId, REFRESH_TOKEN_EXPIRE_TIME);
    }

    // 토큰 유효성 검사
    public Boolean isTokenValid(String token) {
        Date expireDate = validateTokenAndReturnBody(token).getExpiration();
        return expireDate.before(new Date());
    }

    // HMAC-SHA 알고리즘에 쓰기 위해 JWT_SECRET_KEY로 시크릿키 생성
    private Key getSignInKey() {
        byte[] byteKey = /*JwtProvider.*/JWT_SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(byteKey);
    }

    // 새 토큰 발행 (claim, 발행시각, 암호화 알고리즘 등 지정)
    private String generateNewToken(Long id, Long tokenExpireTime) {
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .claim("userId", id)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpireTime))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // 토큰 검증 & 토큰의 Body 반환
    private Claims validateTokenAndReturnBody(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSignInKey())
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
