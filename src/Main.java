import java.util.ArrayList;
import java.util.List;

public class Main{
    public static void main(String[] args){
        CustomerOrder order = new CustomerOrder();
        LoyaltyStatus loyaltyStatus = new LoyaltyStatus();
        double totalCost;
        double costAfterDiscount;

        Food burger = new BaseFood("Burger", 5.0);
        Food hotdog = new BaseFood("Hotdog", 2.5);
        Food fries = new BaseFood("Fries", 2.0);

        Food cheeseBurger = new Cheese(burger);
        Food chiliCheeseDog = new Cheese(new Chili(hotdog));
        Food baconFries = new Bacon(fries);

        order.addItem(cheeseBurger);
        order.addItem(chiliCheeseDog);
        order.addItem(baconFries);

        totalCost = order.calculateTotalCost();
        costAfterDiscount = loyaltyStatus.applyDiscount(totalCost, "Gold");

        System.out.println("Your order includes:");
        System.out.println(cheeseBurger.getDescription());
        System.out.println(chiliCheeseDog.getDescription());
        System.out.println(baconFries.getDescription());
        System.out.println("Total is: $" + totalCost);
        System.out.printf("Since you are a gold member, your new total is: $%.2f", costAfterDiscount);
    }
}

interface Food{
    String getDescription();
    double getCost();
}

class BaseFood implements Food{
    private final String description;
    private final double basePrice;

    public BaseFood(String description, double basePrice){
        this.description = description;
        this.basePrice = basePrice;
    }

    @Override
    public String getDescription(){
        return description;
    }

    @Override
    public double getCost(){
        return basePrice;
    }
}

abstract class ToppingDecorator implements Food{
    protected Food food;

    public ToppingDecorator(Food food){
        this.food = food;
    }

    @Override
    public abstract String getDescription();

    @Override
    public abstract double getCost();
}

class Cheese extends ToppingDecorator{
    private static final double COST = 0.5;

    public Cheese(Food food){
        super(food);
    }

    @Override
    public String getDescription(){
        return "Cheese " + food.getDescription();
    }

    @Override
    public double getCost(){
        return food.getCost() + COST;
    }
}

class Chili extends ToppingDecorator{
    private static final double COST = 0.75;

    public Chili(Food food){
        super(food);
    }

    @Override
    public String getDescription(){
        return "Chili " + food.getDescription();
    }

    @Override
    public double getCost(){
        return food.getCost() + COST;
    }
}

class Bacon extends ToppingDecorator{
    private static final double COST = 1.0;

    public Bacon(Food food){
        super(food);
    }

    @Override
    public String getDescription(){
        return "Bacon " + food.getDescription();
    }

    @Override
    public double getCost(){
        return food.getCost() + COST;
    }
}

class CustomerOrder{
    private final List<Food> items;

    public CustomerOrder(){
        items = new ArrayList<>();
    }

    public void addItem(Food item){
        items.add(item);
    }

    public double calculateTotalCost(){
        double totalCost = 0;
        
        for(Food item : items){
            totalCost += item.getCost();
        }
        
        return totalCost;
    }
}

class LoyaltyStatus{
    public double applyDiscount(double totalCost, String status){
        double discount = 0;
        
        if (status.equals("Gold")) {
            discount = totalCost * 0.1;
        }
        else if (status.equals("Silver")) {
            discount = totalCost * 0.05;
        }
        else {
            discount = 0;
        }
        
        return totalCost - discount;
    }
}