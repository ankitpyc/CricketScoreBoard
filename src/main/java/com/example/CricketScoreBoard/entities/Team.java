package com.example.CricketScoreBoard.entities;


import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Queue;

@Data
@Getter
@Setter
@Builder
public class Team {
    private int teamIndex;
    private List<Player> players;
    private Integer totalScore;
    private int nextDownIndex;
    private int totalWickets;
    private List<Overs> OverPlayed;

}
