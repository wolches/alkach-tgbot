package io.github.wolches.tgbot.alkach.bot;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Setter
@Configuration
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "alkach.bot")
public class BotConfig {

    private String token;
    private String name;

    @Bean
    public BotInstance botInstance() {
        return new BotInstance(token, name);
    }
}
