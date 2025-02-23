package CC14_Group_02_Assignment_2;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class ProductPageTest {

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
    public void TestCancelDisplayingProductDetails() {
        String input = "X";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        Scanner scanner = new Scanner(System.in);

        VendingMachine vendingMachine = new VendingMachine(productDatabase);
        Product.parseProductDatabase(productDatabase);

        List<Item> items = new ArrayList<>();
        Item item1 = new Item("Candies", 4001, "Mentos", 7, 3.50f);
        Item item2 = new Item("Candies", 4002, "Sour Patch", 7, 4.50f);
        Item item3 = new Item("Candies", 4003, "Skittles", 7, 2.50f);
        items.add(item1);
        items.add(item2);
        items.add(item3);

        Product product = new Product("Candies", items);

        ProductPage productPage = new ProductPage(product);
        VendingMachineRunner returns = productPage.runPage(scanner, vendingMachine);

        assertTrue(returns instanceof DefaultPage);

        System.setIn(System.in);
    }

    @Test
    public void TestPurchaseItemFromProductPage() {
        String input = "0";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        Scanner scanner = new Scanner(System.in);

        VendingMachine vendingMachine = new VendingMachine(productDatabase);
        Product.parseProductDatabase(productDatabase);

        List<Item> items = new ArrayList<>();
        Item item1 = new Item("Candies", 4001, "Mentos", 7, 3.50f);
        Item item2 = new Item("Candies", 4002, "Sour Patch", 7, 4.50f);
        Item item3 = new Item("Candies", 4003, "Skittles", 7, 2.50f);
        items.add(item1);
        items.add(item2);
        items.add(item3);

        Product product = new Product("Candies", items);

        ProductPage productPage = new ProductPage(product);
        VendingMachineRunner returns = productPage.runPage(scanner, vendingMachine);

        assertTrue(returns instanceof PurchasePage);

        System.setIn(System.in);
    }

    @Test
    public void TestPurchaseItemFromProductPageOutOfBounds() {
        String input = "10";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        Scanner scanner = new Scanner(System.in);

        VendingMachine vendingMachine = new VendingMachine(productDatabase);
        Product.parseProductDatabase(productDatabase);

        List<Item> items = new ArrayList<>();
        Item item1 = new Item("Candies", 4001, "Mentos", 7, 3.50f);
        Item item2 = new Item("Candies", 4002, "Sour Patch", 7, 4.50f);
        Item item3 = new Item("Candies", 4003, "Skittles", 7, 2.50f);
        items.add(item1);
        items.add(item2);
        items.add(item3);

        Product product = new Product("Candies", items);

        ProductPage productPage = new ProductPage(product);
        VendingMachineRunner returns = productPage.runPage(scanner, vendingMachine);

        assertTrue(returns instanceof DefaultPage);

        System.setIn(System.in);
    }

    @Test
    public void TestPurchaseItemFromProductPageNegativeValue() {
        String input = "-1";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        Scanner scanner = new Scanner(System.in);

        VendingMachine vendingMachine = new VendingMachine(productDatabase);
        Product.parseProductDatabase(productDatabase);

        List<Item> items = new ArrayList<>();
        Item item1 = new Item("Candies", 4001, "Mentos", 7, 3.50f);
        Item item2 = new Item("Candies", 4002, "Sour Patch", 7, 4.50f);
        Item item3 = new Item("Candies", 4003, "Skittles", 7, 2.50f);
        items.add(item1);
        items.add(item2);
        items.add(item3);

        Product product = new Product("Candies", items);

        ProductPage productPage = new ProductPage(product);
        VendingMachineRunner returns = productPage.runPage(scanner, vendingMachine);

        assertTrue(returns instanceof DefaultPage);

        System.setIn(System.in);
    }

    @Test
    public void TestPurchaseItemFromProductPageInvalidValue() {
        String input = "invalid";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        Scanner scanner = new Scanner(System.in);

        VendingMachine vendingMachine = new VendingMachine(productDatabase);
        Product.parseProductDatabase(productDatabase);

        List<Item> items = new ArrayList<>();
        Item item1 = new Item("Candies", 4001, "Mentos", 7, 3.50f);
        Item item2 = new Item("Candies", 4002, "Sour Patch", 7, 4.50f);
        Item item3 = new Item("Candies", 4003, "Skittles", 7, 2.50f);
        items.add(item1);
        items.add(item2);
        items.add(item3);

        Product product = new Product("Candies", items);

        ProductPage productPage = new ProductPage(product);
        VendingMachineRunner returns = productPage.runPage(scanner, vendingMachine);

        assertTrue(returns instanceof DefaultPage);

        System.setIn(System.in);
    }





}