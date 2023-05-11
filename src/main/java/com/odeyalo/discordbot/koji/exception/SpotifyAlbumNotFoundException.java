package com.odeyalo.discordbot.koji.exception;

public class SpotifyAlbumNotFoundException extends RuntimeException {
    private final String albumId;
    public SpotifyAlbumNotFoundException(String message, String albumId) {
        super(message);
        this.albumId = albumId;
    }

    public SpotifyAlbumNotFoundException(String message, Throwable cause, String albumId) {
        super(message, cause);
        this.albumId = albumId;
    }
}
