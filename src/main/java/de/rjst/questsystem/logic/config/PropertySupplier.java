package de.rjst.questsystem.logic.config;


import de.rjst.questsystem.setting.NgqProperty;

@FunctionalInterface
public interface PropertySupplier {

    <T> T apply(NgqProperty ngqProperty, Class<T> type);

}


