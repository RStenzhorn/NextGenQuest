package de.rjst.questsystem.setting;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginManager;

@RequiredArgsConstructor
@Getter
public enum NgqPermission {

    QUEST_CORE("quest.core"),
    QUEST_ADMIN("quest.admin");

    private final Permission permission;

    NgqPermission(final String value) {
        permission = new Permission(value);
    }
}
