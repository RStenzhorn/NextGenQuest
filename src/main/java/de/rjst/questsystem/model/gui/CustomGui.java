package de.rjst.questsystem.model.gui;

import de.rjst.questsystem.model.button.Button;
import de.rjst.questsystem.model.button.ButtonImpl;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public interface CustomGui {

    void interact(InventoryClickEvent event);

    void setButton(int x, int y, Button button);

    Inventory getInventory();
}
