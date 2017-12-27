package de.berufsschule.rpg.dto.converter;

import de.berufsschule.rpg.dto.PossibilityDTO;
import de.berufsschule.rpg.model.Possibility;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class PossibilityDTOConverter {

  public PossibilityDTO toDTO(Possibility model) {

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

  public List<PossibilityDTO> toDTOList(List<Possibility> models) {

    List<PossibilityDTO> dtos = new ArrayList<>();

    for (Possibility model : models) {
      dtos.add(toDTO(model));
    }

    return dtos;
  }

}
