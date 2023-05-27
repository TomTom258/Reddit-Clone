package com.example.redditclone.users.controllers;

import com.example.redditclone.dtos.RegisterDto;
import com.example.redditclone.emailService.EmailSenderService;
import com.example.redditclone.security.JWTGenerator;
import com.example.redditclone.security.TotpManager;
import com.example.redditclone.users.models.User;
import com.example.redditclone.users.repositories.UserRepository;
import com.example.redditclone.users.services.RegistrationValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RegistrationValidator registrationValidator;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private AuthenticationManager authenticationManager;
    @MockBean
    private JWTGenerator jwtGenerator;
    @MockBean
    private EmailSenderService emailSenderService;
    @MockBean
    private TotpManager totpManager;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void AuthController_Register_Returns_Created() throws Exception {
        RegisterDto registerDto = new RegisterDto("testUser", "test@test.com", "password1234", false);
        User user = new User("testUser", "test@test.com", "password1234", false);

        when(registrationValidator.registerUser(user)).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/register")
                        .content(objectMapper.writeValueAsString(registerDto))
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(201)))
                .andExpect(jsonPath("$.message", is("Registration successful.")));
    }
}