package com.odeyalo.discordbot.koji.service.spotify;

import com.odeyalo.discordbot.koji.exception.SpotifyPlaylistNotFoundException;
import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Component;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Playlist;
import se.michaelthelin.spotify.requests.data.playlists.GetPlaylistRequest;

import java.io.IOException;

@Component
public class SpotifyApiSpotifyPlaylistLoader implements SpotifyPlaylistLoader {
    private final SpotifyApi spotifyApi;

    public SpotifyApiSpotifyPlaylistLoader(SpotifyApi spotifyApi) {
        this.spotifyApi = spotifyApi;
    }

    @Override
    public Playlist getPlaylistById(String id) {
        GetPlaylistRequest req = spotifyApi.getPlaylist(id).build();
        try {
            return req.execute();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            throw new SpotifyPlaylistNotFoundException("Playlist not found!", e, id);
        }
    }
}
