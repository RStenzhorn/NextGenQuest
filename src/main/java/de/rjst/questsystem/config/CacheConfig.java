package de.rjst.questsystem.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.bukkit.Server;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static de.rjst.questsystem.config.CacheNames.*;

@Configuration
@EnableCaching
public class CacheConfig {

    // Caching fixen und nutzen
    @Bean
    public Cache questFlyCache(final @NotNull Server server) {
        return new CaffeineCache(FLY,
                Caffeine.newBuilder()
                        .maximumSize(server.getMaxPlayers())
                        .build());
    }

    @Bean
    public Cache questSwimCache(final @NotNull Server server) {
        return new CaffeineCache(SWIM,
                Caffeine.newBuilder()
                        .maximumSize(server.getMaxPlayers())
                        .build());
    }

    @Bean
    public Cache questMoveCache(final @NotNull Server server) {
        return new CaffeineCache(MOVE,
                Caffeine.newBuilder()
                        .maximumSize(server.getMaxPlayers())
                        .build());
    }

    @Bean
    public Cache questVehicleMoveCache(final @NotNull Server server) {
        return new CaffeineCache(VEHICLE_MOVE,
                Caffeine.newBuilder()
                        .maximumSize(server.getMaxPlayers())
                        .build());
    }

    @Bean
    public CacheManager cacheManager(final List<Cache> caches) {
        final SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
        simpleCacheManager.setCaches(caches);
        return simpleCacheManager;
    }

}
