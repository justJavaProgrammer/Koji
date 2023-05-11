package com.odeyalo.discordbot.koji.service.event.listener.commands;

import com.odeyalo.discordbot.koji.service.event.listener.SlashCommandHandler;
import com.odeyalo.discordbot.koji.service.player.state.AudioPlayerState;
import com.odeyalo.discordbot.koji.service.player.state.AudioPlayerStateRepository;
import com.odeyalo.discordbot.koji.utils.TimeUtils;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.List;

import static com.odeyalo.discordbot.koji.config.KojiBotConfig.*;

@Component
public class QueueSlashCommandHandler implements SlashCommandHandler {
    private final AudioPlayerStateRepository repo;

    public QueueSlashCommandHandler(AudioPlayerStateRepository repo) {
        this.repo = repo;
    }

    @Override
    public CommandData getCommandData() {
        return Commands.slash("queue", "See the queue");
    }

    @Override
    public void handleCommand(SlashCommandInteractionEvent event) {
        AudioPlayerState state = repo.get(event.getGuild().getId());
        if (state == null) {
            event.reply("You need to run /play command first!").queue();
            return;
        }
        List<AudioTrack> nextTracks = state.getScheduler().getNextTracks();

        EmbedBuilder builder = new EmbedBuilder().setFooter(BOT_NAME, ICON_URL)
                .setAuthor("Current queue", null, SPINNING_RECORD_ICON_URL)
                .setColor(Color.PINK);
        AudioTrack currentTrack = state.getPlayer().getPlayingTrack();

        if (currentTrack != null) {
            AudioTrackInfo info = currentTrack.getInfo();

            StringBuilder sb = new StringBuilder()
                    .append("```-> ")
                    .append(info.title)
                    .append(" ")
                    .append(TimeUtils.msToBeatifulString(currentTrack.getPosition()))
                    .append("/")
                    .append(TimeUtils.msToBeatifulString(currentTrack.getDuration()))
                    .append("```");

            builder.appendDescription(sb.toString());

        }
        for (AudioTrack track : nextTracks) {
            AudioTrackInfo info = track.getInfo();
            builder.appendDescription(info.title + " " + TimeUtils.msToBeatifulString(track.getDuration()) + "\n");
        }

        event.replyEmbeds(builder.build()).queue();
    }
}
