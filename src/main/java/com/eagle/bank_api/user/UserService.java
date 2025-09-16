package com.eagle.bank_api.user;

import com.eagle.bank_api.user.dto.CreateUserRequest;
import com.eagle.bank_api.user.dto.UserMapper;
import com.eagle.bank_api.user.dto.UserResponse;
import com.eagle.bank_api.user.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.access.AccessDeniedException;
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

    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public UserResponse findById(String userId) {
        if (!userId.startsWith("usr-")){
            throw new UsernameNotFoundException("User was not found.");
        }

        String userIdFromToken = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(!userId.equals(userIdFromToken)){
            throw new AccessDeniedException("Access token is missing or invalid");
        }

        User savedUser = userRepository.findById(Long.valueOf(userId.substring(4)))
                .orElseThrow(() -> new UsernameNotFoundException("User was not found"));

        return UserMapper.toDto(savedUser);
    }
}
