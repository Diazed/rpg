package de.berufsschule.rpg.services;

import de.berufsschule.rpg.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Service
public class DeathService {

  private PlayerService playerService;

  @Autowired
  public DeathService(PlayerService playerService) {
    this.playerService = playerService;
  }

  public void playerDeath(Player player, String deathPage) {
    if (!player.getAlive()) {
      player.setItems(new ArrayList<>());
      if (deathPage == null || Objects.equals(deathPage, "")) {
        playerService.setPlayerPosition("R.I.P.", player);
      } else {
        playerService.setPlayerPosition(deathPage, player);
      }
      player.setHunger(0);
      player.setThirst(0);
      player.setHitpoints(100);
      player.setAlive(true);
      player.setOnDeathPage(true);
      playerService.editPlayer(player);
    }
  }

  public boolean revive(Player player, String startPage) {
    if (player.getOnDeathPage()) {
      player.setOnDeathPage(false);
      if (player.getCheckpoint() == null) {
        if (startPage != null) {
          player.setPosition(startPage);
        } else {
          player.setPosition("start");
        }
      } else {
        player.setPosition(player.getCheckpoint());
      }
      playerService.editPlayer(player);
      return true;
    }
    return false;
  }

}
