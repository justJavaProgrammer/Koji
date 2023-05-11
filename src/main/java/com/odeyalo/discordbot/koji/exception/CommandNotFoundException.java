package com.odeyalo.discordbot.koji.exception;

import net.dv8tion.jda.api.events.GenericEvent;

public class CommandNotFoundException extends KojiException {
    public CommandNotFoundException(String message, GenericEvent event) {
        super(message, event);
    }

    public CommandNotFoundException(String message, Throwable cause, GenericEvent event) {
        super(message, cause, event);
    }
}
