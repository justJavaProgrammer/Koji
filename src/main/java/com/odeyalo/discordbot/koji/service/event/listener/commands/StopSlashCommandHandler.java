package com.odeyalo.discordbot.koji.service.event.listener.commands;

import com.odeyalo.discordbot.koji.service.event.listener.SlashCommandHandler;
import com.odeyalo.discordbot.koji.service.player.state.AudioPlayerState;
import com.odeyalo.discordbot.koji.service.player.state.AudioPlayerStateRepository;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.springframework.stereotype.Component;

import java.awt.*;

import static com.odeyalo.discordbot.koji.config.KojiBotConfig.*;

@Component
public class StopSlashCommandHandler implements SlashCommandHandler {
    private final AudioPlayerStateRepository repository;

    public StopSlashCommandHandler(AudioPlayerStateRepository repository) {
        this.repository = repository;
    }
    @Override
    public CommandData getCommandData() {
        return Commands.slash("stop", "Stop the current track");
    }

    @Override
    public void handleCommand(SlashCommandInteractionEvent event) {
        AudioPlayerState state = repository.get(event.getGuild().getId());

        if (state == null) {
            event.reply("You need to run /play command first!").queue();
            return;
        }

        EmbedBuilder builder = new EmbedBuilder().setFooter(BOT_NAME, ICON_URL)
                .setAuthor("Stop status", null, SPINNING_RECORD_ICON_URL)
                .setColor(Color.PINK);

        AudioPlayer player = state.getPlayer();

        AudioTrack currentTrack = player.getPlayingTrack();

        if (currentTrack == null) {
            builder.appendDescription("Nothing is playing right now!");
            event.replyEmbeds(builder.build()).queue();
            return;
        }

        state.getScheduler().stopCurrentTrack();

        event.reply("Stopped! See the message that was sent!").setEphemeral(true).queue();
    }
}
