package com.demoweb.config;

import com.demoweb.security.DemoWebPasswordEncoder;
import com.demoweb.security.DemoWebUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    // 인증을 제어하는 도구 (Filter 기반으로 만들어짐)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 인증, 접근권한 설정 영역 = http. ~
//        1-1.
//        http
//             .csrf(AbstractHttpConfigurer::disable) // csrf를 사용하지 않겠다는 설정 = csrf? 크로스 사이트 요청 위조. 피싱 공격의 일종
//             .authorizeHttpRequests((authorize) -> authorize
//                                                       .requestMatchers("/account/**").permitAll() // 어카운트 아래에 있는 애들에 대해서는 허용하겠다.
//                                                       .requestMatchers("/", "/home").permitAll() // home은 허용하겠다
//                                                       .requestMatchers("/image/**", "/styles/**").permitAll()// css 얘네도 허용하겠다. (얘네도 허용을 해줘야함)
//                                                       .anyRequest().authenticated()) // 그 외 모든 요청은 인증을 필요설정  authorize? 권한체크 정보.
//                                                    .httpBasic(AbstractHttpConfigurer::disable)
////                                                    .formLogin(Customizer.withDefaults()); // 브라우저에 원래 있는 못생긴 화면 테스트 급할 때 아니면 안씀
//                                                    .formLogin((formlogin)-> formlogin // 일반적으로 사용하는 id, pw 입력받아서 form 로그인하는 행동
//                                                    .loginPage("/account/login")); // 로그인이 필요한 곳에서, 미 로그인 시 여기로 보내라

//        1-2.
//        http
//                .csrf(AbstractHttpConfigurer::disable) // csrf 미사용 설정
//                .authorizeHttpRequests((authorize) -> authorize
//                        .requestMatchers("/board/*write*", "/board/*edit*", "/board/*delete*").authenticated() // authenticated? 로그인한 사용자만 허용 설정
//                        .requestMatchers("/admin/**").hasRole("ADMIN") // hasRole? 권한을 기반 허용 / 해당 페이지는. ADMIN 권한이 있어야만 들어올 수 있다.
//                        .anyRequest().permitAll()) // 그 외 모든 요청은 인증을 필요설정  authorize? 권한체크 정보.
//                        .httpBasic(AbstractHttpConfigurer::disable)
//                        .formLogin((formlogin)-> formlogin
//                        .loginPage("/account/login")); // 로그인이 필요한 곳에서, 미 로그인 시 여기로 보내라

//        2 - 1.
//        http
//                .csrf(AbstractHttpConfigurer::disable) // csrf 미사용 설정
//                .authorizeHttpRequests((authorize) -> authorize
//                        .requestMatchers("/board/*write*", "/board/*edit*", "/board/*delete*").authenticated() // authenticated? 로그인한 사용자만 허용 설정
//                        .requestMatchers("/admin/**").hasRole("ADMIN") // hasRole? 권한을 기반으로해서 허용
//                        .anyRequest().permitAll()) // 그 외 모든 요청은 인증을 필요설정  authorize? 권한체크 정보.
//                .httpBasic(Customizer.withDefaults())
//                .formLogin(AbstractHttpConfigurer::disable); // 일반적으로 사용하는 id, pw 입력받아서 로그인하는 행동

//        2 - 2.
//        http
//                .csrf(AbstractHttpConfigurer::disable) // csrf 미사용 설정
//                .authorizeHttpRequests((authorize) -> authorize
//                        .requestMatchers("/board/*write*", "/board/*edit*", "/board/*delete*").authenticated() // authenticated? 로그인한 사용자만 허용 설정
//                        .requestMatchers("/admin/**").hasRole("ADMIN") // hasRole? 권한을 기반으로해서 허용
//                        .anyRequest().permitAll()) // 그 외 모든 요청은 인증을 필요설정  authorize? 권한체크 정보.
//                .httpBasic(AbstractHttpConfigurer::disable)
//                .formLogin(Customizer.withDefaults()); // 일반적으로 사용하는 id, pw 입력받아서 로그인하는 행동

//        3. 이게 최종버전 앞으로 이 방식으로 쓰면됨
        http
                .csrf(AbstractHttpConfigurer::disable) // csrf 미사용 설정
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/board/*write*", "/board/*edit*", "/board/*delete*").authenticated() // authenticated? 로그인한 사용자만 허용 설정
                        .requestMatchers("/admin/**").hasRole("ADMIN") // hasRole? 권한을 기반으로해서 허용
                        .anyRequest().permitAll()) // 그 외 모든 요청은 인증을 필요설정  authorize? 권한체크 정보.
//                .sessionManagement()
//                .maximumSessions(1) // 동시 세션 제어

                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin((login) -> login
                 // 단, Parameter 설정을 안할 경우에는 - 약속<규격>된대로 input-name 속성을 => id는 'username' / pw는 'password' 로 설정해줘야함(action? /login 으로.)
                        .loginPage("/account/login") // <spring-security가 가지고 있는 몬생긴 form말고, 이쪽 화면에서 처리할게 라는 커스텀 설정>
                        .usernameParameter("memberId") // <input name="유저Id 커스텀 설정">
                        .passwordParameter("passwd") // <input name="유저Pw 커스텀 설정">
//                        .successHandler(loginSuccessHandler())  // 로그인 성공 후 핸들러
//                        .failureHandler(loginFailureHandler())  // 로그인 실패 후 핸들러
                        .loginProcessingUrl("/account/process-login")) // security가 로그인을 처리하는 경로를 지정. 로그인 form/action이랑 맞추기만 하면됨. => 경로는 뭘 쓰던 상관없음
//                        .failureForwardUrl()) // 로그인 실패 시 보낼 경로
                .logout((logout) -> logout
                        .logoutUrl("/account/logout") // <form action="경로 커스텀 설정">
                        .invalidateHttpSession(true) // 로그아웃시 로그인 했던 모든 정보를 삭제
                        .deleteCookies("JSESSIONID") // 로그아웃시 JSESSIONID 쿠키 삭제 톰캣이 만든 세션Id 이름 = JSESSIONID
                        .logoutSuccessUrl("/home")); // 로그아웃시 리다이렉트할 URL
        return http.build();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    // 사용자 관리 영역 = USerDetailService

//    for 1, 2
//    @Bean
//    PasswordEncoder passwordEncoder(){ // Spring Security가 기본적으로 사용하는 passwordEncoder 해시알고리즘을 이용하기 때문에 복원 불가함.
//        return new BCryptPasswordEncoder();
//    }


    // 2 - 1. securityFilterChain()의 2-1, 2-2와 연결되는 테스트  => 하드 코딩 방식 테스트용에나 쓰지 실제로 안씀
//    @Bean
//    public UserDetailsService userDetailsService() {
////        System.out.println(passwordEncoder().encode("Pa$$w0rd"));
//        InMemoryUserDetailsManager userDetailService = new InMemoryUserDetailsManager(); // 사용자 정보를 메모리에 두겠다
//        userDetailService.createUser(User // 사용자 등록
//                .withUsername("inmemoryuser")
////                .password("{noop}Pa$$w0rd") // Security는 기본적으로 암호화 처리가 되어있음. 따라서 암호화 되지 않은 경우 {noop} 를 붙여줘야 사용 가능함.
//                .password(passwordEncoder() .encode("Pa$$w0rd"))
//                .roles("USER") // ROLE_USER 를 만들어라.
//                .build());
//        userDetailService.createUser(User // 사용자 등록
//                .withUsername("inmemoryadmin")
////                .password("{noop}Pa$$w0rd") // Security는 기본적으로 암호화 처리가 되어있음. 따라서 암호화 되지 않은 경우 {noop} 를 붙여줘야 사용 가능함.
//                .password(passwordEncoder() .encode("Pa$$w0rd"))
//                .roles("ADMIN") // ROLE_ADMIN 를 만들어라.
//                .build());
//        return userDetailService;
//    }

    // 2 - 2. securityFilterChain()의 2-1, 2-2와 연결되는 테스트  => 실제로 안씀
//    @Bean
//    public UserDetailsService userDetailsService(DataSource dataSource) { // JdbcUserDetailsManager? DB에 저장함
//        return new JdbcUserDetailsManager(dataSource); // 미리 정해진 테이블(users, authorities), SQL을 사용해서 인증처리
//
//    }

    // 2 - 3. securityFilterChain()의 2-1, 2-2와 연결되는 테스트
//    @Bean
//    public UserDetailsService userDetailsService(DataSource dataSource) { // JdbcUserDetailsManager? DB에 저장함
//
//        JdbcUserDetailsManager userDetailsService = new JdbcUserDetailsManager(dataSource); // 미리 정해진 테이블, SQL을 사용해서 인증처리
//
//        // 사용자 정의 테이블을 사용하기 위해 로그인, 권한 검사에 사용할 Query 지정 (약속된 테이블이 아니라 커스텀이라 코드+쿼리문으로 알려 줘야함)
//        userDetailsService.setUsersByUsernameQuery("select email,password,enabled "
//                                                + "from custom_users "
//                                                + "where email = ?");
//        userDetailsService.setAuthoritiesByUsernameQuery("select email, authority " +
//                                                        "from custom_authorities " +
//                                                        "where email = ?");
//        return userDetailsService;
//    }

    // 3. Custom Encoder
    @Bean PasswordEncoder passwordEncoder() {
        return new DemoWebPasswordEncoder();
    }
    // 3. UserDetails(Dto역할) + Service + PasswordEncoder 클래스 생성하여 직접 커스텀함. = Details가 중요!
    @Bean
    public UserDetailsService userDetailsService() {
        return new DemoWebUserDetailsService();
    }


}



