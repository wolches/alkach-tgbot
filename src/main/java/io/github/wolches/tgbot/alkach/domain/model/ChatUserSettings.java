package io.github.wolches.tgbot.alkach.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chat_user_settings")
public class ChatUserSettings {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "chat_user_id")
    private ChatUser chatUser;

    public static ChatUserSettings createDefaultSettings(ChatUser chatUser) {
        return ChatUserSettings.builder()
                .chatUser(chatUser)
                .build();
    }
}