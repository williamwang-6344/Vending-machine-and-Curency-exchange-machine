package CC14_Group_02_Assignment_2;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultPageTest {

    private DefaultPage defaultPage;
    private File productDatabase = new File("../app/src/main/resources/Databases/ProductDatabase.json");


    OutputStream output = new ByteArrayOutputStream();
    PrintStream printstream = new PrintStream(output);
    PrintStream originalOutput = System.out;

    @BeforeEach
    public void setup() {
        System.setOut(printstream);
    }

    @AfterEach
    public void teardown() {
        System.setOut(originalOutput);
    }

    @Test
    public void TestProductToDisplay() {
        String input = "0";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        Scanner scanner = new Scanner(System.in);

        VendingMachine vendingMachine = new VendingMachine(productDatabase);
        DefaultPage defaultPage = new DefaultPage();
        VendingMachineRunner returns = defaultPage.runPage(scanner, vendingMachine);

        assertTrue(returns instanceof ProductPage);

        System.setIn(System.in);
    }

    @Test
    public void TestProductToDisplayNegativeValue() {
        String input = "-1";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        Scanner scanner = new Scanner(System.in);

        VendingMachine vendingMachine = new VendingMachine(productDatabase);
        DefaultPage defaultPage = new DefaultPage();
        VendingMachineRunner returns = defaultPage.runPage(scanner, vendingMachine);

        assertTrue(returns instanceof DefaultPage);

        System.setIn(System.in);
    }

    @Test
    public void TestProductToDisplayOutOfBoundsValue() {
        String input = "10";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        Scanner scanner = new Scanner(System.in);

        VendingMachine vendingMachine = new VendingMachine(productDatabase);
        DefaultPage defaultPage = new DefaultPage();
        VendingMachineRunner returns = defaultPage.runPage(scanner, vendingMachine);

        assertTrue(returns instanceof DefaultPage);

        System.setIn(System.in);
    }

    @Test
    public void TestProductToDisplayInvalidInput() {
        String input = "invalid";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        Scanner scanner = new Scanner(System.in);

        VendingMachine vendingMachine = new VendingMachine(productDatabase);
        DefaultPage defaultPage = new DefaultPage();
        VendingMachineRunner returns = defaultPage.runPage(scanner, vendingMachine);

        assertTrue(returns instanceof DefaultPage);

        System.setIn(System.in);
    }





}