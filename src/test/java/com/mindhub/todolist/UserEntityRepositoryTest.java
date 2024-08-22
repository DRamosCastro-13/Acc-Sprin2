package com.mindhub.todolist;

import com.mindhub.todolist.models.RoleType;
import com.mindhub.todolist.models.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.mindhub.todolist.repositories.UserEntityRepository;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserEntityRepositoryTest {

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Test
    public void testFindByEmail(){
        // Usuario de muestra
        UserEntity user = new UserEntity("usertest", "password", "testuser@example.com");
        user.setRole(RoleType.USER);
        userEntityRepository.save(user); // guardar en la base de datos

        // Método
        Optional<UserEntity> foundUser = userEntityRepository.findByEmail(user.getEmail());

        // Afirmaciones
        assertThat(foundUser, is(notNullValue()));
        assertThat(foundUser.get().getEmail(), is(equalTo(user.getEmail())));
        assertThat(foundUser.get().getPassword(), is(equalTo(user.getPassword())));
        assertThat(foundUser.get().getUsername(), is(equalTo(user.getUsername())));
        assertThat(foundUser.get().getRole(), is(equalTo(user.getRole())));
    }
}
