package CC14_Group_02_Assignment_2;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class App {


    public static File productDatabase = getFile("../app/src/main/resources/Databases/ProductDatabase.json");

    public static File getFile(String filename) {
        File f = new File(filename);
        return f;
    }

    public static void main(String[] args) {
        if (productDatabase == null) {
            return;
        }

        VendingMachine vm = new VendingMachine(productDatabase);
        vm.runMachine();
    }
}
