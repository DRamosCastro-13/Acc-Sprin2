package com.mindhub.todolist.configurations;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurity {

    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {

        http.authorizeHttpRequests( authorizationManagerRequestMatcherRegistry ->
                authorizationManagerRequestMatcherRegistry //Autorización
                        .requestMatchers("/index.html").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/userEntity/all", "/api/userEntity/{id}").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/userEntity/{id}").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/userEntity").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/task").hasAnyAuthority("ADMIN", "USER")
                )
                .headers( httpSecurityHeadersConfigurer -> httpSecurityHeadersConfigurer.frameOptions(
                        HeadersConfigurer.FrameOptionsConfig::sameOrigin
                ))
                .exceptionHandling( httpSecurityExceptionHandlingConfigurer ->
                        httpSecurityExceptionHandlingConfigurer
                                .authenticationEntryPoint( ((request, response, authException) -> response
                                        .sendError(403)) )
                )
                .formLogin( httpSecurityFormLoginConfigurer ->
                        httpSecurityFormLoginConfigurer
                                .loginPage("/index.html") //La página a la que se redirige si el user no está autenticado
                                .loginProcessingUrl("/api/login") // Solo con ponerlo se genera el endpoint al que se hará la petición para el login
                                .usernameParameter("email")
                                .passwordParameter("password")
                                .successHandler( ((request, response, authentication) ->
                                        clearAuthenticationAttributes(request)) )
                                .permitAll()
                        )
                .csrf(AbstractHttpConfigurer::disable)
                .rememberMe(Customizer.withDefaults())
                .logout( httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer
                        .logoutUrl("/api/logout")
                        .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
                        .deleteCookies("JSESSIONID")
                );

        return http.build();
    }

    public void clearAuthenticationAttributes(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session != null){
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    } // Cada que se trata de iniciar sesión o pase algo que no salga bien se generan flags
    //Cuando el inicio de sesión es exitoso el método borra esos flags
}
