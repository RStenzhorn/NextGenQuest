package de.rjst.questsystem.listener.cache;

import de.rjst.questsystem.config.bean.PluginListener;
import de.rjst.questsystem.database.entity.QuestEntity;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@PluginListener
@Component
public class UnloadPlayerListener implements Listener {

    private final CacheManager cacheManager;

    @EventHandler
    public void apply (final @NotNull PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        final UUID uuid = player.getUniqueId();

        final Cache quests = cacheManager.getCache("quests");
        if (quests != null) {
            quests.evictIfPresent(uuid);
        }
    }

}
