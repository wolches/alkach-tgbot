package io.github.wolches.tgbot.pesda.bot;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Setter
@Configuration
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "pesda.bot")
public class BotConfig {

    private String token;
    private String name;

    @Bean
    public BotInstance botInstance() {
        return new BotInstance(token, name);
    }
}
