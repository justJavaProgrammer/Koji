package com.odeyalo.discordbot.koji.service.scheduler;

import java.util.List;

public interface TrackSchedulerEventListenerRegistry {

    void add(TrackSchedulerEventListener listener);

    void remove(TrackSchedulerEventListener listener);

    List<TrackSchedulerEventListener> getListeners();

}
