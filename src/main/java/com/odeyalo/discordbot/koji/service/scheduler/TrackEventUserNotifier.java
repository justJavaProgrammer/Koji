package com.odeyalo.discordbot.koji.service.scheduler;

import com.odeyalo.discordbot.koji.utils.TimeUtils;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;

import java.awt.*;
import java.util.List;

import static com.odeyalo.discordbot.koji.config.KojiBotConfig.*;

public class TrackEventUserNotifier implements TrackSchedulerEventListener {
    private final MessageChannel channel;

    public TrackEventUserNotifier(MessageChannel channel) {
        this.channel = channel;
    }

    @Override
    public void onTrackStarted(AudioTrack track) {

        AudioTrackInfo info = track.getInfo();


        MessageEmbed embed = new EmbedBuilder().setFooter(BOT_NAME, ICON_URL)
                .setDescription(info.title)
                .setAuthor("Started track", null, SPINNING_RECORD_ICON_URL)
                .setColor(Color.PINK)
                .addField("Author", info.author, true)
                .addField("Duration", TimeUtils.msToBeatifulString(info.length), true)
                .build();

        channel.sendMessageEmbeds(embed).queue();
    }

    @Override
    public void onTrackResumed(AudioTrack track) {
        AudioTrackInfo info = track.getInfo();

        MessageEmbed embed = new EmbedBuilder().setFooter(BOT_NAME, ICON_URL)
                .setDescription(info.title)
                .setAuthor("Resumed paused track", null, SPINNING_RECORD_ICON_URL)
                .setColor(Color.PINK)
                .addField("Author", info.author, true)
                .addField("Duration", TimeUtils.msToBeatifulString(info.length), true)
                .build();
        channel.sendMessageEmbeds(embed).queue();
    }

    @Override
    public void onTrackPaused(AudioPlayer player, AudioTrack track) {
        AudioTrackInfo info = track.getInfo();
        MessageEmbed embed = new EmbedBuilder().setFooter(BOT_NAME, ICON_URL)
                .setDescription(info.title)
                .setAuthor("Paused the current track", null, SPINNING_RECORD_ICON_URL)
                .setColor(Color.PINK)
                .addField("Author", info.author, true)
                .addField("Position", TimeUtils.msToBeatifulString(player.getPlayingTrack().getPosition()), true)
                .addField("Duration", TimeUtils.msToBeatifulString(info.length), true)
                .build();

        channel.sendMessageEmbeds(embed).queue();
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        channel.sendMessage("Track ended: " + track.getInfo().title).queue();
    }

    @Override
    public void onTrackAddedToQueue(AudioTrack track) {
        AudioTrackInfo info = track.getInfo();
        MessageEmbed embed = new EmbedBuilder().setFooter(BOT_NAME, ICON_URL)
                .setDescription(info.title)
                .setAuthor("Added to queue", null, SPINNING_RECORD_ICON_URL)
                .setColor(Color.PINK)
                .addField("Author", info.author, true)
                .addField("Duration", TimeUtils.msToBeatifulString(info.length), true)
                .build();

        channel.sendMessageEmbeds(embed).queue();
    }

    @Override
    public void onPlaylistAddedToQueue(AudioPlaylist audioPlaylist) {
        List<AudioTrack> tracks = audioPlaylist.getTracks();

        EmbedBuilder builder = new EmbedBuilder().setFooter(BOT_NAME, ICON_URL)
                .setDescription(audioPlaylist.getName())
                .setAuthor("Added playlist to the queue", null, SPINNING_RECORD_ICON_URL)
                .setColor(Color.PINK);

        for (AudioTrack track : tracks) {
            AudioTrackInfo info = track.getInfo();
            builder.appendDescription(info.title + " " + TimeUtils.msToBeatifulString(track.getDuration()) + "\n");
        }

        channel.sendMessageEmbeds(builder.build()).queue();
    }

    @Override
    public void onQueueCleared() {
        EmbedBuilder builder = new EmbedBuilder().setFooter(BOT_NAME, ICON_URL)
                .setAuthor("Cleared the queue!", null, SPINNING_RECORD_ICON_URL)
                .setColor(Color.PINK);
        channel.sendMessageEmbeds(builder.build()).queue();
    }
}
