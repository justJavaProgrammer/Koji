package com.odeyalo.discordbot.koji.config;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class JdaConfiguration {

    @Bean
    public JDA jda(@Value("${bot.koji.token}") String token, List<JdaConfigurer> configurers) {

        JDA jda = JDABuilder.createDefault(token)
                .setStatus(OnlineStatus.DO_NOT_DISTURB)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .build();

        configurers.forEach((configurer) -> configurer.configure(jda));

        return jda;
    }
}
