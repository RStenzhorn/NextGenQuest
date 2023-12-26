package de.rjst.questsystem.config;


import de.rjst.questsystem.QuestSystem;
import de.rjst.questsystem.model.enums.MessageType;
import de.rjst.questsystem.model.enums.Property;
import lombok.extern.slf4j.Slf4j;
import org.bukkit.configuration.file.FileConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

@Slf4j
@Configuration
public class FileConfig {

    @Bean
    public FileConfiguration fileConfiguration() {
        return QuestSystem.getInstance().getConfig();
    }


    @Bean
    public Map<String,String> propertyMap(@Qualifier("fileConfiguration") final FileConfiguration fileConfiguration) {
        final Map<String, String> result = new HashMap<>();

        String value;
        String path;
        for (final Property property : Property.values()) {
            path = property.getPath();
            value = fileConfiguration.getString(path);
            result.put(property.getPath(), value);
        }

        return result;
    }

    @Bean
    public Map<String, String> messageMap(@Qualifier("fileConfiguration") final FileConfiguration fileConfiguration) {
        final Map<String, String> result = new HashMap<>();
        final Locale[] locales = Locale.getAvailableLocales();
        for (final MessageType messageType : MessageType.values()) {
            for (final Locale locale : locales) {
                final String path = messageType.getPath(locale);
                final String value = fileConfiguration.getString(path);
                if (value != null) {
                    result.put(path,value);
                }
            }
        }

        return result;
    }


}
