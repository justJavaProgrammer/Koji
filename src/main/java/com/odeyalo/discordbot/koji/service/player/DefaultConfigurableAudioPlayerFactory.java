package com.odeyalo.discordbot.koji.service.player;

import com.odeyalo.discordbot.koji.service.player.config.AudioPlayerConfigurer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DefaultConfigurableAudioPlayerFactory implements AudioPlayerFactory {
    private final List<AudioPlayerConfigurer> audioPlayerConfigurer;
    private final Logger logger = LoggerFactory.getLogger(DefaultConfigurableAudioPlayerFactory.class);

    @Autowired
    public DefaultConfigurableAudioPlayerFactory(List<AudioPlayerConfigurer> audioPlayerConfigurer) {
        this.audioPlayerConfigurer = audioPlayerConfigurer;
    }

    @Override
    public AudioPlayer create(AudioPlayerManager manager) {
        AudioPlayer player = manager.createPlayer();
        audioPlayerConfigurer.forEach(configurer -> configurer.configure(player));
        logger.info("Created: {}", player);
        return player;
    }
}
