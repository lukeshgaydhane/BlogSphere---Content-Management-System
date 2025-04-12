package com.blog.BlogSphere.Configuration;

import com.blog.BlogSphere.Users.CustomUserDetailsService;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/api/**").permitAll() // Ensures all GET requests under "/api/**" are accessible without authentication
                        .requestMatchers("/api/auth/**").permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(httpBasic -> {});
        return http.build();
    }


//    @Bean
//    protected UserDetailsService userDetailsService() {
//        UserDetails user = User.builder().username("pankaj").password(passwordEncoder()
//                .encode("password")).roles("USER").build();
//        UserDetails admin = User.builder().username("admin").password(passwordEncoder()
//                .encode("admin")).roles("ADMIN").build();
//        return new InMemoryUserDetailsManager(ramesh, admin);
//    }

}
//
//
///*
//Key Updates:
//Replaced WebSecurityConfiguration: Removed the extends WebSecurityConfiguration as it is no longer necessary with the new Spring Security structure.
//
//Replaced authorizeRequests(): Switched to authorizeHttpRequests() for defining authorization rules.
//
//Used lambda-style configuration: This is the modern approach for configuring Spring Security.
//
//Returning SecurityFilterChain: Added the SecurityFilterChain bean which is the recommended way to configure Spring Security.
//
//This updated code is aligned with the latest Spring Security practices.
// */
//
///*
//Changes:
//Replaced httpBasic() with httpBasic(Customizer):
//
//Used the lambda-style httpBasic(httpBasic -> httpBasic.realmName("YourRealmName")) to specify the configuration.
//
//Set Realm Name:
//
//Customization allows you to set the realm name or additional options as needed for your application.
//
//Notes:
//Using httpBasic(Customizer) is the recommended way to configure Basic authentication in Spring Security 6.1+.
//
//If you plan to move beyond basic authentication, consider implementing token-based authentication like OAuth2 or JWT for a more secure setup.
// */
