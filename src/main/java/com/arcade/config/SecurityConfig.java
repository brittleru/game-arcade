package com.arcade.config;

import com.arcade.service.user.UserService;
import com.arcade.service.user.UserServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // might delete the user service autowired
//    private UserService userService;
    private DataSource dataSource;
//    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Autowired
    public SecurityConfig(
            DataSource dataSource
//            CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler
//            UserService userService
    ) {
        this.dataSource = dataSource;
//        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
//        this.userService = userService;
    }

    @Bean UserService userService() {
        return new UserServiceImplementation();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService());    //set the custom user details service
        auth.setPasswordEncoder(passwordEncoder()); //set the password encoder - bcrypt
        return auth;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

//        auth.authenticationProvider(authenticationProvider);
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("select username, password, true from user where username=?")
                .authoritiesByUsernameQuery("select user.username, user.id, role.id, users_roles.user_id, users_roles.role_id from user, role, users_roles " +
                        "where user.id=users_roles.user_id and role.id=users_roles.role_id and user.username=?");
//        auth.jdbcAuthentication().dataSource(dataSource);
        auth.authenticationProvider(authenticationProvider());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/games/**").hasAnyAuthority("NORMAL", "ADMIN", "SUPERUSER")
                .antMatchers("/profile/**").hasAnyAuthority("NORMAL", "ADMIN", "SUPERUSER")
                .antMatchers("/resources/**").permitAll()
                .and()
                .formLogin()
                    .loginPage("/login")
                    .loginProcessingUrl("/authenticate-user")
//                    .successHandler(customAuthenticationSuccessHandler)
                    .permitAll()
                .and()
                .logout().permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/access-denied");
    }
}
