package de.rjst.questsystem.config;

import de.rjst.questsystem.QuestSystem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@RequiredArgsConstructor
@Slf4j
@Configuration
public class ExternalLibConfig {

    @Lazy
    @Bean
    public Economy economy(final @NotNull Server server) {
        Economy result = null;
        final RegisteredServiceProvider<Economy> registeredServiceProvider = server.getServicesManager().getRegistration(Economy.class);
        if (registeredServiceProvider != null) {
            result = registeredServiceProvider.getProvider();
            log.info("Economy-Plugin {} injected", result.getName());
        } else {
            final PluginManager pluginManager = server.getPluginManager();
            pluginManager.disablePlugin(QuestSystem.getInstance());
            log.error("Economy not found -> Disable Plugin");
        }
        return result;
    }


}
