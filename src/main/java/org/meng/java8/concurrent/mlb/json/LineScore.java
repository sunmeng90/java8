package org.meng.java8.concurrent.mlb.json;

import com.google.gson.annotations.SerializedName;

public class LineScore {
    @SerializedName("away_team_runs")
    private String awayTeamRuns;
    @SerializedName("home_team_runs")
    private String homeTeamRuns;

    public String getAwayTeamRuns() {
        return awayTeamRuns;
    }

    public void setAwayTeamRuns(String awayTeamRuns) {
        this.awayTeamRuns = awayTeamRuns;
    }

    public String getHomeTeamRuns() {
        return homeTeamRuns;
    }

    public void setHomeTeamRuns(String homeTeamRuns) {
        this.homeTeamRuns = homeTeamRuns;
    }
}
