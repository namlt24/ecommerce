package com.viettel.ecommerce;

import com.viettel.ecommerce.entity.Role;
import com.viettel.ecommerce.repo.RoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class RoleRepositoryTests {
    @Autowired
    private RoleRepository repo;
    @Test
    public void testCreateFirstRole() {
        Role roleAdmin = new Role("Admin", "manage everything");
        Role savedRole = repo.save(roleAdmin);
        assertThat(savedRole.getId()).isGreaterThan(0);
    }
    @Test
    public void testCreateRestRoles(){
        Role roleSalesperson = new Role("Salesperson","manage product price, customers, shipping, orders and sale report");
        Role roleEditor = new Role("Editor","manage categories, brands, products, articles and menus");

        repo.saveAll(List.of(roleSalesperson,roleEditor));
    }
}
