package com.springsecurity.first.project.spring.security.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.springsecurity.first.project.spring.security.security.ApplicationUserRole.*;
import static com.springsecurity.first.project.spring.security.security.ApplicationUserPermission.*;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails annasmith = User.builder()
                                .username("annasmith")
                                .password(this.passwordEncoder.encode("password"))
//                                .roles(STUDENT.name()) //ROLE_STUDENT
                                .authorities(ADMINTREE.getGrantedAuthorities())
                                .build();

        UserDetails lindaUser = User.builder()
                                    .username("linda")
                                    .password(this.passwordEncoder.encode("password123"))
//                                    .roles(ADMIN.name())
                                    .authorities(ADMIN.getGrantedAuthorities())
                                    .build();

        UserDetails tomUser = User.builder()
                                    .username("tom")
                                    .password(this.passwordEncoder.encode("password456"))
//                                    .roles(ADMINTREE.name())
                                    .authorities(ADMINTREE.getGrantedAuthorities())
                                    .build();

        return new InMemoryUserDetailsManager(
                annasmith,
                lindaUser,
                tomUser
        );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "index", "/css/*", "/js/*").permitAll()
                .antMatchers("/api/**").hasAnyRole(ADMIN.name())
                .antMatchers(HttpMethod.DELETE,"/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
                .antMatchers(HttpMethod.PUT,"/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
                .antMatchers(HttpMethod.POST,"/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
                .antMatchers(HttpMethod.GET,"/management/api/**").hasAnyRole(ADMIN.name(), ADMINTREE.name())
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .defaultSuccessUrl("/courses", true);
    }
}
