package E_Commerce;

import java.security.PrivateKey;
import java.util.*;

class OutOfStockException extends Exception{
    public OutOfStockException(String msg){
        super(msg);
    }
}

class InvalidProductException extends Exception{
    public InvalidProductException(String msg){
        super(msg);
    }
}

class Product {
    private int productId;
    private String productName;
    private String category;
    private int stock;
    private double price;

    public Product(int productId, String productName, String category, int stock, double price){
        this.productId = productId;
        this.productName = productName;
        this.category = category;
        this.stock = stock;
        this.price = price;
    }

    public double getPrice(){
        return this.price;
    }

    public String getProductName() {
        return productName;
    }

    public int getProductId() {
        return productId;
    }

    public void manageStock(int n)throws OutOfStockException{
        if(stock < n){
            throw new OutOfStockException("Not Enough Product");
        }
        stock -= n;
    }

    public String toString(){
        return "ID: " + productId + " | Name: " + productName + " | Category: " + category + " | Stock: " + stock + " | Price" + price;
    }
}

class Cart {
    ArrayList<Product> cartItems = new ArrayList<>();

    public void addCart(Product p){
        cartItems.add(p);
    }

    public ArrayList<Product> getcartItems(){
        return cartItems;
    }

    public void clearCart(){
        cartItems.clear();
    }
}

class User {
    private String userName;
    private ArrayList<Order> orders = new ArrayList<>();

    public User(String userName, ArrayList<Order> orders){
        this.userName = userName;
        this.orders = orders;
    }

    public String toString(){
        return "User Name: " + userName + " => " + orders;
    }
}

class Order {
    private static int count = 0;
    private int productId;
    private double totalAmount;
    private Date time;

    public Order(ArrayList<Product> products){
        count = ++count;
        this.productId = count;

        double sum = 0;
        for(Product p : products){
            sum+=p.getPrice();
        }
        this.totalAmount = sum;
        this.time = new Date();
    }

    public String toString(){
        return "\nProduct ID: " + productId + " | Total Amount: " + totalAmount + " | Time: " + time;
    }
}

public class Main {
    Cart cart = new Cart();
    ArrayList<Order> orderHistory = new ArrayList<>();
    ArrayList<Product>products = new ArrayList<>();
    ArrayList<User> users = new ArrayList<>();

    private void addProduct() throws InvalidProductException{
        Scanner sc = new Scanner(System.in);
        String[] category = {"Books", "Clothing", "Grocery", "Electronics"};

        System.out.print("Enter Product Id: ");
        int id = sc.nextInt();
        for(Product p : products){
            if(p.getProductId() == id){
                throw new InvalidProductException("This product ID already Available!");
            }
        }
        sc.nextLine();
        System.out.print("Enter Product Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Price: ");
        double price = sc.nextDouble();
        System.out.print("Enter stock: ");
        int stock = sc.nextInt();
        int i=1;
        for (String s : category){
            System.out.println(" " + i + ". " + s);
            i++;
        }
        System.out.print("Choose options: ");
        int ch = sc.nextInt();
        String categories = " ";

        switch (ch){
            case 1:
                categories = "Books";
                break;

            case 2:
                categories = "Clothing";
                break;

            case 3:
                categories = "Grocery";
                break;

            case 4:
                categories = "Electronics";
                break;

            default:
                System.out.println("Invalid choice!");
                return;
        }
        products.add(new Product(id, name, categories, stock, price));
    }

    private void viewProduct(){
        Scanner sc = new Scanner(System.in);
        for(Product p : products){
            System.out.println(p);
        }
    }

    private void searchProduct() throws InvalidProductException{
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter product name for search: ");
        String s = sc.nextLine();
        String str = s.toLowerCase();

        boolean a = true;
        for(Product p : products){
            if((p).getProductName().toLowerCase().contains(str)){
                System.out.println(p);
                a = false;
            }
        }
        if(a){
            throw new InvalidProductException("Product not Found");
        }
    }

    private void addCart() throws InvalidProductException, OutOfStockException{
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Product ID: ");
        int pid = sc.nextInt();
        System.out.print("Enter no. of Product: ");
        int num = sc.nextInt();

        boolean b = true;
        for (Product p : products) {
            if(p.getProductId() == pid) {
                for(int j=0; j<num; j++) {
                    cart.addCart(p);
                }
                p.manageStock(num);
                b = false;
                System.out.println("Add to Cart Successfully");
            }
        }
        if(b){
            throw new InvalidProductException("Product not Available!");
        }
    }

    private void checkOut(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter user Name: ");
        String user = sc.nextLine();

        orderHistory.add(new Order(cart.getcartItems()));
        users.add(new User(user, orderHistory));
        System.out.println("Order Confirmed");
        cart.clearCart();
    }

    private void viewHistory(){
        for (User e : users){
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        Main m = new Main();

        try {
            while (true) {
                System.out.println("""
                         1. Add Product
                         2. View Product
                         3. Search Product
                         4. Add to cart
                         5. Checkout
                         6. View Order History
                         7. Exit
                        """);
                System.out.print("Enter your Choice: ");
                Scanner sc = new Scanner(System.in);
                int choice = sc.nextInt();

                switch (choice){
                    case 1:
                        m.addProduct();
                        break;

                    case 2:
                        m.viewProduct();
                        break;

                    case 3:
                        m.searchProduct();
                        break;

                    case 4:
                        m.addCart();
                        break;

                    case 5:
                        m.checkOut();
                        break;

                    case 6:
                        m.viewHistory();
                        break;

                    case 7:
                        System.out.println("Exiting...");
                        return;

                    default:
                        System.out.println("Invalid Number!");
                        return;
                }
            }
        }catch (InvalidProductException | OutOfStockException e){
            System.out.println(e.getMessage());
        }
    }
}