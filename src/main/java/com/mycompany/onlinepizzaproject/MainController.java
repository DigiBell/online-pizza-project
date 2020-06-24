package com.mycompany.onlinepizzaproject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.bson.Document;

import com.mycompany.onlinepizzaproject.Model.Account;
import com.mycompany.onlinepizzaproject.Model.Ingredient;
import com.mycompany.onlinepizzaproject.Model.Order;
import com.mycompany.onlinepizzaproject.Model.OrderLine;
import com.mycompany.onlinepizzaproject.Model.Product;
import com.mycompany.onlinepizzaproject.Model.Sale;
import com.mycompany.onlinepizzaproject.MongoDBController.MongoDBController;
import com.mycompany.onlinepizzaproject.backend.User;
import com.mycompany.onlinepizzaproject.backend.Pizza;

import javafx.collections.ObservableList;

// SINGLETON
public class MainController {
    public final static int MAIN_STAGE_HEIGHT = 700;
    public final static int MAIN_STAGE_WIDTH = 900;
    public final static String CUSTOMER_ACCESS_LEVEL = "customer";
    public final static String EMPLOYEE_ACCESS_LEVEL = "employee";
    public final static String MANAGER_ACCESS_LEVEL = "manager";
    private Random rand = new Random();
    private List<Product> productListToDatabase = new ArrayList<>();
    private User loginUser;

    //CUSTOMER VARIABLES
    private Order order;
    private Product productSelected;
    private OrderLine orderLine;
    private String cartLine;
    private Double totalPrice = 0.0;
    private List<OrderLine> orderLineList = new ArrayList<>();
    private List<Product> productListCustomer = new ArrayList<>();
    private List<Product> productsExtraCMC = new ArrayList<>();
    private List<Product> productsExtraOther = new ArrayList<>();
    private List<Sale> saleListToAdd = new ArrayList<>();
    private ObservableList<String> cartLineList; //lines in the Cart listView

    //MANAGER VARIABLES
    private List<Order> orderList = new ArrayList<>(); //orders from database
    private List<Sale> saleList = new ArrayList<>(); //sales from database
    private List<Ingredient> ingredientList = new ArrayList<>(); //ingredients from database
    private Ingredient ingredientToChange;
    private List<Product> productList = new ArrayList<>(); //products from database, both pizza and non-pizza
    private List<Document> topSales = new ArrayList<>();
    private ObservableList<String> topSalesLineList;
    private com.mycompany.onlinepizzaproject.backend.Product productToEdit; 
    private com.mycompany.onlinepizzaproject.backend.Pizza pizzaToEdit;


    private final static MainController MAIN_CONTROLLER_INSTANCE = new MainController();
    private MongoDBController mongoDBController = MongoDBController.getMongoDBControllerInstance();


    public static MainController getMainControllerInstance() {
        return MAIN_CONTROLLER_INSTANCE;
    }

    ////////////////////////////////////////////----GENERATE METHODS----///////////////////////////////////

    public Account generateManager(){
        Account account = new Account((rand.nextInt(2000)+1), "marta@gmail.com", "marta",  MANAGER_ACCESS_LEVEL, new Date(),
                "Marta", "Smith", "Sweden", "Helsingborg",
                "Tranemansgatan 23", "234156", "0703245658");
        return account;
    }

    public List<Product> generateProductCollection()throws Exception{
        System.out.println("generateProductCollection");

        readPizza();
        readIceCream();
        readBevarage();
        readSauce();
        readSideDish();
        System.out.println("generateProductCollection 1");

        Product product;
        product = new Product(601, "Extras", "Extra Ost/kött/kyckling", "Extra Cheese/meat/chicken", "20","cm", 10, true, null);
        productListToDatabase.add(product);
        product = new Product(602, "Extras", "Extra Ost/kött/kyckling", "Extra Cheese/meat/chicken", "30","cm", 15, true, null);
        productListToDatabase.add(product);
        product = new Product(603, "Extras", "Extra Ost/kött/kyckling", "Extra Cheese/meat/chicken", "40","cm", 20, true, null);
        productListToDatabase.add(product);
        product = new Product(604, "Extras", "Extra Övrigt", "Other", "20","cm", 5, true, null);
        productListToDatabase.add(product);
        product = new Product(605, "Extras", "Extra Övrigt", "Other", "30","cm", 10, true, null);
        productListToDatabase.add(product);
        product = new Product(606, "Extras", "Extra Övrigt", "Other", "40","cm", 15, true, null);
        productListToDatabase.add(product);
        product = new Product(701, "Salad", "PIZZASALLAD", null, "250", "g", 15, true, 100);
        productListToDatabase.add(product);

        System.out.println("generateProductCollection 2");

        for (Product p: productListToDatabase) {
            System.out.println(p.toString());
            System.out.println(" ");
        }

        return productListToDatabase;
    }

    private void readPizza()throws Exception{
        File file = new File("C:\\Users\\akira\\Documents\\Intellij Projects\\OnlinePizza_JavaFX2\\src\\main\\text\\pizza.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        List<String> lines = new ArrayList<>();
        while ((st = br.readLine()) != null){
            lines.add(st);
        }
        br.close();
        int size = lines.size();
        Product product;
        int counter = 0;

        for (int i = 0; i < size; i=i+7) {
            counter++;
            product = new Product();
            product.setProductId(100 + counter);
            product.setName(lines.get(i));
            product.setCategory("Pizza");
            product.setDescription("" + lines.get(i+1) + " \n" + lines.get(i+2));
            product.setSize("30");
            product.setUnits("cm");
            product.setPrice(Integer.valueOf(lines.get(i+4)));
            product.setAvailable(true);
            product.setQuantity(null);
            productListToDatabase.add(product);
        }
    }

    private void readIceCream()throws Exception{
        File file = new File("C:\\Users\\akira\\Documents\\Intellij Projects\\OnlinePizza_JavaFX2\\src\\main\\text\\icecream.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        List<String> lines = new ArrayList<>();
        while ((st = br.readLine()) != null){
            lines.add(st);
        }
        br.close();
        int size = lines.size();
        Product product;
        int counter = 0;

        for (int i = 0; i < size; i=i+2) {
            counter++;
            product = new Product();
            product.setProductId(200 + counter);
            product.setName(lines.get(i));
            product.setCategory("Ice-cream");
            product.setDescription("Ben and Jerry's");
            product.setSize(null);
            product.setUnits(null);
            product.setPrice(Integer.valueOf(lines.get(i + 1)));
            product.setAvailable(true);
            product.setQuantity(100);
            productListToDatabase.add(product);
        }
    }

    private void readBevarage()throws Exception{
        File file = new File("C:\\Users\\akira\\Documents\\Intellij Projects\\OnlinePizza_JavaFX2\\src\\main\\text\\beverages.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        List<String> lines = new ArrayList<>();
        while ((st = br.readLine()) != null){
            lines.add(st);
        }
        br.close();
        int size = lines.size();
        Product product;
        int counter = 0;

        for (int i = 0; i < size; i=i+4) {
            counter++;
            product = new Product();
            product.setProductId(300 + counter);
            product.setName(lines.get(i));
            product.setCategory("Beverages");
            product.setDescription(null);
            product.setSize(lines.get(i + 1));
            product.setUnits(lines.get(i + 2));
            product.setPrice(Integer.valueOf(lines.get(i + 3)));
            product.setAvailable(true);
            product.setQuantity(100);
            productListToDatabase.add(product);
        }
    }

    private void readSauce()throws Exception{
        File file = new File("C:\\Users\\akira\\Documents\\Intellij Projects\\OnlinePizza_JavaFX2\\src\\main\\text\\sauce.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        List<String> lines = new ArrayList<>();
        while ((st = br.readLine()) != null){
            lines.add(st);
        }
        br.close();
        int size = lines.size();
        Product product;
        int counter = 0;

        for (int i = 0; i < size; i=i+2) {
            counter++;
            product = new Product();
            product.setProductId(400 + counter);
            product.setName(lines.get(i));
            product.setCategory("Sauce");
            product.setDescription(null);
            product.setSize(null);
            product.setUnits(null);
            product.setPrice(Integer.valueOf(lines.get(i + 1)));
            product.setAvailable(true);
            product.setQuantity(100);
            productListToDatabase.add(product);
        }
    }

    private void readSideDish()throws Exception{
        File file = new File("C:\\Users\\akira\\Documents\\Intellij Projects\\OnlinePizza_JavaFX2\\src\\main\\text\\side_dish.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        List<String> lines = new ArrayList<>();
        while ((st = br.readLine()) != null){
            lines.add(st);
        }
        br.close();
        int size = lines.size();
        Product product;
        int counter = 0;

        for (int i = 0; i < size; i=i+2) {
            counter++;
            product = new Product();
            product.setProductId(500 + counter);
            product.setName(lines.get(i));
            product.setCategory("Side Dish");
            product.setDescription(null);
            product.setSize(null);
            product.setUnits(null);
            product.setPrice(Integer.valueOf(lines.get(i + 1)));
            product.setAvailable(true);
            product.setQuantity(100);
            productListToDatabase.add(product);
        }
    }

    public List<Ingredient> generateIngredientCollection(){
        List<Ingredient> ingredientList = new ArrayList<>();

        Ingredient ingredient = new Ingredient(131, "Red paprika", "package 5 kg", 10, "kg");
        ingredientList.add(ingredient);
        ingredient = new Ingredient(132, "Chiken breast", "frozen, package 1 kg", 10, "kg");
        ingredientList.add(ingredient);
        ingredient = new Ingredient(133, "Ananas", "package 1 kg", 10, "kg");
        ingredientList.add(ingredient);
        ingredient = new Ingredient(134, "Tomato sauce", "package 1 liter", 10, "L");
        ingredientList.add(ingredient);
        ingredient = new Ingredient(135, "Cheese Mozarella", "package 1 kg", 10, "kg");
        ingredientList.add(ingredient);
        ingredient = new Ingredient(136, "Jalapeños", "package 1 kg", 10, "kg");
        ingredientList.add(ingredient);
        ingredient = new Ingredient(137, "Dough", "package 1 kg", 10, "kg");
        ingredientList.add(ingredient);
        ingredient = new Ingredient(138, "Dough(Glutenfree)", "package 5 kg", 10, "kg");
        ingredientList.add(ingredient);
        ingredient = new Ingredient(139, "Nacho chips", "package 1 kg", 10, "kg");
        ingredientList.add(ingredient);
        ingredient = new Ingredient(140, "Taco sauce", "package 1 kg", 10, "kg");
        ingredientList.add(ingredient);
        ingredient = new Ingredient(141, "Beef", "package 1 kg", 10, "kg");
        ingredientList.add(ingredient);
        ingredient = new Ingredient(142, "Garlic", "package 1 kg", 10, "kg");
        ingredientList.add(ingredient);
        ingredient = new Ingredient(143, "Sweet corn", "package 1 kg", 10, "kg");
        ingredientList.add(ingredient);
        ingredient = new Ingredient(144, "Chili peppar", "package 1 kg", 10, "kg");
        ingredientList.add(ingredient);
        ingredient = new Ingredient(145, "Minced meat", "package 1 kg", 10, "kg");
        ingredientList.add(ingredient);
        ingredient = new Ingredient(146, "Onion", "package 1 kg", 10, "kg");
        ingredientList.add(ingredient);
        ingredient = new Ingredient(147, "Mashroom", "package 1 kg", 10, "kg");
        ingredientList.add(ingredient);
        ingredient = new Ingredient(148, "Bacon", "package 1 kg", 10, "kg");
        ingredientList.add(ingredient);
        ingredient = new Ingredient(149, "Ham", "package 1 kg", 10, "kg");
        ingredientList.add(ingredient);

        return ingredientList;
    }

    //////////////////////////////////////////---DATABASE METHODS---///////////////////////////////////

    public boolean sendAccountToDatabase(Account account){
        account.setUserId((rand.nextInt(4999)+1));
        boolean result = mongoDBController.sendAccount(account);
        return result;
    }

    public boolean sendOrderToDatabase(Order order){
        order.setOrderId((rand.nextInt(5000)+5000));
        boolean resultOrder = mongoDBController.sendOrder(order);
        boolean resultSales = sendSalesToDatabase(order);
        mongoDBController.updateProduct(getSaleListToAdd());
        if(resultOrder && resultSales){
            return true;
        }else{
            return false;
        }
    }

    public boolean sendSalesToDatabase(Order order){
        //Create Sale document and insert into Sales
        List<OrderLine> orderLineList = getOrderLineList();
        System.out.println("\norder list\n" + orderLineList.toString());
        int numberOfProducts = orderLineList.size();
        List<Sale> saleList = new ArrayList<>();
        for (int i = 0; i < numberOfProducts; i++) {
            Sale sale = new Sale();
            sale.setOrderId(order.getOrderId());
            sale.setDate(order.getDate());
            sale.setProductId(orderLineList.get(i).getProductId());
            sale.setProductName(orderLineList.get(i).getProductName());
            sale.setProductCategory(orderLineList.get(i).getProductCategory());
            sale.setQuantity(1);
            sale.setPrice(orderLineList.get(i).getTotalPrice());
            saleList.add(sale);
            System.out.println("Sale document: " + sale.toString());
        }
        setSaleListToAdd(saleList);
        //INSERT SALE DOCUMENTS MANY (1 FOR EACH PRODUCT)
        return mongoDBController.sendSales(saleList);
    }

//    public boolean findAccountByEmailPassword(String email, String password){
//        this.loginUser = mongoDBController.getAccount(email, password);
//        if(loginUser == null){
//            return false;
//        }else{
//            setLoginAccount(loginUser);
//            return true;
//        }
//    }

    public boolean findAccountByEmail(String email){
        return mongoDBController.getAccount(email);
    }

    public void getProductsFromDatabase(){
        this.productList = mongoDBController.getProducts();
        Product productCopy;
        List<Product> newProducts = new ArrayList<>();

        for (Product product: productList) {
            productCopy = product;
            if(product.getName().equals("Extra Ost/kött/kyckling")){
                productsExtraCMC.add(productCopy);
            }
            if(product.getName().equals("Extra Övrigt")){
                productsExtraOther.add(productCopy);
            }
            if(!product.getCategory().equals("Extras")){
                newProducts.add(productCopy);
            }
        }
        this.productListCustomer = newProducts;
    }
    
    public void addProductToDatabase(Product product) {
    	//add product to database
    }
    
    public void deleteProductFromDatabase(Product product) {
    	//delete product from database
    }
    public void editProductInDatabase(Product product) {
    	//edit product in database
    }

    public void getIngredientsFromDatabase(){
        this.ingredientList = mongoDBController.getIngredients();
    }

    public boolean updateIngredient( int quantity){
       return mongoDBController.updateIngredinet(getIngredientToChange(), quantity);
    }

    public void getOrdersFromDatabase(){
        this.orderList = mongoDBController.getOrders();
    }

    public void getOrdersFromDatabase(Date from, Date to){
        this.orderList = mongoDBController.getOrders(from, to);
    }

    public void getSalesFromDatabase(){ this.saleList = mongoDBController.getSales(); }

    public void getSalesFromDatabase(Date from, Date to){ this.saleList = mongoDBController.getSales(from, to); }

    public void getTopSalesFromDatabase(Date dateFrom, Date dateTo){ this.topSales = mongoDBController.getTopSales(dateFrom, dateTo); }

    //////////////////////////////////////////-----HELP METHODS-----///////////////////////////////////

    public void clearCart(){
        setOrder(new Order());
        setProductSelected(new Product());
        setOrderLine(new OrderLine());
        setCartLine("");
        setCartLineList(null);
        getOrderLineList().clear();
        setTotalPrice(0.0);
    }

    public void clearDataCustomer(){
        getProductListCustomer().clear();
        getProductsExtraCMC().clear();
        getProductsExtraOther().clear();
        getSaleListToAdd().clear();
        clearCart();
    }

    public void clearDataManager(){
        setLoginAccount(null);
        getOrderList().clear();
        getSaleList().clear();
        getIngredientList().clear();
        getProductList().clear();
    }

	public Random getRand() {
		return rand;
	}

	public void setRand(Random rand) {
		this.rand = rand;
	}

	public List<Product> getProductListToDatabase() {
		return productListToDatabase;
	}

	public void setProductListToDatabase(List<Product> productListToDatabase) {
		this.productListToDatabase = productListToDatabase;
	}

	public User getLoginAccount() {
		return loginUser;
	}

	public void setLoginAccount(User loginUser) {
		this.loginUser = loginUser;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Product getProductSelected() {
		return productSelected;
	}

	public void setProductSelected(Product productSelected) {
		this.productSelected = productSelected;
	}

	public OrderLine getOrderLine() {
		return orderLine;
	}

	public void setOrderLine(OrderLine orderLine) {
		this.orderLine = orderLine;
	}

	public String getCartLine() {
		return cartLine;
	}

	public void setCartLine(String cartLine) {
		this.cartLine = cartLine;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public List<OrderLine> getOrderLineList() {
		return orderLineList;
	}

	public void setOrderLineList(List<OrderLine> orderLineList) {
		this.orderLineList = orderLineList;
	}

	public List<Product> getProductListCustomer() {
		return productListCustomer;
	}

	public void setProductListCustomer(List<Product> productListCustomer) {
		this.productListCustomer = productListCustomer;
	}

	public List<Product> getProductsExtraCMC() {
		return productsExtraCMC;
	}

	public void setProductsExtraCMC(List<Product> productsExtraCMC) {
		this.productsExtraCMC = productsExtraCMC;
	}

	public List<Product> getProductsExtraOther() {
		return productsExtraOther;
	}

	public void setProductsExtraOther(List<Product> productsExtraOther) {
		this.productsExtraOther = productsExtraOther;
	}

	public List<Sale> getSaleListToAdd() {
		return saleListToAdd;
	}

	public void setSaleListToAdd(List<Sale> saleListToAdd) {
		this.saleListToAdd = saleListToAdd;
	}

	public ObservableList<String> getCartLineList() {
		return cartLineList;
	}

	public void setCartLineList(ObservableList<String> cartLineList) {
		this.cartLineList = cartLineList;
	}

	public List<Order> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<Order> orderList) {
		this.orderList = orderList;
	}

	public List<Sale> getSaleList() {
		return saleList;
	}

	public void setSaleList(List<Sale> saleList) {
		this.saleList = saleList;
	}

	public List<Ingredient> getIngredientList() {
		return ingredientList;
	}

	public void setIngredientList(List<Ingredient> ingredientList) {
		this.ingredientList = ingredientList;
	}

	public Ingredient getIngredientToChange() {
		return ingredientToChange;
	}

	public void setIngredientToChange(Ingredient ingredientToChange) {
		this.ingredientToChange = ingredientToChange;
	}

	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

	public List<Document> getTopSales() {
		return topSales;
	}

	public void setTopSales(List<Document> topSales) {
		this.topSales = topSales;
	}

	public ObservableList<String> getTopSalesLineList() {
		return topSalesLineList;
	}

	public void setTopSalesLineList(ObservableList<String> topSalesLineList) {
		this.topSalesLineList = topSalesLineList;
	}


	public com.mycompany.onlinepizzaproject.backend.Product getProductToEdit() {
		return productToEdit;
	}
	

	public void setProductToEdit(com.mycompany.onlinepizzaproject.backend.Product productToEdit) {
		this.productToEdit = productToEdit;
	}

	public MongoDBController getMongoDBController() {
		return mongoDBController;
	}

	public void setMongoDBController(MongoDBController mongoDBController) {
		this.mongoDBController = mongoDBController;
	}

	public Pizza getPizzaToEdit() {
		return pizzaToEdit;
	}

	public void setPizzaToEdit(Pizza pizzaToEdit) {
		this.pizzaToEdit = pizzaToEdit;
	}

    //////////////////////////////////////////----GETTERS & SETTERS----//////////////////////////////////

    
}
