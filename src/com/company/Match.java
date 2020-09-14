package com.company;

import java.util.Map;

public class Match {
    private Team firstTeam;
    private Team secondTeam;


    public Match(Team firstTeam, Team secondTeam) {
        this.firstTeam = firstTeam;
        this.secondTeam = secondTeam;
    }

    public Team getFirstTeam() {
        return firstTeam;
    }

    public Team getSecondTeam() {
        return secondTeam;
    }

    public void getPredictions(String path) {
        Data data = new Data(path);
        Predictions predictions = new Predictions(data, new Match(firstTeam, secondTeam));
        predictions.makeCalculations();
    }
}
