package com.odeyalo.discordbot.koji.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.dv8tion.jda.api.events.GenericEvent;

@EqualsAndHashCode(callSuper = true)
@Data
public class KojiException extends RuntimeException {
    private final GenericEvent event;

    public KojiException(String message, GenericEvent event) {
        super(message);
        this.event = event;
    }

    public KojiException(String message, Throwable cause, GenericEvent event) {
        super(message, cause);
        this.event = event;
    }
}
