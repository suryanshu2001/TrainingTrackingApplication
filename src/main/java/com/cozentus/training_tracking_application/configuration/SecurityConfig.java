package com.cozentus.training_tracking_application.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cozentus.training_tracking_application.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private UserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;
    
    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authz -> authz
            		.requestMatchers("/authenticate", "/register","/user/**").permitAll()
            		
            		// Admin end-points with role "ADMIN" for all methods
                    .requestMatchers(HttpMethod.GET, "/admin/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.POST, "/admin/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/admin/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PATCH, "/admin/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/admin/**").hasRole("ADMIN")
                    
                    // Teacher end-points with role "ADMIN" for all methods
                    .requestMatchers(HttpMethod.GET, "/teacher/**").hasRole("teacher")
                    .requestMatchers(HttpMethod.POST, "/teacher/**").hasRole("teacher")
                    .requestMatchers(HttpMethod.PUT, "/teacher/**").hasRole("teacher")
                    .requestMatchers(HttpMethod.PATCH, "/teacher/**").hasRole("teacher")
                    .requestMatchers(HttpMethod.DELETE, "/teacher/**").hasRole("teacher")
                    
                 // Admin end-points with role "ADMIN" for all methods
                    .requestMatchers(HttpMethod.GET, "/batchProgramCourse/**").authenticated()
                    .requestMatchers(HttpMethod.POST, "/batchProgramCourse/**").authenticated()
                    .requestMatchers(HttpMethod.PUT, "/batchProgramCourse/**").authenticated()
                    .requestMatchers(HttpMethod.PATCH, "/batchProgramCourse/**").authenticated()
                    .requestMatchers(HttpMethod.DELETE, "/batchProgramCourse/**").authenticated()
                    
                    
                    .requestMatchers(HttpMethod.GET, "/attendance/**").authenticated()
                    .requestMatchers(HttpMethod.POST, "/attendance/**").authenticated()
                    .requestMatchers(HttpMethod.PUT, "/attendance/**").authenticated()
                    .requestMatchers(HttpMethod.PATCH, "/attendance/**").authenticated()
                    .requestMatchers(HttpMethod.DELETE, "/attendance/**").authenticated()
                    
                    
                    .requestMatchers(HttpMethod.GET, "/attendance-student/**").authenticated()
                    .requestMatchers(HttpMethod.POST, "/attendance-student/**").authenticated()
                    .requestMatchers(HttpMethod.PUT, "/attendance-student/**").authenticated()
                    .requestMatchers(HttpMethod.PATCH, "/attendance-student/**").authenticated()
                    .requestMatchers(HttpMethod.DELETE, "/attendance-student/**").authenticated()
                    
                    
                    
                    .requestMatchers(HttpMethod.GET, "/batches/**").authenticated()
                    .requestMatchers(HttpMethod.POST, "/batches/**").authenticated()
                    .requestMatchers(HttpMethod.PUT, "/batches/**").authenticated()
                    .requestMatchers(HttpMethod.PATCH, "/batches/**").authenticated()
                    .requestMatchers(HttpMethod.DELETE, "/batches/**").authenticated()
                    
                    
                    .requestMatchers(HttpMethod.GET, "/batchProgramCourseTopics/**").authenticated()
                    .requestMatchers(HttpMethod.POST, "/batchProgramCourseTopics/**").authenticated()
                    .requestMatchers(HttpMethod.PUT, "/batchProgramCourseTopics/**").authenticated()
                    .requestMatchers(HttpMethod.PATCH, "/batchProgramCourseTopics/**").authenticated()
                    .requestMatchers(HttpMethod.DELETE, "/batchProgramCourseTopics/**").authenticated()
                    
                    
                    
                    .requestMatchers(HttpMethod.GET, "/courses/**").authenticated()
                    .requestMatchers(HttpMethod.POST, "/courses/**").authenticated()
                    .requestMatchers(HttpMethod.PUT, "/courses/**").authenticated()
                    .requestMatchers(HttpMethod.PATCH, "/courses/**").authenticated()
                    .requestMatchers(HttpMethod.DELETE, "/courses/**").authenticated()
                    
                    
                    
                    .requestMatchers(HttpMethod.GET, "/daily-topic-coverages/**").authenticated()
                    .requestMatchers(HttpMethod.POST, "/daily-topic-coverages/**").authenticated()
                    .requestMatchers(HttpMethod.PUT, "/daily-topic-coverages/**").authenticated()
                    .requestMatchers(HttpMethod.PATCH, "/daily-topic-coverages/**").authenticated()
                    .requestMatchers(HttpMethod.DELETE, "/daily-topic-coverages/**").authenticated()
                    
                    
                    
                    .requestMatchers(HttpMethod.GET, "/programs/**").authenticated()
                    .requestMatchers(HttpMethod.POST, "/programs/**").authenticated()
                    .requestMatchers(HttpMethod.PUT, "/programs/**").authenticated()
                    .requestMatchers(HttpMethod.PATCH, "/programs/**").authenticated()
                    .requestMatchers(HttpMethod.DELETE, "/programs/**").authenticated()
                    
                    
                    .requestMatchers(HttpMethod.GET, "/students/**").authenticated()
                    .requestMatchers(HttpMethod.POST, "/students/**").authenticated()
                    .requestMatchers(HttpMethod.PUT, "/students/**").authenticated()
                    .requestMatchers(HttpMethod.PATCH, "/students/**").authenticated()
                    .requestMatchers(HttpMethod.DELETE, "/students/**").authenticated()
                    
                    
                    .requestMatchers(HttpMethod.GET, "/teachers/**").authenticated()
                    .requestMatchers(HttpMethod.POST, "/teachers/**").authenticated()
                    .requestMatchers(HttpMethod.PUT, "/teachers/**").authenticated()
                    .requestMatchers(HttpMethod.PATCH, "/teachers/**").authenticated()
                    .requestMatchers(HttpMethod.DELETE, "/teachers/**").authenticated()
                    
                    
                    .requestMatchers(HttpMethod.GET, "/topics/**").authenticated()
                    .requestMatchers(HttpMethod.POST, "/topics/**").authenticated()
                    .requestMatchers(HttpMethod.PUT, "/topics/**").authenticated()
                    .requestMatchers(HttpMethod.PATCH, "/topics/**").authenticated()
                    .requestMatchers(HttpMethod.DELETE, "/topics/**").authenticated()
                    
                    .requestMatchers(HttpMethod.GET, "/exams/**").authenticated()
                    .requestMatchers(HttpMethod.POST, "/exams/**").authenticated()
                    .requestMatchers(HttpMethod.PUT, "/exams/**").authenticated()
                    .requestMatchers(HttpMethod.PATCH, "/exams/**").authenticated()
                    .requestMatchers(HttpMethod.DELETE, "/exams/**").authenticated()
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    

                    
            )
            .exceptionHandling(exceptions -> exceptions
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            );

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
}
