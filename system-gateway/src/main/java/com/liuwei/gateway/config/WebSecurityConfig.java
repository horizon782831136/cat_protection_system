package com.liuwei.gateway.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebSecurity
@Slf4j
@RequiredArgsConstructor
public class WebSecurityConfig{
    private final SecurityUserDetailService securityUserDetailService;
    private final AuthorizationManager authorizationManager;
    private final String[] path = {
            "/oauth/**",
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/v2/**",
            "/doc.html",
            "/actuator/**",
            "/favicon.ico",
            "/css/**",
            "/js/**",
            "/img/**",
            "/fonts/**"
    };

   @Bean
   public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {

       http.authorizeExchange(
               exchange -> exchange
               .pathMatchers(path).permitAll()
               .pathMatchers(HttpMethod.OPTIONS).permitAll()
               .anyExchange().access(authorizationManager)
       ).httpBasic()
               .and()
               .formLogin().loginPage("/login");
       return http.build();

   }
}

