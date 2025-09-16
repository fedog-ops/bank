package com.eagle.bank_api.user;

import com.eagle.bank_api.user.dto.CreateUserRequest;
import com.eagle.bank_api.user.dto.UserResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        UserResponse response = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> fetchUserByID(@PathVariable String userId){
        UserResponse response = userService.findById(userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
