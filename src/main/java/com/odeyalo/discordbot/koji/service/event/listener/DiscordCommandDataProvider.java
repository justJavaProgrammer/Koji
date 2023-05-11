package com.odeyalo.discordbot.koji.service.event.listener;

import net.dv8tion.jda.api.interactions.commands.build.CommandData;

/**
 * An interface to get info about {@link CommandData}
 */
public interface DiscordCommandDataProvider {
    /**
     * Returns CommandData supported by handler
     * @return - supported CommandData, never null
     */
    CommandData getCommandData();
}
