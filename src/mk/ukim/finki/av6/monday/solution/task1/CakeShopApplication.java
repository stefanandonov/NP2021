package mk.ukim.finki.av6.monday.solution.task1;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CakeShopApplication {
    private List<Order> orders;

    public CakeShopApplication() {
        orders = new ArrayList<>();
    }

    public int readCakeOrders(InputStream in) {
        orders = new BufferedReader(new InputStreamReader(in))
                .lines()
                .map(Order::createOrder)
                .collect(Collectors.toList());

        return orders.stream()
                .mapToInt(order -> order.getItems().size())
                .sum();
    }

    public void printLongestOrder(OutputStream out) {
        PrintWriter printWriter = new PrintWriter(out);
        Order longestOrder = orders.stream()
                .max(Comparator.naturalOrder())
                .orElseGet(Order::new);
        printWriter.write(longestOrder.toString());
        printWriter.flush();
    }

    public void printAllOrders(PrintStream out) {
    }
}
