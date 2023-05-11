package com.odeyalo.discordbot.koji.service.event.listener;

import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.hooks.EventListener;

/**
 * An extension for {@link EventListener} to add metadata about supported event for implementation
 */
public interface JdaEventListener extends EventListener {

    Class<? extends GenericEvent> getSupportedEvent();

    @Override
    default void onEvent(GenericEvent genericEvent) {
        if (getSupportedEvent().equals(genericEvent.getClass())) {
            onEventReceived(genericEvent);
        }
    }

    void onEventReceived(GenericEvent genericEvent);
}
