package com.example.CricketScoreBoard.Service;

import com.example.CricketScoreBoard.Adapter.BallAdapter;
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

    public void startBowling(Match match, Scanner scanner) {
        for (int i = 0; i < match.getTotalOvers(); i++) {
            System.out.println(String.format("Over %s", (i + 1)));
            Overs overs = Overs.builder().overIndex(i + 1).ballList(new ArrayList<>()).extrasBowled(0).build();
            match.getPlayingTeam().getOverPlayed().add(overs);
            for (int j = 0; j < 6; j++) {
                System.out.println(String.format("Ball %s", (j + 1)));
                if (match.getMatchState().equals(MatchState.MatchFinished) || match.getMatchState().equals(MatchState.FirstInnings))
                    return;
                if (match.getMatchState().equals(MatchState.SecondInningsInProgress) && match.getPlayingTeams().get(0).getTotalScore() < match.getPlayingTeam().getTotalScore())
                    return;
                String balldata = scanner.next();
                Ball ball = new Ball();
                try {
                    ball = BallAdapter.convertIntoBall(balldata);
                } catch (IllegalArgumentException e) {
                    ball.setRunsScored(0);
                    ball.setBallTpe(BallTpe.Invalid);
                } finally {
                    if (isValidBall(ball)) j--;
                    log.info("ball type is {}", ball.getBallTpe());
                    matchService.ballBowled(ball, match);
                }
            }
        }
    }

    private boolean isValidBall(Ball ball) {
        return (ball.getBallTpe().equals(BallTpe.No) || ball.getBallTpe().equals(BallTpe.Wide) || ball.getBallTpe().equals(BallTpe.Invalid));
    }

}
