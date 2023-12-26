package de.rjst.questsystem.model.gui;

import com.google.common.collect.Maps;
import de.rjst.questsystem.model.button.Button;
import de.rjst.questsystem.model.button.ButtonImpl;
import de.rjst.questsystem.util.FrontendUtil;
import de.rjst.questsystem.util.ValidatorUtil;
import lombok.Data;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.Map;


@Data
public class CustomGuiImpl implements InventoryHolder, CustomGui {

    private final Inventory inventory;

    private final Map<Integer, Button> buttons;

    public CustomGuiImpl(final Component title, final Integer rows) {
        super();
        inventory = Bukkit.createInventory(this, rows * 9, title);
        buttons = Maps.newConcurrentMap();
    }

    @Override
    public final void interact(final InventoryClickEvent event) {
        if (ValidatorUtil.isClickInInventory(event)) {
            final Button button = buttons.get(event.getSlot());
            if (button != null) {
                button.execute(event);
            }
        }
    }


    @Override
    public final void setButton(final int x, final int y, final Button button) {
        final int position = FrontendUtil.getItemPosition(x, y);
        buttons.put(position, button);
        inventory.setItem(position, button.getItem());
    }

}
