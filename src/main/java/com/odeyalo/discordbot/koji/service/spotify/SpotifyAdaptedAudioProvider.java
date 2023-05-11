package com.odeyalo.discordbot.koji.service.spotify;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import java.util.List;

public interface SpotifyAdaptedAudioProvider {

    List<AudioTrack> getAudioTracks(String source);

}
