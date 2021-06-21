package com.ecommerce.lifeshop.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class BasicSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService service;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(service);
        
        //auth.inMemoryAuthentication().withUser("admin").password(passwordEncoder().encode("admin")).authorities("ROLE_ADMIN");
        //Usar para logar o primeiro usuário como administrador passando essa role para ele e dar inicio ao sistema
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/lifeshop").permitAll()
                .antMatchers("/carrinho/**").permitAll()
        		.antMatchers("/item/**").permitAll()
                .antMatchers(HttpMethod.POST, "/usuario/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/usuario/**").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.GET, "/produto/**").permitAll()
                .antMatchers(HttpMethod.GET,"/usuario/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/usuario/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/produto/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/produto/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/produto/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and().httpBasic()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                //.and().formLogin().loginPage("/login").failureUrl("/login?error").permitAll()
                .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login")
                .and().cors()
                .and().csrf().disable();
        
  
               
    }

}
