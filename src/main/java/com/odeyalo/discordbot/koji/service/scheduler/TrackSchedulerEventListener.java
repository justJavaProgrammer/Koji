package com.odeyalo.discordbot.koji.service.scheduler;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;

public interface TrackSchedulerEventListener {

    void onTrackStarted(AudioTrack track);

    void onTrackResumed(AudioTrack audioTrack);

    void onTrackPaused(AudioPlayer player, AudioTrack track);

    void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason);

    void onTrackAddedToQueue(AudioTrack track);

    void onPlaylistAddedToQueue(AudioPlaylist audioPlaylist);

    void onQueueCleared();
}
