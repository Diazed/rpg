package de.berufsschule.rpg.domain.dto.converter;

import de.berufsschule.rpg.domain.dto.SkillDTO;
import de.berufsschule.rpg.domain.model.Skill;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class SkillDTOConverter {

  public SkillDTO toDTO(Skill model) {
    SkillDTO dto = new SkillDTO();

    dto.setId(model.getId());
    dto.setLevel(model.getLevel());
    dto.setNeededSkillPoints(model.getNeededSkillPoints());
    dto.setGivenSkillPoints(model.getGivenSkillPoints());
    dto.setProgress(model.getProgress());
    dto.setDescribtion(model.getDescribtion());
    dto.setName(model.getName());

    return dto;
  }

  public List<SkillDTO> toDTOList(List<Skill> models) {

    List<SkillDTO> dtos = new ArrayList<>();

    for (Skill model : models) {
      dtos.add(toDTO(model));
    }

    return dtos;
  }

}
