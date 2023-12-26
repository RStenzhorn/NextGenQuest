package de.rjst.questsystem.logic.config;

import de.rjst.questsystem.setting.NgqMessageType;
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
public class MessageSupplier implements BiFunction<NgqMessageType, Locale, String> {

    @Qualifier("messageMap")
    private final Map<String, String> messageMap;

    @Override
    public String apply(final @NotNull NgqMessageType ngqMessageType, final Locale locale) {
        final String path = ngqMessageType.getPath(locale);
        String result = messageMap.get(path);
        if (result == null) {
            result = messageMap.get(ngqMessageType.getPath(Locale.GERMAN));
            log.warn("Unknown locale: {} using {}", locale, Locale.GERMAN);
        }

        return result;
    }

}
