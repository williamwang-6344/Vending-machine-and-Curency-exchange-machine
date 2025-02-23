package CC14_Group_02_Assignment_1;

import java.util.ArrayList;

public class TableGenerator {

    public static String generateTable(String[][] table) {
        int[] columnWidths = new int[table[0].length];
        for (String[] strings : table) {
            for (int j = 0; j < strings.length; j++) {
                if (strings[j].length() > columnWidths[j]) {
                    columnWidths[j] = strings[j].length();
                }
            }
        }

        StringBuilder stringBuilder = new StringBuilder();
        String tableJoin = "+";
        String tableHSplit = "-";
        String tableVSplit = "|";
        for (String[] strings : table) {
            for (int j = 0; j < strings.length; j++) {
                stringBuilder.append(tableJoin);
                stringBuilder.append(tableHSplit.repeat(columnWidths[j] + 4));
            }
            stringBuilder.append(tableJoin);
            stringBuilder.append(System.lineSeparator());
            for (int j = 0; j < strings.length; j++) {
                stringBuilder.append(tableVSplit);
                stringBuilder.append("  ").append(strings[j]);
                stringBuilder.append(" ".repeat(columnWidths[j] - strings[j].length() + 2));
            }
            stringBuilder.append(tableVSplit);
            stringBuilder.append(System.lineSeparator());
        }
        for (int j = 0; j < table[0].length; j++) {
            stringBuilder.append(tableJoin);
            stringBuilder.append(tableHSplit.repeat(columnWidths[j] + 4));
        }
        stringBuilder.append(tableJoin);
        stringBuilder.append(System.lineSeparator());
        return stringBuilder.toString();
    }

    public static String generateCurrencyTable(ArrayList<Currency> list) {

        int tableSize = list.size() + 1;
        String[][] currencyTable = new String[tableSize][tableSize];

        currencyTable[0][0] = "From/To";
        //Header Row
        for(int i = 1; i < tableSize; i++) {
            currencyTable[0][i] = list.get(i-1).getCurrencySymbol(); //items are shifted one to the right in the table
        }
        //Column label
        for(int i = 1; i < tableSize; i++) {
            currencyTable[i][0] = list.get(i-1).getCurrencySymbol(); //items are shifted one to the right in the table
        }

        //Filling table with rates
        for(int i = 1; i < tableSize; i++){
            for(int j = 1; j < tableSize; j++){
                currencyTable[i][j] = String.format("%.2f", list.get(i-1).getExchangeRate()/list.get(j-1).getExchangeRate());
            }

        }

        return generateTable(currencyTable);

    }
    public static String generateFavouriteTable(ArrayList<Currency> list) {
        int tableSize = list.size() + 1;
        String[][] currencyTable = new String[tableSize][tableSize];

        currencyTable[0][0] = "From/To";
        //Header Row
        for(int i = 1; i < tableSize; i++) {
            currencyTable[0][i] = list.get(i-1).getCurrencySymbol(); //items are shifted one to the right in the table
        }
        //Column label
        for(int i = 1; i < tableSize; i++) {
            currencyTable[i][0] = list.get(i-1).getCurrencySymbol(); //items are shifted one to the right in the table
        }

        //Filling table with rates
        for(int i = 1; i < tableSize; i++){
            for(int j = 1; j < tableSize; j++){
                currencyTable[i][j] = String.format("%.2f", list.get(i-1).getExchangeRate()/list.get(j-1).getExchangeRate());
                if(list.get(i-1).getPriceHistoryList().size()>=2 && list.get(j-1).getPriceHistoryList().size()==1){
                    //first currency changed and the second remained constant
                    if(list.get(i-1).getPriceHistoryList().get(0).getPrice() > list.get(i-1).getPriceHistoryList().get(1).getPrice()){
                        currencyTable[i][j]+=" ^(I)";
                    }
                    else if(list.get(i-1).getPriceHistoryList().get(0).getPrice() < list.get(i-1).getPriceHistoryList().get(1).getPrice()){
                        currencyTable[i][j]+=" ↓(D)";
                    }
                }
                else if(list.get(i-1).getPriceHistoryList().size()==1 && list.get(j-1).getPriceHistoryList().size()>=2){
                    //second currency changed and the first remained constant
                    if(list.get(j-1).getPriceHistoryList().get(0).getPrice() > list.get(j-1).getPriceHistoryList().get(1).getPrice()){
                        currencyTable[i][j]+=" ↓(D)";
                    }
                    else if(list.get(j-1).getPriceHistoryList().get(0).getPrice() < list.get(j-1).getPriceHistoryList().get(1).getPrice()){
                        currencyTable[i][j]+=" ^(I)";
                    }
                }
                else if(list.get(i-1).getPriceHistoryList().size()>=2 && list.get(j-1).getPriceHistoryList().size()>=2){
                    //if both currencies changed
                    if(list.get(i-1).getPriceHistoryList().get(0).getPrice()/list.get(i-1).getPriceHistoryList().get(1).getPrice() > list.get(j-1).getPriceHistoryList().get(0).getPrice()/list.get(j-1).getPriceHistoryList().get(1).getPrice()){
                        currencyTable[i][j]+=" ^(I)";
                    
                    }else if(list.get(i-1).getPriceHistoryList().get(0).getPrice()/list.get(i-1).getPriceHistoryList().get(1).getPrice() < list.get(j-1).getPriceHistoryList().get(0).getPrice()/list.get(j-1).getPriceHistoryList().get(1).getPrice()){
                        currencyTable[i][j]+=" ↓(D)";
                    }
                }
            }

        }

        return generateTable(currencyTable);
    }

}