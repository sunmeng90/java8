package org.meng.java8.concurrent.mlb;

import com.google.gson.Gson;
import okhttp3.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.meng.java8.concurrent.mlb.json.Result;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BoxscoreRetrieverTest {
/*
    @InjectMocks
    private BoxscoreRetriever boxscoreRetriever;

    @Mock
    private Call httpCall;*/

    @Test
    public void testGameResult() throws IOException {

        //gid_2017_10_01_arimlb_kcamlb_1/
        String game2017 = "gid_2017_10_01_arimlb_kcamlb_1/";
        String url = "http://gd2.mlb.com/components/game/mlb/year_2017/month_10/day_01/gid_2017_10_01_arimlb_kcamlb_1/boxscore.json";
        BoxscoreRetriever boxscoreRetriever = new BoxscoreRetriever();
        Optional<Result> result = boxscoreRetriever.gameResult(game2017);
        assertTrue(result.isPresent());

    }

    /*@Test
    public void testGameResult2() throws IOException {

        //gid_2017_10_01_arimlb_kcamlb_1/
        String game2017 = "gid_2017_10_01_arimlb_kcamlb_1/";
        String url = "http://gd2.mlb.com/components/game/mlb/year_2017/month_10/day_01/gid_2017_10_01_arimlb_kcamlb_1/boxscore.json";
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = new Response.Builder()
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .code(200)
                .message("OK")
                .body(ResponseBody.create(MediaType.parse("application/json; charset=utf-8"),
                        "{'subject':'boxscore', 'data':{}, 'copyright':'sample copyright'}"))
                .build();
        when(httpCall.execute()).thenReturn(response);
        Optional<Result> result = boxscoreRetriever.gameResult(game2017);
        assertTrue(result.isPresent());
        verify(httpCall).execute();
    }*/
}