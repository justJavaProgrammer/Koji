package com.odeyalo.discordbot.koji.config;

import net.dv8tion.jda.api.JDA;

/**
 * Interface to configure the {@link JDA} object
 * Implementation can use {@link org.springframework.core.Ordered} to invoke configurer at specific index
 */
public interface JdaConfigurer {
    /**
     * Configure the given JDA object
     * @param jda - jda to configure
     */
    void configure(JDA jda);

}
