package de.berufsschule.rpg.services;

import de.berufsschule.rpg.domain.model.Decision;
import de.berufsschule.rpg.domain.model.Item;
import de.berufsschule.rpg.domain.model.Player;
import de.berufsschule.rpg.domain.repositories.PlayerRepository;
import de.berufsschule.rpg.eventhandling.gameevents.PlayerEvent;
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
    List<Item> playerItems = player.getItems();
    for (int i = 0; i < playerItems.size(); i++) {
      Item playerItem = playerItems.get(i);
      if (item.getName().equals(playerItem.getName())) {
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
      savePlayer(player);
    }
  }

  public void runAllPlayerEvents(Player player) {
    for (PlayerEvent playerEvent : playerEvents) {
      playerEvent.event(player);
    }
    savePlayer(player);
  }

  public boolean doesPlayerMeetRequirements(Decision clickedDecision, Player player) {
    if (doesDecisionRequireItem(clickedDecision)) {
      return doesPlayerOwnRequiredItem(clickedDecision, player);
    } else {
      return true;
    }
  }

  private boolean doesDecisionRequireItem(Decision decision) {
    return decision.getUsedItem() != null;
  }

  private boolean doesPlayerOwnRequiredItem(Decision decision, Player player) {
    String neededItem = decision.getUsedItem();
    for (Item item : player.getItems()) {
      if (item.getName().equals(neededItem)) {
        return true; //TODO: Use Ids.
      }
    }
    return false;
  }
}
