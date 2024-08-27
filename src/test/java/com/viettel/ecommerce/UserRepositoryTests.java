package com.viettel.ecommerce;

import com.viettel.ecommerce.entity.Role;
import com.viettel.ecommerce.entity.User;
import com.viettel.ecommerce.repo.UserRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;


import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
    @Autowired
    private UserRepository repo;
    @Autowired
    private EntityManager em;
    @Test
    public void testCreateUser(){
        Role roleAdmin = em.find(Role.class,1);
        User namlt24 = new User("namlt24@gmail.com","123456a@","Le","Nam");
        namlt24.addRole(roleAdmin);
        User savedUser = repo.save(namlt24);

        assertThat(savedUser.getId()).isGreaterThan(0);
    }

}
