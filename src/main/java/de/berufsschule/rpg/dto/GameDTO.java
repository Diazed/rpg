package de.berufsschule.rpg.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameDTO {

  private Integer userId;
  private PlayerDTO player;
  private Integer id;
  private GamePlanDTO gameplan;

}
