package root;

import root.ad.StatisticAdvertisementManager;
import root.statistic.StatisticManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

class DirectorTablet {

    void printAdvertisementProfit() {
        Map<String, Double> statMap = StatisticManager.getInstance().calcAdvertisementProfit();
        Double total = 0D;

        for (Map.Entry<String, Double> entry : statMap.entrySet()) {
            ConsoleHelper.writeMessage(String.format(Locale.ENGLISH, "%s - %.2f", entry.getKey(), entry.getValue()));
            total += entry.getValue();
        }

        if (total > 0) {
            ConsoleHelper.writeMessage(String.format(Locale.ENGLISH, "Total - %.2f", total));
        }
    }

    void printCookWorkloading() {
        Map<String, Map<String, Integer>> map = StatisticManager.getInstance().calcCookWorkloading();
        SimpleDateFormat oldFormat = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat newFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);

        for (Map.Entry<String, Map<String, Integer>> entry : map.entrySet()) {
            Date date = null;
            try {
                date = oldFormat.parse(entry.getKey());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            ConsoleHelper.writeMessage(newFormat.format(date));
            for (Map.Entry<String, Integer> innerEntry : entry.getValue().entrySet()) {
                ConsoleHelper.writeMessage(String.format("%s - %d min", innerEntry.getKey(),
                        innerEntry.getValue()/60));
            }
            ConsoleHelper.writeMessage("");
        }
    }

    void printActiveVideoSet() {
        StatisticAdvertisementManager.getInstance().getActiveVideoSet()
                .forEach(s -> ConsoleHelper.writeMessage(String.format(Locale.ENGLISH, "%s - %d", s.getName(), s.getHits())));
    }

    void printArchivedVideoSet() {
        StatisticAdvertisementManager.getInstance().getArchivedVideoSet()
                .forEach(s -> ConsoleHelper.writeMessage(s.getName()));
    }
}