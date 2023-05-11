package com.odeyalo.discordbot.koji.exception;

import net.dv8tion.jda.api.events.GenericEvent;

public class AudioTrackNotFoundException extends KojiException {

    public AudioTrackNotFoundException(String message, GenericEvent event) {
        super(message, event);
    }

    public AudioTrackNotFoundException(String message, Throwable cause, GenericEvent event) {
        super(message, cause, event);
    }
}
