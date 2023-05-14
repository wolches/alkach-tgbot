package io.github.wolches.tgbot.alkach.persistence.model.chat;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id", "chatUser"})
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

    public static ChatUserSettings withUser(ChatUser chatUser) {
        ChatUserSettings settings = getDefault();
        settings.chatUser = chatUser;
        return settings;
    }

    public static ChatUserSettings getDefault() {
        return ChatUserSettings.builder().build();
    }
}