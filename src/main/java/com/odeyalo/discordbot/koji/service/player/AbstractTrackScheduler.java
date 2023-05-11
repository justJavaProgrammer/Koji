package com.odeyalo.discordbot.koji.service.player;

import com.odeyalo.discordbot.koji.service.scheduler.TrackQueue;
import com.odeyalo.discordbot.koji.service.scheduler.TrackSchedulerEventListener;
import com.odeyalo.discordbot.koji.service.scheduler.TrackSchedulerEventListenerRegistry;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.odeyalo.discordbot.koji.service.player.AbstractTrackScheduler.QueueStatus.*;

/**
 * This class schedules tracks for the audio player. It contains the queue of tracks.
 */
public abstract class AbstractTrackScheduler extends AudioEventAdapter implements TrackQueue {
    protected final AudioPlayer player;
    protected final Logger logger = LoggerFactory.getLogger(AbstractTrackScheduler.class);
    protected final TrackSchedulerEventListenerRegistry listenersContainer;

    /**
     * @param player The audio player this scheduler uses
     */
    public AbstractTrackScheduler(AudioPlayer player, TrackSchedulerEventListenerRegistry listenersContainer) {
        this.player = player;
        this.listenersContainer = listenersContainer;
    }

    @Override
    public void play(AudioTrack track, boolean noInterrupt) {
        QueueStatus status = doPlay(track, noInterrupt);

        // Notify only if track was added to queue, since
        // onTrackStarted event will be called in onTrackStart(track) method
        if (status == QUEUED) {
            listenersContainer.getListeners().forEach(listener -> listener.onTrackAddedToQueue(track));
        }
    }

    @Override
    public void playPlaylist(AudioPlaylist playlist, boolean noInterrupt) {
        QueueStatus status = doPlayPlaylist(playlist, noInterrupt);

        if (status == QUEUED) {
            listenersContainer.getListeners().forEach(listener -> listener.onPlaylistAddedToQueue(playlist));
        }
    }

    protected abstract QueueStatus doPlayPlaylist(AudioPlaylist playlist, boolean noInterrupt);

    /**
     * Add the next track to queue or play right away if nothing is in the queue.
     *
     * @param track The track to play or add to queue.
     * @param noInterrupt should be current track be skipped, false if yes, true if no
     * @return STARTED_PLAYING if track started, QUEUED if added to queue
     */
    protected abstract QueueStatus doPlay(AudioTrack track, boolean noInterrupt);

    /**
     * Start the next track, stopping the current one if it is playing.
     */
    public abstract void nextTrack();

    @Override
    public boolean stopCurrentTrack() {
        player.setPaused(true);
        return player.isPaused();
    }

    @Override
    public AudioTrack getCurrentTrack() {
        return player.getPlayingTrack();
    }

    @Override
    public boolean resume() {
        if (player.isPaused()) {
            player.setPaused(false);
            return true;
        }
        return false;
    }

    @Override
    public void clearQueue() {
        doClearQueue();

        listenersContainer.getListeners().forEach(listener -> listener.onQueueCleared());
    }

    protected abstract void doClearQueue();

    @Override
    public void onPlayerPause(AudioPlayer player) {
        listenersContainer.getListeners().forEach(listener -> listener.onTrackPaused(player, player.getPlayingTrack()));
    }

    @Override
    public void onPlayerResume(AudioPlayer player) {
        listenersContainer.getListeners().forEach(listener -> listener.onTrackResumed(player.getPlayingTrack()));
    }
    //todo fix
    @Override
    public void onTrackStart(AudioPlayer player, AudioTrack track) {
        listenersContainer.getListeners().forEach(listener -> listener.onTrackStarted(track));
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        // Only start the next track if the end reason is suitable for it (FINISHED or LOAD_FAILED)
        if (endReason.mayStartNext) {
            nextTrack();
        }
    }

    private void determineEventAndNotify(AudioTrack track, QueueStatus status, List<TrackSchedulerEventListener> lists) {
        switch (status) {

        }
    }


    public enum QueueStatus {
        STARTED_PLAYING,
        QUEUED
    }
}