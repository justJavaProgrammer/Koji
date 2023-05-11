package com.odeyalo.discordbot.koji.service.event.listener.commands;

import com.odeyalo.discordbot.koji.service.event.listener.SlashCommandHandler;
import com.odeyalo.discordbot.koji.service.player.state.AudioPlayerState;
import com.odeyalo.discordbot.koji.service.player.state.AudioPlayerStateRepository;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.springframework.stereotype.Component;

@Component
public class ResumeSlashCommandHandler implements SlashCommandHandler {
    private final AudioPlayerStateRepository repository;

    public ResumeSlashCommandHandler(AudioPlayerStateRepository repository) {
        this.repository = repository;
    }

    @Override
    public CommandData getCommandData() {
        return Commands.slash("resume", "Resume the stopped track!");
    }

    @Override
    public void handleCommand(SlashCommandInteractionEvent event) {
        AudioPlayerState state = repository.get(event.getGuild().getId());

        if (state == null) {
            event.reply("You need to run /play command first!").queue();
            return;
        }

        state.getScheduler().resume();
    }
}
