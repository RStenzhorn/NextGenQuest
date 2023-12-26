package de.rjst.questsystem.model.gui;

import de.rjst.questsystem.model.button.Button;
import de.rjst.questsystem.model.button.ButtonImpl;
import de.rjst.questsystem.util.FrontendUtil;
import de.rjst.questsystem.util.GuiItem;
import de.rjst.questsystem.util.Heads;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;
import java.util.SortedMap;
import java.util.TreeMap;

@Slf4j
@Data
public class PageableGuiImpl implements InventoryHolder, PageableGui {

    private static final Integer PAGE_SIZE = 6;
    private static final int PREVIOUS_BUTTON_INDEX = 48;
    private static final int NUMBER_BUTTON_INDEX = 49;
    private static final int NEXT_BUTTON_INDEX = 50;
    private static final int BACK_BUTTON_INDEX = 53;

    private Inventory inventory;

    private SortedMap<Integer, GuiPage> pages;
    private int index;

    @Nullable
    private Button previousButton;

    @Nullable
    private Button nextButton;

    private Button backButton;


    public PageableGuiImpl(final Component title, final Button backButton) {
        super();
        inventory = Bukkit.createInventory(this, PAGE_SIZE * 9, title);
        this.backButton = backButton;
        index = 1;
        pages = new TreeMap<>();
        pages.put(index, new GuiPageImpl());
    }


    public final void addButton(final Button button) {
        final Optional<Integer> next = getNextFree();
        if (next.isPresent()) {
            final GuiPage page = pages.get(next.get());
            page.addButton(button);
        } else {
            final GuiPage nextPage = new GuiPageImpl();
            nextPage.addButton(button);
            pages.put(pages.lastKey() + 1, nextPage);
        }
    }

    private final Optional<Integer> getNextFree() {
        Integer result = null;
        for (final Map.Entry<Integer, GuiPage> entry : pages.entrySet()) {
            final GuiPage guiPage = entry.getValue();
            if (guiPage.hasSpace()) {
                result = entry.getKey();
                break;
            }
        }
        return Optional.ofNullable(result);
    }

    public final Inventory render() {
        inventory.clear();

        final GuiPage page = pages.get(index);
        page.renderInventory(inventory);

        renderPlaceHolder();
        renderBackButton();
        renderPreviousButton();
        renderPageNumber();
        renderNextButton();
        return inventory;
    }

    private void renderPlaceHolder() {
        final int yMod = 36;
        for (int i = 0; i < 9; i++) {
            final int slot = yMod + i;
            inventory.setItem(slot, GuiItem.getPlaceHolder());
        }
    }

    private void renderBackButton() {
        inventory.setItem(BACK_BUTTON_INDEX, backButton.getItem());
    }

    private void renderPreviousButton() {
        if (index > 1) {
            final ItemStack itemStack = Heads.PREVIOUS;
            previousButton = new ButtonImpl(itemStack, (event -> {
                index--;
                render();
            }));
            inventory.setItem(PREVIOUS_BUTTON_INDEX, itemStack);
        } else {
            previousButton = null;
        }
    }

    private void renderPageNumber() {
        final ItemStack itemStack = GuiItem.getPage(index);
        inventory.setItem(NUMBER_BUTTON_INDEX, itemStack);
    }

    private void renderNextButton() {
        if (pages.lastKey() != index) {
            final ItemStack itemStack = Heads.NEXT;
            nextButton = new ButtonImpl(itemStack, (event -> {
                index++;
                render();
            }));
            inventory.setItem(NEXT_BUTTON_INDEX, itemStack);
        } else {
            nextButton = null;
        }
    }


    public final void interact(final @NotNull InventoryClickEvent event) {
        final int slot = event.getSlot();

        if (previousButton != null && slot == PREVIOUS_BUTTON_INDEX) {
            previousButton.execute(event);

        } else if (nextButton != null && slot == NEXT_BUTTON_INDEX) {
            nextButton.execute(event);

        } else if (slot == BACK_BUTTON_INDEX) {
            backButton.execute(event);

        } else if (slot != NUMBER_BUTTON_INDEX) {
            final GuiPage page = pages.get(index);

            if (page != null) {
                page.interact(event);
            }
        }
    }
}
