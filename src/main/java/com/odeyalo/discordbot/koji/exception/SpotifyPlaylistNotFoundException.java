package com.odeyalo.discordbot.koji.exception;

public class SpotifyPlaylistNotFoundException extends RuntimeException {
    private final String playlistId;
    public SpotifyPlaylistNotFoundException(String message, String playlistId) {
        super(message);
        this.playlistId = playlistId;
    }

    public SpotifyPlaylistNotFoundException(String message, Throwable cause, String playlistId) {
        super(message, cause);
        this.playlistId = playlistId;
    }
}
