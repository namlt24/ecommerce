package com.viettel.ecommerce.service;

import com.viettel.ecommerce.entity.User;
import com.viettel.ecommerce.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository repo;

    public List<User> listAll(){
        return (List<User>) repo.findAll();
    }
}
