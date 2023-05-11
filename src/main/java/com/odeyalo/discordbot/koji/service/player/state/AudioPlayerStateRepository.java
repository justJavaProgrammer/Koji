package com.odeyalo.discordbot.koji.service.player.state;

/**
 * Repository to access the player states.
 * One player per guild.
 * Used to make easy access for handler between different guilds
 */
public interface AudioPlayerStateRepository {
    /**
     * Check if the guild contains AudioPlayerState
     * @param guildId - server id
     * @return - true if the server already contains AudioPlayerState, false otherwise
     */
    boolean containsHandler(String guildId);

    /**
     * Associate the AudioPlayerState with guild.
     * Method replaced the old AudioSendHandler and set a new one
     * @param guildId - server id
     * @param state - state to set
     */
    void setHandler(String guildId, AudioPlayerState state);

    /**
     * Associate AudioPlayerState with guild, do nothing if the guild already has AudioPlayerState
     * @param guildId - server id
     * @param handler - handler to set
     */
    void putIfAbsent(String guildId, AudioPlayerState handler);

    /**
     * Return the AudioPlayerState associated with guild.
     * @param guildId - server id
     * @return - AudioPlayerState associated with guild, null otherwise
     */
    AudioPlayerState get(String guildId);

    /**
     * Remove the association between guild and AudioPlayerState
     * @param guildId - server id
     */
    void removeHandler(String guildId);
}
