package com.sunkyuj.douner.jwt;

import com.sunkyuj.douner.security.JwtProvider;
import com.sunkyuj.douner.security.JwtService;
import com.sunkyuj.douner.security.LoginToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

//import static com.sunkyuj.douner.SecurityConstants.TOKEN_HEADER_PREFIX;

@SpringBootTest
public class JwtProviderTest {

    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    JwtService jwtService;

    @Value("${jwt.jwt-secret-key}")
    private String JWT_SECRET_KEY;
    @Value("${jwt.token-header-prefix}")
    private String TOKEN_HEADER_PREFIX;

    @Test
    public void 토큰생성() throws Exception{
        Long userId = 602L;
        LoginToken token = jwtService.createToken(userId);
        System.out.println("token = " + token.getAccessToken());
    }

    @Test
    public void 토큰_검증하기() throws Exception {
        Long userId = 602L;
        LoginToken token = jwtService.createToken(userId);

        String accessToken = token.getAccessToken();
        accessToken = accessToken.replace(TOKEN_HEADER_PREFIX,"");
        System.out.println("accessToken = " + accessToken);

        // 2. JWT parsing
        Claims claims;
        try{
            claims = Jwts.parserBuilder()
                    .setSigningKey(JWT_SECRET_KEY.getBytes())
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (Exception ignored) {
            throw new Exception(ignored);
        }

    }


}
