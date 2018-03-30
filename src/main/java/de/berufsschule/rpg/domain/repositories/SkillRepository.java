package de.berufsschule.rpg.domain.repositories;

import de.berufsschule.rpg.domain.model.Skill;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillRepository extends CrudRepository<Skill, Integer>{
}
