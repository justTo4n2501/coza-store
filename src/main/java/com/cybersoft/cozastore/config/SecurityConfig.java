package com.cybersoft.cozastore.config;

import com.cybersoft.cozastore.filter.JWTFilter;
import com.cybersoft.cozastore.provider.CustomAuthenManagerProvider;
import com.cybersoft.cozastore.service.CustomUserServiceDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    CustomAuthenManagerProvider customAuthenManagerProvider;
    @Autowired
    JWTFilter jwtFilter;


    @Bean
    public PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }
//  Tao AuthenticationManager de lay thong tin chung thuc
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception{

        CustomUserServiceDetail customUserDetailService = new CustomUserServiceDetail();

        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(customAuthenManagerProvider)
                .build();

    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
//                Cho phép cấu hình chứng thực cho các request
                .authorizeHttpRequests()
//                   Quy định chứng thực cho link chỉ định
                .antMatchers("/login/**").permitAll()

//                   Tất cả các link còn lại đều phải chứng thực
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }

}