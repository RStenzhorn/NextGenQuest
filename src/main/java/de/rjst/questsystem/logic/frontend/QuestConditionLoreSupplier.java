package de.rjst.questsystem.logic.frontend;

import de.rjst.questsystem.database.entity.QuestConditionEntity;
import de.rjst.questsystem.database.entity.config.ConditionSubConditionConfig;
import de.rjst.questsystem.database.entity.config.QuestConditionConfigEntity;
import de.rjst.questsystem.model.enums.ConditionType;
import de.rjst.questsystem.model.enums.MessageType;
import de.rjst.questsystem.model.enums.Placeholder;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.BiFunction;

@RequiredArgsConstructor
@Service("questConditionLoreSupplier")
public class QuestConditionLoreSupplier implements BiFunction<Locale, List<QuestConditionEntity>, List<Component>> {

    @Qualifier("messageSupplier")
    private final BiFunction<MessageType, Locale, String> messageSupplier;

    @Qualifier("replacePlaceholderFunction")
    private final BiFunction<String, Map<String, String>, Component> replacePlaceHolderFunction;

    @Qualifier("questSubConditionLoreSupplier")
    private final BiFunction<Locale, List<ConditionSubConditionConfig>, List<Component>> questSubConditionLoreSupplier;



    @Override
    public List<Component> apply(final Locale locale, final @NotNull List<QuestConditionEntity> questConditions) {
        final List<Component> result = new LinkedList<>();

        for (final QuestConditionEntity questCondition : questConditions) {
            final BigDecimal currentValue = questCondition.getCurrentValue();
            final QuestConditionConfigEntity conditionConfig = questCondition.getConditionConfig();
            final BigDecimal goalValue = conditionConfig.getGoalValue();
            final ConditionType conditionType = ConditionType.valueOf(conditionConfig.getConditionType());
            final String msg = messageSupplier.apply(conditionType.getMessageType(), locale);
            final Component component = replacePlaceHolderFunction.apply(msg,
                    Map.of(
                            Placeholder.GOAL, ConditionType.formatValue(goalValue),
                            Placeholder.VALUE, ConditionType.formatValue(currentValue),
                            Placeholder.TYPE, conditionConfig.getParameter() != null ? conditionType.getTranslateKey(conditionConfig.getParameter()) : ""
                    ));

            result.add(component);
            result.addAll(questSubConditionLoreSupplier.apply(locale, conditionConfig.getConditionSubConditionConfigs()));
        }

        return result;
    }
}
