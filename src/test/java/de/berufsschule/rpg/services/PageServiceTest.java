package de.berufsschule.rpg.services;

import de.berufsschule.rpg.eventhandling.pageevents.Checkpoint;
import de.berufsschule.rpg.eventhandling.pageevents.PageEvent;
import de.berufsschule.rpg.repositories.PageRepository;
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
  private PossibilityService possibilityService;
  @Mock
  private DeathService deathService;
  @Mock
  private PageRepository pageRepository;
  private List<PageEvent> pageEvents = Arrays.asList(new Checkpoint());
  private PageService systemUnderTest;


  @Before
  public void setUp() {

    MockitoAnnotations.initMocks(this);
    systemUnderTest = new PageService(pageEvents, playerService, possibilityService, gameService,
        pageRepository, deathService);


  }

  @Test
  public void test() {
  }

}