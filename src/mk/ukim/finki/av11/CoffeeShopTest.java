package mk.ukim.finki.av11;
//Component participant
interface Beverage {
    double getPrice();
    String getDescription();
}

//Concrete components
class Espresso implements Beverage {
    boolean doubleShot;

    public Espresso(boolean singleOrDouble) {
        this.doubleShot = singleOrDouble;
    }

    @Override
    public double getPrice() {
        return doubleShot ? 80 : 40;
    }

    @Override
    public String getDescription() {
        return doubleShot ? "Double espresso" : "Espresso";
    }
}

class BrewCoffee implements Beverage {

    @Override
    public double getPrice() {
        return 50;
    }

    @Override
    public String getDescription() {
        return "Brew coffee";
    }
}

abstract class BeverageDecorator implements Beverage {
    Beverage wrappedBeverage;

    public BeverageDecorator(Beverage wrappedBeverage) {
        this.wrappedBeverage = wrappedBeverage;
    }
}

class MilkDecorator extends BeverageDecorator {

    public MilkDecorator(Beverage wrappedBeverage) {
        super(wrappedBeverage);
    }

    @Override
    public double getPrice() {
        return wrappedBeverage.getPrice() + 20;
    }

    @Override
    public String getDescription() {
        String wrappedBeverageDesc = wrappedBeverage.getDescription();
        if (wrappedBeverageDesc.contains("with")) {
            return wrappedBeverageDesc + ", milk";
        } else {
            return wrappedBeverageDesc + " with milk";
        }
    }
}

class CreamDecorator extends BeverageDecorator {

    public CreamDecorator(Beverage wrappedBeverage) {
        super(wrappedBeverage);
    }

    @Override
    public double getPrice() {
        return wrappedBeverage.getPrice() + 15;
    }

    @Override
    public String getDescription() {
        String wrappedBeverageDesc = wrappedBeverage.getDescription();
        if (wrappedBeverageDesc.contains("with")) {
            return wrappedBeverageDesc + ", cream";
        } else {
            return wrappedBeverageDesc + " with cream";
        }
    }
}

//This is how it would be without Decorator
//class EspressoWithMilk implements Beverage {
//
//}
//
//class BrewCoffeeWithMilk implements Beverage {
//
//}
//
//class EspressoWithMilkAndCream implements Beverage {
//
//}
//
//class BrewCoffeeWithMilkAndCream {
//
//}
//
//class EspressoWithCream

public class CoffeeShopTest {

    public static void main(String[] args) {
//        Beverage coffee = new Espresso(true);
//        Beverage withMilk = new MilkDecorator(coffee);
//        Beverage finalBeverage = new CreamDecorator(withMilk);

        Beverage finalBeverage = new CreamDecorator(new MilkDecorator(new Espresso(true)));

        System.out.println(finalBeverage.getPrice());
        System.out.println(finalBeverage.getDescription());
    }
}
