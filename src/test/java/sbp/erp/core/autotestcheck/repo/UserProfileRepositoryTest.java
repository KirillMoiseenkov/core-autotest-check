package sbp.erp.core.autotestcheck.repo;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sbp.erp.core.autotestcheck.entity.UserProfile;


@SpringBootTest
class UserProfileRepositoryTest {

    @Autowired
    private UserProfileRepository userProfileRepository;

    private UserProfile userProfile;

    @BeforeEach
    public void setUp() {
        userProfile = new UserProfile("John Doe", "john.doe@example.com");
        userProfileRepository.save(userProfile);
    }

    @Test
    void testFindByName() {
        UserProfile foundUserProfile = userProfileRepository.findByName("John Doe");
        assertNotNull(foundUserProfile);
        assertEquals("john.doe@example.com", foundUserProfile.getEmail());
    }

    @Test
    void testDeleteUserProfile() {
        userProfileRepository.deleteById(userProfile.getId());
        assertFalse(userProfileRepository.existsById(userProfile.getId()));
    }
}