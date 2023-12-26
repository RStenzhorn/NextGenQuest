package de.rjst.questsystem.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;


@Data
@EqualsAndHashCode(callSuper = true)
public class QuestSystemException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 4146060692645283278L;

}
