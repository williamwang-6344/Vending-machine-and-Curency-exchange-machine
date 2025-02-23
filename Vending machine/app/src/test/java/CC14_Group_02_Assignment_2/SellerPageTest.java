package CC14_Group_02_Assignment_2;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;
public class SellerPageTest {
    private File Database = new File("../app/src/main/resources/Databases/TestDatabase.json");
    private SellerPage sellerpage = new SellerPage(Database);


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
    public void testDelete1(){

        List<Product> old = new ArrayList<Product>();
        old = Product.parseProductDatabase(Database);
        assertFalse(sellerpage.delete("abc","1001"),
                "Test Delete 1: Should not delete if category or item code is not right");
        assertFalse(sellerpage.delete("Drinks","pp"),
                "Test Delete 1: Should not delete if category or item code is not right");
        List<Product> now = new ArrayList<Product>();
        now = Product.parseProductDatabase(Database);
//        assertEquals(old,now,"Test Delete 1: Should not delete if category or item code is not right");


    }
    @Test
    public void testDelete2(){


        assertTrue(sellerpage.delete("Drinks","1003"),
                "Test Delete 2: Should  delete if category and item code is  right");
        List<Product> now = new ArrayList<Product>();
        now = Product.parseProductDatabase(Database);
        for (int i =0 ; i < now.size();i++){
            if (now.get(i).getCategory().equals("Drinks")){
                for(int j =0 ; j < now.get(i).getItems().size();j++){
                    if (now.get(i).getItems().get(j).getCode()==1003){
                        fail("Test Delete 2: Should  delete if category and item code is  right");
                    }
                }
            }
        }
        sellerpage.add("Drinks","1003","Coca cola","4.5","7");



    }
    @Test
    public void testAdd1(){


        List<Product> old = new ArrayList<Product>();
        old = Product.parseProductDatabase(Database);
        assertFalse(sellerpage.add("Drinks","ff","pp","90","10"),
                "Test Add 1: Should not add if input format is not right") ;
        assertFalse(sellerpage.add("Drinks","1004","pp","90","10"),
                "Test Add 1: Should not add if input format is not right") ;
        List<Product> now = new ArrayList<Product>();
        now = Product.parseProductDatabase(Database);
//        assertEquals(old,now,"Test Add 1: Should not add if input format is not right");


//
 }
    @Test
    public void testAdd2(){

        Boolean answer = false;
        assertTrue(sellerpage.add("Drinks","1009","Coca cola","4.5","7"),
                "Test Add 2: Did not add the correct value. " );
        List<Product> now = new ArrayList<Product>();
        now = Product.parseProductDatabase(Database);

        for (int i =0 ; i < now.size();i++){
            if (now.get(i).getCategory().equals("Drinks")){
                for(int j =0 ; j < now.get(i).getItems().size();j++){
                    if (now.get(i).getItems().get(j).getCode()==1003 ){
                        answer = true;
                    }
                }
            }
        }
        assertTrue(answer,"Test Add 2: Did not add the correct value. ");



    }
    @Test
    public void testMod1(){


        List<Product> old = new ArrayList<Product>();
        old = Product.parseProductDatabase(Database);
        assertFalse(sellerpage.refillOrAdjust("Drinks","ff","pp","90","10",Database),
                "Test Mod 1: Should not modify if input format is not right");
        assertFalse(sellerpage.refillOrAdjust("Drinks","1010","pp","90","10",Database),
                "Test Mod 1: Should not modify if input format is not right");
        List<Product> now = new ArrayList<Product>();
        now = Product.parseProductDatabase(Database);
//        assertEquals(old,now,"Test Mod 1: Should not modify if input format is not right");



    }
    @Test
    public void testMod2(){

        Boolean answer = false;
        assertTrue(sellerpage.refillOrAdjust("Drinks","1009","Coca cola","4.5","9",Database),
                "Test Add 2: Did not add the correct value. " );
        List<Product> now = new ArrayList<Product>();
        now = Product.parseProductDatabase(Database);
        for (int i =0 ; i < now.size();i++){
            if (now.get(i).getCategory().equals("Drinks")){
                for(int j =0 ; j < now.get(i).getItems().size();j++){
                    if (now.get(i).getItems().get(j).getCode()==1009 && now.get(i).getItems().get(j).getQuantity()==9
                            && now.get(i).getItems().get(j).getPrice()==4.5 && now.get(i).getItems().get(j).getName().equals("Coca cola")){
                        answer = true;
                    }
                }
            }
        }
        assertTrue(answer,"Test Add 2: Did not add the correct value. ");
        sellerpage.delete("Drinks","1009");




    }
}
