package com.odeyalo.discordbot.koji.service.spotify;

import com.odeyalo.discordbot.koji.exception.SpotifyAlbumNotFoundException;
import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Component;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Album;
import se.michaelthelin.spotify.requests.data.albums.GetAlbumRequest;

import java.io.IOException;

@Component
public class SpotifyApiSpotifyAlbumLoader implements SpotifyAlbumLoader {
    private final SpotifyApi spotifyApi;

    public SpotifyApiSpotifyAlbumLoader(SpotifyApi spotifyApi) {
        this.spotifyApi = spotifyApi;
    }

    @Override
    public Album getAlbumById(String id) {
        GetAlbumRequest req = spotifyApi.getAlbum(id).build();
        try {
            return req.execute();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            throw new SpotifyAlbumNotFoundException("Album not found", e, id);
        }
    }
}
