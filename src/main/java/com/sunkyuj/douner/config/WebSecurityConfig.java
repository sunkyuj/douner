package com.sunkyuj.douner.config;

import com.sunkyuj.douner.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private static final String[] PERMIT_URL_ARRAY = {
            /* swagger v2 */
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            /* swagger v3 */
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "docs/**",
            /* login, register */
            "/api/v1/users/login",
            "/api/v1/users/register",
            /* web socket */
            "/ws/chat"
    };



    @Bean
    public BCryptPasswordEncoder encodePwd(){
        return new BCryptPasswordEncoder();     // password를 인코딩 해줄때 쓰기 위함
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        // SecurityFilterChain에서 요청에 접근할 수 있어서 인증, 인가 서비스에 사용

        //basic
        httpSecurity
                .httpBasic().disable()      // http basic auth 기반으로 로그인 인증창이 뜬다. 기본 인증을 이용하지 않으려면 .disable()을 추가해준다.
                .csrf().disable()       // csrf, api server이용시 .disable (html tag를 통한 공격)
                .cors();	 //  다른 도메인의 리소스에 대해 접근이 허용되는지 체크
        // session
        httpSecurity
                .sessionManagement()        // 세션 관리 기능을 작동한다.      .maximunSessions(숫자)로 최대 허용가능 세션 수를 정할수 있다.(-1로 하면 무제한 허용)
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // jwt사용하는 경우 씀(STATELESS는 인증 정보를 서버에 담지 않는다.)
        // authorizeHttpRequests
        httpSecurity
                .authorizeHttpRequests()
                .requestMatchers(PERMIT_URL_ARRAY).permitAll()
                .requestMatchers(HttpMethod.GET,"/api/v1/posts").permitAll()
//                .requestMatchers(HttpMethod.GET,"/api/v1/chat/**").authenticated()
//                .requestMatchers("/api/v1/users/**").authenticated()
                .anyRequest().authenticated()
                .and()

                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        ;
//                .requestMatchers("/swagger-ui/**", "/api/v1/users/register", "/api/v1/users/login").permitAll();
        //      .addFilterBefore(new JwtTokenFilter(userService, secretKey), UsernamePasswordAuthenticationFilter.class)
        // UserNamePasswordAuthenticationFilter적용하기 전에 JWTTokenFilter를 적용 하라는 뜻 입니다.

        return httpSecurity.build();
    }
}