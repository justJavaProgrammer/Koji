package com.odeyalo.discordbot.koji.service.event.listener;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public interface SlashCommandHandler extends DiscordCommandDataProvider {
    /**
     * Handle the command received from discord.
     * @param event - event received from discord
     */
    void handleCommand(SlashCommandInteractionEvent event);
}
