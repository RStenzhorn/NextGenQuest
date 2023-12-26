package de.rjst.questsystem.util;

import com.destroystokyo.paper.profile.PlayerProfile;
import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerTextures;
import org.jetbrains.annotations.NotNull;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.UUID;

@UtilityClass
public class FrontendUtil {


    public int getItemPosition(final int x, final int y) {
        int result = x;
        for (int i = 0; i < y; i++) {
            result += 9;
        }
        return result;
    }

    public void setDisplayName(final @NotNull ItemStack itemStack, final Component name) {
        final ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(name);
        itemStack.setItemMeta(itemMeta);
    }

    public void setLore(final @NotNull ItemStack itemStack, final List<? extends Component> lore) {
        final ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.lore(lore);
        itemStack.setItemMeta(itemMeta);
    }

}
