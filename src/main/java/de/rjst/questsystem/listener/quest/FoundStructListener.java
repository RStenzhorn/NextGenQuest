package de.rjst.questsystem.listener.quest;

import de.rjst.questsystem.config.bean.PluginListener;
import de.rjst.questsystem.model.enums.ConditionType;
import de.rjst.questsystem.model.logic.QuestRequest;
import de.rjst.questsystem.model.logic.Request;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.generator.structure.StructureType;
import org.bukkit.util.StructureSearchResult;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;


@Slf4j
@RequiredArgsConstructor
@PluginListener
@Component
public class FoundStructListener implements Listener {

    private static final List<StructureType> STRUCTURE_TYPES = List.of(
//            StructureType.BURIED_TREASURE,
//            StructureType.DESERT_PYRAMID,
//            StructureType.END_CITY,
//            StructureType.FORTRESS,
//            StructureType.IGLOO,
//            StructureType.JIGSAW,
//            StructureType.JUNGLE_TEMPLE,
//            StructureType.MINESHAFT,
//            StructureType.NETHER_FOSSIL,
//            StructureType.OCEAN_MONUMENT,
//            StructureType.OCEAN_RUIN,
//            StructureType.RUINED_PORTAL,
//            StructureType.SHIPWRECK,
//            StructureType.STRONGHOLD,
//            StructureType.SWAMP_HUT,
            StructureType.WOODLAND_MANSION
    );

    @Qualifier("requestConsumer")
    private final BiConsumer<Player, Request> requestConsumer;

    @Qualifier("subConditionsSupplier")
    private final Function<Player, Map<String, String>> subConditionsSupplier;


    @EventHandler
    public void apply(final PlayerMoveEvent event) {
        final Location location = event.getTo();
        final World world = location.getWorld();
        final Player player = event.getPlayer();

        for (final StructureType structureType : STRUCTURE_TYPES) {
            final StructureSearchResult result = world.locateNearestStructure(location, structureType, 1, false);

            if (result != null) {
                final Map<String, String> subConditions = subConditionsSupplier.apply(player);

                final Request request = QuestRequest.builder()
                        .conditionType(ConditionType.FOUND_STRUCT.name())
                        .parameter(structureType.getKey().value())
                        .value(BigDecimal.ONE)
                        .subConditionTypes(subConditions)
                        .build();
                requestConsumer.accept(player,request);
            }
        }
    }
}
