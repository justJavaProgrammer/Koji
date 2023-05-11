package com.odeyalo.discordbot.koji.config;

import com.odeyalo.discordbot.koji.service.event.listener.support.JdaEventListenerContainer;
import net.dv8tion.jda.api.JDA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class JdaEventListenersRegistrar implements JdaConfigurer {
    private final JdaEventListenerContainer container;
    private final Logger logger = LoggerFactory.getLogger(JdaEventListenersRegistrar.class);

    public JdaEventListenersRegistrar(JdaEventListenerContainer container) {
        this.container = container;
    }

    @Override
    public void configure(JDA jda) {
        doListenersRegistry(jda);
    }

    protected void doListenersRegistry(JDA jda) {
        container.getListeners().values().forEach(
                (listener) -> {
                    jda.addEventListener(listener);
                    logger.debug("Added listener: {}", listener);
                }
        );
    }
}
