package de.rjst.questsystem.model.gui;

import de.rjst.questsystem.model.button.Button;
import de.rjst.questsystem.util.ValidatorUtil;
import lombok.Data;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

@Data
public class GuiPageImpl implements GuiPage {

    private static final int MAX_SIZE = 36;

    private List<Button> buttons;

    public GuiPageImpl() {
        buttons = new ArrayList<>();
    }

    public final void interact(final InventoryClickEvent event) {
        final int slot = event.getSlot();
        if (ValidatorUtil.isClickInInventory(event) && slot < buttons.size()) {
            final Button button = buttons.get(slot);
            if (button != null) {
                button.execute(event);
            }
        }
    }

    public final boolean hasSpace() {
        return buttons.size() < MAX_SIZE;
    }

    @Override
    public final void renderInventory(final Inventory inventory) {
        final int size = buttons.size();
        for (int i = 0; i < size; i++) {
            final Button button = buttons.get(i);
            inventory.setItem(i, button.getItem());
        }
    }

    public final void addButton(final Button button) {
        buttons.add(button);
    }

}
