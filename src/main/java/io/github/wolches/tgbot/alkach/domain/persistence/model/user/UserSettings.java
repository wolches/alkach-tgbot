package io.github.wolches.tgbot.alkach.domain.persistence.model.user;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id", "user"})
@Entity
@Table(name = "user_settings")
public class UserSettings {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "is_admin")
    private boolean admin;

    public static UserSettings withUser(User user) {
        UserSettings settings = getDefault();
        settings.setUser(user);
        return settings;
    }

    public static UserSettings getDefault() {
        return UserSettings.builder()
                .admin(false)
                .build();
    }
}