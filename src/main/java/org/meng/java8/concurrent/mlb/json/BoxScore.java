package org.meng.java8.concurrent.mlb.json;

import com.google.gson.annotations.SerializedName;

public class BoxScore {

    @SerializedName("away_sname")
    private String awaySname;
    @SerializedName("home_sname")
    private String homeSname;
    @SerializedName("away_fname")
    private String awayFname;
    @SerializedName("home_fname")
    private String homeFname;
    private String date;

    @SerializedName("linescore")
    private LineScore lineScore;

    public String getAwaySname() {
        return awaySname;
    }

    public void setAwaySname(String awaySname) {
        this.awaySname = awaySname;
    }

    public String getHomeSname() {
        return homeSname;
    }

    public void setHomeSname(String homeSname) {
        this.homeSname = homeSname;
    }

    public String getAwayFname() {
        return awayFname;
    }

    public void setAwayFname(String awayFname) {
        this.awayFname = awayFname;
    }

    public String getHomeFname() {
        return homeFname;
    }

    public void setHomeFname(String homeFname) {
        this.homeFname = homeFname;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public LineScore getLineScore() {
        return lineScore;
    }

    public void setLineScore(LineScore lineScore) {
        this.lineScore = lineScore;
    }
}
