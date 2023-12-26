package de.rjst.questsystem;

import de.rjst.questsystem.model.enums.Property;
import de.rjst.questsystem.model.enums.MessageType;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.Banner;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.FileSystemResource;

import java.io.File;
import java.util.*;

@Slf4j
public class QuestSystem extends JavaPlugin {

    @Getter
    private static QuestSystem instance;

    private static ConfigurableApplicationContext applicationContext;

    @Override
    public void onEnable() {
        instance = this;
        Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
        setupConfig();
        applicationContext =
                new SpringApplicationBuilder(QuestSystemSpringBoot.class)
                        .bannerMode(Banner.Mode.OFF)
                        .properties(loadConfig())
                        .initializers(appContext -> appContext.setClassLoader(getClassLoader()))
                        .build()
                        .run();
    }

    @Override
    public void onDisable() {
        if (applicationContext != null) {
            applicationContext.close();
        }
    }


    private void setupConfig() {
        final FileConfiguration config = getConfig();

        config.options().setHeader(List.of(getName() + " by RStenzhorn"));
        for (final Property property : Property.values()) {
            config.addDefault(property.getPath(), property.getDefaultValue());
        }

        setupMessageConfig(config);
        config.options().copyDefaults(true);
        saveConfig();
    }

    private static void setupMessageConfig(final FileConfiguration config) {
        for (final MessageType messageType : MessageType.values()) {
            config.addDefault(messageType.getPath(Locale.ENGLISH), messageType.getEnglish());
            config.addDefault(messageType.getPath(Locale.GERMAN), messageType.getGerman());
        }
    }

    private Map<String, Object> loadConfig() {
        final YamlPropertiesFactoryBean yamlFactory = new YamlPropertiesFactoryBean();
        yamlFactory.setResources(new FileSystemResource(new File(getDataFolder(), "config.yml")));
        final var properties = yamlFactory.getObject();

        final Map<String, Object> result = new HashMap<>();
        if (properties != null) {
            for (final Map.Entry<Object, Object> entry : properties.entrySet()) {
                final Object key = entry.getKey();
                result.put(key.toString(), entry.getValue());
            }
        }
        return result;
    }

}
