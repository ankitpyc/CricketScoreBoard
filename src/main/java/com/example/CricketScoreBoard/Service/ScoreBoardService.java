package com.example.CricketScoreBoard.Service;

import com.example.CricketScoreBoard.entities.Match;
import com.example.CricketScoreBoard.entities.Overs;
import com.example.CricketScoreBoard.entities.Player;
import com.example.CricketScoreBoard.entities.Team;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class ScoreBoardService {

    public void printScoreCard(Match match) {
        System.out.println("---------------------------------------------------");
        System.out.println("PlayerName | Score | 4s | 6s | Balls | StrikeRate");
        List<Player> playerList = match.getPlayingTeam().getPlayers();
        for (Player player : playerList) {
            if (match.getPlayerStrikerEnd().equals(player))
                System.out.println(player.getPlayerName() + "*" + " | " + player.getRunsScored() + " | " + player.getTotalFours() + " | " + player.getTotalSixes() + " | " + player.getBallsPlayed() + " | " + player.getStrikeRate());
            else
                System.out.println(player.getPlayerName() + " | " + player.getRunsScored() + " | " + player.getTotalFours() + " | " + player.getTotalSixes() + " | " + player.getBallsPlayed() + " | " + player.getStrikeRate());
        }

        System.out.println("Total : " + match.getPlayingTeam().getTotalScore() + "/" + match.getPlayingTeam().getTotalWickets());
        System.out.println("Overs :" + match.getPlayingTeam().getOverPlayed().size());
        System.out.println("---------------------------------------------------");
    }

    public void printInningsScoreCard(Team team) {
        System.out.println("Team " + team.getTeamIndex() + "ScoreCard ");
        System.out.println("---------------------------------------------------");
        System.out.println("PlayerName | Score | 4s | 6s | Balls | StrikeRate");
        List<Player> playerList = team.getPlayers();
        for (Player player : playerList) {
            System.out.println(player.getPlayerName() + " |  " + player.getRunsScored() + " | " + player.getTotalFours() + " | " + player.getTotalSixes() + " | " + player.getBallsPlayed() + " | " + player.getStrikeRate());
        }
        System.out.println("Total : " + team.getTotalScore() + "-" + team.getTotalWickets());
        System.out.println("Overs :" + getTotalOvers(team));
        System.out.println("---------------------------------------------------");
    }

    private String getTotalOvers(Team team) {
        Overs lastOver = team.getOverPlayed().get(team.getOverPlayed().size() - 1);
        if (lastOver.getBallList().size() < 6)
            return team.getOverPlayed().size() - 1 + "." + lastOver.getBallList().size();
        else
            return (String.valueOf(team.getOverPlayed().size()));
    }
}
