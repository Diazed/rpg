package de.berufsschule.rpg.services;

import de.berufsschule.rpg.model.Decision;
import de.berufsschule.rpg.model.Page;
import de.berufsschule.rpg.model.Player;
import de.berufsschule.rpg.repositories.PlayerRepository;
import de.berufsschule.rpg.validation.PlayerValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.Objects;

@Service
public class PlayerService {

    private PlayerRepository playerRepository;
    private PlayerValidation playerValidation;

    @Autowired
    public PlayerService(PlayerRepository playerRepository, PlayerValidation playerValidation){
        this.playerRepository = playerRepository;
        this.playerValidation = playerValidation;
    }

    public Player getRequestedPlayer(String username){
        return playerRepository.findByUsername(username);
    }

    public void registerPlayer(Player player, BindingResult bindingResult)
    {
        playerValidation.validate(player, bindingResult);
        if (bindingResult.hasErrors())
            return;
        playerRepository.save(player);
    }

    public void editPlayer(Player editedPlayer, Integer id){
        Player originalPlayer = playerRepository.findOne(id);
        originalPlayer.setPosition(editedPlayer.getPosition());
        originalPlayer.setUsername(editedPlayer.getUsername());
        originalPlayer.setPassword(editedPlayer.getPassword());


        playerRepository.save(originalPlayer);
    }

    public void setPlayerPosition(String gameName, String position, Player player){
        player.getPosition().put(gameName, position);
        editPlayer(player, player.getId());
    }

    private boolean isFirstStart(Player player, String gameName) {
        return Objects.equals(player.getPosition().get(gameName), null);
    }

    public void firstStart(Player player, String gameName, String startPage){
        if (isFirstStart(player, gameName))
            setPlayerPosition(gameName, startPage, player);
    }

    public boolean playerDeath(Player player, Page currentPage, String deathPage, String gameName){
        if (isPlayerDead(currentPage, deathPage)){
            player.setItems(new ArrayList<>());
            if (player.getCheckpoint() == null){
              setPlayerPosition(gameName, "R.I.P.", player);
            } else {
              setPlayerPosition(gameName, player.getCheckpoint(), player);
            }

            player.setHunger(0);
            player.setThirst(0);
            player.setHitpoints(100);
            editPlayer(player, player.getId());
            return true;
        }
        return false;
    }

    private boolean isPlayerDead(Page currentPage, String deathPage){
        return Objects.equals(currentPage.getName(), "R.I.P.") || Objects.equals(currentPage.getName(), deathPage);
    }

    public void roundEffects(Player player, String gameName, String deathPage, Decision clickedDecision, Integer hunger, Integer thirst){
      roundExp(player);
      increaseHunger(player, gameName, deathPage, hunger);
      increaseThirst(player, gameName, deathPage, thirst);
      decisionInjury(player, gameName, deathPage, clickedDecision);
      editPlayer(player, player.getId());
    }

  private void decisionInjury(Player player, String gameName, String deathPage, Decision clickedDecision){

    int injury = clickedDecision.getInjury();
    int playerHealth = player.getHitpoints();

    if (injury > 0){
      if (playerHealth - injury < 0){
        playerDied(player, gameName, deathPage);
      } else {
        player.setHitpoints(playerHealth - injury);
      }
    }
  }

  private void increaseThirst(Player player, String gameName, String deathPage, Integer thirst) {
    if (player.getThirst() + thirst > 100) {
      playerDied(player, gameName, deathPage);
    } else {
      player.setThirst(player.getThirst() + thirst);
    }
  }

  private void increaseHunger(Player player, String gameName, String deathPage, Integer hunger) {
    if (player.getHunger() + hunger > 100) {
      playerDied(player, gameName, deathPage);
    } else {
      player.setHunger(player.getHunger() + hunger);
    }

  }

  private void playerDied(Player player, String gameName, String deathPage){
    if (deathPage == null || Objects.equals(deathPage, "")) {
      player.getPosition().put(gameName, "R.I.P.");
    }else {
      player.getPosition().put(gameName, deathPage);
    }
  }

  private void roundExp(Player player){
    Integer playerLvl = player.getPlayerLvl();
    Integer playerXp = player.getExp();
    Integer neededXp = 0;
    for (int i=0; i<playerLvl; i++){
      neededXp += i * 50;
    }
    neededXp = neededXp - playerXp;
    if (neededXp - 10 < 0){
      player.setPlayerLvl(playerLvl + 1);
      player.setExp(0);
    } else {
      player.setExp(playerXp + 10);
    }
  }

}
