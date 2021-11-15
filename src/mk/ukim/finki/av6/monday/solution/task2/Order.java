package mk.ukim.finki.av6.monday.solution.task2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Order implements Comparable<Order> {
    private int id;
    private List<Item> items;

    public Order() {
        id = -1;
        items = new ArrayList<>();
    }

    public Order(int id, List<Item> items) {
        this.id = id;
        this.items = items;
    }

    /**
     * orderId item1Name item1Price item2Name item2Price
     */
    public static Order createOrder(String line, int minItemsPerOrder) throws InvalidOrderException {
        String[] parts = line.split("\\s+");
        int orderId = Integer.parseInt(parts[0]);
        List<Item> items = new ArrayList<>();

        Arrays.stream(parts)
                .skip(1)
                .forEach(part -> {
                    if (Character.isAlphabetic(part.charAt(0))) {
                        if (part.charAt(0) == 'C')
                            items.add(new Cake(part));
                        else
                            items.add(new Pie(part));
                    } else
                        items.get(items.size() - 1).setPrice(Integer.parseInt(part));
                });
        if (items.size() < minItemsPerOrder)
            throw new InvalidOrderException(orderId);
        return new Order(orderId, items);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Item> getItems() {
        return items;
    }

    public int totalItemsSum() {
        return items.stream().mapToInt(Item::getPrice).sum();
    }

    public int totalPies() {
        return (int) items.stream().filter(i -> i.getType().equals(Type.PIE)).count();
    }

    public int totalCakes() {
        return (int) items.stream().filter(i -> i.getType().equals(Type.CAKE)).count();
    }

    /**
     * orderId totalOrderItems totalPies totalCakes totalAmount
     */
    @Override
    public String toString() {
        return id + " " + items.size() + " " + totalPies() + " " + totalCakes() + " " + totalItemsSum();
    }

    @Override
    public int compareTo(Order other) {
        return Integer.compare(this.totalItemsSum(), other.totalItemsSum());
    }
}
