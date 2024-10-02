//package com.demoweb.config;
//
//import com.demoweb.security.DemoWebPasswordEncoder;
//import com.demoweb.security.DemoWebUserDetailsService;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class yoona_SecurityConfiguration {
//
//    // 인증을 제어하는 도구 (Filter 기반으로 만들어짐)
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//        http
//                .csrf(AbstractHttpConfigurer::disable) // csrf 미사용 설정
//                .authorizeHttpRequests((authorize) -> authorize
//                        .requestMatchers("/board/*write*", "/board/*edit*", "/board/*delete*").authenticated() // authenticated? 로그인한 사용자만 허용 설정
//                        .requestMatchers("/admin/**").hasRole("ADMIN") // hasRole? 권한을 기반으로해서 허용
//                        .anyRequest().permitAll()) // 그 외 모든 요청은 인증을 필요설정  authorize? 권한체크 정보.
//                .sessionManagement(session -> session
//                        .maximumSessions(2) // 동시 세션 제어
////                        .maxSessionsPreventsLogin(true) // 동시 로그인 차단
//                )
//                .httpBasic(AbstractHttpConfigurer::disable)
//                .formLogin((login) -> login
//                 // 단, Parameter 설정을 안할 경우에는 - 약속<규격>된대로 input-name 속성을 => id는 'username' / pw는 'password' 로 설정해줘야함(action? /login 으로.)
//                        .loginPage("/account/login") // <spring-security가 가지고 있는 몬생긴 form말고, 이쪽 화면에서 처리할게 라는 커스텀 설정>
//                        .usernameParameter("memberId") // <input name="유저Id 커스텀 설정">
//                        .passwordParameter("passwd") // <input name="유저Pw 커스텀 설정">
////                        .successHandler(loginSuccessHandler())  // 핸들러 생성해야함
//                        .loginProcessingUrl("/account/process-login")) // security가 로그인을 처리하는 경로를 지정. 로그인 form/action이랑 맞추기만 하면됨. => 경로는 뭘 쓰던 상관없음
////                        .failureForwardUrl()) // 로그인 실패 시 보낼 경로
//                .logout((logout) -> logout
//                        .logoutUrl("/account/logout") // <form action="경로 커스텀 설정">
//                        .invalidateHttpSession(true) // 로그아웃시 로그인 했던 모든 정보를 삭제
//                        .deleteCookies("JSESSIONID") // 로그아웃시 JSESSIONID 쿠키 삭제 톰캣이 만든 세션Id 이름 = JSESSIONID
//                        .logoutSuccessUrl("/home")); // 로그아웃시 리다이렉트할 URL
//        return http.build();
//    }
//
//    // 3. Custom Encoder
//    @Bean PasswordEncoder passwordEncoder() {
//        return new DemoWebPasswordEncoder();
//    }
//
//    // 3. UserDetails(Dto역할) + Service + PasswordEncoder 클래스 생성하여 직접 커스텀함. = Details가 중요!
//    @Bean
//    public UserDetailsService userDetailsService() {
//        return new DemoWebUserDetailsService();
//    }
//
//}
//
//
//
