package com.odeyalo.discordbot.koji.service.spotify;

import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioTrack;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeSearchProvider;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.BasicAudioPlaylist;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.michaelthelin.spotify.model_objects.specification.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class HardCodedSpotifyAdaptedAudioProvider implements SpotifyAdaptedAudioProvider {
    public static final String SPOTIFY_TRACK_PREFIX = "https://open.spotify.com/track/";
    public static final String SPOTIFY_ALBUM_PREFIX = "https://open.spotify.com/album/";
    public static final String SPOTIFY_PLAYLIST_PREFIX = "https://open.spotify.com/playlist/";
    private final Logger logger = LoggerFactory.getLogger(HardCodedSpotifyAdaptedAudioProvider.class);
    private final SpotifyTrackLoader trackLoader;
    private final SpotifyAlbumLoader albumLoader;
    private final SpotifyPlaylistLoader playlistLoader;

    @Autowired
    public HardCodedSpotifyAdaptedAudioProvider(SpotifyTrackLoader trackLoader, SpotifyAlbumLoader albumLoader, SpotifyPlaylistLoader playlistLoader) {
        this.trackLoader = trackLoader;
        this.albumLoader = albumLoader;
        this.playlistLoader = playlistLoader;
    }

    @Override
    public List<AudioTrack> getAudioTracks(String source) {
        YoutubeSearchProvider provider = new YoutubeSearchProvider();
        if (source.startsWith(SPOTIFY_TRACK_PREFIX)) {
            AudioTrack audioTrack = convertToAudioTrack(source, provider);
            return Collections.singletonList(audioTrack);
        }
        if (source.startsWith(SPOTIFY_ALBUM_PREFIX)) {
            return getAlbumAudioTracks(source, provider);
        }
        if (source.startsWith(SPOTIFY_PLAYLIST_PREFIX)) {
            return getPlaylistAudioTracks(source, provider);
        }
        return null;
    }

    @NotNull
    private List<AudioTrack> getPlaylistAudioTracks(String source, YoutubeSearchProvider provider) {
        List<AudioTrack> result = new ArrayList<>();
        String playlistId = source.substring(SPOTIFY_PLAYLIST_PREFIX.length(), source.lastIndexOf("?"));
        Playlist playlist = playlistLoader.getPlaylistById(playlistId);
        for (PlaylistTrack item : playlist.getTracks().getItems()) {
            String uri = item.getTrack().getHref();
            AudioTrack audioTrack = convertToAudioTrack(uri, provider);
            result.add(audioTrack);
        }
        return result;
    }

    @NotNull
    private List<AudioTrack> getAlbumAudioTracks(String source, YoutubeSearchProvider provider) {
        String albumId = source.substring(SPOTIFY_ALBUM_PREFIX.length(), source.lastIndexOf("?"));
        Album album = albumLoader.getAlbumById(albumId);
        List<AudioTrack> result = new ArrayList<>();

        for (TrackSimplified track : album.getTracks().getItems()) {
            String uri = track.getHref();
            AudioTrack audioTrack = convertToAudioTrack(uri, provider);
            result.add(audioTrack);
        }
        return result;
    }

    private AudioTrack convertToAudioTrack(String source, YoutubeSearchProvider provider) {
        String query = getTrackSearchQuery(source);
        BasicAudioPlaylist item = (BasicAudioPlaylist) provider.loadSearchResult(query, (info) -> new YoutubeAudioTrack(info, new YoutubeAudioSourceManager()));
        return item.getTracks().get(0);
    }

    @NotNull
    private String getTrackSearchQuery(String source) {
        int endIndex = source.lastIndexOf("?") == -1 ? source.length() : source.lastIndexOf("?");
        String id = source.substring(source.lastIndexOf('/') + 1, endIndex);
        Track track = trackLoader.getTrackById(id);
        String query = track.getName() + " " + track.getArtists()[0].getName();
        logger.info("Built the search query from spofity: {}", query);
        return query;
    }
}
