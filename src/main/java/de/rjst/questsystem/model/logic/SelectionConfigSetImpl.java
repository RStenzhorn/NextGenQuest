package de.rjst.questsystem.model.logic;

import de.rjst.questsystem.database.entity.PlayerEntity;
import de.rjst.questsystem.database.entity.config.QuestSelectionConfigEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SelectionConfigSetImpl implements SelectionConfigSet{

    private PlayerEntity player;
    private Long id;
    private QuestSelectionConfigEntity questSelectionConfig;

}
