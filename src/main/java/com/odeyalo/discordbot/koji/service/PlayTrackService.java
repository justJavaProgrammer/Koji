package com.odeyalo.discordbot.koji.service;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import lombok.AllArgsConstructor;
import lombok.Data;
import net.dv8tion.jda.api.interactions.commands.CommandInteraction;

public interface PlayTrackService {
    /**
     * Start or add to queue the given track
     * @param event - event that has been fired.
     * @param source - source from play track. Can be URL or path to file
     * @return - PlayingStatus based on decision made by implementation. Never null
     */
    PlayingResult play(CommandInteraction event, String source);

    @Data
    @AllArgsConstructor
    class PlayingResult {
        private AudioTrack track;
        private PlayingStatus status;

        public static PlayingResult loading() {
            return new PlayingResult(null, PlayingStatus.LOADING);
        }

        public static PlayingResult startedPlaying(AudioTrack track) {
            return new PlayingResult(track, PlayingStatus.STARTED_PLAYING);
        }

        public static PlayingResult queued(AudioTrack track) {
            return new PlayingResult(track, PlayingStatus.QUEUED);
        }

        public static PlayingResult denied() {
            return new PlayingResult(null, null);
        }
    }

    enum PlayingStatus {
        LOADING,
        STARTED_PLAYING,
        QUEUED,
        DENIED
    }
}
