package CC14_Group_02_Assignment_2;

import java.util.Scanner;

public class ProductPage implements VendingMachineRunner {

    private Product product;

    public ProductPage(Product product) {
        this.product = product;
    }

    private void getOptions() {
        System.out.print("Options: [X] Go Back [#] Number corresponding to the item you want to purchase\n> ");
    }

    @Override
    public VendingMachineRunner runPage(Scanner scanner, VendingMachine vm) {

        String userInput = "";

        System.out.println("\n@@@@@ PRODUCT DETAILS @@@@@\n");
        System.out.print("===== Category: " + product.getCategory() + "=====");
        System.out.println(Product.viewDetail(product));
        getOptions();
        while(scanner.hasNextLine()) {
            userInput = scanner.nextLine();
            if (userInput.toUpperCase().equals("X")) {
                return new DefaultPage();
            } else {
                try {
                    int value = Integer.parseInt(userInput);
                    if (value >= 0 && value < product.getItems().size()) {
                        return new PurchasePage(product.getItems().get(value));
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
