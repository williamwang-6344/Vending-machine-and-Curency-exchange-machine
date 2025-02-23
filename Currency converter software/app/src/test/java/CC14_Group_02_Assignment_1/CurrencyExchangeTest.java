package CC14_Group_02_Assignment_1;

import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class CurrencyExchangeTest {

    private CurrencyExchange testingExchange;
    private final InputStream originalIn = System.in;

    private final PrintStream originalOut = System.out;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();


    @BeforeEach
    public void init() {

        ArrayList<Currency> list = App.parseFile("src/test/resources/TestDatabase.csv");


        this.testingExchange = new CurrencyExchange(list);



    }

    @AfterEach
    public void endState(){
        System.setOut(originalOut);
    }

    @Test
    public void testAddNewTypeScanner(){


        //Test Correct input
        String testString = "AUD,USD,2.0,20-12-2022";
        System.setIn(new ByteArrayInputStream(testString.getBytes()));
        Scanner testScanner = new Scanner(System.in);
        this.testingExchange.newType(testScanner);

        //Confirmation
        Currency changed = this.testingExchange.getCurrencyBySymbol("AUD");
        assertNotNull(changed);
        assertTrue(changed.getExchangeRate() == 2.0f);

        //Resetting system input method
        System.setIn(originalIn);

    }

    @Test
    public void testChangeRateScanner(){


        //Test Correct input
        String testString = "SGD,USD,3.1,20-12-2022";
        System.setIn(new ByteArrayInputStream(testString.getBytes()));
        Scanner testScanner = new Scanner(System.in);
        this.testingExchange.changeRate(testScanner);

        //Confirmation
        Currency changed = this.testingExchange.getCurrencyBySymbol("SGD");
        assertNotNull(changed);
        assertTrue(changed.getExchangeRate() == 3.1f);

        testString = "SGD,USD,NaN,20-12-2022";
        System.setIn(new ByteArrayInputStream(testString.getBytes()));
        testScanner = new Scanner(System.in);
        this.testingExchange.newType(testScanner);


        changed = this.testingExchange.getCurrencyBySymbol("SGD");
        assertNotNull(changed);
        assertTrue(changed.getExchangeRate() == 3.1f);


        //Resetting system input method
        System.setIn(originalIn);

    }


    @Test
    public void testGetRates(){

        assertEquals(2.0, this.testingExchange.showRate("USD", "SGD"));
        assertEquals(0.5, this.testingExchange.showRate("SGD", "USD"));
        assertEquals(0.0f, this.testingExchange.showRate("XYZ", "SGD"));
        assertEquals(0.0f, this.testingExchange.showRate("USD", "XYZ"));

    }

    @Test
    public void testAddNewCurrencyTypeFailures(){
        //Currency already exists, no change expected
        this.testingExchange.addNewCurrencyType("SGD","USD",99,"12-12-2022");
        assertEquals(0.5, this.testingExchange.showRate("SGD", "USD"));

        //Existing currency to pin rate to doesn't exist, no change expected
        this.testingExchange.addNewCurrencyType("SGD","XYZ",99,"12-12-2022");
        assertEquals(0.5, this.testingExchange.showRate("SGD", "USD"));

        //Date format incorrect, no change expected
        this.testingExchange.addNewCurrencyType("XYZ","USD",99,"not-a-date");
        assertEquals(0, this.testingExchange.showRate("XYZ", "USD"));

        //Date too far in future, no change expected
        this.testingExchange.addNewCurrencyType("XYZ","USD",99,"01-01-1999");
        assertEquals(0, this.testingExchange.showRate("XYZ", "USD"));

    }

    @Test
    public void testFindDateBetweenInterval(){

        //Testing that this just gets the dates of each day in the range
        List<ArrayList<Date>> returnedList;

        returnedList = this.testingExchange.findDateBetweenInterval(
                "SGD", "USD",
                this.testingExchange.getCurrencyList(),
                "failed","failed");

        assertNull(returnedList);

        returnedList = this.testingExchange.findDateBetweenInterval(
                "SGD", "USD",
                this.testingExchange.getCurrencyList(),
                "01-02-2022","03-02-2022");

        assertTrue(returnedList.size() == 3);
        assertTrue(returnedList.get(0).get(0).toString().startsWith("Tue Feb 01"));

    }



    @Test
    public void testMaintainCurrencyTypes(){

        //Test failure from not enough args
        String testString = "ADJUST SGD USD 2.5";
        System.setIn(new ByteArrayInputStream(testString.getBytes()));
        Scanner testScanner = new Scanner(System.in);
        this.testingExchange.maintainCurrencyTypes(testScanner);
        assertNotEquals(2.5, this.testingExchange.showRate("SGD", "USD"));

        //Test failure from not enough args
        testString = "ADJUST SGD USD SEVEN 21-12-2022";
        System.setIn(new ByteArrayInputStream(testString.getBytes()));
        testScanner = new Scanner(System.in);
        this.testingExchange.maintainCurrencyTypes(testScanner);
        assertNotEquals(2.5, this.testingExchange.showRate("SGD", "USD"));


        //Test success
        testString = "ADJUST SGD USD 2.5 21-12-2022";
        System.setIn(new ByteArrayInputStream(testString.getBytes()));
        testScanner = new Scanner(System.in);
        this.testingExchange.maintainCurrencyTypes(testScanner);
        assertEquals(2.5, this.testingExchange.showRate("SGD", "USD"));

    }

    @Test
    public void testUpdateCurrencies(){

        //Should be null before testing
        assertNull(this.testingExchange.favouriteCurrency.get(1));

        //Incorrect number of favourites
        String testString = "USD,USD,SGD";
        System.setIn(new ByteArrayInputStream(testString.getBytes()));
        Scanner testScanner = new Scanner(System.in);
        this.testingExchange.updateCurrencies(testScanner);

        //Should still be null
        assertNull(this.testingExchange.favouriteCurrency.get(1));

        //Correct number
        testString = "USD,USD,SGD,SGD";
        System.setIn(new ByteArrayInputStream(testString.getBytes()));
        testScanner = new Scanner(System.in);
        this.testingExchange.updateCurrencies(testScanner);

        assertEquals("USD", this.testingExchange.favouriteCurrency.get(0).getCurrencySymbol());
        assertEquals("USD", this.testingExchange.favouriteCurrency.get(1).getCurrencySymbol());
        assertEquals("SGD", this.testingExchange.favouriteCurrency.get(2).getCurrencySymbol());
        assertEquals("SGD", this.testingExchange.favouriteCurrency.get(3).getCurrencySymbol());

    }




    @Test
    public void testRunExchange(){

        //Set output capture
        System.setOut(new PrintStream(outputStream));

        //Set input for testing
        String testString = "J";
        System.setIn(new ByteArrayInputStream(testString.getBytes()));

        //assertEquals("Command not found. Please try again.", outputStream.toString().trim());


    }

    @Test
    public void testGetRateBefore(){

        // Parsing date requires exception handling
        try {
            assertTrue(0.0f == this.testingExchange.getRateBefore("USD", this.testingExchange.getCurrencyList(), App.sdformat.parse("01-01-1980")));
            assertTrue(1.0f == this.testingExchange.getRateBefore("USD", this.testingExchange.getCurrencyList(), App.sdformat.parse("01-01-2030"))); //Year 2030
        } catch (ParseException e){
            System.out.println("Couldn't parse test dates.");
            assertTrue(false); // Should fail the test if this happens
        }



    }

    @Test
    public void testPrintSummaryOfTheConversionRates(){

        //(String symbol1, String symbol2, ArrayList<Currency> currencyList, String startingDate,String endingDate){
        this.testingExchange.printSummaryOfTheConversionRates("USD", "SGD",
                this.testingExchange.getCurrencyList(),
                "01-01-1980","01-01-2030");

        //No assertions for complex display, just to put output into logs and indicate errors.
    }

    @Test
    public void testConvertMoney(){


        //Handled error on amount not as number
        String testString = "USD,SGD,ERROR";
        System.setIn(new ByteArrayInputStream(testString.getBytes()));
        Scanner testScanner = new Scanner(System.in);
        this.testingExchange.convertMoney(testScanner);

        //Handled error on not enough inputs
        testString = "USD,SGD";
        System.setIn(new ByteArrayInputStream(testString.getBytes()));
        testScanner = new Scanner(System.in);
        this.testingExchange.convertMoney(testScanner);

        //Symbol doesn't exist input
        testString = "USD,XYZ,20";
        System.setIn(new ByteArrayInputStream(testString.getBytes()));
        testScanner = new Scanner(System.in);
        this.testingExchange.convertMoney(testScanner);

        //Working input
        testString = "USD,SGD,20";
        System.setIn(new ByteArrayInputStream(testString.getBytes()));
        testScanner = new Scanner(System.in);
        this.testingExchange.convertMoney(testScanner);


    }

    @Test
    public void testShowReport(){

        //Not working input
        String testString = "USD,SGD,01-01-1980";
        System.setIn(new ByteArrayInputStream(testString.getBytes()));
        Scanner testScanner = new Scanner(System.in);
        this.testingExchange.showReport(testScanner);

        //Working input
        testString = "USD,SGD,01-01-1980,01-01-2030";
        System.setIn(new ByteArrayInputStream(testString.getBytes()));
        testScanner = new Scanner(System.in);
        this.testingExchange.showReport(testScanner);
    }

    @Test
    public void testAdminModeScanner(){

        //Test moving to convert currency
        String testString = "C\nX\nX"; //convert, exit to normal, exit out
        System.setIn(new ByteArrayInputStream(testString.getBytes()));
        Scanner testScanner = new Scanner(System.in);
        this.testingExchange.adminMode(testScanner);

        //Test setting admin password - can't check, but can ensure acceptance happens with no errors
        testString = "*newpassword"; //set new admin password
        System.setIn(new ByteArrayInputStream(testString.getBytes()));
        testScanner = new Scanner(System.in);
        this.testingExchange.adminMode(testScanner);

    }

    @Test
    public void testRunExchangeScanner(){

        //Test moving to convert currency
        String testString = "NOCMD\nX"; //enter false command and exit
        System.setIn(new ByteArrayInputStream(testString.getBytes()));
        //Run exchange creates scanner based on system.in already.
        this.testingExchange.runExchange();


    }

}
