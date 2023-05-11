package com.odeyalo.discordbot.koji.config;

import com.odeyalo.discordbot.koji.service.player.LavaAudioPlayerSendHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

@Configuration
public class LavaPlayerConfiguration {

    @Bean
    public AudioPlayerManager audioPlayerManager() {
        AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerRemoteSources(playerManager);
        return playerManager;
    }
}
