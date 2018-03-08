package com.decipher.usermanage.config.springsecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by decipher16 on 3/3/17.
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
//        .antMatchers("/resources/**").permitAll()
               .antMatchers("/admin*").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/dba*").access("hashRole('ROLE_DBA')")
                .antMatchers("/user*").access("hasRole('ROLE_USER')")
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
        .and()
        .formLogin().loginPage("/login").failureUrl("/login?error")
        .usernameParameter("email").passwordParameter("password")
        .defaultSuccessUrl("/success").loginProcessingUrl("/processLogin")
        .and()
        .logout().logoutSuccessUrl("/login?logout")
        .and()
        .exceptionHandling().accessDeniedPage("/403")
        .and()
        .csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }
}
