package sbp.erp.core.autotestcheck.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sbp.erp.core.autotestcheck.entity.UserProfile;
import sbp.erp.core.autotestcheck.repo.UserProfileRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserProfileServiceTest {

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private UserProfileRepository userProfileRepository;

    private UserProfile userProfile;

    @BeforeEach
    public void setUp() {
        userProfile = new UserProfile("John Doe", "john.doe@example.com");
        userProfileRepository.save(userProfile);
    }

    @Test
    void testCreateUserProfile() {
        UserProfile createdUserProfile = userProfileService.createProfile(userProfile);
        assertNotNull(createdUserProfile);
        assertEquals("John Doe", createdUserProfile.getName());
        assertEquals("john.doe@example.com", createdUserProfile.getEmail());
    }

    @Test
    void testGetUserProfileById() {
        UserProfile foundUserProfile = userProfileService.getProfileById(userProfile.getId()).orElse(null);
        assertNotNull(foundUserProfile);
        assertEquals("John Doe", foundUserProfile.getName());
    }

    @Test
    void testUpdateUserProfile() {
        UserProfile updatedUserProfile = new UserProfile("Jane Doe", "jane.doe@example.com");
        UserProfile result = userProfileService.updateProfile(userProfile.getId(), updatedUserProfile);
        assertNotNull(result);
        assertEquals("Jane Doe", result.getName());
        assertEquals("jane.doe@example.com", result.getEmail());
    }

    @Test
    void testDeleteUserProfile() {
        userProfileService.deleteProfile(userProfile.getId());
        assertFalse(userProfileRepository.existsById(userProfile.getId()));
    }
}