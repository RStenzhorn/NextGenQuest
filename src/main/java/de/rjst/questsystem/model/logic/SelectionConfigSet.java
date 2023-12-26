package de.rjst.questsystem.model.logic;

import de.rjst.questsystem.database.entity.PlayerEntity;
import de.rjst.questsystem.database.entity.config.QuestSelectionConfigEntity;

public interface SelectionConfigSet {

    PlayerEntity getPlayer();

    Long getId();

    QuestSelectionConfigEntity getQuestSelectionConfig();

}
