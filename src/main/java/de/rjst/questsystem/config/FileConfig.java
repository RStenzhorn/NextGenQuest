package de.rjst.questsystem.config;


import de.rjst.questsystem.NextGenQuest;
import de.rjst.questsystem.setting.NgqProperty;
import de.rjst.questsystem.setting.NgqMessageType;
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
        return NextGenQuest.getInstance().getConfig();
    }


    @Bean
    public Map<String,String> propertyMap(@Qualifier("fileConfiguration") final FileConfiguration fileConfiguration) {
        final Map<String, String> result = new HashMap<>();

        String value;
        String path;
        for (final NgqProperty ngqProperty : NgqProperty.values()) {
            path = ngqProperty.getPath();
            value = fileConfiguration.getString(path);
            result.put(ngqProperty.getPath(), value);
        }

        return result;
    }

    @Bean
    public Map<String, String> messageMap(@Qualifier("fileConfiguration") final FileConfiguration fileConfiguration) {
        final Map<String, String> result = new HashMap<>();
        final Locale[] locales = Locale.getAvailableLocales();
        for (final NgqMessageType ngqMessageType : NgqMessageType.values()) {
            for (final Locale locale : locales) {
                final String path = ngqMessageType.getPath(locale);
                final String value = fileConfiguration.getString(path);
                if (value != null) {
                    result.put(path,value);
                }
            }
        }

        return result;
    }


}
