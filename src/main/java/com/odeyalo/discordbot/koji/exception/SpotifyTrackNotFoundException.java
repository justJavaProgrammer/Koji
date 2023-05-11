package com.odeyalo.discordbot.koji.exception;

public class SpotifyTrackNotFoundException extends RuntimeException {
    private final String trackId;
    public SpotifyTrackNotFoundException(String message, String trackId) {
        super(message);
        this.trackId = trackId;
    }

    public SpotifyTrackNotFoundException(String message, Throwable cause, String trackId) {
        super(message, cause);
        this.trackId = trackId;
    }
}
