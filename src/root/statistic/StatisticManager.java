package root.statistic;

import root.ad.Advertisement;
import root.statistic.event.CookedOrderEventDataRow;
import root.statistic.event.EventDataRow;
import root.statistic.event.EventType;
import root.statistic.event.VideoSelectedEventDataRow;

import java.text.SimpleDateFormat;
import java.util.*;

public class StatisticManager {
    private static StatisticManager ourInstance = new StatisticManager();
    private StatisticStorage statisticStorage = new StatisticStorage();

    public static StatisticManager getInstance() {
        return ourInstance;
    }

    private StatisticManager() {
        List<Advertisement> list1 = new ArrayList<>();
        List<Advertisement> list2 = new ArrayList<>();
        List<Advertisement> list3 = new ArrayList<>();
        Object someContent = new Object();

        list1.add(new Advertisement(someContent, "1st Video", 5000, 100, 3 * 60));
        list1.add(new Advertisement(someContent, "2nd Video", 200, 10, 15 * 60));
        list2.add(new Advertisement(someContent, "3rd Video", 100, 3, 10 * 60));
        list2.add(new Advertisement(someContent, "4th Video", 650, 15, 7 * 60));
        list3.add(new Advertisement(someContent, "5th Video", 880, 8, 3 * 60));
        list3.add(new Advertisement(someContent, "6th Video", 330, 7, 11 * 60));
    }

    public void register(EventDataRow data) {
        this.statisticStorage.put(data);
    }

    public Map<String, Double> calcAdvertisementProfit() {
        List<EventDataRow> videos = statisticStorage.getStorage().get(EventType.SELECTED_VIDEOS);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        Map<String, Double> resultMap = new TreeMap<>(Collections.reverseOrder());

        for (EventDataRow video : videos) {
            VideoSelectedEventDataRow videoRow = (VideoSelectedEventDataRow)video;
            String dateKey = simpleDateFormat.format(videoRow.getDate());

            if (resultMap.containsKey(dateKey)) {
                resultMap.put(dateKey, resultMap.get(dateKey) + (videoRow.getAmount() / 100.00));
            }
            else {
                resultMap.put(dateKey, videoRow.getAmount() / 100.00);
                }
        }
        return resultMap;
    }

    public Map<String, Map<String, Integer>> calcCookWorkloading() {
        Map<String, Map<String, Integer>> result = new TreeMap<>(Collections.reverseOrder());
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

        for (EventDataRow eventDataRow : statisticStorage.getStorage().get(EventType.COOKED_ORDER)) {
            CookedOrderEventDataRow cookedOrderEventDataRow = (CookedOrderEventDataRow) eventDataRow;
            String dataKey = format.format(cookedOrderEventDataRow.getDate());
            String cookName = cookedOrderEventDataRow.getCookName();
            int cookingTime = cookedOrderEventDataRow.getTime();

            if (!result.containsKey(dataKey)) {
                Map<String, Integer> innerMap = new TreeMap<>();
                innerMap.put(cookName, cookingTime);
                result.put(dataKey, innerMap);
            }
            else {
                Map<String, Integer> map = result.get(dataKey);
                if (!map.containsKey(cookName)) map.put(cookName, cookedOrderEventDataRow.getTime());
                else map.put(cookName, map.get(cookName) + cookedOrderEventDataRow.getTime());
            }
        }
        return result;
    }

    private class StatisticStorage {
        private Map<EventType, List<EventDataRow>> storage = new HashMap<>();

        public StatisticStorage() {
            for (EventType event : EventType.values()) {
                this.storage.put(event, new ArrayList<>());
            }
        }

        private void put(EventDataRow data) {
            this.storage.get(data.getType()).add(data);
        }

        public Map<EventType, List<EventDataRow>> getStorage() {
            return storage;
        }
    }
}