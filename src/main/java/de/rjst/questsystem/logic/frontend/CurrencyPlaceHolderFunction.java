package de.rjst.questsystem.logic.frontend;

import de.rjst.questsystem.logic.config.PropertySupplier;
import de.rjst.questsystem.model.enums.Property;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.function.Function;

@RequiredArgsConstructor
@Service("currencyPlaceHolderFunction")
public class CurrencyPlaceHolderFunction implements Function<BigInteger, String> {

    private final PropertySupplier propertySupplier;

    @Override
    @NotNull
    public String apply(@NotNull final BigInteger currency) {
        String result = String.valueOf(currency);

        final String currencyName;
        if (BigInteger.ONE.equals(currency)) {
            currencyName = propertySupplier.apply(Property.CURRENCY_SINGULAR, String.class);
        } else {
            currencyName = propertySupplier.apply(Property.CURRENCY_PLURAL, String.class);
        }
        result += " " + currencyName;

        return result;
    }
}
