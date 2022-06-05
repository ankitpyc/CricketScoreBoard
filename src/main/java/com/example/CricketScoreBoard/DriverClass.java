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
        Team teamA = matchService.initializeTeam(match,scanner);
        teamA.setOverPlayed(new ArrayList<>());
        match.setMatchState(MatchState.FirstInningsInProgress);
        matchService.initializeMatch(match, teamA);
        bowlingService.bowlingInit(match, scanner);
        scoreBoardService.printInningsScoreCard(teamA);
        Team teamB = matchService.initializeTeam(match,scanner);
        teamB.setOverPlayed(new ArrayList<>());

        matchService.addTeam(match, teamB);
        match.setMatchState(MatchState.SecondInningsInProgress);
        matchService.initializeMatch(match, teamB);
        bowlingService.bowlingInit(match, scanner);
        declareWinner(teamA, teamB);
    }

    private void declareWinner(Team teamA, Team teamB) {
        scoreBoardService.printInningsScoreCard(teamA);
        scoreBoardService.printInningsScoreCard(teamB);

        if (teamA.getTotalScore() > teamB.getTotalScore())
            System.out.println("Winning team is " + "teamA");
        else
            System.out.println("Winning team is " + "teamB");

    }


}

