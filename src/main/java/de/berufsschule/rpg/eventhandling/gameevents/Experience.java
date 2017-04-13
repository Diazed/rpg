package de.berufsschule.rpg.eventhandling.gameevents;

import de.berufsschule.rpg.model.Game;
import de.berufsschule.rpg.model.Player;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Experience implements PlayerEvent {
  @Override
  public void event(Player player, Game game) {
    Optional<Integer> optionalExp = Optional.ofNullable(game.getRoundExp());
    if (optionalExp.isPresent()) {
      increaseExperience(player, game.getRoundExp());
    } else {
      increaseExperience(player, 10);
    }
  }

  private void increaseExperience(Player player, Integer gainedExp) {
    Integer skillPoints = player.getSkillPoints();
    Integer playerLvl = player.getPlayerLvl();
    Integer playerXp = player.getExp();
    Integer neededXpToLevelUp = getNeededExperience(playerLvl);
    Integer remainingXpToLevelUp = neededXpToLevelUp - playerXp;
    if (remainingXpToLevelUp - gainedExp < 0) {
      player.setPlayerLvl(playerLvl + 1);
      player.setSkillPoints(skillPoints + 1);
      player.setExp(0);
    } else {
      player.setExp(playerXp + gainedExp);
    }
    player.setNeededExp(neededXpToLevelUp);
    player.setLevelProgress(getProgressPercentage(player.getExp(), neededXpToLevelUp));
  }

  private Integer getNeededExperience(Integer playerLvl) {
    Integer neededXp = 0;
    for (int i = 0; i < (playerLvl + 1); i++) {
      neededXp += i * 50;
    }
    return neededXp;
  }

  private Integer getProgressPercentage(double currentExp, double neededExp) {
    double onePercent = neededExp / 100;
    double percentage = currentExp / onePercent;

    return ((int) percentage);
  }
}
