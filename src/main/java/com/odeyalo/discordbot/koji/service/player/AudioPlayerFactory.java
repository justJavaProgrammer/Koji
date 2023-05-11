package com.odeyalo.discordbot.koji.service.player;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventListener;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

public interface AudioPlayerFactory {
    /**
     * Create a new {@link AudioPlayer}
     * @param playerManager - manager to create player from
     * @return - new AudioPlayer
     */
    AudioPlayer create(AudioPlayerManager playerManager);


    @Data
    @AllArgsConstructor
    class AudioPlayerMetadata {
        private List<AudioEventListener> listeners;
        private AudioPlayer audioPlayer;
        private AudioPlayerManager manager;
    }
}
