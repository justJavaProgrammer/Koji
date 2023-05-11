package com.odeyalo.discordbot.koji.service.event.listener.support;

import com.odeyalo.discordbot.koji.service.event.listener.JdaEventListener;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.hooks.EventListener;

import java.util.Map;

public interface JdaEventListenerRegistry {

    void addEventListener(JdaEventListener eventListener);

    Map<Class<? extends GenericEvent>, EventListener> getListeners();

    void remove(JdaEventListener listener);
}
