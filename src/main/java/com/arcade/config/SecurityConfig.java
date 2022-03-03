package com.arcade.config;

import com.arcade.dao.user.RoleDao;
import com.arcade.dao.user.UserDao;
import com.arcade.entity.user.User;
import com.arcade.service.user.UserService;
import com.arcade.service.user.UserServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private DataSource dataSource;
    private UserDao userDao;
    private RoleDao roleDao;

    @Autowired
    public SecurityConfig(DataSource dataSource, UserDao userDao, RoleDao roleDao) {
        this.dataSource = dataSource;
        this.userDao = userDao;
        this.roleDao = roleDao;
    }

    @Bean
    public UserService userService() {
        return new UserServiceImplementation(userDao, roleDao, passwordEncoder());
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler(userService());
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new CustomLogoutSuccessHandler();
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
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("select username, password, true from user where username=?")
                .authoritiesByUsernameQuery("select user.username, user.id, role.id, users_roles.user_id, users_roles.role_id from user, role, users_roles " +
                        "where user.id=users_roles.user_id and role.id=users_roles.role_id and user.username=?");
        auth.authenticationProvider(authenticationProvider());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // TODO: only certain people can use the POST, PUT and DELETE api
        http
            .csrf()
                .ignoringAntMatchers("/api/**")
            .and()
            .authorizeRequests()
                .antMatchers("/games/**").hasAnyAuthority("ROLE_NORMAL", "ROLE_ADMIN", "ROLE_SUPERUSER")
                .antMatchers("/profile/**").hasAnyAuthority("ROLE_NORMAL", "ROLE_ADMIN", "ROLE_SUPERUSER")
//                .antMatchers(HttpMethod.POST, "/api/**").authenticated()
//                .antMatchers(HttpMethod.PUT, "/api/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_SUPERUSER")
//                .antMatchers(HttpMethod.DELETE, "/api/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_SUPERUSER")
                .antMatchers("/resources/**").permitAll()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                    .loginPage("/login")
                    .loginProcessingUrl("/authenticate-user")
                    .successHandler(authenticationSuccessHandler())
                    .permitAll()
                .and()
                .logout()
                    .logoutUrl("/logout-user")
                    .logoutSuccessHandler(logoutSuccessHandler())
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
//                    .clearAuthentication(true)
                    .permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/access-denied");
    }
}
