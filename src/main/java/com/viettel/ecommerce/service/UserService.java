package com.viettel.ecommerce.service;

import com.viettel.ecommerce.dto.RoleDTO;
import com.viettel.ecommerce.dto.UserDTO;
import com.viettel.ecommerce.entity.Role;
import com.viettel.ecommerce.entity.User;
import com.viettel.ecommerce.exception.DuplicateDataException;
import com.viettel.ecommerce.exception.ResourceNotFoundException;
import com.viettel.ecommerce.repo.UserRepository;
import com.viettel.ecommerce.util.DataUtil;
import com.viettel.ecommerce.util.FileUploadUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    public static final int USERS_PER_PAGE = 4;
    public final ModelMapper mapper = new ModelMapper();

    @Autowired
    private UserRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> listAll() {
        return (List<User>) repo.findAll();
    }

    public UserDTO createUser(UserDTO userDTO) {
        User existUser = repo.getUserByEmail(userDTO.getEmail());
        if (!DataUtil.isNullOrEmpty(existUser)) {
            throw new DuplicateDataException("User is exist with email: " + userDTO.getEmail());
        }
        User user = mapper.map(userDTO, User.class);
        encodePassword(user);
        User savedUser = repo.save(user);
        return mapper.map(savedUser, UserDTO.class);
    }

    private void encodePassword(User user) {
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    public UserDTO getUserById(Integer id) {
        User user = repo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User is not exists with a given id: " + id)
        );
        return mapper.map(user, UserDTO.class);
    }

    public UserDTO updateUser(Integer userId, UserDTO updatedUser, MultipartFile multipartFile) throws IOException {
        User savedUser = repo.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User is not exists with a given id: " + userId)
        );
        User existUser = repo.getUserByEmail(updatedUser.getEmail());
        if (!DataUtil.isNullOrEmpty(existUser) && !DataUtil.safeEqual(existUser.getEmail(), updatedUser.getEmail())) {
            throw new DuplicateDataException("Can not update user with exist email: " + updatedUser.getEmail());
        }
        if (multipartFile != null) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            updatedUser.setPhotos(fileName);
            String uploadDir = "user-photos/" + userId;
            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        } else {
            FileUploadUtil.cleanDir("user-photos/" + userId);
        }

        savedUser.setEmail(updatedUser.getEmail());
        savedUser.setPassword(encodePassword(updatedUser.getPassword()));
        savedUser.setEnabled(updatedUser.isEnabled());
        savedUser.setPhotos(updatedUser.getPhotos());
        savedUser.setFirstName(updatedUser.getFirstName());
        savedUser.setLastName(updatedUser.getLastName());
        if (!DataUtil.isNullOrEmpty(updatedUser.getRoles())) {
            Set<Role> roles = updatedUser.getRoles().stream()
                    .map(roleDTO -> mapper.map(roleDTO, Role.class))
                    .collect(Collectors.toSet());
            savedUser.setRoles(roles);
        } else {
            savedUser.setRoles(new HashSet<>());
        }
        repo.save(savedUser);
        return mapper.map(savedUser, UserDTO.class);
    }

    public void deleteUser(Integer id) {
        User user = repo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User is not exists with a given id: " + id)
        );
        repo.deleteById(id);
    }

    public Page<User> listByPage(int pageNum, String sortField, String sortDir){
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNum - 1, USERS_PER_PAGE, sort);
        return repo.findAll(pageable);
    }
}
