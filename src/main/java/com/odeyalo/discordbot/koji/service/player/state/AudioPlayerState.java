package com.odeyalo.discordbot.koji.service.player.state;

import com.odeyalo.discordbot.koji.service.player.AbstractTrackScheduler;
import com.odeyalo.discordbot.koji.service.sender.MessageChannelMessageSender;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import lombok.Data;
import net.dv8tion.jda.api.audio.AudioSendHandler;

@Data
public class AudioPlayerState {
    private final AudioPlayer player;
    private final AudioPlayerManager playerManager;
    private final AudioSendHandler audioSendHandler;
    private final AbstractTrackScheduler scheduler;
    private final MessageChannelMessageSender messageSender;

    public AudioPlayerState(AudioPlayer player, AudioPlayerManager playerManager, AudioSendHandler audioSendHandler, AbstractTrackScheduler scheduler, MessageChannelMessageSender messageSender) {
        this.player = player;
        this.playerManager = playerManager;
        this.audioSendHandler = audioSendHandler;
        this.scheduler = scheduler;
        this.messageSender = messageSender;
    }
}
