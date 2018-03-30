package de.berufsschule.rpg.services;

import de.berufsschule.rpg.eventhandling.pageevents.Checkpoint;
import de.berufsschule.rpg.eventhandling.pageevents.PageEvent;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class JumpServiceTest {

  @Mock
  private GameService gameService;
  @Mock
  private PlayerService playerService;
  @Mock
  private PossibilityService possibilityService;
  @Mock
  private DeathService deathService;
  private List<PageEvent> pageEvents = Arrays.asList(new Checkpoint());
  private JumpService systemUnderTest;


  @Before
  public void setUp() {

    MockitoAnnotations.initMocks(this);
    systemUnderTest = new JumpService(pageEvents, playerService, possibilityService, gameService,
        deathService);


  }

  @Test
  public void test() {
  }

}