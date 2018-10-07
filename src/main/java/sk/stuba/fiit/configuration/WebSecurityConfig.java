package sk.stuba.fiit.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import sk.stuba.fiit.service.NASUserDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http//
                .authorizeRequests()//
                .antMatchers("/webjars/**").permitAll()//
                .antMatchers("/css/**").permitAll()//
                .anyRequest().authenticated()//
                .and()//
                .formLogin()//
                .loginPage("/login")//
                .permitAll()//
                .and()//
                .logout()//
                .permitAll();//
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return new NASUserDetailsService();
    }
}
