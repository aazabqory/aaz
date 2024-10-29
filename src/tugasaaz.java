import java.util.stream.Collectors;
import java.util.*;

class Product {
    private String name;
    private double price;
    private String category;
    private boolean isOfficialStore;
    private double rating;

    public Product(String name, double price, String category, boolean isOfficialStore, double rating) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.isOfficialStore = isOfficialStore;
        this.rating = rating;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getCategory() { return category; }
    public boolean isOfficialStore() { return isOfficialStore; }
    public double getRating() { return rating; }
}

class ShoppingCart {
    private Map<Product, Integer> items = new HashMap<>();

    public void addItem(Product product, int quantity) {
        items.put(product, items.getOrDefault(product, 0) + quantity);
        System.out.println(quantity + "x " + product.getName() + " ditambahkan ke keranjang");
    }

    public void updateQuantity(Product product, int newQuantity) {
        if (items.containsKey(product)) {
            items.put(product, newQuantity);
            System.out.println("Jumlah " + product.getName() + " diupdate menjadi " + newQuantity);
        }
    }

    public double getTotal() {
        return items.entrySet().stream()
                .mapToDouble(e -> e.getKey().getPrice() * e.getValue())
                .sum();
    }

    public void displayCart() {
        System.out.println("\n=== Shopping Cart ===");
        items.forEach((product, quantity) ->
                System.out.println(product.getName() + " x" + quantity + " = Rp" + (product.getPrice() * quantity))
        );
        System.out.println("Total: Rp" + getTotal());
    }
}

class Wishlist {
    private Set<Product> wishlistItems = new HashSet<>();

    public void addToWishlist(Product product) {
        if (wishlistItems.add(product)) {
            System.out.println(product.getName() + " ditambahkan ke wishlist");
        } else {
            System.out.println("Produk sudah ada di wishlist");
        }
    }

    public void removeFromWishlist(Product product) {
        if (wishlistItems.remove(product)) {
            System.out.println(product.getName() + " dihapus dari wishlist");
        }
    }

    public void displayWishlist() {
        System.out.println("\n=== Wishlist ===");
        wishlistItems.forEach(product ->
                System.out.println(product.getName() + " - Rp" + product.getPrice())
        );
    }
}

class ProductCatalog {
    private List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        products.add(product);
    }

    public List<Product> searchProducts(String keyword, boolean officialStoreOnly) {
        return products.stream()
                .filter(p -> p.getName().toLowerCase().contains(keyword.toLowerCase()))
                .filter(p -> !officialStoreOnly || p.isOfficialStore())
                .sorted((p1, p2) -> Double.compare(p2.getRating(), p1.getRating()))
                .collect(Collectors.toList());
    }

    public void displaySearchResults(List<Product> results) {
        System.out.println("\n=== Search Results ===");
        results.forEach(p -> System.out.println(
                p.getName() + " - Rp" + p.getPrice() +
                        " - Rating: " + p.getRating() +
                        (p.isOfficialStore() ? " (Official Store)" : "")
        ));
    }
}

public class tugasaaz {
    public static void main(String[] args) {
        ProductCatalog catalog = new ProductCatalog();
        ShoppingCart cart = new ShoppingCart();
        Wishlist wishlist = new Wishlist();

        Product phone = new Product("Samsung Galaxy S24", 15000000, "Electronics", true, 4.8);
        Product case1 = new Product("Phone Case", 50000, "Accessories", false, 4.5);
        Product tv = new Product("Smart TV", 5000000, "Electronics", true, 4.7);

        catalog.addProduct(phone);
        catalog.addProduct(case1);
        catalog.addProduct(tv);

        System.out.println("Testing Search Functionality:");
        List<Product> searchResults = catalog.searchProducts("Samsung", true);
        catalog.displaySearchResults(searchResults);

        System.out.println("\nTesting Shopping Cart:");
        cart.addItem(phone, 1);
        cart.addItem(case1, 2);
        cart.displayCart();
        cart.updateQuantity(case1, 3);
        cart.displayCart();

        System.out.println("\nTesting Wishlist:");
        wishlist.addToWishlist(tv);
        wishlist.addToWishlist(phone);
        wishlist.displayWishlist();
        wishlist.removeFromWishlist(tv);
        wishlist.displayWishlist();
    }
}