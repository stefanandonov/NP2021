package mk.ukim.finki.av6.monday.solution.task2;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CakeShopApplication {
    private int minItemsPerOrder;
    private List<Order> orders;

    public CakeShopApplication() {
        orders = new ArrayList<>();
    }

    public CakeShopApplication(int minOrderItems) {
        minItemsPerOrder = minOrderItems;
        orders = new ArrayList<>();
    }

    /**
     * This is the place to handle the exception, because this class should
     * decide if the order is added or not. Be careful where you put a try-catch block
     * as it will cost you points on the exam.
     */
    public void readCakeOrders(InputStream in) {
        orders = new BufferedReader(new InputStreamReader(in))
                .lines()
                .map(line -> {
                    try {
                        return Order.createOrder(line, minItemsPerOrder);
                    } catch (InvalidOrderException e) {
                        System.out.println(e.getMessage());
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public void printAllOrders(OutputStream out) {
        PrintWriter printWriter = new PrintWriter(out);
        orders.stream().sorted(Comparator.reverseOrder()).forEach(order -> printWriter.println(order.toString()));
        printWriter.flush();
    }
}
