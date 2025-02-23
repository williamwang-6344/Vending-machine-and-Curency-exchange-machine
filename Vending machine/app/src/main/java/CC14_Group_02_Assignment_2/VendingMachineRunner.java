package CC14_Group_02_Assignment_2;

import java.util.Scanner;

public interface VendingMachineRunner {

    /**
     * Every page when it runs (e.g. default, login, register, transaction)
     * will generate a new page and will be run by other classes via implementation
     * of the runPage function.
     *
     * @param scanner
     * @param vm
     */
    public VendingMachineRunner runPage(Scanner scanner, VendingMachine vm);
}