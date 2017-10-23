package de.berufsschule.rpg.services;

import de.berufsschule.rpg.eventhandling.gameevents.PlayerEvent;
import de.berufsschule.rpg.model.Item;
import de.berufsschule.rpg.model.Player;
import de.berufsschule.rpg.model.Possibility;
import de.berufsschule.rpg.repositories.PlayerRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {

  @Autowired
  private PlayerRepository playerRepository;
  private List<PlayerEvent> playerEvents;

  @Autowired
  public PlayerService(List<PlayerEvent> playerEvents) {
    this.playerEvents = playerEvents;
  }

  public void savePlayer(Player editedPlayer) {
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

  public void setPlayerPosition(Integer position, Player player) {
    player.setPosition(position);
    savePlayer(player);
  }

  public void firstStart(Player player, Integer startPage) {
    if (player.getPosition() == null) {
      player.setPlayerLvl(1);
      player.setExp(0);
      player.setHitpoints(100);
      player.setHunger(0);
      player.setThirst(0);
      player.setAlive(true);
      player.setOnDeathPage(false);
      if (startPage == null) {
        setPlayerPosition(0, player);
      } else {
        setPlayerPosition(startPage, player);
      }
    }
  }

  public void runAllPlayerEvents(Player player) {
    for (PlayerEvent playerEvent : playerEvents) {
      playerEvent.event(player);
    }
    savePlayer(player);
  }

  public boolean doesPlayerMeetRequirements(Possibility clickedPossibility, Player player) {
    if (doesDecisionRequireItem(clickedPossibility)) {
      return doesPlayerOwnRequiredItem(clickedPossibility, player);
    } else {
      return true;
    }
  }

  private boolean doesDecisionRequireItem(Possibility possibility) {
    return possibility.getUsedItem() != null;
  }

  private boolean doesPlayerOwnRequiredItem(Possibility possibility, Player player) {
    String neededItem = possibility.getUsedItem();
    for (String item : player.getItems()) {
      if (item.equals(neededItem))
        return true;
    }
    return false;
  }
}
