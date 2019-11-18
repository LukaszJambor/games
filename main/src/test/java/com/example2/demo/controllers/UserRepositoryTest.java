package com.example2.demo.controllers;

import com.example2.demo.dao.UserRepository;
import com.example2.demo.model.UserEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldFindUserByLogin_whenAvailable(){
        //given
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin("test@test.pl");
        userEntity.setPassword("password");
        userEntity.setActive(Boolean.TRUE);

        //when
        entityManager.persist(userEntity);
        entityManager.flush();

        //then
        UserEntity found = userRepository.findUserEntityByLogin("test@test.pl");

        assertThat(found.getLogin()).isEqualTo("test@test.pl");
        assertThat(found.getPassword()).isEqualTo("password");
    }
}
