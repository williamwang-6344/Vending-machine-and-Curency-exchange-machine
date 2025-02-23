package CC14_Group_02_Assignment_2;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.io.*;

public class TransactionPage implements VendingMachineRunner {

    private Item item;
    private int quantitySelectd;

    public TransactionPage(Item item,int quantitySelected) {
        this.item = item;
        this.quantitySelectd = quantitySelected;

    }

    public void getOptions() {
        System.out.print("Options: [X] Go Back [CD] purchase by card [CS] purchase by cash.\n> ");
    }

    @Override
    public VendingMachineRunner runPage(Scanner scanner, VendingMachine vm) {
        System.out.println("\n@@@@@ Purchase: "+ this.quantitySelectd+ " * "+this.item.getName() + " for "+ this.quantitySelectd* this.item.getPrice()  +" dollars @@@@@\n");
        String userInput = null;
        getOptions();
        while(scanner.hasNextLine()) {
            userInput = scanner.nextLine();
            if (userInput.toUpperCase().equals("X")){
                return new DefaultPage();
            } else if(userInput.equals("CD")) {

                if (!vm.getCurrentUser().equals("") && vm.cardManager.checkSavedCard(vm.getCurrentUser())) {
                    System.out.println("You have saved card, do you want to use it? (Y/N)");
                    String input = scanner.nextLine();
                    if (input.equals("Y")) {
                        System.out.println("Transaction Successful!");
                        return new DefaultPage();
                    } else {
                        TransactionUI.main(vm, this.item, this.quantitySelectd);
                        continue;
                    }
                } else {
                    TransactionUI.main(vm, this.item, this.quantitySelectd);
                    continue;
                }
            } else if (userInput.equals("CS")) {
                System.out.println("Cash payment not supported yet");
            }else{
                vm.getInvalid();
            }
        }
        return new DefaultPage();
    }







}
