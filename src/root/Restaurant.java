package root;

import root.kitchen.Cook;
import root.kitchen.Order;
import root.kitchen.Waiter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class Restaurant {
    private static final int ORDER_CREATING_INTERVAL = 100;
    private static final LinkedBlockingQueue<Order> orderQueue = new LinkedBlockingQueue<>();

    public static void main(String[] args) throws IOException {

        Cook cook1 = new Cook("Gordon");
        cook1.setQueue(orderQueue);
        Cook cook2 = new Cook("Jamie");
        cook2.setQueue(orderQueue);

        Waiter waiter = new Waiter();

        List<Tablet> tabletList = new ArrayList<>();
        Tablet tablet;

        for (int i = 1; i <= 5; i++) {
            tablet = new Tablet(i);
            tablet.setQueue(orderQueue);
            tabletList.add(tablet);
        }

        cook1.addObserver(waiter);
        cook2.addObserver(waiter);

        new Thread(cook1).start();
        new Thread(cook2).start();

        Thread thread = new Thread(new RandomOrderGeneratorTask(tabletList, ORDER_CREATING_INTERVAL));
        thread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.interrupt();

        DirectorTablet directorTablet = new DirectorTablet();
        directorTablet.printAdvertisementProfit();
        directorTablet.printCookWorkloading();
        directorTablet.printActiveVideoSet();
        directorTablet.printArchivedVideoSet();
    }
}