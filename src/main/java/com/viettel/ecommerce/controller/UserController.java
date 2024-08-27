package com.viettel.ecommerce.controller;

import com.viettel.ecommerce.dto.UserDTO;
import com.viettel.ecommerce.entity.User;
import com.viettel.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserDTO> createDepartment(@RequestBody UserDTO userDTO){
        DepartmentDto department = departmentService.createDepartment(departmentDto);
        return new ResponseEntity<>(department, HttpStatus.CREATED);
    }
    @GetMapping("/users")
    public ResponseEntity<List<User>> listAll(){
        return ResponseEntity.ok(userService.listAll());
    }
}
