package root;

import root.kitchen.Dish;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ConsoleHelper {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static String readString() throws IOException {
        return br.readLine();
    }

    public static List<Dish> getAllDishesForOrder() throws IOException {
        ConsoleHelper.writeMessage(Dish.allDishesToString());
        List<Dish> order = new ArrayList<>();
        String userInput;
        while (true) {
            ConsoleHelper.writeMessage("Ваш выбор:");
            userInput = readString();
            if (userInput.equals("exit")) break;
            try {
                order.add(Dish.valueOf(userInput));
            } catch (IllegalArgumentException ex) {
                ConsoleHelper.writeMessage("Такого блюда нет в меню");
            }
        }
        return order;
    }
}