package sbp.erp.core.autotestcheck.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sbp.erp.core.autotestcheck.entity.UserProfile;
import sbp.erp.core.autotestcheck.repo.UserProfileRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private static final Logger logger = LoggerFactory.getLogger(UserProfileService.class);

    private final UserProfileRepository userProfileRepository;

    public List<UserProfile> getAllProfiles() {
        logger.info("Fetching all user profiles");
        List<UserProfile> userProfiles = userProfileRepository.findAll();
        logger.info("Fetched {} user profiles", userProfiles.size());
        return userProfiles;
    }

    public Optional<UserProfile> getProfileById(Long id) {
        logger.info("Fetching user profile with id: {}", id);
        Optional<UserProfile> userProfile = userProfileRepository.findById(id);
        if (userProfile.isPresent()) {
            logger.info("Found user profile with id: {}", id);
        } else {
            logger.warn("User profile with id: {} not found", id);
        }
        return userProfile;
    }

    public UserProfile createProfile(UserProfile userProfile) {
        logger.info("Creating new user profile: {}", userProfile.getName());
        UserProfile createdProfile = userProfileRepository.save(userProfile);
        logger.info("Created user profile with id: {}", createdProfile.getId());
        return createdProfile;
    }

    public UserProfile updateProfile(Long id, UserProfile userDetails) {
        logger.info("Updating user profile with id: {}", id);
        Optional<UserProfile> existingUser = userProfileRepository.findById(id);
        if (existingUser.isPresent()) {
            UserProfile updatedUser = existingUser.get();
            updatedUser.setName(userDetails.getName());
            updatedUser.setEmail(userDetails.getEmail());
            logger.info("Updated user profile with id: {}", id);
            return userProfileRepository.save(updatedUser);
        } else {
            logger.warn("User profile with id: {} not found for update", id);
            return null;
        }
    }

    public void deleteProfile(Long id) {
        logger.info("Deleting user profile with id: {}", id);
        userProfileRepository.deleteById(id);
        logger.info("Deleted user profile with id: {}", id);
    }
}