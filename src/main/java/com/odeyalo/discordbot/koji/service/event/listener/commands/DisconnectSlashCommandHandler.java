package com.odeyalo.discordbot.koji.service.event.listener.commands;

import com.odeyalo.discordbot.koji.service.event.listener.SlashCommandHandler;
import com.odeyalo.discordbot.koji.service.player.state.AudioPlayerState;
import com.odeyalo.discordbot.koji.service.player.state.AudioPlayerStateRepository;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.managers.AudioManager;
import org.springframework.stereotype.Component;

@Component
public class DisconnectSlashCommandHandler implements SlashCommandHandler {
    private final AudioPlayerStateRepository repository;

    public DisconnectSlashCommandHandler(AudioPlayerStateRepository repository) {
        this.repository = repository;
    }

    @Override
    public CommandData getCommandData() {
        return Commands.slash("disconnect", "Disconnect the bot from the voice channel. Do nothing if bot isn't connected to voice chat")
                .setGuildOnly(true);
    }

    @Override
    public void handleCommand(SlashCommandInteractionEvent event) {
        try {
            AudioManager manager = event.getGuild().getAudioManager();

            manager.closeAudioConnection();

            event.reply("Bot has been successfully disconnected from voice chat!")
                    .setEphemeral(true)
                    .queue();

        } catch (NullPointerException ex) {
            event.reply("Bot was not connected to the voice chat")
                    .setEphemeral(true)
                    .queue();
        }
    }
}
