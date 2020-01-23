package root.ad;

import root.ConsoleHelper;
import root.statistic.StatisticManager;
import root.statistic.event.VideoSelectedEventDataRow;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AdvertisementManager {
    private int timeSeconds;
    private final AdvertisementStorage storage = AdvertisementStorage.getInstance();

    public AdvertisementManager(int timeSeconds) {
        this.timeSeconds = timeSeconds;
    }

    public void processVideos() {
        if (storage.list().isEmpty()) {
            throw new NoVideoAvailableException();
        }

        List<Advertisement> ads = storage.list()
                .stream()
                .filter(ad -> ad.getHits() > 0)
                .collect(Collectors.toList());

        List<Advertisement> bestAds = knapSack(ads);
        if (bestAds == null) return;

        bestAds.sort(Comparator.comparingLong(Advertisement::getAmountPerOneDisplaying)
                        .thenComparingInt(Advertisement::getDuration)
                        .reversed());

        StatisticManager.getInstance().register(new VideoSelectedEventDataRow(bestAds, calcAmount(bestAds)
                , calcDuration(bestAds)));

        bestAds.forEach(ad -> {
            ConsoleHelper.writeMessage(ad.toString());
            ad.revalidate();
        });
    }

    //вычисляет общую длительность набора видеороликов
    private int calcDuration(List<Advertisement> ads) {
        return ads.stream()
                .mapToInt(Advertisement::getDuration)
                .sum();
    }

    //вычисляет общую стоимость набора видеороликов
    private long calcAmount(List<Advertisement> ads) {
        return ads.stream()
                .mapToLong(Advertisement::getAmountPerOneDisplaying)
                .sum();
    }

    private List<Advertisement> knapSack(List<Advertisement> ads) {

        int n = ads.size();
        int[] weights = new int[n];
        int[] values = new int[n];
        int maxWeight = timeSeconds;
        int i = 0;

        for (Advertisement ad : ads) {
            weights[i] = ad.getDuration();
            values[i] = (int) ad.getAmountPerOneDisplaying();
            i++;
        }

        List<Advertisement> res = new ArrayList<>();
        int w;
        int[][] table = new int[n + 1][maxWeight + 1];

        // Building table[][] in bottom up manner
        for (i = 0; i<= n; i++) {
            for (w = 0; w<= maxWeight; w++) {
                if (i == 0 || w == 0)
                    table[i][w] = 0;
                else if (weights[i - 1]<= w)
                    table[i][w] = Math.max(values[i - 1] + table[i - 1][w - weights[i - 1]], table[i - 1][w]);
                else
                    table[i][w] = table[i - 1][w];
            }
        }

        // Finding the items to include in the bag
        while (n > 0) {
            if (table[n][maxWeight] != table[n-1][maxWeight]) {
                res.add(ads.get(n-1));
                maxWeight -= weights[n-1];
            }
            n -= 1;
        }
        return res;
    }
}