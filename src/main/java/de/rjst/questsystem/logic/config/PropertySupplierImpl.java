package de.rjst.questsystem.logic.config;

import de.rjst.questsystem.setting.NgqProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class PropertySupplierImpl implements PropertySupplier {

    @Qualifier("propertyMap")
    private final Map<String, String> propertyMap;

    @Override
    public final <T> T apply(final @NotNull NgqProperty ngqProperty, final @NotNull Class<T> type) {
        final String value = propertyMap.get(ngqProperty.getPath());

        final T result;
        if (type.equals(String.class)) {
            result = type.cast(value);
        } else {
            try {
                final Constructor<T> constructor = type.getConstructor(String.class);
                result = constructor.newInstance(value);
            } catch (final InstantiationException | IllegalAccessException | InvocationTargetException |
                           NoSuchMethodException ex) {
                log.error("Invalid NgqProperty invalid Value: {}", ngqProperty);
                throw new UnsupportedOperationException(ex);
            }
        }
        return result;
    }
}
