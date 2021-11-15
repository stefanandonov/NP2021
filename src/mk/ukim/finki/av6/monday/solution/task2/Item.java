package mk.ukim.finki.av6.monday.solution.task2;

public abstract class Item {
    private String name;
    private int price;

    public Item(String name) {
        this.name = name;
        this.price = 0;
    }

    public Item(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    abstract Type getType();
}
