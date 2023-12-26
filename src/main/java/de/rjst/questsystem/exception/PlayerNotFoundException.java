package de.rjst.questsystem.exception;

import lombok.Getter;

import java.io.Serial;

@Getter
public class PlayerNotFoundException extends QuestSystemException {

    @Serial
    private static final long serialVersionUID = -2070601923546901700L;

    private final String target;


    public PlayerNotFoundException(final String target) {
        super();
        this.target = target;
    }
}
