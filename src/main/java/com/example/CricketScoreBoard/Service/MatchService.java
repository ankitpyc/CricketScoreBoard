package com.example.CricketScoreBoard.Service;

import com.example.CricketScoreBoard.entities.*;
import com.example.CricketScoreBoard.enums.MatchState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

@Service
public class MatchService {


    @Autowired
    private ScoreBoardService scoreBoardService;
    private static int teamIndex = 1;
    public void addTeam(Match match, Team A) {
        if (Objects.isNull(match.getPlayingTeams())) match.setPlayingTeams(new ArrayList<>());
        match.getPlayingTeams().add(A);
    }

    public void rotateStrike(Match match) {
        Player striker = match.getPlayerStrikerEnd();
        match.setPlayerStrikerEnd(match.getNonStrikerEnd());
        match.setNonStrikerEnd(striker);
    }

    private Overs getCurrOver(Match match) {
        if (match.getPlayingTeam().getOverPlayed().size() == 0)
            match.getPlayingTeam().getOverPlayed().add(Overs.builder().overIndex(1).extrasBowled(0).ballList(new ArrayList<>()).extrasBowled(0).build());
        return match.getPlayingTeam().getOverPlayed().get(match.getPlayingTeam().getOverPlayed().size() - 1);
    }

    private void executeBall(Match match, Overs overs, Ball ball) {
        switch (ball.getBallTpe()) {
            case Wide:
            case No:
                overs.setExtrasBowled(overs.getExtrasBowled() + 1);
                match.getPlayerStrikerEnd().setBallsPlayed(match.getPlayerStrikerEnd().getBallsPlayed()+1);
                match.getPlayingTeam().setTotalScore(match.getPlayingTeam().getTotalScore()+1);
                break;
            case Wicket:
                System.out.println("Player bowled : "+match.getPlayerStrikerEnd().getPlayerName());
                overs.getBallList().add(ball);
                scoreRun(match, ball);
                wicketDown(match);
                break;
            case Four:
                match.getPlayerStrikerEnd().setTotalFours(match.getPlayerStrikerEnd().getTotalFours() + 1);
                overs.getBallList().add(ball);
                scoreRun(match, ball);
                break;
            case Six:
                match.getPlayerStrikerEnd().setTotalSixes(match.getPlayerStrikerEnd().getTotalSixes() + 1);
                overs.getBallList().add(ball);
                scoreRun(match, ball);
                break;
            default:
                overs.getBallList().add(ball);
                scoreRun(match, ball);

        }
    }
    private Player getPlayerAtStrikerEnd(Match match){
        return match.getPlayerStrikerEnd();
    }
    private void updateScore(int runsScored,Match match,Player Striker){
        Striker.setBallsPlayed(Striker.getBallsPlayed()+1);
        match.getPlayingTeam().setTotalScore(match.getPlayingTeam().getTotalScore()+runsScored);
        Striker.setRunsScored(Striker.getRunsScored() + runsScored);
        Striker.setStrikeRate( (((double)(Striker.getRunsScored()) / Striker.getBallsPlayed()) * 100));
    }
    private void scoreRun(Match match, Ball ball) {
        Player Striker  = getPlayerAtStrikerEnd(match);
        updateScore(ball.getRunsScored(),match,Striker);
        if (ball.getRunsScored() % 2 == 1)
            rotateStrike(match);

    }

    public void ballBowled(Ball ball, Match match) {
        Overs currOver = getCurrOver(match);
        executeBall(match, currOver, ball);
        if (currOver.getBallList().size() == 6) {
            System.out.println("Over "+currOver.getOverIndex()+" finshed");
            rotateStrike(match);
            scoreBoardService.printScoreCard(match);
            if (match.getTotalOvers() == match.getCurrOvers()) {
                InningsOver(match);
                return;
            }
            match.setCurrOvers(match.getCurrOvers() + 1);
        }
    }

    private void wicketDown(Match match) {
        match.getPlayerStrikerEnd().setBallsPlayed(match.getPlayerStrikerEnd().getBallsPlayed()+1);
        Team team = match.getPlayingTeam();
        team.setTotalWickets(team.getTotalWickets() + 1);
        if (team.getTotalWickets()  == match.getTotalPlayers()-1) {
            InningsOver(match);
            return;
        }
        Player player = team.getPlayers().get(team.getNextDownIndex());
        match.setPlayerStrikerEnd(player);
        team.setNextDownIndex(team.getNextDownIndex()+1);

    }

    private void InningsOver(Match match) {
        if (match.getPlayingTeam().getTeamIndex() == 1)
            match.setMatchState(MatchState.FirstInnings);
        else {
            match.setMatchState(MatchState.MatchFinished);
        }
    return;
    }

    public void initializeMatch(Match match, Team teamA) {
        match.setPlayingTeam(teamA);
        match.setPlayerStrikerEnd(teamA.getPlayers().get(0));
        match.setNonStrikerEnd(teamA.getPlayers().get(1));
        teamA.setNextDownIndex(2);
    }

    public Team initializeTeam(Match match, Scanner scanner){
        Team teamA = Team.builder().teamIndex(teamIndex++).nextDownIndex(3).players(new ArrayList<>()).totalWickets(0).totalScore(0).totalWickets(0).build();
        System.out.println("Enter Team Details");
        for (int i = 0; i < match.getTotalPlayers(); i++) {
            String playerName = scanner.next();
            Player player = Player.builder().playerName(playerName).runsScored(0).totalFours(0).totalSixes(0).ballsPlayed(0).StrikeRate(0.0).build();
            teamA.getPlayers().add(player);
        }
        match.getPlayingTeams().add(teamA);
        return teamA;
    }

}
