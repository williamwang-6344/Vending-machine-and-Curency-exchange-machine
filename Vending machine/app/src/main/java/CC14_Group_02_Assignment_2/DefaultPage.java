package CC14_Group_02_Assignment_2;

import CC14_Group_02_Assignment_2.VendingMachine;
import CC14_Group_02_Assignment_2.VendingMachineRunner;

import java.util.Scanner;

public class DefaultPage implements VendingMachineRunner {

    // Add more options to this for different user stories
    private void getOptions() {
        System.out.print("Options: [#] Number corresponding to the category, to view its full product details\n");
    }

    private void getLoginOptions(VendingMachine machine) {
        if (machine.getCurrentUser().equals("")) {
            System.out.print("Account Options:\n> ");
            System.out.print("  LOGIN username password\n> ");
            System.out.print("  REGISTER username password\n> ");
            System.out.print("  LOGOUT\n> ");
        }
    }

    public VendingMachineRunner runPage(Scanner scanner, VendingMachine vm) {

        System.out.println("########## Welcome to Vending Machine ##########\n");
        VendingMachine.displayProducts(vm.getProducts());

        getOptions();
        getLoginOptions(vm);
        String userInput = "";
        while(scanner.hasNextLine()) {

            userInput = scanner.nextLine();
            //Place commandline commands here, either to spawn GUI, or call commands directly

            if (userInput.toUpperCase().equals("L") && vm.getCurrentUser().equals("")) {
                LoginGUI.main(vm);








            }

            //Very basic console login - needs to be updated at some point, or removed if we just switch to GUI only
            else if(userInput.toUpperCase().startsWith("LOGIN")){

                String[] commands = userInput.split(" ");
                if(commands.length != 3){
                    System.out.println("Not enough arguments for login, please enter in format: login username password");
                    continue;
                }
                String result = vm.getUserManager().loginUser(commands[1],commands[2]);

                if(result.equals("fail")){
                    System.out.println("Failed to login as user " + commands[1]);
                } else {
                    vm.setCurrentUser(commands[1], result);
                    System.out.println("Login successful! Welcome " + vm.getCurrentUser() + "!");
                    System.out.println("You can now type commands to interact with the vending machine");

                }
                continue;
            }

            //Very basic console account registration - same notes as login
            else if(userInput.toUpperCase().startsWith("REGISTER")){
                String[] commands = userInput.split(" ");
                if(commands.length != 3){
                    System.out.println("Not enough arguments for register, please enter in format: register username password");
                    continue;
                }
                //Register new 'user' level account
                boolean result = vm.getUserManager().registerUser(commands[1],commands[2], "user");
                if(result){
                    vm.setCurrentUser(commands[1], "user");
                    System.out.println("New user created.");
                    System.out.println("Login successful! Welcome " + vm.getCurrentUser() + "!");
                    System.out.println("You can now type commands to interact with the vending machine");
                } else {
                    System.out.println("Failed to create user. ##TODO list reasons for failure");
                }
                continue;
            }
            if (vm.currentUserLevel.equals("seller") && userInput.toUpperCase().equals("S")) {
                return new SellerPage(App.productDatabase);
            }


            if(userInput.toUpperCase().startsWith("LOGOUT")){

                vm.setCurrentUser("", "none");
                continue;
            }

            try {
                int value = Integer.parseInt(userInput);
                if (value >= 0 && value < vm.getProducts().size()) {
                    return new ProductPage(vm.getProducts().get(value));
                } else {
                    vm.getInvalid();
                    getOptions();
                    getLoginOptions(vm);
                }
            } catch (NumberFormatException e) {
                vm.getInvalid();
                getOptions();
            }

        }
        return new DefaultPage();




    }


}