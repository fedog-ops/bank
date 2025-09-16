package com.eagle.bank_api;

import com.eagle.bank_api.user.UserController;
import com.eagle.bank_api.user.UserService;
import com.eagle.bank_api.user.dto.AddressRequest;
import com.eagle.bank_api.user.dto.CreateUserRequest;
import com.eagle.bank_api.user.dto.UserResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTests {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void testCreateUser() throws Exception {
        AddressRequest addressRequest = new AddressRequest();
        addressRequest.setLine1("123 Main St");
        addressRequest.setTown("London");
        addressRequest.setCounty("Greater London");
        addressRequest.setPostcode("E1 6AN");

        CreateUserRequest request = new CreateUserRequest();
        request.setName("John Doe");
        request.setEmail("john.doe@example.com");
        request.setNumber("+447911123456");
        request.setAddress(addressRequest);

        UserResponse.AddressResponse addressResponse = UserResponse.AddressResponse.builder()
                .line1("123 Felix St")
                .town("Manchester")
                .county("Stockport")
                .postcode("SK8 7HN")
                .build();

        UserResponse response = UserResponse.builder()
                .id("1")
                .name("John Doe")
                .email("john.doe@example.com")
                .number("+447911123456")
                .address(addressResponse)
                .createdAt("2025-09-16T12:00:00Z")
                .updatedAt("2025-09-16T12:00:00Z")
                .build();

        when(userService.createUser(any(CreateUserRequest.class))).thenReturn(response);

        mockMvc.perform(post("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.number").value("+447911123456"))
                .andExpect(jsonPath("$.address.line1").value("123 Felix St"))
                .andExpect(jsonPath("$.address.town").value("Manchester"))
                .andExpect(jsonPath("$.address.county").value("Stockport"))
                .andExpect(jsonPath("$.address.postcode").value("SK8 7HN"));
    }

    @Test
    void testCreateInvalidUser() throws Exception {
        CreateUserRequest request = new CreateUserRequest();
        request.setName("John Doe");
        request.setEmail(null);
        request.setNumber("+447911123456");
        request.setAddress(null);

        mockMvc.perform(post("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
                .andExpect(status().isBadRequest());
    }
}
