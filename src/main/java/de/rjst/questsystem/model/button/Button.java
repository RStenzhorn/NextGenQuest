package de.rjst.questsystem.model.button;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public interface Button {

    ItemStack getItem();

    void execute(InventoryClickEvent event);
}
