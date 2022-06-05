package com.example.CricketScoreBoard.Adapter;

import com.example.CricketScoreBoard.entities.Ball;
import com.example.CricketScoreBoard.enums.BallTpe;

public class BallAdapter {
    public static Ball convertIntoBall(String balldata) throws IllegalArgumentException {
        BallTpe ballTpe = BallTpe.Other;
        Ball ball = new Ball();
        if (balldata.equalsIgnoreCase("4")) {
            ballTpe = BallTpe.Four;
            ball.setRunsScored(ballTpe.getExtra());
        } else if (balldata.equalsIgnoreCase("6")) {
            ballTpe = BallTpe.Six;
            ball.setRunsScored(ballTpe.getExtra());
        } else if (balldata.equalsIgnoreCase("W")) {
            System.out.println("Ball type is wicket");
            ball.setBallTpe(BallTpe.Wicket);
            ballTpe = BallTpe.Wicket;
            ball.setRunsScored(0);
        } else if (balldata.equalsIgnoreCase("N")) {
            ballTpe = BallTpe.No;
            ball.setRunsScored(BallTpe.No.getExtra());
        } else if (balldata.equalsIgnoreCase("Wd")) {
            ballTpe = BallTpe.Wide;
            ball.setRunsScored(BallTpe.No.getExtra());
        } else {
            ball.setRunsScored(Integer.parseInt(balldata));
        }
        ball.setBallTpe(ballTpe);
        return ball;
    }

}
