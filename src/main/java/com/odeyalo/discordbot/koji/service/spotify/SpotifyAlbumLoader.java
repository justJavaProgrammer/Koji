package com.odeyalo.discordbot.koji.service.spotify;

import se.michaelthelin.spotify.model_objects.specification.Album;

public interface SpotifyAlbumLoader {

    Album getAlbumById(String id);

}
