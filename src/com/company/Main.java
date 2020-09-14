package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {


        System.out.println("FOOTBALL MATCH RESULT PREDICTOR ;)");
        System.out.println("Enter input file location path:");
        Scanner scanner1 = new Scanner(System.in);
        String pathName = scanner1.next(); ///Users/alicja/Downloads/D1.csv

        scanner1.nextLine();
        System.out.println("Enter team names");
        System.out.println("Home team");
        String homeTeam = scanner1.nextLine();
        System.out.println("Away team");
        String awayTeam = scanner1.nextLine();

        Match match=new Match(new Team(homeTeam, true),new Team(awayTeam,false));
        match.getPredictions(pathName);
    }
}