package de.berufsschule.rpg.services;

import de.berufsschule.rpg.eventhandling.gameevents.PlayerEvent;
import de.berufsschule.rpg.model.Decision;
import de.berufsschule.rpg.model.Game;
import de.berufsschule.rpg.model.Item;
import de.berufsschule.rpg.model.Player;
import de.berufsschule.rpg.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {

  private PlayerRepository playerRepository;
  private List<PlayerEvent> playerEvents;

  @Autowired
  public PlayerService(PlayerRepository playerRepository, List<PlayerEvent> playerEvents) {
    this.playerRepository = playerRepository;
    this.playerEvents = playerEvents;
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

  public void firstStart(Player player, String startPage) {
    if (player.getPosition() == null) {
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

  public void runAllPlayerEvents(Game game, Player player) {
    for (PlayerEvent playerEvent : playerEvents) {
      playerEvent.event(player, game);
    }
    editPlayer(player);
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
