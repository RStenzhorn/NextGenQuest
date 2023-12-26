package de.rjst.questsystem.model.logic;

import java.math.BigDecimal;
import java.util.Map;

public interface Request {

    String getConditionType();
    String getParameter();

    BigDecimal getValue();
    Map<String, String> getSubConditionTypes();

}
