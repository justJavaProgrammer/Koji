package com.odeyalo.discordbot.koji.service.scheduler;

import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import java.util.List;

public interface TrackQueue {
    /**
     * Add track to queue, interrupt the current one if flag is true
     * @param track - track to add to queue or start to play
     * @param noInterrupt - true if current track must be not interrupted
     */
    void play(AudioTrack track, boolean noInterrupt);

    /**
     * Add playlist to queue, interrupt the current one if flag is true
     * @param playlist - playlist to add to queue or start to play
     * @param noInterrupt - true if current track must be not interrupted
     */
    void playPlaylist(AudioPlaylist playlist, boolean noInterrupt);
    /**
     * Add track to the queue
     * @param track - track to add in the queue
     */
    default void add(AudioTrack track) {
        play(track, true);
    }

    default void addPlaylist(AudioPlaylist playlist) {
        playPlaylist(playlist, true);
    }

    /**
     * Stops the current track
     * @return true - if stopped, false - if not
     */
    boolean stopCurrentTrack();

    /**
     * @return - current playing track, null if nothing is playing now
     */
    AudioTrack getCurrentTrack();

    /**
     * Resume the stopped track, do nothing if track was not paused
     * @return true if track was resumed, false otherwise
     */
    boolean resume();

    /**
     * @return - next tracks in the queue
     */
    List<AudioTrack> getNextTracks();

    void clearQueue();
}
