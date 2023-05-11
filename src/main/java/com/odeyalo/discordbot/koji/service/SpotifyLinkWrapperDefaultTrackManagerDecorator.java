package com.odeyalo.discordbot.koji.service;

import com.odeyalo.discordbot.koji.service.player.AudioPlayerFactory;
import com.odeyalo.discordbot.koji.service.player.state.AudioPlayerStateRepository;
import com.odeyalo.discordbot.koji.service.scheduler.TrackSchedulerEventListenerRegistry;
import com.odeyalo.discordbot.koji.service.spotify.SpotifyAdaptedAudioProvider;
import com.odeyalo.discordbot.koji.service.spotify.SpotifyApiSpotifyTrackLoader;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.interactions.commands.CommandInteraction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SpotifyLinkWrapperDefaultTrackManagerDecorator extends DefaultTrackManager {
    public static final String SPOTIFY_URL = "https://open.spotify.com";
    private final SpotifyAdaptedAudioProvider spotifyAdaptedAudioProvider;
    private final Logger logger = LoggerFactory.getLogger(SpotifyApiSpotifyTrackLoader.class);

    public SpotifyLinkWrapperDefaultTrackManagerDecorator(AudioPlayerManager playerManager,
                                                          AudioPlayerStateRepository stateHolder,
                                                          AudioPlayerFactory audioPlayerFactory,
                                                          TrackSchedulerEventListenerRegistry registry, SpotifyAdaptedAudioProvider spotifyAdaptedAudioProvider) {
        super(playerManager, stateHolder, audioPlayerFactory, registry);
        this.spotifyAdaptedAudioProvider = spotifyAdaptedAudioProvider;
    }

    @Override
    public PlayingResult play(CommandInteraction event, String source) {
        if (source.startsWith(SPOTIFY_URL)) {
            List<AudioTrack> tracks = spotifyAdaptedAudioProvider.getAudioTracks(source);
            for (AudioTrack track : tracks) {
                super.play(event, track.getInfo().uri);
            }
        }

        return super.play(event, source);
    }


}
