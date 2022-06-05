package com.example.CricketScoreBoard.Service;

import com.example.CricketScoreBoard.entities.Ball;
import com.example.CricketScoreBoard.entities.Match;
import com.example.CricketScoreBoard.entities.Overs;
import com.example.CricketScoreBoard.enums.BallTpe;
import com.example.CricketScoreBoard.enums.MatchState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Scanner;

@Service
@Slf4j
public class BowlingService {
    @Autowired
    private MatchService matchService;

    public void bowlingInit(Match match, Scanner scanner) {
        for (int i = 0; i < match.getTotalOvers(); i++) {
            Overs overs = Overs.builder().overIndex(i + 1).ballList(new ArrayList<>()).extrasBowled(0).build();
            match.getOversBowled().add(overs);
            for (int j = 0; j < 6; j++) {
                if(match.getMatchState().equals(MatchState.MatchFinished) || match.getMatchState().equals(MatchState.FirstInnings))
                    return;
                if(match.getMatchState().equals(MatchState.SecondInningsInProgress) && match.getPlayingTeams().get(0).getTotalScore() < match.getPlayingTeam().getTotalScore())
                    return;
                System.out.println("Ball " + (j + 1));
                String balldata = scanner.next();
                Ball ball = new Ball();
                BallTpe ballTpe = BallTpe.Other;
                try {
                    if (balldata.equalsIgnoreCase("4")) {
                        ballTpe = BallTpe.Four;
                        ball.setRunsScored(ballTpe.getExtra());
                    } else if (balldata.equalsIgnoreCase("6")) {
                        ballTpe = BallTpe.Six;
                        ball.setRunsScored(ballTpe.getExtra());
                    } else if (balldata.equalsIgnoreCase("W")) {
                        System.out.println("Ball tyep is wicket");
                        ball.setBallTpe(BallTpe.Wicket);
                        ballTpe = BallTpe.Wicket;
                        ball.setRunsScored(0);
                    } else if (balldata.equalsIgnoreCase("N")) {
                        ballTpe = BallTpe.No;
                        ball.setRunsScored(BallTpe.No.getExtra());
                    } else if (balldata.equalsIgnoreCase("Wd")) {
                        ballTpe = BallTpe.Wide;
                    } else if (Character.isDigit(Integer.parseInt(balldata))) {
                        ball.setRunsScored(Integer.parseInt(balldata));
                    } else {
                        ballTpe = BallTpe.valueOf(balldata);
                    }
                    ball.setBallTpe(ballTpe);
                } catch (IllegalArgumentException e) {
                    ball.setRunsScored(Integer.parseInt(balldata));
                    ball.setBallTpe(BallTpe.Other);
                } finally {
                    if (ballTpe.equals(BallTpe.No) || ballTpe.equals(BallTpe.Wide))
                        j--;
                    log.info("ball type is {}", ball.getBallTpe());
                    matchService.ballBowled(ball, match);
                }
            }
        }


    }

}
