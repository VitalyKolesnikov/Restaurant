package root.ad;

public class Advertisement implements Comparable {
    private Object content; // видео
    private String name; // имя/название
    private long initialAmount; // начальная сумма, стоимость рекламы в копейках
    private int hits; // количество оплаченных показов
    private int duration; // продолжительность в секундах
    private long amountPerOneDisplaying; // стоимость одного показа рекламного объявления в копейках

    public Advertisement(Object content, String name, long initialAmount, int hits, int duration) {
        this.content = content;
        this.name = name;
        this.initialAmount = initialAmount;
        this.hits = hits;
        this.duration = duration;
        amountPerOneDisplaying = hits > 0 ? initialAmount / hits : 0;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public long getAmountPerOneDisplaying() {
        return amountPerOneDisplaying;
    }

    public int getHits() {
        return hits;
    }

    public void revalidate() {
        if (hits < 1) {
            throw new UnsupportedOperationException();
        } else {
            hits--;
        }
    }

    public long getAmountPerSecond() {
        return getAmountPerOneDisplaying()*1000/getDuration();
    }

    @Override
    public String toString() {
        return getName() + " is displaying... " +
                getAmountPerOneDisplaying() + ", " +
                getAmountPerSecond();
    }

    @Override
    public int compareTo(Object o) {
        return this.name.compareToIgnoreCase(((Advertisement)o).getName());
    }
}