package CC14_Group_02_Assignment_2;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class VendingMachine {

    List<Product> products;
    List<User> users;
    String currentUser = "";
    String currentUserLevel = "none";
    UserManager userManager;

    CardManager cardManager;


    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String user, String userLevel) {
        this.currentUser = user;
        this.currentUserLevel = userLevel;
    }

    public VendingMachine(File productDatabase) {
        this.products = Product.parseProductDatabase(productDatabase);
        this.userManager = new UserManager("../app/src/main/resources/Databases/UserProfiles.csv");
        this.cardManager = new CardManager("../app/src/main/resources/Databases/Card.csv", "../app/src/main/resources/Databases/UserSavedCard.csv");
    }

    public List<Product> getProducts() {
        return this.products;
    }


    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public UserManager getUserManager(){
        return this.userManager;
    }
    public CardManager getCardManager(){
        return this.cardManager;
    }

    public void getInvalid() {
        System.out.println("\nInvalid Input. Please try again\n");
    }

    public static void displayProducts(List<Product> productsList) {
        if (productsList == null || productsList.size() <= 0) {
            System.out.println("The vending machine has no products to display");
            return;
        }

        for (int i = 0; i < productsList.size(); i++) {
            Product product = productsList.get(i);
            System.out.println(Product.viewSimple(product, i));
        }
    }
    public static void displayFullProducts(List<Product> productsList) {
        if (productsList == null || productsList.size() <= 0) {
            System.out.println("The vending machine has no products to display");
            return;
        }
        for (int i = 0; i < productsList.size(); i++) {
            Product product = productsList.get(i);
            System.out.println(product.viewDetail(product));
        }
    }


    /**
     * This function will ensure the default page is always running.
     * Other pages will run from the default page based on the options available to guest/customer
     */
    public void runMachine() {
        Scanner scanner = new Scanner(System.in);
        VendingMachineRunner defaultPage = new DefaultPage();


        while(true) {


            defaultPage = defaultPage.runPage(scanner, this);
            if (defaultPage == null) {
                break;
            }
        }
        scanner.close();
//        Scanner scanner = new Scanner(System.in);
//        VendingMachineRunner defaultPage = new SellerPage(App.productDatabase);
//        while(true) {
//            defaultPage = defaultPage.runPage(scanner, this);
//            if (defaultPage == null) {
//                break;
//            }
//        }
//        scanner.close();
    }





}
