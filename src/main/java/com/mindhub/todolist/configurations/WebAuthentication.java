package com.mindhub.todolist.configurations;

import com.mindhub.todolist.models.UserEntity;
import com.mindhub.todolist.repositories.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebAuthentication extends GlobalAuthenticationConfigurerAdapter {

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService( username -> {
            //Autenticación
            UserEntity user = userEntityRepository.findByEmail(username).
                    orElseThrow( () -> new UsernameNotFoundException("Username not found"));

            return new User( user.getEmail(), user.getPassword(), AuthorityUtils.createAuthorityList
                    (user.getRole().toString()));

        } );
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
