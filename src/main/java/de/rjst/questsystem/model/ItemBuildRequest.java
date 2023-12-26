package de.rjst.questsystem.model;

import de.rjst.questsystem.model.enums.MessageType;
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
     MessageType getItemName();

     @Nullable
     MessageType getDescription();

     @NotNull
     Map<String, String> getPlaceholder();

}
