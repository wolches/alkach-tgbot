package io.github.wolches.tgbot.alkach.domain.persistence.repo;

import io.github.wolches.tgbot.alkach.domain.persistence.model.user.UserSettings;
import io.github.wolches.tgbot.alkach.domain.persistence.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserSettingsRepository extends JpaRepository<UserSettings, Long> {

    Optional<UserSettings> findByUser(User user);
}
