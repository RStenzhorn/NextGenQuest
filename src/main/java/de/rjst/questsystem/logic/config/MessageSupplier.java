package de.rjst.questsystem.logic.config;

import de.rjst.questsystem.model.enums.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Map;
import java.util.function.BiFunction;

@Slf4j
@RequiredArgsConstructor
@Service("messageSupplier")
public class MessageSupplier implements BiFunction<MessageType, Locale, String> {

    @Qualifier("messageMap")
    private final Map<String, String> messageMap;

    @Override
    public String apply(final @NotNull MessageType messageType, final Locale locale) {
        final String path = messageType.getPath(locale);
        String result = messageMap.get(path);
        if (result == null) {
            result = messageMap.get(messageType.getPath(Locale.GERMAN));
            log.warn("Unknown locale: {} using {}", locale, Locale.GERMAN);
        }

        return result;
    }

}
