package CC14_Group_02_Assignment_2;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public class UserManager {

    private File userList;
    private HashMap<String, User> currentUsers;


    public UserManager(String userListPath){
        this.userList = new File(userListPath);
        this.currentUsers = new HashMap<>();
        this.restoreFromDB();
    }

    public boolean removeUser(String user){
        //Removes user by name #TODO ensure only used by authorised account
        for(String key : currentUsers.keySet()) {
            if(key == user){
                currentUsers.remove(key);
                return true;
            }
        }
        //User not found
        return false;
    }

    public boolean restoreFromDB(){
        //Reloads user list from what is saved to disk. Changes made currently set to save to disk on success.
        HashMap<String, User> refreshedData = new HashMap<>();

        try {
            Scanner scanner = new Scanner(userList);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] user = line.split(",");
                if(user.length != 3){
                    System.out.println("Malformed DB Entry: " + line.strip());
                    System.out.println("Skipping...");
                    continue;
                }
                refreshedData.put(user[0], new User(user[0], user[1], user[2]));
            }
            scanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("Unable to refresh DB from file. File not found.");
            //throw new RuntimeException(e);
            return false;
        }

        this.currentUsers = refreshedData;
        return true;
    }

    public boolean saveToDB() {

        //Write changes to user DB to file
        //This rewrites the whole file, to allow for easy deletion of users, etc.
        //Refactor to JSON if this becomes annoying in future
        String userdata = "";
        for(String key : currentUsers.keySet()){
            User user = currentUsers.get(key);
            userdata += String.format("%s,%s,%s\n", user.getUsername(), user.getPassword(), user.getRole());
        }


        //Write the DB out
        try {
            java.io.PrintWriter writer = new PrintWriter(new FileWriter(userList));
            writer.print(userdata);
            writer.close();
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public String loginUser(String username, String password){

        if(this.currentUsers.containsKey(username)){
            if(this.currentUsers.get(username).getPassword().equals(password)){
                return this.currentUsers.get(username).getRole();
            } else {
                return "fail";
            }
        } else {
            return "fail";
        }
    }

    public boolean registerUser(String username, String password, String accessLevel){
        if(this.currentUsers.containsKey(username)){
            System.out.println("User already exists.");
            return false;
        } else {
            this.currentUsers.put(username, new User(username,password,accessLevel));
            this.saveToDB();
            return true;
        }
    }





}
