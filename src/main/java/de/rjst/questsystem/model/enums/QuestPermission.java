package de.rjst.questsystem.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginManager;

@RequiredArgsConstructor
@Getter
public enum QuestPermission {

    QUEST_CORE("quest.core"),
    QUEST_ADMIN("quest.admin");

    private final Permission permission;

    QuestPermission(final String value) {
        permission = new Permission(value);
        final PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.addPermission(permission);
    }
}
