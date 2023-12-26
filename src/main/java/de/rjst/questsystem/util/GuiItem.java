package de.rjst.questsystem.util;

import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class GuiItem {

    private final ItemStack placeHolder;
    private final Map<Integer, ItemStack> pages;

    static {
        placeHolder = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        FrontendUtil.setDisplayName(placeHolder, Component.text(""));

        pages = new HashMap<>();
        pages.put(1, Heads.PAGE_1);
        pages.put(2, Heads.PAGE_2);
        pages.put(3, Heads.PAGE_3);
        pages.put(4, Heads.PAGE_4);
        pages.put(5, Heads.PAGE_5);
        pages.put(6, Heads.PAGE_6);
        pages.put(7, Heads.PAGE_7);
        pages.put(8, Heads.PAGE_8);
        pages.put(9, Heads.PAGE_9);
        pages.put(10, Heads.PAGE_10);
        pages.put(11, Heads.PAGE_11);
        pages.put(12, Heads.PAGE_12);
        pages.put(13, Heads.PAGE_13);
        pages.put(14, Heads.PAGE_14);
        pages.put(15, Heads.PAGE_15);
    }

    @NotNull
    public ItemStack getPlaceHolder() {
        return placeHolder.clone();
    }

    @NotNull
    public ItemStack getPage(@NotNull final Integer index) {
        return pages.getOrDefault(index, new ItemStack(Material.AIR)).clone();
    }
}
