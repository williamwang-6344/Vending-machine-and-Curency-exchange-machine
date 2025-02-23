package CC14_Group_02_Assignment_1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;

import static CC14_Group_02_Assignment_1.App.sdformat;
import static org.junit.jupiter.api.Assertions.*;

public class CurrencyTest {

    private Currency currency;

    @Test
    public void updateCurrencySymbolTest() throws ParseException {
        currency = new Currency("AUD", 1.45f, sdformat.parse("11-11-2011"));
        assertEquals("AUD", currency.getCurrencySymbol());

        currency.updateCurrencySymbol("USD");
        assertEquals("USD", currency.getCurrencySymbol());
    }



}
