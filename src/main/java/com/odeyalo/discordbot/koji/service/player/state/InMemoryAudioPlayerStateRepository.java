package com.odeyalo.discordbot.koji.service.player.state;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryAudioPlayerStateRepository implements AudioPlayerStateRepository {
    private final Map<String, AudioPlayerState> handlers = new ConcurrentHashMap<>();

    @Override
    public boolean containsHandler(String guildId) {
        return handlers.containsKey(guildId);
    }

    @Override
    public void setHandler(String guildId, AudioPlayerState audioSendHandler) {
        handlers.put(guildId, audioSendHandler);
    }

    @Override
    public void putIfAbsent(String guildId, AudioPlayerState handler) {
        handlers.putIfAbsent(guildId, handler);
    }

    @Override
    public AudioPlayerState get(String guildId) {
        return handlers.get(guildId);
    }

    @Override
    public void removeHandler(String guildId) {
        handlers.get(guildId);
    }
}
