package org.meng.java8.concurrent.mlb;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.meng.java8.concurrent.mlb.json.Result;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BoxscoreRetriever implements Function<List<String>, List<Result>> {
    private OkHttpClient client = new OkHttpClient();
    private Gson gson = new Gson();
    private final String BASE = "http://gd2.mlb.com/components/game/mlb/";

    @Override
    public List<Result> apply(List<String> strings) {


        return strings.stream().map(this::gameResult)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    public Optional<Result> gameResult(String pattern) {

        //gid_2017_10_01_arimlb_kcamlb_1/
        String[] parts = pattern.split("_");
        String url = String.format(BASE + "year_%4s/month_%s/day_%s/%sboxscore.json", parts[1], parts[2], parts[3], pattern);
        Request request = new Request.Builder()
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                return Optional.empty();
            }
//            System.out.println(response.body().string());
            return Optional.ofNullable(gson.fromJson(response.body().charStream(), Result.class));

        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
