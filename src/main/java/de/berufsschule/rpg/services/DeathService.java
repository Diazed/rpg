package de.berufsschule.rpg.services;

import de.berufsschule.rpg.domain.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class DeathService {

  private PlayerService playerService;

  @Autowired
  public DeathService(PlayerService playerService) {
    this.playerService = playerService;
  }

  public void playerDeath(Player player, Integer deathPage) {
    if (!player.getAlive()) {
      player.setItems(new ArrayList<>());

      playerService.setPlayerPosition(deathPage, player);

      player.setHunger(0);
      player.setThirst(0);
      player.setHitpoints(100);
      player.setAlive(true);
      player.setOnDeathPage(true);
      playerService.savePlayer(player);
    }
  }

  public boolean revive(Player player, Integer startPage) {
    if (!player.getAlive()) {
      return true;
    }
    if (player.getOnDeathPage()) {
      player.setOnDeathPage(false);
      if (player.getCheckpoint() == null) {
          player.setPosition(startPage);
      } else {
        player.setPosition(player.getCheckpoint());
      }
      playerService.savePlayer(player);
      return true;
    }
    return false;
  }

}
