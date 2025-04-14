package com.backend.core.security.config;

import com.backend.core.security.service.UserDetailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
//@RequiredArgsConstructor
public class SecurityConfig {

    //private final AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults()) //para consumir por usuario y contraseña
                .sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // valida no en memoria sino con la vida del token
                .authorizeHttpRequests(http->{
                    //endpoints públicos
                    http.requestMatchers(HttpMethod.GET,"/auth/get").permitAll();
                    //endponits privados
                    http.requestMatchers(HttpMethod.GET,"/auth/post").hasAnyAuthority("CREATE","READ");
                    http.requestMatchers(HttpMethod.PATCH,"/auth/patch").hasAnyAuthority("REFACTOR");
                    http.requestMatchers(HttpMethod.DELETE,"/auth/delete").hasAnyRole("ADMIN","DEVELOPER");
                    http.anyRequest().denyAll();
                })

                .build();
    }

    /* Para trabajar con methods
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults()) //para consumir por usuario y contraseña
                .sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // valida no en memoria sino con la vida del token
                .build();
    }*/

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailServiceImpl userDetailService){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailService);
        return provider;
    }

    /*
    @Bean
    public UserDetailsService userDetailsService(){
        List<UserDetails> userDetailsList = new ArrayList<>();
        userDetailsList.add(User.withUsername("jear")
                .password("1234")
                .roles("ADMIN")
                .authorities("READ","CREATE")
                .build());
        userDetailsList.add(User.withUsername("jear2")
                .password("1234")
                .roles("USER")
                .authorities("READ")
                .build());
        return new InMemoryUserDetailsManager(userDetailsList);

    }*/




    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
