package com.viettel.ecommerce.repo;

import com.viettel.ecommerce.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Integer> {
}
