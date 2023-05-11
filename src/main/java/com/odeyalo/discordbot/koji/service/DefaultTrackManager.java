package com.odeyalo.discordbot.koji.service;

import com.odeyalo.discordbot.koji.service.player.AudioPlayerFactory;
import com.odeyalo.discordbot.koji.service.player.LavaAudioPlayerSendHandler;
import com.odeyalo.discordbot.koji.service.player.AbstractTrackScheduler;
import com.odeyalo.discordbot.koji.service.player.QueueMessagePublisherTrackScheduler;
import com.odeyalo.discordbot.koji.service.player.state.AudioPlayerState;
import com.odeyalo.discordbot.koji.service.player.state.AudioPlayerStateRepository;
import com.odeyalo.discordbot.koji.service.scheduler.TrackEventUserNotifier;
import com.odeyalo.discordbot.koji.service.scheduler.TrackSchedulerEventListenerRegistry;
import com.odeyalo.discordbot.koji.service.sender.DefaultMessageChannelMessageSender;
import com.odeyalo.discordbot.koji.service.sender.MessageChannelMessageSender;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.CommandInteraction;
import net.dv8tion.jda.api.managers.AudioManager;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.List;

import static com.odeyalo.discordbot.koji.config.KojiBotConfig.*;

public class DefaultTrackManager implements TrackManager {
    private final AudioPlayerManager playerManager;
    private final AudioPlayerStateRepository stateHolder;
    private final AudioPlayerFactory audioPlayerFactory;
    private final TrackSchedulerEventListenerRegistry registry;

    @Autowired
    public DefaultTrackManager(AudioPlayerManager playerManager,
                               AudioPlayerStateRepository stateHolder,
                               AudioPlayerFactory audioPlayerFactory,
                               TrackSchedulerEventListenerRegistry registry) {

        this.playerManager = playerManager;
        this.stateHolder = stateHolder;
        this.audioPlayerFactory = audioPlayerFactory;
        this.registry = registry;
    }


    @Override
    public PlayingResult play(CommandInteraction event, String source) {
        Guild guild = event.getGuild();
        if (guild == null) {
            event.reply("Only guild is supported").queue();
            return PlayingResult.denied();
        }
        String id = guild.getId();
        AudioManager audioManager = guild.getAudioManager();

        AudioPlayerState state = getState(event, id);

        audioManager.setSendingHandler(state.getAudioSendHandler());

        playerManager.loadItem(source, new DefaultAudioLoadResultHandler(state));

        return PlayingResult.loading();
    }

    @NotNull
    private AudioPlayerState getState(CommandInteraction event, String id) {
        AudioPlayerState state = stateHolder.get(id);

        if (state == null) {
            AudioPlayer player = audioPlayerFactory.create(playerManager);
            registry.add(new TrackEventUserNotifier(event.getMessageChannel()));

            state = new AudioPlayerState(player, playerManager,
                    new LavaAudioPlayerSendHandler(player),
                    new QueueMessagePublisherTrackScheduler(player, registry),
                    new DefaultMessageChannelMessageSender(event.getMessageChannel()));


            player.addListener(state.getScheduler());

            stateHolder.putIfAbsent(id, state);
        }
        return state;
    }


    static class DefaultAudioLoadResultHandler implements AudioLoadResultHandler {
        private final AudioPlayerState state;

        public DefaultAudioLoadResultHandler(AudioPlayerState state) {
            this.state = state;
        }

        @Override
        public void trackLoaded(AudioTrack audioTrack) {
            AbstractTrackScheduler scheduler = state.getScheduler();
            scheduler.add(audioTrack);
        }

        @Override
        public void playlistLoaded(AudioPlaylist audioPlaylist) {
            AbstractTrackScheduler scheduler = state.getScheduler();
            scheduler.addPlaylist(audioPlaylist);
        }

        @Override
        public void noMatches() {
            EmbedBuilder builder = new EmbedBuilder().setFooter(BOT_NAME, ICON_URL)
                    .setAuthor("Oops, something went wrong!", null, SPINNING_RECORD_ICON_URL)
                    .setColor(Color.PINK)
                    .setDescription("Cannot find this track. Sorry!");

            state.getMessageSender().sendMessage(builder.build());
        }

        @Override
        public void loadFailed(FriendlyException e) {
            EmbedBuilder builder = new EmbedBuilder().setFooter(BOT_NAME, ICON_URL)
                    .setAuthor("Oops, something went wrong!", null, SPINNING_RECORD_ICON_URL)
                    .setColor(Color.PINK)
                    .setDescription(e.getMessage());

            state.getMessageSender().sendMessage(builder.build());
        }
    }
}
