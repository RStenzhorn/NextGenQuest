package de.rjst.questsystem.logic.frontend;

import de.rjst.questsystem.util.PaperUtil;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.BiFunction;

@Service("replacePlaceholderFunction")
public class ReplacePlaceholderFunction implements BiFunction<String, Map<String, String>, Component> {

    @NotNull
    @Override
    public Component apply(final String msg, final @NotNull Map<String, String> args) {
        String text = msg;
        for (final Map.Entry<String, String> entry : args.entrySet()) {
            final String placeHolderKey = getPlaceHolderKey(entry.getKey());
            final String value = entry.getValue();
            text = text.replace(placeHolderKey, value);
        }

        return PaperUtil.getMessage(text);
    }

    @Contract(pure = true)
    @NotNull
    private static String getPlaceHolderKey(final String key) {
        return "%" + key + "%";
    }
}
