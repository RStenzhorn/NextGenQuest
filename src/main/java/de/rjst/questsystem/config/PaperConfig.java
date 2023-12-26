package de.rjst.questsystem.config;

import de.rjst.questsystem.NextGenQuest;
import de.rjst.questsystem.config.bean.PaperPluginCommand;
import de.rjst.questsystem.config.bean.PluginCommand;
import de.rjst.questsystem.config.bean.PluginListener;
import lombok.extern.slf4j.Slf4j;
import org.bukkit.Server;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Configuration
public class PaperConfig {

    @Bean
    public JavaPlugin plugin() {
        return JavaPlugin.getPlugin(NextGenQuest.class);
    }

    @Bean
    public Server server(final @NotNull JavaPlugin plugin) {
        return plugin.getServer();
    }

    @Bean
    public CommandMap commandMap(final @NotNull Server server) {
        return server.getCommandMap();
    }

    @Bean
    public PluginManager pluginManager(final @NotNull Server server) {
        return server.getPluginManager();
    }

    @Bean
    public BukkitScheduler bukkitScheduler(final @NotNull Server server) {
        return server.getScheduler();
    }

    @Bean
    public Executor mainThreadExecutor(final @NotNull BukkitScheduler bukkitScheduler, final JavaPlugin plugin) {
        return bukkitScheduler.getMainThreadExecutor(plugin);
    }

    @Bean
    @Primary
    public ClassLoader pluginClassLoader(final @NotNull JavaPlugin plugin) {
        return plugin.getClass().getClassLoader();
    }

    @Bean
    public ApplicationRunner registerListeners(final @NotNull List<Listener> listeners, final JavaPlugin plugin, final PluginManager pluginManager) {
        final AtomicInteger registeredListener = new AtomicInteger();

        listeners.forEach(listener -> {
            final Class<? extends Listener> clazz = listener.getClass();
            final PluginListener annotation = clazz.getAnnotation(PluginListener.class);
            if (annotation != null) {
                pluginManager.registerEvents(listener, plugin);
                registeredListener.getAndIncrement();
            }
        });

        log.info("Listener: {}", registeredListener.get());
        return args -> {
        };
    }

    @Bean
    public ApplicationRunner registerCommands(final @NotNull List<CommandExecutor> commandExecutors, final JavaPlugin plugin, final CommandMap commandMap) {
        final AtomicInteger registeredCommand = new AtomicInteger();

        commandExecutors.forEach(commandExecutor -> {
            final Class<? extends CommandExecutor> clazz = commandExecutor.getClass();
            final PluginCommand annotation = clazz.getAnnotation(PluginCommand.class);
            if (annotation != null) {
                final String name = annotation.value();
                commandMap.register(plugin.getName(), new PaperPluginCommand(name, commandExecutor));
                registeredCommand.getAndIncrement();
            }
        });

        log.info("Commands: {} ", registeredCommand.get());
        return args -> {
        };
    }


}

