package com.example.ShopNow.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration

public class SecurityConfig {

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager theUserDetailsManager = new JdbcUserDetailsManager(dataSource) ;

        theUserDetailsManager.setUsersByUsernameQuery("select username, password, enabled from users where username=?");
        theUserDetailsManager.setAuthoritiesByUsernameQuery("select username, authority from authorities where username=?");

        return theUserDetailsManager;
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(configurer ->
                        configurer
                                .requestMatchers("/").permitAll()
                                .requestMatchers(HttpMethod.GET,"/api/product/get/**").permitAll()
                                .requestMatchers(HttpMethod.GET,"/api/product/get/products/all").permitAll()
                                .requestMatchers(HttpMethod.GET,"/api/product/getStartupData").permitAll()
                                .requestMatchers(HttpMethod.POST,"/api/product/**").hasAnyRole("ADMIN","USER")
                                .requestMatchers(HttpMethod.PUT,"/api/product/**").hasAnyRole("ADMIN","USER")
                                .requestMatchers(HttpMethod.DELETE,"/api/product/**").hasAnyRole("ADMIN","USER")
                                .requestMatchers(HttpMethod.GET,"/api/product/search").permitAll()

                                .requestMatchers(HttpMethod.GET,"/api/user/get/**").permitAll()
                                .requestMatchers(HttpMethod.POST,"/api/user/create").permitAll()
                                .requestMatchers(HttpMethod.POST,"/api/user/login").permitAll()
                                .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
                                .requestMatchers("/user/**").hasRole("ADMIN")
                                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET,"/api/user/**").permitAll()

                                .requestMatchers("/api/transaction/**").hasAnyRole("User","ADMIN")

                                .anyRequest().authenticated()
                );
      http.httpBasic(Customizer.withDefaults());
        http.csrf(csrf -> csrf.disable());
        return http.build();
    }
}
