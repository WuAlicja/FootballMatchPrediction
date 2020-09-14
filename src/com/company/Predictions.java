package com.company;

import org.apache.commons.math3.distribution.PoissonDistribution;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Predictions {
    private Data data;
    private Match match;

    public Predictions(Data data, Match match) {
        this.data = data;
        this.match = match;
    }

    public void makeCalculations() {
        List<List<String>> records = data.readLastSeasonData();

        int totalNumberOfHomeGames = records.size() - 1;
        int totalNumberOfAwayGames = totalNumberOfHomeGames;

        int leagueHomeGoals = getLeagueHomeGoals(records);

        int leagueAwayGoals = getLeagueAwayGoals(records);

        String homeTeam;
        String awayTeam;

        if (match.getFirstTeam().isHome()) {
            homeTeam = match.getFirstTeam().getName();
            awayTeam = match.getSecondTeam().getName();
        } else {
            homeTeam = match.getSecondTeam().getName();
            awayTeam = match.getFirstTeam().getName();
        }

        double homeTeamHomeScoredGoals = calculateHomeScoredGoals(homeTeam, records);

        double homeTeamHomeGames = calculateHomeGamesPlayed(homeTeam, records);

        double awayTeamAwayScoredGoals = calculateAwayScoredGoals(awayTeam, records);

        double awayTeamAwayGames = calculateAwayGamesPlayed(awayTeam, records);

        double awayTeamAwayConcededGoals = calculateAwayConcededGoals(awayTeam, records);

        double homeTeamHomeConcededGoals = calculateHomeConcededGoals(homeTeam, records);

        double averageScoredGoalsByHomeTeam = (double) leagueHomeGoals / (double) totalNumberOfHomeGames;
        double averageScoredGoalsByAwayTeam = (double) leagueAwayGoals / (double) totalNumberOfAwayGames;
        double averageConcededGoalsByHomeTeam = (double) leagueAwayGoals / (double) totalNumberOfHomeGames;
        double averageConcededGoalsByAwayTeam = (double) leagueHomeGoals / (double) totalNumberOfHomeGames;

        double homeTeamAttackStrength = (homeTeamHomeScoredGoals / homeTeamHomeGames) / averageScoredGoalsByHomeTeam;
        double awayTeamDefensiveStrength = (awayTeamAwayConcededGoals / awayTeamAwayGames) / averageConcededGoalsByAwayTeam;
        double awayTeamAttackStrength = (awayTeamAwayScoredGoals / awayTeamAwayGames) / averageScoredGoalsByAwayTeam;
        double homeTeamDefensiveStrength = (homeTeamHomeConcededGoals / homeTeamHomeGames) / averageConcededGoalsByHomeTeam;

        double homeTeamExpectedGoals = homeTeamAttackStrength * awayTeamDefensiveStrength * averageScoredGoalsByHomeTeam;
        double awayTeamExpectedGoals = awayTeamAttackStrength * homeTeamDefensiveStrength * averageScoredGoalsByAwayTeam;

        System.out.println(homeTeam + " score probability:");
        printDashes();
        for (
                Map.Entry<Integer, String> entry : (calculateProbabilityOfResultForEachTeam(homeTeamExpectedGoals)).entrySet()) {
            System.out.println("Scoring " + entry.getKey() + " goals probability is " + entry.getValue());
        }
        printDashes();

        System.out.println(awayTeam + " score probability:");
        printDashes();
        for (Map.Entry<Integer, String> entry : (calculateProbabilityOfResultForEachTeam(awayTeamExpectedGoals)).entrySet()) {
            System.out.println("Scoring " + entry.getKey() + " goals probability is " + entry.getValue());
        }

        printDashes();
        printDashes();
        System.out.println("Probability of match result for " + homeTeam + " : " + awayTeam);
        for (Map.Entry<String, String> entry : (calculateProbabilityOfMatchResult(homeTeamExpectedGoals, awayTeamExpectedGoals)).entrySet()) {
            System.out.println("Scoring " + entry.getKey() + " goals probability is " + entry.getValue());
        }
    }

    private int getLeagueHomeGoals(List<List<String>> records) {
        int leagueHomeGoals = 0;
        for (int i = 1; i < records.size(); i++) {
            leagueHomeGoals = leagueHomeGoals + Integer.parseInt(records.get(i).get(5));
        }
        return leagueHomeGoals;
    }

    private int getLeagueAwayGoals(List<List<String>> records) {
        int leagueAwayGoals = 0;
        for (int i = 1; i < records.size(); i++) {
            leagueAwayGoals = leagueAwayGoals + Integer.parseInt(records.get(i).get(6));
        }
        return leagueAwayGoals;
    }

    private double calculateHomeGamesPlayed(String team, List<List<String>> leagueData) {
        int n = 0;

        for (int i = 1; i < leagueData.size(); i++) {
            if (leagueData.get(i).get(3).equals(team)) {
                n++;
            }
        }
        return n;
    }

    private double calculateAwayGamesPlayed(String team, List<List<String>> leagueData) {
        int n = 0;

        for (int i = 1; i < leagueData.size(); i++) {
            if (leagueData.get(i).get(4).equals(team)) {
                n++;
            }
        }
        return n;
    }


    private double calculateAwayScoredGoals(String team, List<List<String>> leagueData) {
        double n = 0;

        for (int i = 1; i < leagueData.size(); i++) {
            if (leagueData.get(i).get(4).equals(team)) {
                n = n + Integer.parseInt(leagueData.get(i).get(6));
            }

        }
        return n;
    }

    private double calculateAwayConcededGoals(String team, List<List<String>> leagueData) {
        double n = 0;
        for (int i = 1; i < leagueData.size(); i++) {
            if (leagueData.get(i).get(4).equals(team)) {
                n = n + Integer.parseInt(leagueData.get(i).get(5));
            }
        }
        return n;
    }

    private double calculateHomeScoredGoals(String team, List<List<String>> leagueData) {
        double n = 0;
        for (int i = 1; i < leagueData.size(); i++) {
            if (leagueData.get(i).get(3).equals(team)) {
                n = n + Integer.parseInt(leagueData.get(i).get(5));
            }

        }
        return n;
    }

    private double calculateHomeConcededGoals(String team, List<List<String>> leagueData) {
        double n = 0;
        for (int i = 1; i < leagueData.size(); i++) {
            if (leagueData.get(i).get(3).equals(team)) {
                n = n + Integer.parseInt(leagueData.get(i).get(6));
            }
        }
        return n;
    }

    static String showPercentages(Double probability) {
        int n = (int) (probability * 10000);
        double n1 = n / 100D;
        return Double.toString(n1) + "%";

    }

    private Map<Integer, String> calculateProbabilityOfResultForEachTeam(double teamExpectedGoals) {
        PoissonDistribution distribution = new PoissonDistribution(teamExpectedGoals);

        Map<Integer, String> probabilityInPercentages = new HashMap<>();

        for (int i = 0; i < 6; i++) {
            probabilityInPercentages.put(i, showPercentages(distribution.probability(i)));
        }
        return probabilityInPercentages;
    }

    private Map<String, String> calculateProbabilityOfMatchResult(double homeTeamExpectedGoals, double awayTeamExpectedGoals) {
        PoissonDistribution homeTeamDistribution = new PoissonDistribution(homeTeamExpectedGoals);
        PoissonDistribution awayTeamDistribution = new PoissonDistribution(awayTeamExpectedGoals);
        Map<Integer, Double> homeTeamProbability = new HashMap<>();
        Map<Integer, Double> awayTeamProbability = new HashMap<>();


        for (int i = 0; i < 6; i++) {
            homeTeamProbability.put(i, homeTeamDistribution.probability(i));
            awayTeamProbability.put(i, awayTeamDistribution.probability(i));

        }
        Map<String, String> resultsPercentage = new HashMap<>();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                String goals = i + "-" + j;
                resultsPercentage.put(goals, showPercentages(homeTeamProbability.get(i) * awayTeamProbability.get(j)));
            }

        }
        return resultsPercentage;

    }


    private void printDashes() {
        System.out.println("");
        System.out.println("--------------------------------------------------------");
        System.out.println("");
    }
}
