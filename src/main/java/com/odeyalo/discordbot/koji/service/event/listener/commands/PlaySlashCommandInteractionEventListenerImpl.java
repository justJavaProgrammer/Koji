package com.odeyalo.discordbot.koji.service.event.listener.commands;

import com.odeyalo.discordbot.koji.service.PlayTrackService;
import com.odeyalo.discordbot.koji.service.event.listener.SlashCommandHandler;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.springframework.stereotype.Component;

import static com.odeyalo.discordbot.koji.service.PlayTrackService.PlayingStatus.LOADING;

@Component
public class PlaySlashCommandInteractionEventListenerImpl implements SlashCommandHandler {

    private final PlayTrackService playTrackService;

    public PlaySlashCommandInteractionEventListenerImpl(PlayTrackService playTrackService) {
        this.playTrackService = playTrackService;
    }

    @Override
    public void handleCommand(SlashCommandInteractionEvent event) {
        String url = event.getOption("url").getAsString();

        PlayTrackService.PlayingResult result = playTrackService.play(event, url);

        if (result.getStatus() == LOADING) {
            event.reply("Track is currently loading...").setEphemeral(true).queue();
            return;
        }

        event.reply("Success").setEphemeral(true).queue();;
    }

    @Override
    public CommandData getCommandData() {
        return Commands.slash("play", "Play the music from URL! /play [URL]")
                .addOption(OptionType.STRING, "url", "Url to play song", true)
                .setGuildOnly(true);
    }
}
