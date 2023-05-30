//package com.sunkyuj.douner.security;
//
//import com.redjen.instagram.config.security.JwtProvider;
//import com.redjen.instagram.domain.common.LoginToken;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class JwtService {
//
//    private final JwtProvider jwtProvider;
//
//    public LoginToken createToken(Long id) {
//        String accessToken = jwtProvider.publishAccessToken(id);
//        String refreshToken = jwtProvider.publishRefreshToken(id);
//        return LoginToken.builder()
//                .accessToken(accessToken)
//                .refreshToken(refreshToken)
//                .expireSec(jwtProvider.getAccessTokenExpireTime())
//                .build();
//    }
//
//    public LoginToken createTokenByRefreshToken(String refreshToken) {
//        Long id = jwtProvider.getIdFromToken(refreshToken);
//        String accessToken = jwtProvider.publishAccessToken(id);
//        String newRefreshToken = jwtProvider.publishRefreshToken(id);
//        return LoginToken.builder()
//                .accessToken(accessToken)
//                .refreshToken(newRefreshToken)
//                .expireSec(jwtProvider.getAccessTokenExpireTime())
//                .build();
//    }
//}
