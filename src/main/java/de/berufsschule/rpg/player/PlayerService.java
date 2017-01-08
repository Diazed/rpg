package de.berufsschule.rpg.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

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

}
