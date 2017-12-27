package de.berufsschule.rpg.dto.converter;

import de.berufsschule.rpg.dto.ItemDTO;
import de.berufsschule.rpg.model.Item;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ItemDTOConverter {

  public ItemDTO toDTO(Item model) {
    ItemDTO dto = new ItemDTO();

    dto.setId(model.getId());
    dto.setConsumable(model.isConsumable());
    dto.setAmount(model.getAmount());
    dto.setName(model.getName());
    dto.setDescription(model.getDescription());

    return dto;
  }

  public List<ItemDTO> toDTOList(List<Item> models) {

    List<ItemDTO> dtos = new ArrayList<>();

    for (Item model : models) {
      dtos.add(toDTO(model));
    }

    return dtos;
  }

}
