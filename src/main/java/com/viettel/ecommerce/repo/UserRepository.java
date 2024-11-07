package com.viettel.ecommerce.repo;

import com.viettel.ecommerce.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("select u from User u where u.email =:email")
    public User getUserByEmail(@Param("email") String email);

    @Query("select u from User u where u.firstName like %?1% or u.lastName like %?1%")
    public Page<User> findAll(String keyword, Pageable pageable);
}
