package sbp.erp.core.autotestcheck.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sbp.erp.core.autotestcheck.entity.UserProfile;
import sbp.erp.core.autotestcheck.service.UserProfileService;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserProfileController {

    private static final Logger logger = LoggerFactory.getLogger(UserProfileController.class);

    private final UserProfileService userProfileService;

    // Получить список всех пользователей
    @GetMapping
    public List<UserProfile> getAllUsers() {
        logger.info("Received request to fetch all user profiles");
        return userProfileService.getAllProfiles();
    }

    // Получить пользователя по ID
    @GetMapping("/{id}")
    public ResponseEntity<UserProfile> getUserById(@PathVariable Long id) {
        logger.info("Received request to fetch user profile with id: {}", id);
        Optional<UserProfile> user = userProfileService.getProfileById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Создать нового пользователя
    @PostMapping
    public ResponseEntity<UserProfile> createUser(@RequestBody UserProfile userProfile) {
        logger.info("Received request to create new user profile: {}", userProfile.getName());
        UserProfile createdUser = userProfileService.createProfile(userProfile);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    // Обновить данные пользователя
    @PutMapping("/{id}")
    public ResponseEntity<UserProfile> updateUser(@PathVariable Long id, @RequestBody UserProfile userDetails) {
        logger.info("Received request to update user profile with id: {}", id);
        UserProfile updatedUser = userProfileService.updateProfile(id, userDetails);
        return updatedUser != null ? ResponseEntity.ok(updatedUser) : ResponseEntity.notFound().build();
    }

    // Удалить пользователя
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        logger.info("Received request to delete user profile with id: {}", id);
        userProfileService.deleteProfile(id);
        return ResponseEntity.noContent().build();
    }
}