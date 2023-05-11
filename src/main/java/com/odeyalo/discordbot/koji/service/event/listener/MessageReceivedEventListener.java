package com.odeyalo.discordbot.koji.service.event.listener;

import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Component;

@Component
public class MessageReceivedEventListener implements JdaEventListener {

    @Override
    public Class<? extends GenericEvent> getSupportedEvent() {
        return MessageReceivedEvent.class;
    }

    @Override
    public void onEventReceived(GenericEvent genericEvent) {
//        MessageReceivedEvent event = (MessageReceivedEvent) genericEvent;
//        System.out.println("Message has been received: " + event.getMessage().getContentDisplay());
    }
}
