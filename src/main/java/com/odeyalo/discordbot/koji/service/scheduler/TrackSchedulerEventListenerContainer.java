package com.odeyalo.discordbot.koji.service.scheduler;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TrackSchedulerEventListenerContainer implements TrackSchedulerEventListenerRegistry {
    protected final List<TrackSchedulerEventListener> listeners;

    public TrackSchedulerEventListenerContainer(List<TrackSchedulerEventListener> listeners) {
        this.listeners = listeners;
    }

    @Override
    public void add(TrackSchedulerEventListener listener) {
        listeners.add(listener);
    }

    @Override
    public void remove(TrackSchedulerEventListener listener) {
        listeners.remove(listener);
    }

    @Override
    public List<TrackSchedulerEventListener> getListeners() {
        return listeners;
    }
}
