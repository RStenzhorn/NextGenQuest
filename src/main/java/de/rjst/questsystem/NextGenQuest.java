package de.rjst.questsystem;

import de.rjst.questsystem.setting.NgqProperty;
import de.rjst.questsystem.setting.NgqMessageType;
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
public class NextGenQuest extends JavaPlugin {

    @Getter
    private static NextGenQuest instance;

    private static ConfigurableApplicationContext applicationContext;

    @Override
    public void onEnable() {
        instance = this;
        Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
        setupConfig();
        applicationContext =
                new SpringApplicationBuilder(NextGenQuestSpringBoot.class)
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
        for (final NgqProperty ngqProperty : NgqProperty.values()) {
            config.addDefault(ngqProperty.getPath(), ngqProperty.getDefaultValue());
        }

        setupMessageConfig(config);
        config.options().copyDefaults(true);
        saveConfig();
    }

    private static void setupMessageConfig(final FileConfiguration config) {
        for (final NgqMessageType ngqMessageType : NgqMessageType.values()) {
            config.addDefault(ngqMessageType.getPath(Locale.ENGLISH), ngqMessageType.getEnglish());
            config.addDefault(ngqMessageType.getPath(Locale.GERMAN), ngqMessageType.getGerman());
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
