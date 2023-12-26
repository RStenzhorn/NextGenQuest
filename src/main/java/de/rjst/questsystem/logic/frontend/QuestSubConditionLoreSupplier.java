package de.rjst.questsystem.logic.frontend;

import de.rjst.questsystem.database.entity.config.ConditionSubConditionConfig;
import de.rjst.questsystem.database.entity.config.QuestSubConditionConfigEntity;
import de.rjst.questsystem.model.enums.ConditionType;
import de.rjst.questsystem.setting.NgqMessageType;
import de.rjst.questsystem.setting.NgqPlaceholder;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.BiFunction;

@RequiredArgsConstructor
@Service("questSubConditionLoreSupplier")
public class QuestSubConditionLoreSupplier implements BiFunction<Locale, List<ConditionSubConditionConfig>, List<Component>> {

    @Qualifier("messageSupplier")
    private final BiFunction<NgqMessageType, Locale, String> messageSupplier;

    @Qualifier("replacePlaceholderFunction")
    private final BiFunction<String, Map<String, String>, Component> replacePlaceHolderFunction;

    @Override
    public List<Component> apply(final Locale locale, final @NotNull List<ConditionSubConditionConfig> conditionSubConditionConfigs) {
        final List<Component> result = new LinkedList<>();
        for (final ConditionSubConditionConfig conditionSubConditionConfig : conditionSubConditionConfigs) {
            final QuestSubConditionConfigEntity subConditionConfig = conditionSubConditionConfig.getQuestSubConditionConfig();
            final ConditionType subConditionType = ConditionType.valueOf(subConditionConfig.getConditionType());
            final NgqMessageType ngqMessageType = subConditionType.getNgqMessageType();
            final String msg = messageSupplier.apply(ngqMessageType, locale);
            final Component message = replacePlaceHolderFunction.apply(msg,
                    Map.of(
                            NgqPlaceholder.TYPE, subConditionType.getTranslateKey(subConditionConfig.getParameter())
                    ));
            result.add(message);
        }

        return result;
    }
}
