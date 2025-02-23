package CC14_Group_02_Assignment_1;

import java.util.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
public class App {
    public final static String formatString = "dd-MM-yyyy";
    public final static SimpleDateFormat sdformat = new SimpleDateFormat(formatString);
    public final static String filePath = "Database.csv";

    public static ArrayList<Currency> parseFile(String path){
        File mycsv = new File(path);
        ArrayList<Currency> listCurrency = new ArrayList<>();
        try{
            Scanner scan = new Scanner(mycsv);
            while(scan.hasNextLine()) {
                String entry = scan.nextLine();
                ArrayList<String> attributes = new ArrayList<>();
                StringTokenizer st = new StringTokenizer(entry, ",");
                while (st.hasMoreElements()) {
                    attributes.add(st.nextElement().toString());
                }

                //Parse variables from line
                String symbol = attributes.get(0);
                float price = Float.parseFloat(attributes.get(1));
                Date date = sdformat.parse(attributes.get(2));

                Currency existing =  Currency.GetCurrencyBySymbolFromList(symbol, listCurrency);
                if(existing != null) {
                    //Currency exists in list already, update
                    existing.updateExchangeRate(price,date);
                } else {
                    //Currency not in list yet, add new
                    Currency currencyEntry = new Currency(symbol, price, date);
                    listCurrency.add(currencyEntry);
                }
            }
            scan.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not Found!");
        } catch (NumberFormatException e) {
            System.err.println("Error parsing float");
        } catch (ParseException e) {
            System.err.println("Wrong date format");
            e.printStackTrace();
        }
        return listCurrency;
    }
    public static ArrayList<Currency> parseFileHistory(String path){
        File mycsv = new File(path);
        ArrayList<Currency> listCurrency = new ArrayList<>();
        try{
            Scanner scan = new Scanner(mycsv);
            while(scan.hasNextLine()){
                String entry = scan.nextLine();
                ArrayList<String> attributes = new ArrayList<>();
                StringTokenizer st = new StringTokenizer(entry, ",");
                while (st.hasMoreElements()) {
                    attributes.add(st.nextElement().toString());
                }
                Currency currencyEntry = new Currency(attributes.get(0),Float.parseFloat(attributes.get(1)), sdformat.parse(attributes.get(2)));
                listCurrency.add(currencyEntry);
            }
            scan.close();
        }catch (FileNotFoundException e) {
            System.err.println("File not Found!");
        } catch (NumberFormatException e) {
            System.err.println("Wrong date format");
            e.printStackTrace();
        } catch (ParseException e) {
            System.err.println("Wrong format-- not a float");
            e.printStackTrace();
        }
        return listCurrency;
    }


    public static void main(String[] args){
        ArrayList<Currency> listCurrency= App.parseFile(filePath);
        CurrencyExchange exchange = new CurrencyExchange(listCurrency);

        exchange.runExchange();
    }

}
