package de.rjst.questsystem.listener.frontend;

import de.rjst.questsystem.config.bean.PluginListener;
import de.rjst.questsystem.model.gui.PageableGui;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.springframework.stereotype.Component;

@PluginListener
@Component
public class PageableGuiListener implements Listener {

    @EventHandler
    public static void apply(final InventoryClickEvent event) {
        final Inventory inventory = event.getInventory();
        final InventoryHolder holder = inventory.getHolder();

        if (holder instanceof final PageableGui pageableGui) {
            event.setCancelled(true);
            pageableGui.interact(event);
        }
    }
}
