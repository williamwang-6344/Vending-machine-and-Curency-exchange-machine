package CC14_Group_02_Assignment_1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static CC14_Group_02_Assignment_1.App.sdformat;
import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class CurrencyPriceDataTest {

    private CurrencyPriceDate currencyPriceDate;
    private ArrayList<CurrencyPriceDate> priceHistoryList;

    @BeforeEach
    public void init() {
        this.currencyPriceDate = new CurrencyPriceDate(10, null);
    }

    @Test
    public void setPriceTest() {
        assertEquals(10, this.currencyPriceDate.getPrice());
        this.currencyPriceDate.setPrice(20);
        assertEquals(20, this.currencyPriceDate.getPrice());
    }

    @Test
    public void getPriceDataByDateTest() throws ParseException {
        Date date1 = sdformat.parse("11-11-2011");
        Date date2 = sdformat.parse("12-11-2011");

        CurrencyPriceDate cpd = new CurrencyPriceDate(30, date1);
        CurrencyPriceDate cpd2 = new CurrencyPriceDate(30, date2);

        this.priceHistoryList = new ArrayList<>();
        this.priceHistoryList.add(cpd);
        this.priceHistoryList.add(cpd2);

        assertEquals(cpd2, CurrencyPriceDate.getPriceDataByDate(date2, this.priceHistoryList));
    }

}
