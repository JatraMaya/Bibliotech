package com.jatramaya.bibliotech.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

import com.jatramaya.bibliotech.entity.user.Role;
import com.jatramaya.bibliotech.exception.CustomAccessDeniedHandler;
import com.jatramaya.bibliotech.exception.CustomAuthenticationEntryPoint;
import com.jatramaya.bibliotech.exception.CustomLogoutHandler;
import com.jatramaya.bibliotech.filter.JWTFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Autowired
        private CorsConfigurationSource configurationSource;

        @Autowired
        private CustomAccessDeniedHandler accessDeniedHandler;

        @Autowired
        private CustomAuthenticationEntryPoint authenticationEntryPoint;

        @Autowired
        private CustomLogoutHandler customLogoutHandler;

        @Autowired
        private JWTFilter jwtFilter;

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

                http
                                .csrf(csrf -> csrf.disable())
                                .httpBasic(authBasic -> authBasic.disable())
                                .formLogin(form -> form.disable())
                                .cors(cors -> cors.configurationSource(configurationSource))
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/", "/health", "/error").permitAll()
                                                .requestMatchers(HttpMethod.POST, "/auth/login",
                                                                "/auth/register",
                                                                "/auth/password")
                                                .permitAll()
                                                .requestMatchers("/admin/**").hasRole(Role.ADMIN.name())
                                                .anyRequest().authenticated())
                                .logout(logout -> logout
                                                .logoutUrl("/api/v1/auth/logout")
                                                .logoutSuccessHandler(customLogoutHandler)
                                                .invalidateHttpSession(true)
                                                .deleteCookies("JSESSIONID"))
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .exceptionHandling(ex -> ex
                                                .accessDeniedHandler(accessDeniedHandler)
                                                .authenticationEntryPoint(authenticationEntryPoint))
                                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
                return http.build();

        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
                return config.getAuthenticationManager();
        }

}