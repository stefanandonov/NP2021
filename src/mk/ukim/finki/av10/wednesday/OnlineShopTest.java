package mk.ukim.finki.av10.wednesday;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

enum COMPARATOR_TYPE {
    NEWEST_FIRST,
    OLDEST_FIRST,
    LOWEST_PRICE_FIRST,
    HIGHEST_PRICE_FIRST,
    MOST_SOLD_FIRST,
    LEAST_SOLD_FIRST
}

class ProductNotFoundException extends Exception {
    ProductNotFoundException(String message) {
        super(message);
    }
}


class Product {
    String id;
    String name;
    LocalDateTime createdAt;
    double price;
    int quantitySold;

    public Product(String id, String name, LocalDateTime createdAt, double price) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.price = price;
        this.quantitySold = 0;
    }

    public double buy(int quantity) {
        double amount = quantity * price;
        quantitySold += quantity;
        return amount;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantitySold() {
        return quantitySold;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", createdAt=" + createdAt +
                ", price=" + price +
                ", quantitySold=" + quantitySold +
                '}';
    }
}


class OnlineShop {

    Map<String, Product> productsById;
    Map<String, List<Product>> productsByCategory;

    OnlineShop() {
        productsById = new HashMap<>(); //za O(1) kompleksnost
        productsByCategory = new HashMap<>();
    }

    void addProduct(String category, String id, String name, LocalDateTime createdAt, double price) {
        Product p = new Product(id, name, createdAt, price);
        productsById.put(id, p);

        productsByCategory.putIfAbsent(category, new ArrayList<>());
        productsByCategory.get(category).add(p);
    }

    double buyProduct(String id, int quantity) throws ProductNotFoundException {
        Product product = productsById.get(id);
        if (product == null) {
            throw new ProductNotFoundException(String.format("Product with id %s does not exist in the online shop!", id));
        }
        return product.buy(quantity);
    }

    private Comparator<Product> createComparator(COMPARATOR_TYPE comparatorType) {
        Comparator<Product> oldest = Comparator.comparing(Product::getCreatedAt);
        Comparator<Product> lowestPrice = Comparator.comparing(Product::getPrice);
        Comparator<Product> leastSold = Comparator.comparing(Product::getQuantitySold);

        switch (comparatorType) {
            case OLDEST_FIRST:
                return oldest;
            case NEWEST_FIRST:
                return oldest.reversed();
            case LOWEST_PRICE_FIRST:
                return lowestPrice;
            case HIGHEST_PRICE_FIRST:
                return lowestPrice.reversed();
            case LEAST_SOLD_FIRST:
                return leastSold;
            default:
                return leastSold.reversed();
        }
    }

    List<List<Product>> listProducts(String category, COMPARATOR_TYPE comparatorType, int pageSize) {
        List<List<Product>> result = new ArrayList<>();

        List<Product> allProduct;
        if (category == null) {
            allProduct = new ArrayList<>(productsById.values());
        } else {
            allProduct = productsByCategory.get(category);
        }

        Comparator<Product> comparator = createComparator(comparatorType);

        allProduct.sort(comparator);

        for (int start = 0; start < allProduct.size(); start += pageSize) {
            int end = start + pageSize;
            if (end>allProduct.size()){
                end = allProduct.size();
            }
            result.add(allProduct.subList(start, end));

        }

        return result;
    }

}

public class OnlineShopTest {

    public static void main(String[] args) {
        OnlineShop onlineShop = new OnlineShop();
        double totalAmount = 0.0;
        Scanner sc = new Scanner(System.in);
        String line;
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            String[] parts = line.split("\\s+");
            if (parts[0].equalsIgnoreCase("addproduct")) {
                String category = parts[1];
                String id = parts[2];
                String name = parts[3];
                LocalDateTime createdAt = LocalDateTime.parse(parts[4]);
                double price = Double.parseDouble(parts[5]);
                onlineShop.addProduct(category, id, name, createdAt, price);
            } else if (parts[0].equalsIgnoreCase("buyproduct")) {
                String id = parts[1];
                int quantity = Integer.parseInt(parts[2]);
                try {
                    totalAmount += onlineShop.buyProduct(id, quantity);
                } catch (ProductNotFoundException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                String category = parts[1];
                if (category.equalsIgnoreCase("null"))
                    category = null;
                String comparatorString = parts[2];
                int pageSize = Integer.parseInt(parts[3]);
                COMPARATOR_TYPE comparatorType = COMPARATOR_TYPE.valueOf(comparatorString);
                printPages(onlineShop.listProducts(category, comparatorType, pageSize));
            }
        }
        System.out.println("Total revenue of the online shop is: " + totalAmount);

    }

    private static void printPages(List<List<Product>> listProducts) {
        for (int i = 0; i < listProducts.size(); i++) {
            System.out.println("PAGE " + (i + 1));
            listProducts.get(i).forEach(System.out::println);
        }
    }
}

