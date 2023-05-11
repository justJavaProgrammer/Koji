package com.odeyalo.discordbot.koji.config;

import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;

import java.io.IOException;

@Configuration
public class SpotifyApiConfiguration {


    @Bean
    public SpotifyApi spotifyApi(@Value("${spotify.client.id}") String clientId,
                                 @Value("${spotify.client.secret}") String clientSecret) {
        return new SpotifyApi.Builder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .build();
    }

    @Bean
    public ClientCredentialsRequest credentials(SpotifyApi spotifyApi) {
        return spotifyApi.clientCredentials()
                .build();
    }

    @Bean
    public SpotifyAccessToken accessToken(SpotifyApi spotifyApi, ClientCredentialsRequest request) throws IOException, ParseException, SpotifyWebApiException {
        String accessToken = request.execute().getAccessToken();
        spotifyApi.setAccessToken(accessToken);
        return new SpotifyAccessToken(accessToken);
    }
}
