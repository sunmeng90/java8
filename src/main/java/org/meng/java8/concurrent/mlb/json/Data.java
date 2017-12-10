package org.meng.java8.concurrent.mlb.json;

import com.google.gson.annotations.SerializedName;

public class Data {
    @SerializedName("boxscore")
    private BoxScore boxScore;

    public BoxScore getBoxScore() {
        return boxScore;
    }

    public void setBoxScore(BoxScore boxScore) {
        this.boxScore = boxScore;
    }
}
