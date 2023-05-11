package com.odeyalo.discordbot.koji.service.event.listener.commands;

import com.odeyalo.discordbot.koji.service.event.listener.SlashCommandHandler;
import net.dv8tion.jda.api.entities.channel.unions.AudioChannelUnion;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.springframework.stereotype.Component;

@Component
public class ConnectSlashCommandInteractionEventListener implements SlashCommandHandler {
//    private final AudioManager audioManager;

//    @Autowired
//    public ConnectSlashCommandInteractionEventListener(AudioManager audioManager) {
//        this.audioManager = audioManager;
//    }

    @Override
    public CommandData getCommandData() {
        return Commands.slash("connect", "Connect the bot to the voice channel")
                .setGuildOnly(true);
    }

    @Override
    public void handleCommand(SlashCommandInteractionEvent event) {
        try {
            AudioChannelUnion channel = event.getMember().getVoiceState().getChannel();
            event.getGuild().getAudioManager().openAudioConnection(channel);
//            new MessageEmbed() // todo: Create builder
//            MessageCreateData.fromEmbeds();
            event.reply("Connected to the voice channel: " + channel.getName())
                    .setEphemeral(false)
                    .queue();
        } catch (NullPointerException | IllegalArgumentException ex) {
            event.reply("You must be connected to the voice channel!").queue();
        }
    }
}
