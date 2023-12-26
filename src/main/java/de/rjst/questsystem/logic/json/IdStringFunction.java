package de.rjst.questsystem.logic.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.function.Function;

@Slf4j
@RequiredArgsConstructor
@Service("idStringFunction")
public class IdStringFunction implements Function<Set<Long>, String> {

    private final ObjectMapper objectMapper;

    @Override
    public String apply(final Set<Long> ids) {
        final String result;
        try {
            result = objectMapper.writeValueAsString(ids);
        } catch (final JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }

        return result;
    }
}
