package de.berufsschule.rpg.domain.dto.converter;

import de.berufsschule.rpg.domain.dto.PossibilityDTO;
import de.berufsschule.rpg.domain.model.Decision;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class DecisionDTOConverter {

  public PossibilityDTO toDTO(Decision model) {

    PossibilityDTO dto = new PossibilityDTO();

    dto.setId(model.getId());
    dto.setProbability(model.getProbability());
    dto.setUsedItem(model.getUsedItem());
    dto.setHasItem(model.isHasItem());
    dto.setRequiredSkill(model.getRequiredSkill());
    dto.setSkillMinLvl(model.getSkillMinLvl());
    dto.setSkillSuccessLvl(model.getSkillSuccessLvl());
    dto.setHasSkill(model.isHasSkill());
    dto.setText(model.getText());

    return dto;
  }

  public List<PossibilityDTO> toDTOList(List<Decision> models) {

    List<PossibilityDTO> dtos = new ArrayList<>();
    for (Decision model : models) {
      dtos.add(toDTO(model));
    }
    return dtos;
  }

}
