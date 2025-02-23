package CC14_Group_02_Assignment_2;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Product {

    private String category;
    private List<Item> items;

    public Product(String category, List<Item> items) {
        this.category = category;
        this.items = items;
    }

    public List<Item> getItems() {
        return items;
    }

    public String getCategory() {
        return category;
    }

    public static List<Product> parseProductDatabase(File productDatabase) {
        List<Product> products = new ArrayList<>();
        if (!productDatabase.exists()) {
            return products;
        }

        try {
            JSONParser parser = new JSONParser();
            JSONArray database = (JSONArray) parser.parse(new FileReader(productDatabase));

            for(Object productInfo : database) {
                JSONObject jsonInfo = (JSONObject) productInfo;

                String category = jsonInfo.get("category").toString();

                JSONArray productJSONArray = (JSONArray) jsonInfo.get("products");
                List<Item> itemList = new ArrayList<>();
                for(Object itemInfo : productJSONArray) {
                    JSONObject jsonItemInfo = (JSONObject) itemInfo;
                    int item_code = Integer.parseInt(jsonItemInfo.get("item_code").toString());
                    String item_name = jsonItemInfo.get("item_name").toString();
                    int item_quantity = Integer.parseInt(jsonItemInfo.get("item_quantity").toString());
                    float item_price = Float.parseFloat(jsonItemInfo.get("item_price").toString());
                    Item item = new Item(category, item_code, item_name, item_quantity, item_price);
                    itemList.add(item);
                }

                Product product = new Product(category, itemList);
                products.add(product);
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found");;
        } catch (IOException e) {
            System.out.println("Invalid file");;
        } catch (ParseException e) {
            e.printStackTrace();
        }catch (NumberFormatException e) {

            System.out.println("Unable to parse the file");;
        }


        return products;
    }

    public static String viewDetail(Product product) {
        if (product == null) {
            return "\nThe vending machine doesn't store the Product\n";
        }

        String productInfo = "";
        for(int i = 0; i < product.getItems().size(); i++) {
            Item item = product.getItems().get(i);
            productInfo += ("\n["+i+"]\n");
            productInfo += ("Item Code: " + item.getCode() + "\n");
            productInfo += ("Item Name: " + item.getName() + "\n");
            productInfo += ("Item Quantity: " + item.getQuantity() + "\n");
            productInfo += ("Item Price: " + item.getPrice() + "\n");
        }
        return productInfo;
    }

    public static String viewSimple(Product product, int index) {
        if (product == null || product.getItems().size() == 0) {
            return "\nThe vending machine doesn't store the Product\n";
        }
        String productInfo = String.format("===== Category: %s -> [%d] =====\n", product.getCategory(), index);
        for(int i = 0; i < product.getItems().size(); i++) {
            productInfo += ((i+1) + ". " + product.getItems().get(i).getName() + "\n");
        }
        return productInfo;
    }
}
