package root.ad;

import java.util.List;
import java.util.stream.Collectors;

public class StatisticAdvertisementManager {
    private static StatisticAdvertisementManager ourInstance = new StatisticAdvertisementManager();
    AdvertisementStorage storage = AdvertisementStorage.getInstance();

    public static StatisticAdvertisementManager getInstance() {
        return ourInstance;
    }

    private StatisticAdvertisementManager() {
    }

    public List<Advertisement> getActiveVideoSet() {
        return storage.list().stream()
                .filter(ad -> ad.getHits() > 0)
                .sorted()
                .collect(Collectors.toList());
    }

    public List<Advertisement> getArchivedVideoSet() {
        return storage.list().stream()
                .filter(ad -> ad.getHits() == 0)
                .sorted()
                .collect(Collectors.toList());
    }
}