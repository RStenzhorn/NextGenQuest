package de.rjst.questsystem.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

@UtilityClass
public class PaperUtil {

    @NotNull
    public Optional<Player> getPlayer(@NotNull final CommandSender sender) {
        Player result = null;

        if (sender instanceof final Player player) {
            result = player;
        }
        return Optional.ofNullable(result);
    }

    @NotNull
    public Locale getLocale(@NotNull final CommandSender sender) {
        var result = Locale.ENGLISH;

        final Optional<Player> optionalPlayer = getPlayer(sender);
        if (optionalPlayer.isPresent()) {
            final Player player = optionalPlayer.get();
            result = player.locale();
        }
        return result;
    }

    @NotNull
    public Optional<OfflinePlayer> getOfflinePlayer(@Nullable final UUID uuid) {
        OfflinePlayer result = null;

        if (uuid != null) {
            result = Bukkit.getOfflinePlayer(uuid);
        }
        return Optional.ofNullable(result);
    }

    @NotNull
    public Optional<Player> getPlayer(@NotNull final String name) {
        return Optional.ofNullable(Bukkit.getPlayer(name));
    }

    public @NotNull Component getMessage(final String message) {
        final String replacedColor = replaceLegacyColors(message);
        return MiniMessage.miniMessage().deserialize(replacedColor);
    }

    public Biome getBioMeByLocation(final Location location) {
        final World world = location.getWorld();
        return world.getBiome(location);
    }


    private final Map<String, String> COLOR_MAP = new HashMap<>();

    static {
        COLOR_MAP.put("&0", "<#000000>");
        COLOR_MAP.put("&1", "<#0000AA>");
        COLOR_MAP.put("&2", "<#00AA00>");
        COLOR_MAP.put("&3", "<#00AAAA>");
        COLOR_MAP.put("&4", "<#AA0000>");
        COLOR_MAP.put("&5", "<#AA00AA>");
        COLOR_MAP.put("&6", "<#FFAA00>");
        COLOR_MAP.put("&7", "<#AAAAAA>");
        COLOR_MAP.put("&8", "<#555555>");
        COLOR_MAP.put("&9", "<#5555FF>");
        COLOR_MAP.put("&a", "<#55FF55>");
        COLOR_MAP.put("&b", "<#55FFFF>");
        COLOR_MAP.put("&c", "<#FF5555>");
        COLOR_MAP.put("&d", "<#FF55FF>");
        COLOR_MAP.put("&e", "<#FFFF55>");
        COLOR_MAP.put("&f", "<#FFFFFF>");
    }

    private String replaceLegacyColors(final String message) {
        String result = message;
        for (final Map.Entry<String, String> entry : COLOR_MAP.entrySet()) {
            result = result.replace(entry.getKey(), entry.getValue());
        }
        return result;
    }


}
