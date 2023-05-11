package com.odeyalo.discordbot.koji.service.spotify;

import se.michaelthelin.spotify.model_objects.specification.Track;

public interface SpotifyTrackLoader {

    Track getTrackById(String id);

}
