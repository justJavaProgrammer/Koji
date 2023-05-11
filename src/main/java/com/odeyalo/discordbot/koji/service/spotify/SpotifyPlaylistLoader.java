package com.odeyalo.discordbot.koji.service.spotify;

import se.michaelthelin.spotify.model_objects.specification.Playlist;

public interface SpotifyPlaylistLoader {

    Playlist getPlaylistById(String id);

}
