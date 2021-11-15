package mk.ukim.finki.av6.monday.solution.task2;

public class Pie extends Item {
    public Pie(String name) {
        super(name);
    }

    @Override
    Type getType() {
        return Type.PIE;
    }

    @Override
    public int getPrice() {
        return super.getPrice() + 50;
    }
}
