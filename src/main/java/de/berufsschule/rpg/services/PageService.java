package de.berufsschule.rpg.services;

import de.berufsschule.rpg.model.Page;
import de.berufsschule.rpg.repositories.PageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PageService {

  private PageRepository pageRepository;

  @Autowired
  public PageService(PageRepository pageRepository) {
    this.pageRepository = pageRepository;
  }

  public void savePage(Page page) {
    pageRepository.save(page);
  }


}
