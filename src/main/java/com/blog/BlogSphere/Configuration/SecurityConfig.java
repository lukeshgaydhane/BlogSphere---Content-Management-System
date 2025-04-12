package com.blog.BlogSphere.Configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated()
                )
                .httpBasic(httpBasic -> httpBasic.realmName("BlogSphere"));
        return http.build();
    }
}


/*
Key Updates:
Replaced WebSecurityConfiguration: Removed the extends WebSecurityConfiguration as it is no longer necessary with the new Spring Security structure.

Replaced authorizeRequests(): Switched to authorizeHttpRequests() for defining authorization rules.

Used lambda-style configuration: This is the modern approach for configuring Spring Security.

Returning SecurityFilterChain: Added the SecurityFilterChain bean which is the recommended way to configure Spring Security.

This updated code is aligned with the latest Spring Security practices.
 */

/*
Changes:
Replaced httpBasic() with httpBasic(Customizer):

Used the lambda-style httpBasic(httpBasic -> httpBasic.realmName("YourRealmName")) to specify the configuration.

Set Realm Name:

Customization allows you to set the realm name or additional options as needed for your application.

Notes:
Using httpBasic(Customizer) is the recommended way to configure Basic authentication in Spring Security 6.1+.

If you plan to move beyond basic authentication, consider implementing token-based authentication like OAuth2 or JWT for a more secure setup.
 */
