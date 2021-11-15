package mk.ukim.finki.av6.monday.solution.task1;

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
    public static Order createOrder(String line) {
        String[] parts = line.split("\\s+");
        int orderId = Integer.parseInt(parts[0]);
        List<Item> items = new ArrayList<>();

        Arrays.stream(parts)
                .skip(1)
                .forEach(part -> {
                    if (Character.isAlphabetic(part.charAt(0)))
                        items.add(new Item(part));
                    else
                        items.get(items.size() - 1).setPrice(Integer.parseInt(part));
                });
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

    @Override
    public String toString() {
        return id + " " + items.size();
    }

    @Override
    public int compareTo(Order other) {
        return Integer.compare(this.items.size(), other.items.size());
    }
}
