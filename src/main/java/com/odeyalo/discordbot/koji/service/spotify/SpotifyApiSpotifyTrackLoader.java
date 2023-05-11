package com.odeyalo.discordbot.koji.service.spotify;

import com.odeyalo.discordbot.koji.exception.SpotifyTrackNotFoundException;
import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.tracks.GetTrackRequest;

import java.io.IOException;

@Component
public class SpotifyApiSpotifyTrackLoader implements SpotifyTrackLoader {
    private final SpotifyApi spotifyApi;

    @Autowired
    public SpotifyApiSpotifyTrackLoader(SpotifyApi spotifyApi) {
        this.spotifyApi = spotifyApi;

    }

    @Override
    public Track getTrackById(String id) {
        GetTrackRequest getTrackRequest = spotifyApi.getTrack(id).build();
        try {
            return getTrackRequest.execute();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            throw new SpotifyTrackNotFoundException("Track by id not found", e, id);
        }
    }
}
