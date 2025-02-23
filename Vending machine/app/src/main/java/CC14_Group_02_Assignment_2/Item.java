package CC14_Group_02_Assignment_2;

public class Item {

    private String category;
    private int code;
    private String name;
    private int quantity;
    private float price;

    public Item(String category, int code, String name, int quantity, float price) {
        this.category = category;
        this.code = code;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String viewItemDetails(Item item) {
        String itemInfo = "===== Item Details: =====\n";
        itemInfo += ("Item Code: " + item.getCode() + "\n");
        itemInfo += ("Item Name: " + item.getName() + "\n");
        itemInfo += ("Item Quantity: " + item.getQuantity() + "\n");
        itemInfo += ("Item Price: " + item.getPrice() + "\n");

        return itemInfo;
    }
}
