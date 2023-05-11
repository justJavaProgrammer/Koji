package com.odeyalo.discordbot.koji.service.event.listener.commands;

import com.odeyalo.discordbot.koji.service.event.listener.SlashCommandHandler;
import com.odeyalo.discordbot.koji.service.player.state.AudioPlayerState;
import com.odeyalo.discordbot.koji.service.player.state.AudioPlayerStateRepository;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.springframework.stereotype.Component;

import java.awt.*;

import static com.odeyalo.discordbot.koji.config.KojiBotConfig.*;

@Component
public class SkipTrackSlashCommandHandler implements SlashCommandHandler {
    private final AudioPlayerStateRepository repository;

    public SkipTrackSlashCommandHandler(AudioPlayerStateRepository repository) {
        this.repository = repository;
    }

    @Override
    public CommandData getCommandData() {
        return Commands.slash("skip", "Skip the current track");
    }

    @Override
    public void handleCommand(SlashCommandInteractionEvent event) {
        AudioPlayerState state = repository.get(event.getGuild().getId());
        if (state == null) {
            event.reply("type /play to run this command!").queue();
            return;
        }
        state.getScheduler().nextTrack();
        EmbedBuilder builder = new EmbedBuilder().setFooter(BOT_NAME, ICON_URL)
                .setDescription("Skipped!")
                .setColor(Color.PINK);

        event.replyEmbeds(builder.build()).queue();

    }
}
