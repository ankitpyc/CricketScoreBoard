package com.example.CricketScoreBoard.enums;

public enum MatchState {
    FirstInningsInProgress("firstInningsInProgress"),
    FirstInnings("first innings"),

    SecondInningsInProgress("secondInningsInProgress"),

    MatchFinished("Match Over");
    private String matchState;

    MatchState(String matchState) {

        this.matchState = matchState;
    }
}
