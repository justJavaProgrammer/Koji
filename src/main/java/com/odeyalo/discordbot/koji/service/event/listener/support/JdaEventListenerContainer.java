package com.odeyalo.discordbot.koji.service.event.listener.support;

import com.odeyalo.discordbot.koji.service.event.listener.JdaEventListener;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JdaEventListenerContainer implements JdaEventListenerRegistry {
    private final Map<Class<? extends GenericEvent>, EventListener> listeners;
    private final Logger logger = LoggerFactory.getLogger(JdaEventListenerContainer.class);

    @Autowired
    public JdaEventListenerContainer(List<JdaEventListener> eventListeners) {
        if (eventListeners.isEmpty()) {
            logger.warn("Empty list of listener has been received. Check your configuration again");
        }
        this.listeners = eventListeners.stream().collect(Collectors.toMap(JdaEventListener::getSupportedEvent, Function.identity()));
    }

    public JdaEventListenerContainer(Map<Class<? extends GenericEvent>, EventListener> listeners) {
        Assert.notNull(listeners, "Listeners map must be not null!");
        this.listeners = listeners;
    }

    @Override
    public void addEventListener(JdaEventListener eventListener) {
        listeners.put(eventListener.getSupportedEvent(), eventListener);
    }

    @Override
    public Map<Class<? extends GenericEvent>, EventListener> getListeners() {
        return listeners;
    }

    @Override
    public void remove(JdaEventListener listener) {
        listeners.values().remove(listener);
    }
}
