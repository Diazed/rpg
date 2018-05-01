package de.berufsschule.rpg.domain.dto.converter;

import de.berufsschule.rpg.domain.dto.PageDTO;
import de.berufsschule.rpg.domain.model.Page;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PageDTOConverter {

  private DecisionDTOConverter decisionDTOConverter;
  private ItemDTOConverter itemDTOConverter;
  private SkillDTOConverter skillDTOConverter;

  @Autowired
  public PageDTOConverter(
      DecisionDTOConverter decisionDTOConverter,
      ItemDTOConverter itemDTOConverter,
      SkillDTOConverter skillDTOConverter) {
    this.decisionDTOConverter = decisionDTOConverter;
    this.itemDTOConverter = itemDTOConverter;
    this.skillDTOConverter = skillDTOConverter;
  }

  public PageDTO toDTO(Page model) {

    PageDTO dto = new PageDTO();

    dto.setId(model.getId());
    dto.setName(model.getName());
    dto.setStorytext(model.getStorytext());
    dto.setCheckpoint(model.isCheckpoint());
    dto.setUsedItem(model.getUsedItem());
    dto.setHealthManipulation(model.getHealthManipulation());
    dto.setHungerManipulation(model.getHungerManipulation());
    dto.setThirstManipulation(model.getThirstManipulation());
    dto.setXpManipulation(model.getXpManipulation());
    dto.setSkillPointManipulation(model.getSkillPointManipulation());
    dto.setPossibilities(decisionDTOConverter.toDTOList(model.getDecisions()));
    dto.setItems(itemDTOConverter.toDTOList(model.getItems()));
    dto.setSkills(skillDTOConverter.toDTOList(model.getSkills()));

    return dto;
  }

  public List<PageDTO> toDTOList(List<Page> models) {

    List<PageDTO> dtos = new ArrayList<>();

    for (Page model : models) {
      dtos.add(toDTO(model));
    }

    return dtos;
  }
}
