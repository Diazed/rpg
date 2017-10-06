package de.berufsschule.rpg.services;

import de.berufsschule.rpg.eventhandling.pageevents.Checkpoint;
import de.berufsschule.rpg.eventhandling.pageevents.PageEvent;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class PageServiceTest {

  @Mock
  private GameService gameService;
  @Mock
  private PlayerService playerService;
  @Mock
  private DecisionService decisionService;
  @Mock
  private DeathService deathService;
  private List<PageEvent> pageEvents = Arrays.asList(new Checkpoint());
  private PageService systemUnderTest;


  @Before
  public void setUp() throws Exception {

    MockitoAnnotations.initMocks(this);
    systemUnderTest = new PageService(pageEvents, playerService, decisionService, gameService,
        deathService);


  }

  @Test
  public void test() {
  }

}