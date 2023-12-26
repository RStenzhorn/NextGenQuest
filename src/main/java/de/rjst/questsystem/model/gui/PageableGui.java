package de.rjst.questsystem.model.gui;

import de.rjst.questsystem.model.button.Button;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public interface PageableGui {

    void interact(InventoryClickEvent event);

    void addButton(Button button);

    Inventory render();
}
