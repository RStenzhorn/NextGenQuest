package de.rjst.questsystem.exception;

import lombok.Getter;

import java.io.Serial;

@Getter
public class InvalidArgsAmountException extends QuestSystemException {

    @Serial
    private static final long serialVersionUID = -8493116497262353243L;



    public InvalidArgsAmountException() {
        super();
    }
}
