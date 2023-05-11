package com.odeyalo.discordbot.koji.service.player;

import com.odeyalo.discordbot.koji.service.scheduler.TrackSchedulerEventListenerRegistry;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class QueueMessagePublisherTrackScheduler extends AbstractTrackScheduler {
    protected final BlockingQueue<AudioTrack> queue;

    /**
     * @param player The audio player this scheduler uses
     */
    public QueueMessagePublisherTrackScheduler(AudioPlayer player, TrackSchedulerEventListenerRegistry registry) {
        super(player, registry);
        this.queue = new LinkedBlockingQueue<>();
    }

    @Override
    protected QueueStatus doPlayPlaylist(AudioPlaylist playlist, boolean noInterrupt) {
        List<AudioTrack> tracks = playlist.getTracks();
        if (!player.startTrack(tracks.get(0), noInterrupt)) {
            queue.addAll(tracks);
            logger.info("Added the playlist {} to the queue: {}", playlist.getName(), this);
            return QueueStatus.QUEUED;
        }
        tracks.remove(0);
        queue.addAll(tracks);
        return QueueStatus.QUEUED;
    }

    @Override
    protected QueueStatus doPlay(AudioTrack track, boolean noInterrupt) {
        if (!player.startTrack(track, noInterrupt)) {
            queue.offer(track);
            logger.info("Added the track {} to queue: {}", track.getInfo().uri, this);
            return QueueStatus.QUEUED;
        }
        return QueueStatus.STARTED_PLAYING;
    }

    @Override
    public void nextTrack() {
        // Start the next track, regardless of if something is already playing or not. In case queue was empty, we are
        // giving null to startTrack, which is a valid argument and will simply stop the player.
        player.startTrack(queue.poll(), false);
    }

    @Override
    public List<AudioTrack> getNextTracks() {
        return new ArrayList<>(queue);
    }

    @Override
    protected void doClearQueue() {
        queue.clear();
    }
}
