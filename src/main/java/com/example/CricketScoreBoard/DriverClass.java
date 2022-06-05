package com.example.CricketScoreBoard;

import com.example.CricketScoreBoard.Service.BowlingService;
import com.example.CricketScoreBoard.Service.MatchService;
import com.example.CricketScoreBoard.Service.ScoreBoardService;
import com.example.CricketScoreBoard.entities.*;
import com.example.CricketScoreBoard.enums.BallTpe;
import com.example.CricketScoreBoard.enums.MatchState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Scanner;

@Slf4j
@Configuration
public class DriverClass {
    @Autowired
    private MatchService matchService;
    @Autowired
    private BowlingService bowlingService;
    @Autowired
    private ScoreBoardService scoreBoardService;

    @PostConstruct
    public void initialize() {
        Match match = new Match();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter total no of Players");
        match.setTotalPlayers(scanner.nextInt());
        System.out.println("Enter total no of Overs");
        match.setTotalOvers(scanner.nextInt());
        match.setPlayingTeams(new ArrayList<>());
        Team teamA = matchService.initializeTeam(match, scanner);
        teamA.setOverPlayed(new ArrayList<>());
        match.setMatchState(MatchState.FirstInningsInProgress);
        //Initialize match for team A
        matchService.initializeMatch(match, teamA);
        //Bowling starts for team A
        bowlingService.startBowling(match, scanner);
        scoreBoardService.printInningsScoreCard(teamA);
        Team teamB = matchService.initializeTeam(match, scanner);
        teamB.setOverPlayed(new ArrayList<>());
        //Initialize match for team B
        matchService.addTeam(match, teamB);
        match.setMatchState(MatchState.SecondInningsInProgress);
        //Load players into ground
        matchService.initializeMatch(match, teamB);
        //Bowling starts for team B
        bowlingService.startBowling(match, scanner);
        declareWinner(teamA, teamB);
    }

    private void declareWinner(Team teamA, Team teamB) {
        scoreBoardService.printInningsScoreCard(teamA);
        scoreBoardService.printInningsScoreCard(teamB);

        if (teamA.getTotalScore() > teamB.getTotalScore())
            System.out.println(String.format("Team A won by %s runs",teamA.getTotalScore() - teamB.getTotalScore()));
        else if(teamB.getTotalScore() > teamA.getTotalScore())
            System.out.println(String.format("Team B won by %s wickets",10 - teamB.getTotalWickets()));
        else
            System.out.println("Match Draw between team A and team B");

    }


}

