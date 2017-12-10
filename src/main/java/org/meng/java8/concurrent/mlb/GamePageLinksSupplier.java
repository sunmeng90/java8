package org.meng.java8.concurrent.mlb;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GamePageLinksSupplier implements Supplier<List<String>> {
    private static final String BASE = "http://gd2.mlb.com/components/game/mlb/";

    private LocalDate startDate;

    private int days;

    public GamePageLinksSupplier(LocalDate startDate, int days) {
        this.startDate = startDate;
        this.days = days;
    }

    public List<String> getGameLinks(LocalDate localDate) {
        String formattedDate = String.format("year_%4s/month_%02d/day_%02d", localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth());
        try {
            Document doc = Jsoup.connect(BASE + formattedDate).get();
            Elements el = doc.select("a");
            return el.stream().filter(link -> link.attr("href").contains("gid"))
                    .map(link -> link.attr("href"))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<String> get() {
        return Stream.iterate(startDate, d -> d.plusDays(1))
                .limit(days)
                .map(d -> getGameLinks(d))
                .flatMap(list -> list.isEmpty() ? Stream.empty() : list.stream())
                .collect(Collectors.toList());
    }
}
