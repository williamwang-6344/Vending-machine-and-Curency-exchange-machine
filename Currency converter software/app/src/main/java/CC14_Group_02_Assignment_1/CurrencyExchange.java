package CC14_Group_02_Assignment_1;

import java.util.*;
import java.io.*;
import java.text.ParseException;

public class CurrencyExchange {

    private String adminPassword = "admin";
    private final ArrayList<Currency> listCurrency;
    private final ArrayList<Currency> historyCurrency;

    public ArrayList<Currency> favouriteCurrency;

    public CurrencyExchange(ArrayList<Currency> listCurrency) {
        this.listCurrency = listCurrency;
        this.historyCurrency = App.parseFileHistory(App.filePath);
        this.favouriteCurrency = new ArrayList<>();
        initFavourites();
    }

    public ArrayList<Currency> getCurrencyList() {
        return this.listCurrency;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public void initFavourites() {
        this.favouriteCurrency.add(Currency.GetCurrencyBySymbolFromList("USD", listCurrency));
        this.favouriteCurrency.add(Currency.GetCurrencyBySymbolFromList("CNY", listCurrency));
        this.favouriteCurrency.add(Currency.GetCurrencyBySymbolFromList("JPY", listCurrency));
        this.favouriteCurrency.add(Currency.GetCurrencyBySymbolFromList("EUR", listCurrency));
    }

    public void displayNewExchange() {
        System.out.println("##### Welcome to the Currency Exchange #####");
    }

    public void displayCurrencyExchange() {
        System.out.println("\n##### Currency Exchange #####");
    }

    public void displayFavouriteTable() {
        System.out.print("$$$$$$ Show Favourite Table $$$$$" + "\n-Format: Symbol1, Symbol2, Symbol3, Symbol4 OR [X] - To cancel update\n> ");
    }

    public void displayCustomerHelp() {
        String customerHelp = "To enter the admin mode, enter [*] and then type admin password, followed by <Enter>\n" +
                "\n" +
                "Normal User Command list:\n" +
                "C - Convert money from one currency to another\n" +
                "F - Show favourite currencies and their conversions\n" +
                "T - Show entire currency table\n" +
                "H - Show report of two currency in specific time interval\n" +
                "X - Exit\n" +
                "> ";
        System.out.print(customerHelp);
    }

    public void displayAdminHelp() {
        String adminHelp = "Normal Admin Command list:\n" +
                "C - Convert money from one currency to another\n" +
                "F - Show favourite currencies and their conversions\n" +
                "T - Show entire currency table\n" +
                "N - Add new exchange rates\n" +
                "A - Adjust exchange rates\n" +
                "U - Update favourite currencies and their conversions\n" +
                "H - Show report of two currency in specific time interval\n" +
                "X - Exit\n" +
                "> ";
        System.out.print(adminHelp);
    }

    public void displayAdmin() {
        String admin = "\n##### ADMIN MODE #####\n" +
                "To change password, enter [*] and then type new password, followed by <Enter>";

        System.out.println(admin);
    }

    public void displayCurrencyConversion() {
        String currencyConversionHelp = "\n$$$$$$ Convert Currency $$$$$\n" +
                "Use the following format to convert currency OR [X] - Cancel Currency Conversion\n" +
                "Format: SymbolWantsToCovert, SymbolCovertsTo, AmountToConvert\n" +
                "> ";

        System.out.print(currencyConversionHelp);
    }

    public void displayReport() {
        String showReportHelp = "\n$$$$$$ Show Report $$$$$$\n" +
                "Use the following format to show report OR [X] - Cancel Showing Report\n" +
                "Format: SymbolWantsToCovert, SymbolCovertsTo, StartingDate(dd-MM-yyyy), EndingDate(dd-MM-yyyy)\n" +
                "> ";
        System.out.print(showReportHelp);
    }

    public void displayAdjustRate() {
        String showReportHelp = "\n$$$$$$ Adjust Rate $$$$$$\n" +
                "Use the following format to show report OR [X] - Cancel Adjusting Rate\n" +
                "Format: SymbolCovertFrom, SymbolCovertsTo, Rate, Date(dd-MM-yyyy)\n" +
                "> ";
        System.out.print(showReportHelp);
    }

    public void displayNewType() {
        String showNewType = "\n$$$$$$ Add New Type $$$$$$\n" +
                "Use the following format to add new type OR [X] - Cancel Showing New Type\n" +
                "Format: NewSymbol, ExistingSymbol, Rate, Date(dd-MM-yyyy)\n" +
                "> ";
        System.out.print(showNewType);
    }

    public void displayMaintainCurrencyTypes() {
        String showMaintainCurrencyTypes = "\n$$$$$$ Maintain Currency Types $$$$$$\n" +
                "Use the following format to maintain currency types OR [X] - Cancel Maintaining Currency Types\n" +
                "Format: ADJUST <CURRENCY1> <CURRENCY2> <NEW RATE> <ON DATE>\n" +
                "> ";
        System.out.print(showMaintainCurrencyTypes);
    }

    public void adjustRate(String symbol1, String symbol2, float rate, String date) {
        Currency cur1 = this.getCurrencyBySymbol(symbol1);
        if (cur1 == null) {
            System.out.printf("Currency %s does not exist%n", symbol1);
            return;
        }

        Currency cur2 = this.getCurrencyBySymbol(symbol2);
        if (cur2 == null) {
            System.out.printf("Currency %s does not exist%n", symbol2);
            return;
        }

        try {
            if (App.sdformat.parse(date).after(cur1.getDate()) == false || App.sdformat.parse(date).after(cur2.getDate()) == false) {
                System.out.println("Date Entered can only be before the current date of both currencies entered");
                System.out.println(cur1.getCurrencySymbol() + "'s current date: " + App.sdformat.format(cur1.getDate()));
                System.out.println(cur2.getCurrencySymbol() + "'s current date: " + App.sdformat.format(cur2.getDate()));
                return;
            }
        } catch (ParseException e1) {
            System.out.print("The date you entered is not correct, Please try again (Date format: dd-MM-yyyy) or exist by pressing[X]\n> ");
        }

        float rateChange = rate / (cur1.getExchangeRate() / cur2.getExchangeRate());
        float newRate = rateChange * cur1.getExchangeRate();
        Currency newEntry;
        try {
            //newEntry = new Currency(symbol1, newRate,App.sdformat.parse(date) );
            cur1.updateExchangeRate(newRate, App.sdformat.parse(date));

            //this.listCurrency.add(0,newEntry);
            System.out.println("Successfully updated\n>");
            PrintWriter pw = new PrintWriter(App.filePath);
            for (Currency currency : this.listCurrency) {
                //For each currency in list, print all values
                for (CurrencyPriceDate priceDate : currency.getPriceHistoryList()) {
                    pw.println(currency.getCurrencySymbol() + "," + priceDate.getPrice() + "," + App.sdformat.format(priceDate.getDate()));
                }
                //pw.println(this.listCurrency.get(i).getCurrencySymbol()+","+this.listCurrency.get(i).getExchangeRate()+","+App.sdformat.format(this.listCurrency.get(i).getDate()));
            }
            pw.flush();
            pw.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (ParseException e1) {
            return;
        }

    }

    public Currency getCurrencyBySymbol(String symbol) {
        for (Currency current : this.listCurrency) {
            if (current.getCurrencySymbol().equals(symbol)) {
                return current;
            }
        }
        return null;
    }

    public float showRate(String symbol1, String symbol2) {
        Currency cur1 = this.getCurrencyBySymbol(symbol1);
        if (cur1 == null) {
            System.out.printf("\nCurrency %s does not exist\n%n", symbol1);
            return 0;
        }

        Currency cur2 = this.getCurrencyBySymbol(symbol2);
        if (cur2 == null) {
            System.out.printf("\nCurrency %s does not exist\n%n", symbol2);
            return 0;
        }
        return (cur1.getExchangeRate() / cur2.getExchangeRate());
    }

    // Feature #7
    public void addNewCurrencyType(String symbol1, String symbol2, float rate, String date) {
        Currency cur2 = this.getCurrencyBySymbol(symbol2);
        if (this.getCurrencyBySymbol(symbol1) != null) {
            System.out.printf("Currency %s already exists%n", symbol1);
            return;
        }
        if (cur2 == null) {
            System.out.printf("Currency %s does not exist%n", symbol2);
            return;
        }

        try {
            if (App.sdformat.parse(date).after(cur2.getDate()) == false) {
                System.out.println("Date Entered can only be before the current date of the currencies entered");
                System.out.println(cur2.getCurrencySymbol() + "'s current date: " + App.sdformat.format(cur2.getDate()));
                return;
            }
        } catch (ParseException e1) {
            System.out.println("The date you entered is not correct, Please try again (Date format: dd-MM-yyyy) or exist by pressing[X]");
        }

        float newRate = rate * cur2.getExchangeRate();
        try {
            Currency cur1 = new Currency(symbol1, newRate, App.sdformat.parse(date));

            this.listCurrency.add(0, cur1);
            System.out.println("Successfully added new type\n>");
            PrintWriter pw = new PrintWriter(App.filePath);
            for (Currency currency : this.listCurrency) {
                //For each currency in list, print all values
                for (CurrencyPriceDate priceDate : currency.getPriceHistoryList()) {
                    pw.println(currency.getCurrencySymbol() + "," + priceDate.getPrice() + "," + App.sdformat.format(priceDate.getDate()));
                }
            }
            pw.flush();
            pw.close();
        } catch (FileNotFoundException e) {
            return;
        } catch (ParseException e1) {
            // TODO Auto-generated catch block
            return;
        }
    }

    public List<ArrayList<Date>> findDateBetweenInterval(String symbol1, String symbol2, ArrayList<Currency> currencyList, String StartingDate, String EndingDate) {
        try {
            ArrayList<Date> date_list = new ArrayList<>();

            Date sd = App.sdformat.parse(StartingDate);
            Date ed = App.sdformat.parse(EndingDate);
            for (int i = currencyList.size() - 1; i >= 0; i--) {
                if ((currencyList.get(i).getCurrencySymbol().equals(symbol1) || currencyList.get(i).getCurrencySymbol().equals(symbol2)) && (currencyList.get(i).getDate().after(sd) ||
                        currencyList.get(i).getDate().equals(sd)) && (currencyList.get(i).getDate().before(ed) ||
                        currencyList.get(i).getDate().equals(ed))) {

                    date_list.add(currencyList.get(i).getDate());
                }
            }
            ArrayList<ArrayList<Date>> period_date_list = new ArrayList<>();

            // list of period_date
            for (int i = 1; i < date_list.size(); i++) {
                ArrayList<Date> period_date = new ArrayList<>();
                // time interval for each period date
                //each period date have a sd and ed
                period_date.add(date_list.get(i - 1));
                period_date.add(date_list.get(i));
                period_date_list.add(period_date);

            }
            // time interval for each period date
            //each period date have a sd and ed
            if (period_date_list.size() != 0) {
                if (period_date_list.get(0).get(0).after(sd)) {
                    ArrayList<Date> period_date = new ArrayList<>();
                    // time interval for each period date
                    //each period date have a sd and ed
                    period_date.add(sd);
                    period_date.add(period_date_list.get(0).get(0));
                    period_date_list.add(0, period_date);
                }
                if (period_date_list.get(period_date_list.size() - 1).get(1).before(ed)) {
                    ArrayList<Date> period_date = new ArrayList<>();
                    // time interval for each period date
                    //each period date have a sd and ed
                    period_date.add(period_date_list.get(period_date_list.size() - 1).get(1));
                    period_date.add(ed);
                    period_date_list.add(period_date_list.size(), period_date);
                }
            }
            return period_date_list;


        } catch (ParseException e1) {
            System.out.println("The date you entered is not correct, Please try again (Date format: dd-MM-yyyy) or exist by pressing[X]");
        }
        return null;
    }

    public float getRateBefore(String symbol, ArrayList<Currency> currencyList, Date endingDate){
        float beforeStarting = 0.0f;
        for (Currency currency : currencyList) {
            if (currency.getCurrencySymbol().equals(symbol) && currency.getDate().before(endingDate)) {
                beforeStarting = currency.getExchangeRate();
                break;
            }
        }

        return beforeStarting;

    }


    public void printSummaryOfTheConversionRates(String symbol1, String symbol2, ArrayList<Currency> currencyList, String startingDate,String endingDate){
        Currency cur1 = this.getCurrencyBySymbol(symbol1);
        if(cur1 == null) {
            System.out.println(String.format("\nCurrency %s does not exist\nPlease try other currencies or exist by pressing [X]", symbol1));
            return;
        }
        Currency cur2 = this.getCurrencyBySymbol(symbol2);
        if(cur2 == null) {
            System.out.println(String.format("\nCurrency %s does not exist\nPlease try other currencies or exist by pressing [X]", symbol2));
            return;
        }
        List< ArrayList<Date>> period_date_list = findDateBetweenInterval(symbol1,symbol2,currencyList,startingDate,endingDate);
        List< ArrayList<Float>> period_rate_list = new ArrayList<ArrayList<Float>>();
        if (period_date_list.size()!= 0){
            for (int i = 0 ; i< period_date_list.size();i++){
                float rate ;
                float rate1 = getRateBefore(symbol1,currencyList,period_date_list.get(i).get(1));
                float rate2 = getRateBefore(symbol2,currencyList,period_date_list.get(i).get(1));
                if (rate1 ==0.0f || rate2 ==0.0f){
                    rate = 0.0f;
                }
                else{
                    rate = rate1/rate2;
                }
                float day =  (period_date_list.get(i).get(1).getTime() - period_date_list.get(i).get(0).getTime())/ (1000 * 60 * 60 * 24)
                        % 365;

                ArrayList<Float> period_rate = new ArrayList<Float>();
                period_rate.add(rate);
                period_rate.add(day);
                period_rate_list.add(period_rate);
            }

            System.out.println("\nHistory_Of_Conversion_Rates\n");
            for(int i = 0; i< period_date_list.size();i++){

                System.out.println(App.sdformat.format(period_date_list.get(i).get(0)) + ", "+App.sdformat.format(period_date_list.get(i).get(1))+" Rate: "+period_rate_list.get(i).get(0));
            }
            System.out.println("\nPs: rate equals 0 means the database does not have one of " + symbol1 + " and " + symbol2 + " recorded within that period.\n" );
            ArrayList<Float> period_rate = new ArrayList<Float>();
            ArrayList<Float> sd = new ArrayList<Float>();
            float sum = 0;
            float valid_day = 0;

            for (ArrayList<Float> floats : period_rate_list) {
                if (floats.get(0) != 0) {
                    sum += floats.get(0) * floats.get(1);
                    valid_day += floats.get(1);
                    period_rate.add(floats.get(0));
                }

            }
            float average = sum/valid_day;
            System.out.println("Average: " + average+"\n");
            float maximum = Collections.max(period_rate);
            float minimum = Collections.min(period_rate);

            System.out.println("Maximum: "+maximum+"\n");
            System.out.println("Minimum: "+minimum+"\n");

            for (ArrayList<Float> floats : period_rate_list) {
                if (floats.get(0) != 0) {
                    for (int j = 0; j < floats.get(1); j++) {
                        sd.add(floats.get(0));
                    }
                }
            }
            float standardDeviation = StandardDeviation.calculateSD(sd);
            System.out.println("StandardDeviation: "+standardDeviation );
        }
        else {
            System.out.println("\nNo data available in the period given. please try another time period or exit by pressing [X].");
        }

    }

    public void convertMoney(Scanner scanner) { // Feature #2 from the
        displayCurrencyConversion();
        String userInput;
        while(scanner.hasNextLine()) {
            userInput = scanner.nextLine();
            if (userInput.equalsIgnoreCase("X")) {
                displayCurrencyExchange();
                break;
            }
            String[] splitUp = userInput.split(",");
            for (int i =0; i < splitUp.length;i++){
                splitUp[i]= splitUp[i].strip();
            }
            if (splitUp.length != 3) {
                System.out.print("\nThe format you entered is incorrect, please follow the format below or exit by pressing [X].\nFormat: SymbolWantsToCovert, SymbolCovertsTo, AmountToConvert\n> ");


            } else {

                try {
                    float amount = Float.parseFloat(splitUp[2]);
                    if (this.showRate(splitUp[0].toUpperCase(), splitUp[1].toUpperCase()) != 0) {
                        amount = amount * this.showRate(splitUp[0].toUpperCase(), splitUp[1].toUpperCase());
                        System.out.printf("\nMoney Conversion Successful!!!\nThe current exchange rate for %s to %s is: %f%n",
                                splitUp[0].toUpperCase(), splitUp[1].toUpperCase(), this.showRate(splitUp[0].toUpperCase(), splitUp[1].toUpperCase()));
                        System.out.printf("Amount of " + splitUp[0].toUpperCase() + " exchanged for " + splitUp[1].toUpperCase() + " is: " + "%.2f" + " " + splitUp[1].toUpperCase() + "\n", amount);

                        displayCurrencyConversion();
                    } else {
                        System.out.print("Try other currencies or exit by pressing [X]\n> ");
                    }
                } catch (NumberFormatException e) {
                    System.out.print("\nInput amount can only be a numerical value. Please try again or exit by pressing [X].\n-Format: SymbolWantsToCovert, SymbolCovertsTo, AmountToConvert\n> ");
                }





            }
        }
    }

    public void showReport(Scanner scanner) { // Feature #2 from the
        displayReport();
        String userInput;
        while (scanner.hasNextLine()) {
            userInput = scanner.nextLine();
            if (userInput.equalsIgnoreCase("X")) {
                displayCurrencyExchange();
                break;
            }
            String[] splitUp = userInput.split(",");
            for (int i =0; i < splitUp.length;i++){
                splitUp[i]= splitUp[i].strip();
            }
            if (splitUp.length != 4 ||userInput.isEmpty()) {
                System.out.print("\nThe format you entered is incorrect, please follow the format below or exit by pressing [X].\n-Format: SymbolWantsToCovert, SymbolCovertsTo, StartingDate(dd-MM-yyyy), EndingDate(dd-MM-yyyy)\n> ");
            } else {
                // This function doesn't account for invalid arguments (what happens if the symbols and date are int value
                String[] datelist1 = splitUp[2].split("-");
                String[] datelist2 = splitUp[3].split("-");
                if (datelist1.length == 3 && datelist2.length ==3){
                    if(datelist1[0].length()==2 && datelist1[1].length()==2 && datelist1[2].length()==4 && datelist2[0].length()==2 && datelist2[1].length()==2 && datelist2[2].length()==4) {
                        this.printSummaryOfTheConversionRates(splitUp[0].toUpperCase(), splitUp[1].toUpperCase(), this.historyCurrency, splitUp[2], splitUp[3]);
                        displayReport();
                    }else{
                        System.out.print("\nDate entered not in the correct format\n-Format: SymbolWantsToCovert, SymbolCovertsTo, StartingDate(dd-MM-yyyy), EndingDate(dd-MM-yyyy)\n>");
                    }
                }else{
                    System.out.print("\nDate entered not in the correct format\n-Format: SymbolWantsToCovert, SymbolCovertsTo, StartingDate(dd-MM-yyyy), EndingDate(dd-MM-yyyy)\n>");
                }


            }
        }
    }

    public void newType(Scanner scanner){
        this.displayNewType();
        String userInput = null;
        while (scanner.hasNextLine()) {
            userInput = scanner.nextLine();
            if (userInput.toUpperCase().equals("X")) {
                displayCurrencyExchange();
                break;
            }
            String[] splitedUp = userInput.split(",");
            for (int i =0; i < splitedUp.length;i++){
                splitedUp[i]= splitedUp[i].strip();
            }
            if (splitedUp.length != 4||userInput.isEmpty()) {
                System.out.print("\nThe format you entered is incorrect, please follow the format below or exit by pressing [X].\n-Format: SymbolCovertFrom, SymbolCovertsTo, Rate , Date(dd-MM-yyyy)> ");
            } else {
                String[] datelist1 = splitedUp[3].split("-");
                if (datelist1.length == 3){
                    if(datelist1[0].length()==2 && datelist1[1].length()==2 && datelist1[2].length()==4) {
                        float newRate;
                        try {
                            newRate = Float.parseFloat(splitedUp[2]);
                            this.addNewCurrencyType(splitedUp[0].toUpperCase(), splitedUp[1].toUpperCase(), newRate, splitedUp[3]);
                        } catch (NumberFormatException e) {
                            System.out.println("Rate must be an numerical value. Please try again.");
                        }
                    }else{
                        System.out.print("\nDate entered not in the correct format\n-Format: SymbolWantsToCovert, SymbolCovertsTo, StartingDate(dd-MM-yyyy), EndingDate(dd-MM-yyyy)\n>");
                    }
                }
                else{
                    System.out.print("\nDate entered not in the correct format\n-Format: SymbolWantsToCovert, SymbolCovertsTo, StartingDate(dd-MM-yyyy), EndingDate(dd-MM-yyyy)\n>");
                }

            }
        }
    }

    public void changeRate(Scanner scanner){
        this.displayAdjustRate();
        String userInput = null;
        while (scanner.hasNextLine()) {
            userInput = scanner.nextLine();
            if (userInput.toUpperCase().equals("X")) {
                displayCurrencyExchange();
                break;
            }
            String[] splitedUp = userInput.split(",");
            for (int i =0; i < splitedUp.length;i++){
                splitedUp[i]= splitedUp[i].strip();
            }
            if (splitedUp.length != 4||userInput.isEmpty()) {
                System.out.println("\nThe format you entered is incorrect, please follow the format below or exit by pressing [X].\n-Format: SymbolCovertFrom, SymbolCovertsTo, Rate , Date(dd-MM-yyyy)");
            } else {
                String[] datelist1 = splitedUp[3].split("-");
                if (datelist1.length == 3){
                    if(datelist1[0].length()==2 && datelist1[1].length()==2 && datelist1[2].length()==4) {
                        float newrate;
                        try {
                            newrate = Float.parseFloat(splitedUp[2]);
                            this.adjustRate(splitedUp[0].toUpperCase(), splitedUp[1].toUpperCase(), newrate, splitedUp[3]);
                        } catch (NumberFormatException e) {
                            System.out.println("Rate must be an numerical value. Please try again.");
                        }
                    }else{
                        System.out.print("\nDate entered not in the correct format\n-Format: SymbolWantsToCovert, SymbolCovertsTo, StartingDate(dd-MM-yyyy), EndingDate(dd-MM-yyyy)\n>");
                    }
                }
                else{
                    System.out.print("\nDate entered not in the correct format\n-Format: SymbolWantsToCovert, SymbolCovertsTo, StartingDate(dd-MM-yyyy), EndingDate(dd-MM-yyyy)\n>");
                }

            }
        }
    }

    public void updateCurrencies(Scanner scanner) {
        displayFavouriteTable();
        String userInputH = null;
        while (scanner.hasNextLine()) {
            userInputH = scanner.nextLine();
            if (userInputH.equalsIgnoreCase("X")) {
                displayCurrencyExchange();
                break;
            }
            String[] splitUp = userInputH.split(",");
            for (int i =0; i < splitUp.length;i++){
                splitUp[i]= splitUp[i].strip();
            }
            if (splitUp.length != 4) {
                System.out.println("The format you entered is incorrect, please follow the format below or exit by pressing [X].\n-Format: Symbol1 Symbol2, Symbol3, Symbol4\n>");
            } else {
                int i = 0;
                while (i < splitUp.length) {
                    if (Currency.GetCurrencyBySymbolFromList(splitUp[i].toUpperCase(), this.listCurrency) != null) {
                        this.favouriteCurrency.set(i, Currency.GetCurrencyBySymbolFromList(splitUp[i].toUpperCase(), listCurrency));
                        i++;
                    } else {
                        System.out.println(splitUp[i].toUpperCase() + " is not in the database. Try another currency or exit by pressing [X]\n>");
                        break;
                    }
                }
                if (i == 4) {
                    System.out.println(TableGenerator.generateFavouriteTable(this.favouriteCurrency));
                    displayFavouriteTable();
                }
            }
        }
    }

    public void maintainCurrencyTypes(Scanner scanner) {
        displayMaintainCurrencyTypes();
        String userInputM = null;
        while (scanner.hasNextLine()) {
            userInputM = scanner.nextLine();
            if (userInputM.toUpperCase().startsWith("ADJUST ")) {
                String[] inputs = userInputM.split(" ");
                if (inputs.length != 5) {
                    System.out.printf("Input for ADJUST command must be in format 'ADJUST <CURRENCY1> <CURRENCY2> <NEW RATE> <ON DATE - %s>%n", App.formatString);
                    break;
                }
                float newRate;
                try {
                    newRate = Float.parseFloat(inputs[3]);
                } catch (NumberFormatException e) {
                    System.err.println("Rate could not be read as float. Please try again.");
                    e.printStackTrace();
                    break;
                }
                this.adjustRate(inputs[1], inputs[2], newRate, inputs[4]);
            } else if (userInputM.toUpperCase().equals("N")) {
                this.newType(scanner);
            } else if (userInputM.toUpperCase().equals("X")) {
                displayCurrencyExchange();
                break;
            }
        }
    }

    public void adminMode(Scanner scanner) {
        displayAdmin();
        displayAdminHelp();
        String adminInput;
        while (scanner.hasNextLine()) {
            adminInput = scanner.nextLine();
            if (adminInput.equalsIgnoreCase("C")) { // Convert currency, Feature #2
                convertMoney(scanner);
            } else if (adminInput.equalsIgnoreCase("F")) { // Display favourite, Feature #4
                System.out.println(TableGenerator.generateFavouriteTable(this.favouriteCurrency));
            } else if (adminInput.equalsIgnoreCase("T")) { // Display table, Feature #4
                System.out.println(TableGenerator.generateCurrencyTable(this.listCurrency));
            } else if (adminInput.equalsIgnoreCase("H")) {
                this.showReport(scanner);
            } else if (adminInput.equalsIgnoreCase("A")) {
                this.changeRate(scanner);
            } else if (adminInput.equalsIgnoreCase("N")) {
                this.newType(scanner);
            } else if (adminInput.equalsIgnoreCase("M")) {
                this.maintainCurrencyTypes(scanner);
            } else if (adminInput.equalsIgnoreCase("U")) {
                this.updateCurrencies(scanner);
            } else if (adminInput.equalsIgnoreCase("X")) {
                System.out.println("\n##### EXITING ADMIN MODE #####");
                displayCurrencyExchange();
                break;
            } else if (adminInput.startsWith("*")) {
                setAdminPassword(adminInput.substring(1));
                System.out.println("Admin password successfully updated!\n");
            } else {
                System.out.println("\nCommand not found. Please try again.\n");
            }
            displayAdminHelp();
        }
    }

    public void runExchange() {
        Scanner scanner = new Scanner(System.in);
        String userInput;
        boolean closeExchange = false;
        displayNewExchange();
        while (!closeExchange) {
            displayCustomerHelp();
            while (scanner.hasNextLine()) {
                userInput = scanner.nextLine();
                if (userInput.equalsIgnoreCase("X")) {
                    closeExchange = true;
                    break;
                } else if (userInput.equals("*" + adminPassword)) {
                    adminMode(scanner);
                } else if (userInput.equalsIgnoreCase("C")) { // Convert currency, Feature #2
                    convertMoney(scanner);
                } else if (userInput.equalsIgnoreCase("H")) {
                    this.showReport(scanner);
                } else if (userInput.equalsIgnoreCase("T")) { // Display table, Feature #4
                    System.out.println(TableGenerator.generateCurrencyTable(this.listCurrency));
                } else if (userInput.equalsIgnoreCase("F")) { // Display favourite, Feature #4
                    System.out.println(TableGenerator.generateFavouriteTable(this.favouriteCurrency));
                } else {
                    System.out.println("\nCommand not found. Please try again.\n");
                }
                displayCustomerHelp();
            }
        }
        System.out.println("\nCurrency Exchange is now closed.");
        scanner.close();
    }
}
