//package com.sunkyuj.douner.security;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//
//import java.util.Date;
//
//public class JwtTokenUtil {
//    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24; // 1일
//    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7; // 7일
//    public static String createToken(String userName, long expireTimeMs, String key) {
//        Claims claims = Jwts.claims(); // 일종의 map
//        claims.put("userName", userName);
//
//        return Jwts.builder()       // 토큰 생성
//                .setClaims(claims)
//                .setIssuedAt(new Date(System.currentTimeMillis()))      //  시작 시간 : 현재 시간기준으로 만들어짐
//                .setExpiration(new Date(System.currentTimeMillis() + expireTimeMs))     // 끝나는 시간 : 지금 시간 + 유지할 시간(입력받아옴)
//                .signWith(SignatureAlgorithm.HS256, key)
//                .compact();
//    }
//}