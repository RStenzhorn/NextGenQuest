package de.rjst.questsystem.listener.cache;

import de.rjst.questsystem.config.bean.PluginListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.function.Consumer;

@Slf4j
@RequiredArgsConstructor
@PluginListener
@Component
public class LoadPlayerListener implements Listener {

    @Qualifier("loadCacheConsumer")
    private final Consumer<UUID> loadCacheConsumer;

    @EventHandler
    public void apply(final @NotNull PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        final UUID uuid = player.getUniqueId();

        loadCacheConsumer.accept(uuid);
    }
}
