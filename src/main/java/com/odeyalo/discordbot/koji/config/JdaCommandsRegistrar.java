package com.odeyalo.discordbot.koji.config;

import com.odeyalo.discordbot.koji.service.event.listener.DiscordCommandDataProvider;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JdaCommandsRegistrar implements JdaConfigurer {
    private final List<DiscordCommandDataProvider> providers;

    public JdaCommandsRegistrar(List<DiscordCommandDataProvider> providers) {
        this.providers = providers;
    }

    @Override
    public void configure(JDA jda) {
        CommandListUpdateAction action = jda.updateCommands();

        providers.forEach((provider) -> action.addCommands(provider.getCommandData()));

        action.queue();
    }
}
