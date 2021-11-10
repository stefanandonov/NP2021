package mk.ukim.finki.av5.weightcontainer_soultion;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

interface Weightable extends Comparable<Weightable> {

    double getWeight();

    @Override
    default int compareTo(Weightable other) {
        return Double.compare(getWeight(), other.getWeight());
    }
}

class WeightableDouble implements Weightable {
    double weight;

    public WeightableDouble(double weight) {
        this.weight = weight;
    }

    @Override
    public double getWeight() {
        return weight;
    }
}

class WeightableString implements Weightable {
    String word;

    public WeightableString(String word) {
        this.word = word;
    }

    @Override
    public double getWeight() {
        return word.length();
    }
}

class Container<T extends Weightable> {

    private List<T> elements;

    public Container() {
        elements = new ArrayList<>();
    }

    public void addElement(T element) {
        elements.add(element);
    }

    public List<T> lighterThan(T element) {
        return elements.stream()
                .filter(i -> i.compareTo(element) < 0)
                .collect(Collectors.toList());
    }

    public List<T> between(T a, T b) {
        return elements.stream()
                .filter(i -> i.compareTo(a) > 0 && i.compareTo(b) < 0)
                .collect(Collectors.toList());
    }

    public double containerSum() {
        return elements.stream().mapToDouble(Weightable::getWeight).sum();
    }

    /**
     * Try to change the ? with T here and see the difference!
     */
    public int compare(Container<? extends Weightable> container2) {
        return Double.compare(this.containerSum(), container2.containerSum());
    }
}

public class ContainerTester {
    public static void main(String[] args) {
        Container<WeightableDouble> container1 = new Container();
        Container<WeightableDouble> container2 = new Container();
        Container<WeightableString> container3 = new Container();
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int p = sc.nextInt();
        double a = sc.nextDouble();
        double b = sc.nextDouble();
        WeightableDouble wa = new WeightableDouble(a);
        WeightableDouble wb = new WeightableDouble(b);
        for (int i = 0; i < n; i++) {
            double weight = sc.nextDouble();
            container1.addElement(new WeightableDouble(weight));
        }
        for (int i = 0; i < m; i++) {
            double weight = sc.nextDouble();
            container2.addElement(new WeightableDouble(weight));
        }
        for (int i = 0; i < p; i++) {
            String s = sc.next();
            container3.addElement(new WeightableString(s));
        }
        List<WeightableDouble> resultSmaller = container1.lighterThan(wa);
        List<WeightableDouble> resultBetween = container1.between(wa, wb);
        System.out.println("Lighter than " + wa.getWeight() + ":");
        for (WeightableDouble wd : resultSmaller) {
            System.out.println(wd.getWeight());
        }
        System.out.println("Between " + wa.getWeight() + " and " + wb.getWeight() + ":");
        for (WeightableDouble wd : resultBetween) {
            System.out.println(wd.getWeight());
        }
        System.out.println("Comparison: ");
        System.out.println(container1.compare(container2));
        System.out.println(container1.compare(container3));
    }
}
