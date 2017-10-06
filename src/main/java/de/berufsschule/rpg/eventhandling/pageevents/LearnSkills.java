package de.berufsschule.rpg.eventhandling.pageevents;

import de.berufsschule.rpg.model.Page;
import de.berufsschule.rpg.model.Player;
import de.berufsschule.rpg.model.Skill;
import de.berufsschule.rpg.services.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LearnSkills implements PageEvent {

    private SkillService skillService;

    @Autowired
    public LearnSkills(SkillService skillService) {
        this.skillService = skillService;
    }

    @Override
    public void event(Page page, Player player) {
        if (!page.getSkills().isEmpty()){
            for (String skill : page.getSkills()){
                boolean learnedSkill = false;
                for (Skill playerSkill : player.getSkills()) {
                    if (playerSkill.getName().equals(skill)) {
                        learnedSkill = true;
                    }
                }
                if (!learnedSkill) {
                    player.getSkills().add(skillService.getSkillByName(skill));
                }
            }
        }
    }
}
