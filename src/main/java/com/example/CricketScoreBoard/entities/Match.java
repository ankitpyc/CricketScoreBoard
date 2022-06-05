package com.example.CricketScoreBoard.entities;

import com.example.CricketScoreBoard.enums.MatchState;
import lombok.*;

import java.util.List;

@Builder
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Match {
    private List<Team> playingTeams;
    private Team playingTeam;
    private int totalPlayers;
    private Player playerStrikerEnd;
    private Player nonStrikerEnd;
    private int totalOvers;
    private int currOvers;
    private MatchState matchState;
    private List<Overs> OversBowled;
}
