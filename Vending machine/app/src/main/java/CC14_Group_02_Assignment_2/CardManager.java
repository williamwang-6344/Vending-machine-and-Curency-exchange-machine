package CC14_Group_02_Assignment_2;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;
public class CardManager {
    private final File cardList;
    private final File cardSaver;
    private HashMap<String, String> currentCards;
    public CardManager(String cardListPath, String cardSaverPath) {
        this.cardList = new File(cardListPath);
        this.cardSaver = new File(cardSaverPath);
        this.currentCards = new HashMap<>();
        this.loadCardFromDb();
    }
    public void loadCardFromDb(){
        HashMap<String, String> cardDetail = new HashMap<>();

        try {
            Scanner scanner = new Scanner(cardList);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] cardLine = line.split(",");
                cardDetail.put(cardLine[0],cardLine[1]);
            }
            scanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("Unable to refresh DB from file. File not found.");
            //throw new RuntimeException(e);
        }

        this.currentCards = cardDetail;
    }
    public boolean checkCardDetail(String cardNumber, String password){

        if(this.currentCards.containsKey(cardNumber)){
            return this.currentCards.get(cardNumber).equals(password);
        } else {
            return false;
        }
    }

    public void saveCard(String currentUser, String cardNumber) {
        try {
            FileWriter fw = new FileWriter(cardSaver, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw);
            out.println(currentUser + "," + cardNumber);
            out.close();
        } catch (IOException e) {
            System.out.println("Unable to save card to file. File not found.");
            //throw new RuntimeException(e);
        }
    }

    public boolean checkSavedCard(String currentUser) {
        try {
            Scanner scanner = new Scanner(cardSaver);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] cardLine = line.split(",");
                if (cardLine[0].equals(currentUser)) {
                    return true;
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to check saved card. File not found.");
            //throw new RuntimeException(e);
        }
        return false;
    }
}
