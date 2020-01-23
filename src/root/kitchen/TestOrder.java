package root.kitchen;

import root.Tablet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class TestOrder extends Order {

    public TestOrder(Tablet tablet) throws IOException {
        super(tablet);
    }

    @Override
    protected void initDishes() {
        this.dishes = new ArrayList<>();
        int numRandom = ThreadLocalRandom.current().nextInt(1, Dish.values().length);
        if (numRandom == 0) {
            numRandom = 1;
        }
        Dish[] dishes = Dish.values();

        for (int i = 0; i < numRandom; i++) {
            int random = (int)(Math.random() * (Dish.values().length));
            this.dishes.add(dishes[random]);
        }
    }
}