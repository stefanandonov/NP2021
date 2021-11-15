package mk.ukim.finki.av6.monday.solution.task2;

public class Cake extends Item {

    public Cake(String name) {
        super(name);
    }

    @Override
    Type getType() {
        return Type.CAKE;
    }
}
