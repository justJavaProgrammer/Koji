package com.odeyalo.discordbot.koji.service.event.listener;

import com.odeyalo.discordbot.koji.exception.CommandNotFoundException;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class SlashCommandInteractionEventListenerDispatcher implements JdaEventListener {
    private final Map<String, SlashCommandHandler> handlers;
    private final Logger logger = LoggerFactory.getLogger(SlashCommandInteractionEventListenerDispatcher.class);

    @Autowired
    public SlashCommandInteractionEventListenerDispatcher(List<SlashCommandHandler> handlers) {
        this.handlers = handlers.stream().collect(Collectors.toMap((handler) -> handler.getCommandData().getName(), Function.identity()));
    }

    @Override
    public Class<? extends GenericEvent> getSupportedEvent() {
        return SlashCommandInteractionEvent.class;
    }

    /**
     * Delegate the job to specific handlers based on event name
     * @param genericEvent - received event from discord
     * @throws CommandNotFoundException if command is not found
     */
    @Override
    public void onEventReceived(GenericEvent genericEvent) {
        SlashCommandInteractionEvent event = (SlashCommandInteractionEvent) genericEvent;

        String name = event.getName();
        SlashCommandHandler handler = handlers.get(name);

        if (handler == null) {
            logger.warn("Command with name: {} is not found and can't be handled!", name);
            throw new CommandNotFoundException(String.format("The command: %s was not found", name), event);
        }
        logger.debug("Delegate the actual job to: {}", handler);
        handler.handleCommand(event);
    }
}
