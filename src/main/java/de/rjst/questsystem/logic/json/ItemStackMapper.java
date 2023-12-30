package de.rjst.questsystem.logic.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.bukkit.inventory.ItemStack;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Function;

@RequiredArgsConstructor
@Service
public class ItemStackMapper implements Function<String, ItemStack> {

    private final ObjectMapper objectMapper;

    @Override
    public ItemStack apply(final String itemStackJson) {
        final ItemStack result;
        try {
            final Map<String, Object> objectMap = objectMapper.readValue(itemStackJson, new MapReference());
            result = ItemStack.deserialize(objectMap);
        } catch (final Exception ex) {
            throw new IllegalArgumentException(ex);
        }
        return result;
    }

    private static class MapReference extends TypeReference<Map<String, Object>> {
    }
}
