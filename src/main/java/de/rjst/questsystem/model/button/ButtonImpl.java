package de.rjst.questsystem.model.button;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

@Data
@NoArgsConstructor
public class ButtonImpl implements Button {

    private ItemStack item;

    private Consumer<? super InventoryClickEvent> action;


    public ButtonImpl(final ItemStack itemStack) {
        this(itemStack, event -> {});
    }

    public ButtonImpl(final ItemStack itemStack, final Consumer<? super InventoryClickEvent> action) {
        this.item = itemStack;
        this.action = action;
    }

    @Override
    public final void execute(final InventoryClickEvent event) {
        action.accept(event);
    }
}
