package com.app.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ConfigSeguridad {
      private final FiltroAutenticacionJwt filtroAutenticacionJwt;
      private final AuthenticationProvider autenticacionProvider;

      @Bean
      public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http.csrf(AbstractHttpConfigurer::disable)
                  .cors(cors -> cors.configure(http))
                  .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/**").permitAll()
                        .requestMatchers("/api/sistema/autenticacion/**").permitAll()
                        .requestMatchers("/api/sistema/registro/**").permitAll()
                        .requestMatchers("/api/tramites/**").permitAll()
                        .requestMatchers("/api/ai/**").permitAll()
                        .anyRequest().permitAll())
                  .sessionManagement(session -> session
                                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                  .authenticationProvider(autenticacionProvider)
                  .addFilterBefore(filtroAutenticacionJwt, UsernamePasswordAuthenticationFilter.class);

            return http.build();
      }
}
