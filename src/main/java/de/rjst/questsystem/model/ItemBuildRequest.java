package de.rjst.questsystem.model;

import de.rjst.questsystem.setting.NgqMessageType;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.Map;

public interface ItemBuildRequest {

     @NotNull
     Locale getLocale();

     @NotNull
     ItemStack getBaseItem();

     @Nullable
     NgqMessageType getItemName();

     @Nullable
     NgqMessageType getDescription();

     @NotNull
     Map<String, String> getPlaceholder();

}
