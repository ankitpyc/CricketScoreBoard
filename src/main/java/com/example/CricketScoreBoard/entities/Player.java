package com.example.CricketScoreBoard.entities;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
public class Player {
    private String playerName;
    private Integer runsScored;
    private Integer ballsPlayed;
    private String TeamName;
    private Integer totalFours;
    private Integer totalSixes;
    private Double StrikeRate;
}
