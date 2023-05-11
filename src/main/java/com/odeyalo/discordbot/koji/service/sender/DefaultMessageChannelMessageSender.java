package com.odeyalo.discordbot.koji.service.sender;

import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;

public class DefaultMessageChannelMessageSender implements MessageChannelMessageSender {
    private final MessageChannel messageChannel;

    public DefaultMessageChannelMessageSender(MessageChannel messageChannel) {
        this.messageChannel = messageChannel;
    }

    @Override
    public void sendMessage(MessageEmbed embed) {
        messageChannel.sendMessageEmbeds(embed).queue();
    }

    @Override
    public void sendMessage(String rawContent) {
        messageChannel.sendMessage(rawContent).queue();
    }
}
