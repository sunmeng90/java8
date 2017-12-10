package org.meng.java8.concurrent.mlb.json;

import javax.swing.*;

public class Result {
    private String subject;
    private String copyright;
    private Data data;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        BoxScore boxScore = getData().getBoxScore();

        return String.format("%20s: %20s %s , %20s %s", boxScore.getDate(),
                boxScore.getAwayFname(),
                boxScore.getLineScore().getAwayTeamRuns(),
                boxScore.getHomeFname(),
                boxScore.getLineScore().getHomeTeamRuns());
    }
}
