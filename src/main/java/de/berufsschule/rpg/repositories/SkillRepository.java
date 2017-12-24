package de.berufsschule.rpg.repositories;

import de.berufsschule.rpg.model.Skill;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillRepository extends CrudRepository<Skill, Integer>{
}
