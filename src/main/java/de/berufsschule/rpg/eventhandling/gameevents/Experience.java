package de.berufsschule.rpg.eventhandling.gameevents;

import de.berufsschule.rpg.model.Game;
import de.berufsschule.rpg.model.Player;
import java.util.Optional;
import org.springframework.stereotype.Component;


@Component
public class Experience implements PlayerEvent {

  @Override
  public void event(Player player) {
    Game game = player.getGame();
    Optional<Integer> optionalExp = Optional.ofNullable(game.getRoundExp());
    if (optionalExp.isPresent()) {
      increaseExperience(player, game.getRoundExp());
    } else {
      increaseExperience(player, 20);
    }
  }

  private void increaseExperience(Player player, Integer gainedExp) {

    Integer neededXpToLevelUp;
    Integer playerXp = player.getExp() + gainedExp;

    while (true) {
      Integer skillPoints = player.getSkillPoints();
      Integer playerLvl = player.getPlayerLvl();

      neededXpToLevelUp = getNeededExperience(playerLvl);
      Integer remainingXpToLevelUp = neededXpToLevelUp - playerXp;
      if (remainingXpToLevelUp <= 0) {
        player.setPlayerLvl(playerLvl + 1);
        player.setSkillPoints(skillPoints + 10);
        playerXp = playerXp - neededXpToLevelUp;
      } else {
        player.setExp(playerXp);
        break;
      }
    }

    player.setNeededExp(neededXpToLevelUp);
    player.setLevelProgress(getProgressPercentage(player.getExp(), neededXpToLevelUp));
  }

  private Integer getNeededExperience(Integer playerLvl) {
    Double neededXp = 0d;
    for (int i = 0; i <= (playerLvl + 1); i++) {
      neededXp += i * 10d;
    }
    return neededXp.intValue();
  }

  private Integer getProgressPercentage(double currentExp, double neededExp) {
    Double onePercent = neededExp / 100;
    Double percentage = currentExp / onePercent;

    return (percentage.intValue());
  }
}
