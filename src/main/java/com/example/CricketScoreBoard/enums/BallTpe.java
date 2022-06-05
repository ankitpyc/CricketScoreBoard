package com.example.CricketScoreBoard.enums;

import com.example.CricketScoreBoard.entities.Ball;
import lombok.*;
@Getter
public enum BallTpe {
    Wide("Wide", 1),
    No("No", 1),
    Wicket("Wicket", 0),
    Six("6", 6),
    Other("other",0),
    Invalid("invalid",0),
    Four("4", 4);
    private String ballName;
    private int extra;

    BallTpe(String name, int run) {
        this.ballName = name;
        this.extra = run;
    }
}
