package com.viettel.ecommerce.controller;

import com.viettel.ecommerce.dto.UserDTO;
import com.viettel.ecommerce.entity.User;
import com.viettel.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        UserDTO savedUser = userService.createUser(userDTO);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable("id") Integer userId,
                                              @RequestBody UserDTO updatedUser) {
        UserDTO userDTO = userService.updateUser(userId, updatedUser);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") Integer id) {
        UserDTO userDTO = userService.getUserById(id);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/listAll")
    public ResponseEntity<List<User>> listAll() {
        return ResponseEntity.ok(userService.listAll());
    }
}
