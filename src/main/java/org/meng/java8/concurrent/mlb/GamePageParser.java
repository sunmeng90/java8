package org.meng.java8.concurrent.mlb;

import com.sun.xml.internal.ws.util.CompletedFuture;
import org.meng.java8.concurrent.mlb.json.BoxScore;
import org.meng.java8.concurrent.mlb.json.Result;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.concurrent.CompletableFuture;

public class GamePageParser {

    private int getTotalScore(Result result) {
        BoxScore boxScore = result.getData().getBoxScore();
        String awayRuns = boxScore.getLineScore().getAwayTeamRuns();
        String homeRuns = boxScore.getLineScore().getHomeTeamRuns();
        return Integer.parseInt(awayRuns) + Integer.parseInt(homeRuns);
    }

    private OptionalInt getMaxScore(List<Result> results) {
        return results.stream().mapToInt(this::getTotalScore).max();
    }

    public Optional<Result> getMaxGame(List<Result> results) {
        return results.stream().max(Comparator.comparing(this::getTotalScore));
    }

    public void printGames(LocalDate localDate, int days) {
        CompletableFuture<List<Result>> future = CompletableFuture.supplyAsync(new GamePageLinksSupplier(localDate, days))
                .thenApply(new BoxscoreRetriever());

        CompletableFuture<OptionalInt> futureMaxScore = future.thenApplyAsync(this::getMaxScore);
        CompletableFuture<Optional<Result>> futureMaxGame = future.thenApplyAsync(this::getMaxGame);

        CompletableFuture<String> futureMax = futureMaxScore.thenCombineAsync(futureMaxGame, (score, result) ->
                String.format("Highest score: %d, Max Game: %s", score.orElse(0), result.orElse(null))
        );

        CompletableFuture.allOf(futureMax).join();
        future.join().forEach(System.out::println);
        System.out.println(futureMax.join());

    }


    public static void main(String[] args) {
        GamePageParser gamePageParser = new GamePageParser();
        gamePageParser.printGames(LocalDate.of(2017, 10, 1), 10);
    }

}
