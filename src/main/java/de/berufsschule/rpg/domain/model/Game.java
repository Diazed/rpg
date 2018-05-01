package de.berufsschule.rpg.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Setter
@Getter
public class Game {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private Integer userId;
  @ManyToOne
  private GamePlan gamePlan;
  @OneToOne(mappedBy = "game")
  @Cascade(CascadeType.ALL)
  private Player player;

}
