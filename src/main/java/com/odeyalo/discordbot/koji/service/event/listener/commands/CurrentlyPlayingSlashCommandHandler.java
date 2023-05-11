package com.odeyalo.discordbot.koji.service.event.listener.commands;

import com.odeyalo.discordbot.koji.service.event.listener.SlashCommandHandler;
import com.odeyalo.discordbot.koji.service.player.state.AudioPlayerState;
import com.odeyalo.discordbot.koji.service.player.state.AudioPlayerStateRepository;
import com.odeyalo.discordbot.koji.utils.TimeUtils;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.springframework.stereotype.Component;

import java.awt.*;

import static com.odeyalo.discordbot.koji.config.KojiBotConfig.*;

@Component
public class CurrentlyPlayingSlashCommandHandler implements SlashCommandHandler {
    private final AudioPlayerStateRepository repository;

    public CurrentlyPlayingSlashCommandHandler(AudioPlayerStateRepository repository) {
        this.repository = repository;
    }

    @Override
    public CommandData getCommandData() {
        return Commands.slash("currently-playing", "See the current playing track!");
    }

    @Override
    public void handleCommand(SlashCommandInteractionEvent event) {
        AudioPlayerState state = repository.get(event.getGuild().getId());
        if (state == null) {
            event.reply("You need to run /play command first!").queue();
            return;
        }
        EmbedBuilder builder = new EmbedBuilder().setFooter(BOT_NAME, ICON_URL)
                .setAuthor("Now playing: ", null, SPINNING_RECORD_ICON_URL)
                .setColor(Color.PINK);

        AudioTrack playingTrack = state.getPlayer().getPlayingTrack();

        if (playingTrack == null) {
            MessageEmbed embed = builder.appendDescription("Nothing is playing right now! Type /play").build();
            event.replyEmbeds(embed).queue();
            return;
        }
        AudioTrackInfo info = playingTrack.getInfo();


        MessageEmbed embed = builder
                .setDescription(info.title)
                .addField("Author", info.author, true)
                .addField("Position", TimeUtils.msToBeatifulString(playingTrack.getPosition()), true)
                .addField("Duration", TimeUtils.msToBeatifulString(info.length), true)
                .build();

        event.replyEmbeds(embed).queue();
    }
}
