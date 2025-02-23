package CC14_Group_02_Assignment_2;
import CC14_Group_02_Assignment_2.VendingMachine;
import CC14_Group_02_Assignment_2.VendingMachineRunner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class SellerPage implements VendingMachineRunner {
    private File database;
    public SellerPage(File database) {
        this.database = database;
    }
    public static void getOptions() {
        System.out.print("Options: [A] Add new product\n" +
                "         [D] Delete a product\n" +
                "         [M] Modify an existing  product\n" +
                "         [X] Exit \n> ");
    }
    public  boolean delete(String Category, String itemCode){
        try {
            JSONParser parser = new JSONParser();
            JSONArray database = (JSONArray) parser.parse(new FileReader(this.database ));
            JSONArray newDatabase = new JSONArray();

            for(int i = 0; i <  database.size();i++) {
                JSONObject jsonInfo = (JSONObject) database.get(i);
                JSONArray productJSONArray = (JSONArray) jsonInfo.get("products");
                if (jsonInfo.get("category").toString().equals(Category) ){
                    JSONArray newProductJSONArray = new JSONArray();
                    for(Object itemInfo : productJSONArray) {

                        JSONObject jsonItemInfo = (JSONObject) itemInfo;

                        if (jsonItemInfo.get("item_code").toString().equals(itemCode)){



                        }
                        else{
                            newProductJSONArray.add(jsonItemInfo);
                        }


                    }

                    JSONObject newObj = new JSONObject();
                    newObj.put("category",jsonInfo.get("category") );
                    newObj.put("products",newProductJSONArray );
                    newDatabase.add(newObj);

                }
                else{
                    newDatabase.add(jsonInfo);
                }



            }


            FileWriter file = new FileWriter(this.database );
            file.write(newDatabase.toJSONString());
            file.flush();
            if (newDatabase.equals(database)){
                return false;
            }
            else{
                return true;
            }




        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();

        }
        return false;
    }
    public  static boolean refillOrAdjust(String Category, String itemCode,String itemName, String itemPrice, String itemQuantity , File Database){

        try {
            if (Integer.parseInt(itemCode)<0 || Integer.parseInt(itemQuantity)<0|| Float.parseFloat(itemPrice)<0 ){
                System.out.println("ItemCode, itemPrice , itemQuantity must  all be  positive.");
                return false;

            }
            String  Price = String.format("%.2f", Float.parseFloat(itemPrice));
            JSONParser parser = new JSONParser();
            JSONArray database = (JSONArray) parser.parse(new FileReader(Database ));
            JSONArray newDatabase = new JSONArray();

            for(int i = 0; i <  database.size();i++) {
                JSONObject jsonInfo = (JSONObject) database.get(i);
                JSONArray productJSONArray = (JSONArray) jsonInfo.get("products");
                if (jsonInfo.get("category").toString().equals(Category) ){
                    JSONArray newProductJSONArray = new JSONArray();
                    for(Object itemInfo : productJSONArray) {

                        JSONObject jsonItemInfo = (JSONObject) itemInfo;

                        if (jsonItemInfo.get("item_code").toString().equals(itemCode)){

                            JSONObject newItem = new JSONObject();
                            newItem.put("item_code",Integer.parseInt(itemCode));
                            newItem.put("item_price",Float.parseFloat(Price));
                            newItem.put("item_name",itemName);

                            newItem.put("item_quantity",Integer.parseInt(itemQuantity));
                            newProductJSONArray.add(newItem);




                        }
                        else{
                            newProductJSONArray.add(jsonItemInfo);
                        }


                    }

                    JSONObject newObj = new JSONObject();
                    newObj.put("category",jsonInfo.get("category") );
                    newObj.put("products",newProductJSONArray );
                    newDatabase.add(newObj);

                }
                else{
                    newDatabase.add(jsonInfo);
                }



            }


            FileWriter file = new FileWriter(Database );
            file.write(newDatabase.toJSONString());
            file.flush();
            if (newDatabase.equals(database)){
                return false;
            }
            else{
                return true;
            }





        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();

        }
        catch (NumberFormatException e) {
            System.out.println("Price and quantity must be an numerical value. Please try again.");
        }
        return false;
    }
    public  boolean add(String Category, String itemCode,String itemName, String itemPrice, String itemQuantity ){
        try {
            if (Integer.parseInt(itemCode)<0 || Integer.parseInt(itemQuantity)<0|| Float.parseFloat(itemPrice)<0 ){
                System.out.println("ItemCode, itemPrice , itemQuantity must  all be  positive.");
                return false;

            }
            String  Price = String.format("%.2f", Float.parseFloat(itemPrice));
            JSONParser parser = new JSONParser();
            JSONArray database = (JSONArray) parser.parse(new FileReader(this.database ));
            JSONArray newDatabase = new JSONArray();
            int count =0;
            for(int i = 0; i <  database.size();i++) {
                JSONObject jsonInfo = (JSONObject) database.get(i);
                JSONArray productJSONArray = (JSONArray) jsonInfo.get("products");
                JSONArray newProductJSONArray = new JSONArray();
                System.out.println(count);
                if (jsonInfo.get("category").toString().equals(Category) ){

                    int counter=0;
                    for(Object itemInfo : productJSONArray) {

                        JSONObject jsonItemInfo = (JSONObject) itemInfo;

                        if (jsonItemInfo.get("item_code").toString().equals(itemCode)){


                            System.out.println("Item code already exist");
                            return false;




                        }
                        else if (!jsonItemInfo.get("item_code").toString().equals(itemCode) && counter!=productJSONArray.size()-1){
                            counter+=1;
                            newProductJSONArray.add(jsonItemInfo);
                        }
                        else if (counter==productJSONArray.size()-1){
                            newProductJSONArray.add(jsonItemInfo);
                            JSONObject newItem = new JSONObject();
                            newItem.put("item_code",Integer.parseInt(itemCode));
                            newItem.put("item_price",Float.parseFloat(Price));
                            newItem.put("item_name",itemName);

                            newItem.put("item_quantity",Integer.parseInt(itemQuantity));
                            newProductJSONArray.add(newItem);
                        }


                    }

                    JSONObject newObj = new JSONObject();
                    newObj.put("category",jsonInfo.get("category") );
                    newObj.put("products",newProductJSONArray );
                    newDatabase.add(newObj);

                }
                else if (!jsonInfo.get("category").toString().equals(Category) && count!= database.size()-1){
                    newDatabase.add(jsonInfo);
                    count+=1;
                }
                else if (count == database.size()-1  ){
                    newDatabase.add(jsonInfo);
                    JSONObject newItem = new JSONObject();
                    newItem.put("item_code",Integer.parseInt(itemCode));
                    newItem.put("item_price",Float.parseFloat(Price));
                    newItem.put("item_name",itemName);

                    newItem.put("item_quantity",Integer.parseInt(itemQuantity));
                    newProductJSONArray.add(newItem);
                    JSONObject newObj = new JSONObject();
                    newObj.put("category",Category );
                    newObj.put("products",newProductJSONArray );
                    newDatabase.add(newObj);

                }



            }


            FileWriter file = new FileWriter(this.database );
            file.write(newDatabase.toJSONString());
            file.flush();
            if (newDatabase.equals(database)){
                return false;
            }
            else{
                return true;
            }






        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();

        }
        catch (NumberFormatException e) {
            System.out.println("Price and quantity must be an numerical value. Please try again.");
        }
        return false;
    }
    public  void modify(Scanner scanner){
        System.out.println("Choose item to Modify, Format: Category,Item Code ,Item Name, Item price, Item quantity or press X to exit\n>");
        while(scanner.hasNextLine()) {
            String userInput = scanner.nextLine();
            if (userInput.toUpperCase().equals("X")) {
                this.getOptions();

                break;
            }
            String[] splitedUp = userInput.split(",");
            for (int i =0; i < splitedUp.length;i++){
                splitedUp[i]= splitedUp[i].strip();
            }
            if (splitedUp.length!=5 || userInput.isEmpty()){
                System.out.println("Format: Category,Item Code ,Item Name, Item price, Item quantity or press X to exit\n>");
            }
            else{
                if (this.refillOrAdjust(splitedUp[0],splitedUp[1],splitedUp[2],splitedUp[3],splitedUp[4],App.productDatabase)){
                    System.out.println("\nModification complete!");
                    System.out.println("Choose item to Modify, Format: Category,Item Code ,Item Name, Item price, Item quantity or press X to exit\n>");
                }
                else{
                    System.out.println("Modification failed! Please check your input value");
                    System.out.println("Choose item to Modify, Format: Category,Item Code ,Item Name, Item price, Item quantity or press X to exit\n>");
                }
            }

        }

    }
    public  void userDelete(Scanner scanner){
        System.out.println("Choose item to Delete; Format Category,Item Code or press X to exit\n>");
        while(scanner.hasNextLine()) {
            String userInput = scanner.nextLine();
            if (userInput.toUpperCase().equals("X")) {
                this.getOptions();

                break;
            }
            String[] splitedUp = userInput.split(",");
            for (int i =0; i < splitedUp.length;i++){
                splitedUp[i]= splitedUp[i].strip();
            }
            if (splitedUp.length!=2 || userInput.isEmpty()){
                System.out.println("Format: Category,Item Code or press X to exit\n>");
            }
            else{
                if (this.delete(splitedUp[0],splitedUp[1])){
                    System.out.println("\nDeletion complete!");
                    System.out.println("Choose item to delete; Format Category,Item Code or press X to exit\n>");
                }
                else{
                    System.out.println("Deletion failed! Please check your input value");
                    System.out.println("Choose item to delete; Format Category,Item Code or press X to exit\n>");
                }
            }

        }

    }
    public  void userAdd(Scanner scanner){
        System.out.println("Choose item to Add, Format: Category,Item Code ,Item Name, Item price, Item quantity or press X to exit\n>");
        while(scanner.hasNextLine()) {
            String userInput = scanner.nextLine();
            if (userInput.toUpperCase().equals("X")) {
                this.getOptions();

                break;
            }
            String[] splitedUp = userInput.split(",");
            for (int i =0; i < splitedUp.length;i++){
                splitedUp[i]= splitedUp[i].strip();
            }
            if (splitedUp.length!=5 || userInput.isEmpty()){
                System.out.println("Format: Category,Item Code ,Item Name, Item price, Item quantity or press X to exit\n>");
            }
            else{
                if (this.add(splitedUp[0],splitedUp[1],splitedUp[2],splitedUp[3],splitedUp[4])){
                    System.out.println("\nAddition complete!");
                    System.out.println("Choose item to Add, Format: Category,Item Code ,Item Name, Item price, Item quantity or press X to exit\n>");
                }
                else{
                    System.out.println("Addition failed! Please check your input value");
                    System.out.println("Choose item to Add, Format: Category,Item Code ,Item Name, Item price, Item quantity or press X to exit\n>");
                }
            }

        }

    }



    public VendingMachineRunner runPage(Scanner scanner, VendingMachine vm){
        System.out.println("\n########## Welcome to Seller Page ##########\n");


        
        getOptions();
        while(scanner.hasNextLine()) {
            String userInput = scanner.nextLine();
            if (userInput.toUpperCase().equals("X")){
                vm.currentUserLevel = "none";
                vm.currentUser = "";
                return new DefaultPage();
            } else if (userInput.toUpperCase().equals("D")) {
                this.userDelete(scanner);
            }
             else if (userInput.toUpperCase().equals("M")) {
                this.modify(scanner);
            }
            else if (userInput.toUpperCase().equals("A")) {
                this.userAdd(scanner);
            }
            else{
                System.out.println("Invalid Input. Try again");
            }

        }
        return new SellerPage(App.productDatabase);
        
    }
}
