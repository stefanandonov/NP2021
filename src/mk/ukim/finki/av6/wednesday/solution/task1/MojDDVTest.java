package mk.ukim.finki.av6.wednesday.solution.task1;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

enum TaxType {
    A, B, V
}

class AmountNotAllowedException extends Exception {
    public AmountNotAllowedException(int amount) {
        super(String.format("Receipt with amount %d is not allowed to be scanned", amount));
    }
}

class Item {
    private int price;
    private TaxType tax;

    public Item(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setTax(TaxType tax) {
        this.tax = tax;
    }

    /**
     * No need for Strategy pattern since the calculation is very simple.
     */
    public double getCalculatedTax() {
        if (tax.equals(TaxType.A)) return 0.18 * price;
        else if (tax.equals(TaxType.B)) return 0.05 * price;
        else return 0;
    }
}

class Receipt implements Comparable<Receipt> {
    private long id;
    private List<Item> items;

    public Receipt(long id, List<Item> items) {
        this.id = id;
        this.items = items;
    }

    /**
     * We take the parts of the line, skip 1 for the id and then see if the part begins with a digit,
     * if so it means it is the price, we parse it and put a new Item in the list. If it is not the price, it is
     * the tax type, so we get the lastly added list element and to it, we set the tax type.
     *
     * @param line - one of the lines that we get from the BufferedReader, i.e. one line from the input
     * @return - new Receipt
     * @throws AmountNotAllowedException
     */
    public static Receipt create(String line) throws AmountNotAllowedException {
        String[] parts = line.split("\\s+");
        long id = Long.parseLong(parts[0]);
        List<Item> items = new ArrayList<>();

        Arrays.stream(parts)
                .skip(1)
                .forEach(i -> {
                    if (Character.isDigit(i.charAt(0))) {
                        items.add(new Item(Integer.parseInt(i)));
                    } else {
                        items.get(items.size() - 1).setTax(TaxType.valueOf(i));
                    }
                });

        if (totalAmount(items) > 30000)
            throw new AmountNotAllowedException(totalAmount(items));
        return new Receipt(id, items);
    }

    /**
     * We have two totalAmount methods - one is static, one is not.
     */
    public static int totalAmount(List<Item> items) {
        return items.stream().mapToInt(Item::getPrice).sum();
    }

    public int totalAmount() {
        return items.stream().mapToInt(Item::getPrice).sum();
    }

    public double taxReturns() {
        return items.stream().mapToDouble(Item::getCalculatedTax).sum();
    }

    public long getId() {
        return id;
    }

    /**
     * We can use Comparator to implement the compareTo() method.
     * Comparator.comparing().thenComparing().... can be chained as many times we need, and
     * must end with .compare(this, other) since we implement a compareTo() method and
     * need to return an int value.
     */
    @Override
    public int compareTo(Receipt other) {
        return Comparator.comparing(Receipt::taxReturns)
                .thenComparing(Receipt::totalAmount)
                .compare(this, other);
    }

    @Override
    public String toString() {
        return String.format("%10d\t%10d\t%5.5f", id, totalAmount(), taxReturns());
    }
}

class MojDDV {

    private List<Receipt> receipts;

    public MojDDV() {
        this.receipts = new ArrayList<>();
    }

    /**
     * Takes all lines from the input,
     * maps each line to a Receipt (or throws exception and maps to null),
     * collects to a List. Then, removes all null values from the List using .filter(Objects::nonNull)
     */
    public void readRecords(InputStream in) {
        receipts = new BufferedReader(new InputStreamReader(in))
                .lines()
                .map(line -> {
                    try {
                        return Receipt.create(line);
                    } catch (AmountNotAllowedException e) {
                        System.out.println(e.getMessage());
                        return null;
                    }
                }).collect(Collectors.toList());
        receipts = receipts.stream().filter(Objects::nonNull).collect(Collectors.toList());
    }

    /**
     * Do not forget FLUSH of the PrintWriter!
     */
    public void printSorted(PrintStream out) {
        PrintWriter printWriter = new PrintWriter(out);
        receipts.stream().sorted().forEach(i -> printWriter.println(i.toString()));
        printWriter.flush();
    }

    /**
     * We can use DoubleSummaryStatistics on any DoubleStream. Therefore, we can use it
     * in any case when we can map items to a double value (ex. in this case their taxReturns).
     */
    public void printStatistics(PrintStream out) {
        PrintWriter printWriter = new PrintWriter(out);
        DoubleSummaryStatistics summaryStatistics = receipts.stream()
                .mapToDouble(Receipt::taxReturns).summaryStatistics();

        printWriter.println(String.format("min:\t%05.03f\nmax:\t%05.03f\nsum:\t%05.03f\ncount:\t%-5d\navg:\t%05.03f\n",
                summaryStatistics.getMin(),
                summaryStatistics.getMax(),
                summaryStatistics.getSum(),
                summaryStatistics.getCount(),
                summaryStatistics.getAverage()));
        printWriter.flush();
    }
}

public class MojDDVTest {

    public static void main(String[] args) {
        MojDDV mojDDV = new MojDDV();

        System.out.println("===READING RECORDS FROM INPUT STREAM===");
        mojDDV.readRecords(System.in);

        System.out.println("===PRINTING SORTED RECORDS BY TAX RETURNS TO OUTPUT STREAM ===");
        mojDDV.printSorted(System.out);

        System.out.println("===PRINTING SUMMARY STATISTICS FOR TAX RETURNS TO OUTPUT STREAM===");
        mojDDV.printStatistics(System.out);
    }
}