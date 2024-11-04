package com.viettel.ecommerce.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viettel.ecommerce.dto.UserDTO;
import com.viettel.ecommerce.dto.UserExcelExporter;
import com.viettel.ecommerce.entity.User;
import com.viettel.ecommerce.service.UserService;
import com.viettel.ecommerce.util.FileUploadUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestParam("user") String userJson,
                                              @RequestParam(value = "file", required = false) MultipartFile multipartFile) throws IOException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            UserDTO userDTO = objectMapper.readValue(userJson, UserDTO.class);
            UserDTO savedUser = userService.createUser(userDTO);
            if (multipartFile != null) {
                String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
                userDTO.setPhotos(fileName);
                String uploadDir = "user-photos/" + savedUser.getId();
                FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
            }
            return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
        } catch (Exception e) {
            throw e;
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable("id") Integer userId,
                                              @RequestParam("user") String userJson,
                                              @RequestParam(value = "file", required = false) MultipartFile multipartFile) throws IOException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            UserDTO userDTO = objectMapper.readValue(userJson, UserDTO.class);
            UserDTO savedUser = userService.updateUser(userId, userDTO, multipartFile);
            return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
        } catch (Exception e) {
            throw e;
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully!.");
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") Integer id) {
        UserDTO userDTO = userService.getUserById(id);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("")
    public ResponseEntity<List<User>> listAll() {
        return ResponseEntity.ok(userService.listAll());
    }

    @PostMapping(value = "/uploads",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImages(
            @ModelAttribute("file") MultipartFile file
    ) {
        try {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            String uploadDir = "user-photo";
            FileUploadUtil.saveFile(uploadDir, fileName, file);

            return ResponseEntity.ok("Upload successfully !");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/page/{pageNum}")
    public ResponseEntity<List<User>> listByPage(@PathVariable("pageNum") int pageNum,
                                                 @Param("sortField") String sortField, @Param("sortDir") String sortDir) {
        Page<User> page = userService.listByPage(pageNum, sortField, sortDir);
        List<User> userList = page.getContent();
        return ResponseEntity.ok(userList);
    }

    @GetMapping("/export/excel")
    public void exportToExcel(HttpServletResponse response) throws Exception{
        List<User> listUsers = userService.listAll();

        UserExcelExporter exporter = new UserExcelExporter();
        exporter.export(listUsers, response);
    }
}
