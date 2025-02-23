package CC14_Group_02_Assignment_2;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class VendingMachineTest {

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
    public void TestEmptyProductsListToDisplay() {
        List<Product> products = new ArrayList<>();
        VendingMachine.displayProducts(products);
        String message = "The vending machine has no products to display\n";
        assertEquals(message.strip(), output.toString().strip());
        System.setIn(System.in);
    }

    @Test
    public void TestInvalidProductsListToDisplay() {
        VendingMachine.displayProducts(null);
        String message = "The vending machine has no products to display\n";
        assertEquals(message.strip(), output.toString().strip());
        System.setIn(System.in);
    }


}
