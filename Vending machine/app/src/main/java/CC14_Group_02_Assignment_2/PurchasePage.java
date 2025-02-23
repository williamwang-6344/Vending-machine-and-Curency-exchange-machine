package CC14_Group_02_Assignment_2;

import java.util.Scanner;

public class PurchasePage implements VendingMachineRunner {

    private Item item;

    public PurchasePage(Item item) {
        this.item = item;
    }

    public void getOptions() {
        System.out.print("Options: [X] Go Back [#] Number corresponds to the number of items you want to purchase\n> ");
    }

    @Override
    public VendingMachineRunner runPage(Scanner scanner, VendingMachine vm) {

        System.out.println("\n@@@@@ Purchase: " + item.getName() + " @@@@@\n");
        System.out.println(this.item.viewItemDetails(item));

        String userInput = null;
        getOptions();
        while(scanner.hasNextLine()) {
            userInput = scanner.nextLine();
            if (userInput.toUpperCase().equals("X")){
                return new DefaultPage();
            } else {
                try {
                    int value = Integer.parseInt(userInput); // This is the quantity user has selected
                    if (value > 0 && value <= item.getQuantity()) {

                        // This is used as a placeholder for the page which will implement the functionality
                        // for the transaction of the purchase.
                        return new TransactionPage(this.item,value);
                    } else {
                        vm.getInvalid();
                        getOptions();
                    }
                } catch (NumberFormatException e) {
                    vm.getInvalid();
                    getOptions();
                }
            }
        }
        return new DefaultPage();
    }







}
