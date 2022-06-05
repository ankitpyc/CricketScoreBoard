package com.example.CricketScoreBoard.entities;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
@Data
public class Overs {
  int overIndex;
  List<Ball> ballList;
  Integer extrasBowled;
}
