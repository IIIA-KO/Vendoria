package com.vendoria.security.config;

import com.vendoria.security.filter.SignInRequestFilter;
import com.vendoria.security.handler.CustomLogoutSuccessHandler;
import com.vendoria.security.handler.SignInSuccessfulHandler;
import com.vendoria.security.service.CustomUserDetailsService;
import com.vendoria.user.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Value("${security.app.authentication.cookie}")
    private String USER_CREDENTIALS_COOKIE;

    private final SignInSuccessfulHandler signInSuccessfulHandler;

    private final CustomLogoutSuccessHandler customLogoutSuccessHandler;

    private final SignInRequestFilter signInRequestFilter;

    /*@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/").authenticated()
                        .requestMatchers(HttpMethod.DELETE).hasAuthority(Role.ADMIN.toString())
                        .anyRequest().permitAll()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(login -> login
                        .loginPage("/signin")
                        .successHandler(signInSuccessfulHandler))
                .logout(logout -> logout
                        .deleteCookies(USER_CREDENTIALS_COOKIE))
                .addFilterBefore(signInRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }*/

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/signin", "/register", "/products").permitAll()
                        .requestMatchers("/admin/**").hasRole(Role.ADMIN.name())
                        .requestMatchers("/cart").hasRole(Role.USER.name())
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/signin")
                        .successHandler(signInSuccessfulHandler))
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessHandler(customLogoutSuccessHandler)
                        .deleteCookies(USER_CREDENTIALS_COOKIE)
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .permitAll()
                )
                .addFilterBefore(signInRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
