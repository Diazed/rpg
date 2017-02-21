package de.berufsschule.rpg.services;

import de.berufsschule.rpg.model.Decision;
import de.berufsschule.rpg.model.Item;
import de.berufsschule.rpg.model.Player;
import de.berufsschule.rpg.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class PlayerService {

  private PlayerRepository playerRepository;


  @Autowired
  public PlayerService(PlayerRepository playerRepository) {
    this.playerRepository = playerRepository;
  }


  public void editPlayer(Player editedPlayer) {
    playerRepository.save(editedPlayer);
  }

  public void removeItemFromPlayer(Player player, Item item) {

    List<String> playerItems = player.getItems();

    for (int i = 0; i < playerItems.size(); i++) {
      String playerItem = playerItems.get(i);
      if (item.getName().equals(playerItem)) {
        playerItems.remove(i);
        player.setItems(playerItems);
        playerRepository.save(player);
        break;
      }
    }
  }


  public void setPlayerPosition(String position, Player player) {
    player.setPosition(position);
    editPlayer(player);
  }

  private boolean isFirstStart(Player player) {
    return Objects.equals(player.getPosition(), null);
  }

  public void firstStart(Player player, String startPage) {

    if (isFirstStart(player)) {
      player.setPlayerLvl(1);
      player.setExp(0);
      player.setHitpoints(100);
      player.setHunger(0);
      player.setThirst(0);
      player.setAlive(true);
      player.setOnDeathPage(false);
      if (startPage == null) {
        setPlayerPosition("start", player);
      } else {
        setPlayerPosition(startPage, player);
      }

    }
  }

  public void playerDeath(Player player, String deathPage) {

    player.setItems(new ArrayList<>());
    if (deathPage == null || Objects.equals(deathPage, "")) {
      setPlayerPosition("R.I.P.", player);
    } else {
      setPlayerPosition(deathPage, player);
    }
    player.setHunger(0);
    player.setThirst(0);
    player.setHitpoints(100);
    player.setAlive(true);
    player.setOnDeathPage(true);
    editPlayer(player);
  }

  public boolean revive(Player player) {
    if (player.getOnDeathPage()) {
      player.setOnDeathPage(false);
      if (player.getCheckpoint() == null) {
        player.setPosition("start");
      } else {
        player.setPosition(player.getCheckpoint());
      }
      editPlayer(player);
      return true;
    }
    return false;
  }


  public void roundEffects(Player player, Integer hunger, Integer thirst) {
    roundExp(player);
    increaseHunger(player, hunger);
    increaseThirst(player, thirst);
    editPlayer(player);
  }

  private void increaseThirst(Player player, Integer thirst) {
    if (player.getThirst() + thirst > 100) {
      player.setAlive(false);
    } else {
      player.setThirst(player.getThirst() + thirst);
    }
  }

  private void increaseHunger(Player player, Integer hunger) {
    if (player.getHunger() + hunger > 100) {
      player.setAlive(false);
    } else {
      player.setHunger(player.getHunger() + hunger);
    }

  }

  private void roundExp(Player player) {
    Integer playerLvl = player.getPlayerLvl();
    Integer playerXp = player.getExp();
    Integer neededXp = 0;
    for (int i = 0; i < playerLvl; i++) {
      neededXp += i * 50;
    }
    neededXp = neededXp - playerXp;
    if (neededXp - 10 < 0) {
      player.setPlayerLvl(playerLvl + 1);
      player.setExp(0);
    } else {
      player.setExp(playerXp + 10);
    }
  }

  public boolean doesPlayerMeetRequirements(Decision clickedDecision, Player player) {

    if (doesDecisionRequireItem(clickedDecision)) {
      return doesPlayerOwnRequiredItem(clickedDecision, player);
    } else {
      return true;
    }
  }

  private boolean doesDecisionRequireItem(Decision decision) {
    return decision.getItem().getName() != null;
  }

  private boolean doesPlayerOwnRequiredItem(Decision decision, Player player) {
    String neededItem = decision.getUsedItem();
    for (String item : player.getItems()) {
      if (item.equals(neededItem))
        return true;
    }
    return false;
  }

}
