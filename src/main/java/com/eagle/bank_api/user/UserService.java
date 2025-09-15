package com.eagle.bank_api.user;

import com.eagle.bank_api.user.dto.CreateUserRequest;
import com.eagle.bank_api.user.dto.UserMapper;
import com.eagle.bank_api.user.dto.UserResponse;
import com.eagle.bank_api.user.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public UserResponse createUser(CreateUserRequest request) {
        User user = UserMapper.toEntity(request);
        User savedUser = userRepository.save(user);
        return UserMapper.toDto(savedUser);
    }

    public Boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }
}
