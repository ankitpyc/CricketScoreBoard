package com.example.CricketScoreBoard.entities;

import com.example.CricketScoreBoard.enums.BallTpe;
import lombok.*;

@Getter
@Setter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ball {
    String balldata;
    int runsScored;
    Boolean isExtra;
    BallTpe ballTpe;
    Boolean isWicket;
}
