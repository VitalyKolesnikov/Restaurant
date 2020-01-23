package root.ad;

import java.util.ArrayList;
import java.util.List;

public class AdvertisementStorage {
    private static AdvertisementStorage ourInstance = new AdvertisementStorage();

    public static AdvertisementStorage getInstance() {
        return ourInstance;
    }

    private AdvertisementStorage() {
        Object someContent = new Object();
        add(new Advertisement(someContent, "First Video", 2000, 10, 5 * 60));
        add(new Advertisement(someContent, "Second Video", 500, 10, 15 * 60));
        add(new Advertisement(someContent, "Third Video", 100, 10, 10 * 60));
        add(new Advertisement(someContent, "Четвертое видео", 1500, 10, 30 * 60));
        add(new Advertisement(someContent, "Пятое видео", 4000, 10, 2 * 60));
        add(new Advertisement(someContent, "Sixth Video", 2500, 10, 20 * 60));
        add(new Advertisement(someContent, "Седьмое видео", 3000, 10, 36 * 60));
        add(new Advertisement(someContent, "Восьмое видео", 500, 10, 3 * 60));
        add(new Advertisement(someContent, "Ninth Video", 4500, 10, 18 * 60));
    }

    private final List<Advertisement> videos = new ArrayList<>();

    public List<Advertisement> list() {
        return videos;
    }

    public void add(Advertisement advertisement) {
        videos.add(advertisement);
    }
}