package de.berufsschule.rpg.dto.converter;

import de.berufsschule.rpg.dto.GamePlanDTO;
import de.berufsschule.rpg.model.GamePlan;
import org.springframework.stereotype.Component;

@Component
public class GamePlanDTOConverter {

  private PageDTOConverter pageDTOConverter;
  private SkillDTOConverter skillDTOConverter;
  private ItemDTOConverter itemDTOConverter;

  public GamePlanDTOConverter(PageDTOConverter pageDTOConverter,
      SkillDTOConverter skillDTOConverter,
      ItemDTOConverter itemDTOConverter) {
    this.pageDTOConverter = pageDTOConverter;
    this.skillDTOConverter = skillDTOConverter;
    this.itemDTOConverter = itemDTOConverter;
  }

  public GamePlanDTO toDTO(GamePlan model) {
    GamePlanDTO dto = new GamePlanDTO();

    dto.setId(model.getId());
    dto.setFilename(model.getFilename());
    dto.setName(model.getName());
    dto.setStartPage(model.getStartPage());
    dto.setDeathPage(model.getDeathPage());
    dto.setRoundHunger(model.getRoundHunger());
    dto.setRoundThirst(model.getRoundThirst());
    dto.setRoundExp(model.getRoundExp());
    dto.setPages(pageDTOConverter.toDTOList(model.getPages()));
    dto.setItems(itemDTOConverter.toDTOList(model.getItems()));
    dto.setSkills(skillDTOConverter.toDTOList(model.getSkills()));

    return dto;
  }

}
