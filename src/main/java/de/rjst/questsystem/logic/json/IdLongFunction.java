package de.rjst.questsystem.logic.json;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.function.Function;

@Slf4j
@RequiredArgsConstructor
@Service("idLongFunction")
public class IdLongFunction implements Function<String, Set<Long>> {

    private final ObjectMapper objectMapper;

    @Override
    public Set<Long> apply(final String json) {
        final Set<Long> result;
        try {
            result = objectMapper.readValue(json, new SetTypeReference());
        } catch (final JacksonException ex) {
            throw new IllegalArgumentException(ex);
        }
        return result;
    }

    private static class SetTypeReference extends TypeReference<Set<Long>> {
    }
}
