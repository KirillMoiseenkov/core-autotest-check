package sbp.erp.core.autotestcheck.controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import sbp.erp.core.autotestcheck.entity.UserProfile;
import sbp.erp.core.autotestcheck.repo.UserProfileRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserProfileControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(UserProfileControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserProfileRepository userProfileRepository;

    private UserProfile userProfile;

    @BeforeEach
    public void setUp() {
        userProfile = new UserProfile("John Doe", "john.doe@example.com");
        userProfileRepository.save(userProfile);
        logger.info("Test user profile saved: {}", userProfile.getName());
    }

    @Test
    public void testGetAllUsers() throws Exception {
        logger.info("Testing GET /users endpoint");
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[0].email").value("john.doe@example.com"));
    }

    @Test
    public void testGetUserById() throws Exception {
        logger.info("Testing GET /users/{id} endpoint with id: {}", userProfile.getId());
        mockMvc.perform(get("/users/{id}", userProfile.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
    public void testCreateUser() throws Exception {
        logger.info("Testing POST /users endpoint");
        UserProfile newUser = new UserProfile("Jane Doe", "jane.doe@example.com");
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newUser)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Jane Doe"))
                .andExpect(jsonPath("$.email").value("jane.doe@example.com"));
    }

    @Test
    public void testUpdateUser() throws Exception {
        logger.info("Testing PUT /users/{id} endpoint with id: {}", userProfile.getId());
        UserProfile updatedUser = new UserProfile("John Smith", "john.smith@example.com");
        mockMvc.perform(put("/users/{id}", userProfile.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Smith"))
                .andExpect(jsonPath("$.email").value("john.smith@example.com"));
    }

    @Test
    public void testDeleteUser() throws Exception {
        logger.info("Testing DELETE /users/{id} endpoint with id: {}", userProfile.getId());
        mockMvc.perform(delete("/users/{id}", userProfile.getId()))
                .andExpect(status().isNoContent());
    }
}