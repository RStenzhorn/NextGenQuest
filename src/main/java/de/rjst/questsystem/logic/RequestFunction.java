package de.rjst.questsystem.logic;

import de.rjst.questsystem.database.entity.QuestConditionEntity;
import de.rjst.questsystem.database.entity.QuestEntity;
import de.rjst.questsystem.database.entity.config.ConditionSubConditionConfig;
import de.rjst.questsystem.database.entity.config.QuestConditionConfigEntity;
import de.rjst.questsystem.database.entity.config.QuestSubConditionConfigEntity;
import de.rjst.questsystem.database.repository.QuestConditionRepository;
import de.rjst.questsystem.database.repository.QuestRepository;
import de.rjst.questsystem.model.logic.Request;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

@Slf4j
@RequiredArgsConstructor
@Service("requestFunction")
public class RequestFunction implements BiFunction<UUID, Request, List<QuestEntity>> {

    //TODO Refactor

    @Qualifier("loadPlayerFunction")
    private final Function<UUID, List<QuestEntity>> loadPlayerFunction;

    @Qualifier("questConditionRepository")
    private final QuestConditionRepository questConditionRepository;

    @Qualifier("questRepository")
    private final QuestRepository questRepository;

    @Override
    public List<QuestEntity> apply(final UUID uuid, final Request request) {
        final List<QuestEntity> result = new ArrayList<>();
        final List<QuestEntity> quests = loadPlayerFunction.apply(uuid);
        for (final QuestEntity quest : quests) {
            if (!quest.getSuccess()) {
                updateConditions(quest, request);
                if (isQuestSuccess(quest)) {
                    quest.setSuccess(true);
                    questRepository.save(quest);

                    result.add(quest);
                }
            }
        }
        return result;
    }


    private void updateConditions(final @NotNull QuestEntity quest, final Request request) {
        for (final QuestConditionEntity condition : quest.getConditions()) {
            final QuestConditionConfigEntity conditionConfig = condition.getConditionConfig();
            final String parameterType = conditionConfig.getConditionType();
            final String parameter = conditionConfig.getParameter();
            final String requestConditionType = request.getConditionType();
            final String requestParameter = request.getParameter();
            final Map<String, String> subConditionTypes = request.getSubConditionTypes();

            if (parameterType.equals(requestConditionType)) {
                if (parameter == null && requestParameter == null || Objects.equals(parameter, requestParameter)) {
                    if (isSubConditionsSuccess(subConditionTypes, conditionConfig.getConditionSubConditionConfigs())) {
                        applyConditionUpdate(request, condition);
                    }
                }
            }
        }
    }

    private void applyConditionUpdate(final @NotNull Request request, final @NotNull QuestConditionEntity condition) {
        final BigDecimal currentValue = condition.getCurrentValue();
        final BigDecimal addedValue = currentValue.add(request.getValue());
        condition.setCurrentValue(addedValue);
        if (isConditionSuccess(condition)) {
            condition.setSuccess(true);
        }
        questConditionRepository.save(condition);
    }

    private static boolean isConditionSuccess(final @NotNull QuestConditionEntity questCondition) {
        final BigDecimal currentValue = questCondition.getCurrentValue();
        final QuestConditionConfigEntity conditionConfig = questCondition.getConditionConfig();
        return currentValue.compareTo(conditionConfig.getGoalValue()) >= 0;
    }

    private static boolean isSubConditionsSuccess(final Map<String, String> requestSubConditions, final @NotNull List<ConditionSubConditionConfig> subConditionConfigs) {
        boolean result = true;
        for (final ConditionSubConditionConfig subConditionConfig : subConditionConfigs) {
            final QuestSubConditionConfigEntity questSubConditionConfig = subConditionConfig.getQuestSubConditionConfig();
            final String subConditionType = questSubConditionConfig.getConditionType();
            final String parameter = questSubConditionConfig.getParameter();
            final String subConditionValue = requestSubConditions.get(subConditionType);

            if (!parameter.equals(subConditionValue)) {
                result = false;
                break;
            }
        }
        return result;
    }

    private static boolean isQuestSuccess(final @NotNull QuestEntity quest) {
        boolean result = true;
        for (final QuestConditionEntity condition : quest.getConditions()) {
            if (!condition.getSuccess()) {
                result = false;
                break;
            }
        }
        return result;
    }


}
