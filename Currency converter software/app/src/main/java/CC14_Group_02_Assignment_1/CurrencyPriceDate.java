package CC14_Group_02_Assignment_1;

import java.util.ArrayList;
import java.util.Date;

public class CurrencyPriceDate {
    private Date date;
    private float price;

    public CurrencyPriceDate(float price, Date date){
        this.price = price;
        this.date = date;
    }

    public Date getDate() {
        return date;
    }


    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public static CurrencyPriceDate getPriceDataByDate(Date date, ArrayList<CurrencyPriceDate> list){
        //Static method used to search array list for object matching by date
        for(CurrencyPriceDate pricedate : list){
            if(pricedate.date.equals(date)){
                return pricedate;
            }
        }
        return null;
    }
}
