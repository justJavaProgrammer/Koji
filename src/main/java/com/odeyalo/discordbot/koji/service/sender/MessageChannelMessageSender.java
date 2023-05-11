package com.odeyalo.discordbot.koji.service.sender;

import net.dv8tion.jda.api.entities.MessageEmbed;

public interface MessageChannelMessageSender {

    void sendMessage(MessageEmbed embed);

    void sendMessage(String rawContent);
}
