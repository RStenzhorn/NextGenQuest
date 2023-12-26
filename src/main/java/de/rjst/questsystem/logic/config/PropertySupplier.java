package de.rjst.questsystem.logic.config;


import de.rjst.questsystem.model.enums.Property;

@FunctionalInterface
public interface PropertySupplier {

    <T> T apply(Property property, Class<T> type);

}


