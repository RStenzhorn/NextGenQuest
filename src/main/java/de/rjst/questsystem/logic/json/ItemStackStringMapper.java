package de.rjst.questsystem.logic.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Function;

@RequiredArgsConstructor
@Service
public class ItemStackStringMapper implements Function<ItemStack, String> {

    private final ObjectMapper objectMapper;

    @Override
    public String apply(@NotNull final ItemStack itemStack) {
        final Map<String, Object> serialize = itemStack.serialize();
        final String result;
        try {
            result = objectMapper.writeValueAsString(serialize);
        } catch (final JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
        return result;
    }


}
