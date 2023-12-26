package de.rjst.questsystem.util;

import de.rjst.questsystem.setting.NgqPermission;
import lombok.experimental.UtilityClass;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.permissions.Permissible;
import org.bukkit.permissions.Permission;
import org.jetbrains.annotations.NotNull;

@UtilityClass
public class ValidatorUtil {

    public boolean isNotPermitted(final @NotNull Permissible sender, final @NotNull NgqPermission ngqPermission) {
        final Permission paperPermission = ngqPermission.getPermission();
        return !sender.hasPermission(paperPermission);
    }

    public boolean isClickInInventory(final @NotNull InventoryClickEvent event) {
        final Inventory inventory = event.getInventory();
        return event.getRawSlot() <= inventory.getSize() && event.getSlotType() != InventoryType.SlotType.OUTSIDE;
    }
}
