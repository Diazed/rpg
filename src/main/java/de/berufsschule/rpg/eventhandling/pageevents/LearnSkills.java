package de.berufsschule.rpg.eventhandling.pageevents;

import de.berufsschule.rpg.domain.model.Page;
import de.berufsschule.rpg.domain.model.Player;
import de.berufsschule.rpg.domain.model.Skill;
import org.springframework.stereotype.Component;

@Component
public class LearnSkills implements PageEvent {



    @Override
    public void event(Page page, Player player) {
        if (!page.getSkills().isEmpty()){
            for (Skill skill : page.getSkills()) {
                boolean learnedSkill = false;
                for (Skill playerSkill : player.getSkills()) {
                    if (playerSkill.getName().equals(skill.getName())) {
                        learnedSkill = true;
                    }
                }
                if (!learnedSkill) {
                    player.getSkills().add(skill);
                }
            }
        }
    }
}
