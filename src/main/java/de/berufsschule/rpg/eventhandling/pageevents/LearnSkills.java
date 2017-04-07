package de.berufsschule.rpg.eventhandling.pageevents;

import de.berufsschule.rpg.model.Page;
import de.berufsschule.rpg.model.Player;
import de.berufsschule.rpg.services.PlayerService;
import de.berufsschule.rpg.services.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LearnSkills implements PageEvent {

    private SkillService skillService;
    private PlayerService playerService;

    @Autowired
    public LearnSkills(SkillService skillService, PlayerService playerService) {
        this.skillService = skillService;
        this.playerService = playerService;
    }

    @Override
    public void event(Page page, Player player) {
        if (!page.getSkills().isEmpty()){
            for (String skill : page.getSkills()){
                player.getSkills().add(skillService.getSkillByName(skill));
            }
        }
    }
}
