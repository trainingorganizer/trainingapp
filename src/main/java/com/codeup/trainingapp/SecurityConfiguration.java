package com.codeup.trainingapp;

import com.codeup.trainingapp.Services.UserDetailsLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private UserDetailsLoader usersLoader;

    public SecurityConfiguration(UserDetailsLoader usersLoader) {
        this.usersLoader = usersLoader;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(usersLoader) // How to find users by their username
                .passwordEncoder(passwordEncoder()) // How to encode and verify passwords
        ;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                /* Login configuration */
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/profile") // user's home page, it can be any URL
                .permitAll() // Anyone can go to the login page
                /* Logout configuration */
                .and()
                .logout()
                .logoutSuccessUrl("/login?logout") // append a query string value
                /* Pages that can be viewed without having to log in */
                .and()
                .authorizeRequests()
                .antMatchers("/") // anyone can see the home and the ads pages
                .permitAll()
                /* Pages that require athentication */
                .and()
                .authorizeRequests()
                .antMatchers(
                        "/coordinator",
                        "/newCourse",
                        "/makeInstructor",
                        "/profile",
                        "/instructor/courses",
                        "/instructor/course/{course_id}",
                        "/instructor/course/{course_id}/edit",
                        "/instructor/curricula",
                        "/instructor/curriculum/{curriculum_id}",
                        "/instructor/course/add-grade/{gradable_id}",
                        "/instructor/curriculum/create",
                        "/curriculum/create_grade",
                        "/instructor/course/{course_id}/attendance_form",
                        "/submit_attendance",
                        "/student",
                        "/courses",
                        "/provider/{name}",
                        "/instructor/curriculum/{curriculum_id}/create_grade"
                        // only authenticated users can edit ads
                )
                .authenticated()
        ;
    }
}
