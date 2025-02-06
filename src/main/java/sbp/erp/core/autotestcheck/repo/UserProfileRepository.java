package sbp.erp.core.autotestcheck.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import sbp.erp.core.autotestcheck.entity.UserProfile;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    UserProfile findByName(String name);
}
