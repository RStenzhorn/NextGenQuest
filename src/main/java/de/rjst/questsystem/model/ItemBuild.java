package de.rjst.questsystem.model;

import de.rjst.questsystem.setting.NgqMessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bukkit.inventory.ItemStack;

import java.util.Locale;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemBuild implements ItemBuildRequest {

    private Locale locale;
    private ItemStack baseItem;
    private NgqMessageType itemName;
    private NgqMessageType description;
    private Map<String, String> placeholder;
}
