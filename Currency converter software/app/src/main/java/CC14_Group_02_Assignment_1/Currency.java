package CC14_Group_02_Assignment_1;

import java.util.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Currency {

    private String currencySymbol;

    private CurrencyPriceDate currentPriceDate;

    private ArrayList<CurrencyPriceDate> priceHistoryList; //Store history of all prices in a tuple like object

    //public static SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd"); //Unused?



    public Currency(String currencySymbol, float exchangeRate, Date date) {
        this.currencySymbol = currencySymbol;

        this.currentPriceDate = new CurrencyPriceDate(exchangeRate, date);
        priceHistoryList = new ArrayList<CurrencyPriceDate>();
        priceHistoryList.add(this.currentPriceDate);
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public ArrayList<CurrencyPriceDate> getPriceHistoryList(){ return this.priceHistoryList;}

    public float getExchangeRate() {
        return this.currentPriceDate.getPrice();
    }

    public Date getDate() {
        return this.currentPriceDate.getDate();
    }

    public void updateCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public void updateExchangeRate(float exchangeRate, Date date) {
        //Updates current exchange rate if date is more recent than, or equal to current date and will update history
        //Will store any older than current date in history
        if(date.before(this.getDate())){
            //Old entry, only update history (add new or update existing)
            CurrencyPriceDate existing = CurrencyPriceDate.getPriceDataByDate(date, this.priceHistoryList);
            if(existing != null){
                //Update existing entry
                existing.setPrice(exchangeRate);
            } else {
                this.priceHistoryList.add(new CurrencyPriceDate(exchangeRate, date));
            }

        } else if (date.equals(this.getDate())) {
            //Same date as current entry, update currently pointed to priceDate
            this.currentPriceDate.setPrice(exchangeRate);

        } else if (date.after(this.getDate())) {
            //After currently stored date, update current on object, and create new object in history
            this.currentPriceDate = new CurrencyPriceDate(exchangeRate, date);
            this.priceHistoryList.add(0, this.currentPriceDate);

        }

    }

    public static Currency GetCurrencyBySymbolFromList(String symbol, ArrayList<Currency> list){
        for(Currency currency : list){
            if(currency.getCurrencySymbol().equals(symbol)){
                return currency;
            }
        }
        return null;
    }


}


