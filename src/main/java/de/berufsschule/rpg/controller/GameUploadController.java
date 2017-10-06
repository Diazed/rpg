package de.berufsschule.rpg.controller;

import de.berufsschule.rpg.services.FileService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

@Controller
@Slf4j
public class GameUploadController {

  private FileService fileService;

  @Autowired
  public GameUploadController(FileService fileService) {
    this.fileService = fileService;
  }

  @RequestMapping(value = "/upload/file", method = RequestMethod.POST)
  public String uploadFile(MultipartFile file) {

    File destFile = fileService.getGameDirectory();
    if (!destFile.exists()) {
      try {
        if (destFile.createNewFile()) {
          log.info("New File created");
        }
      } catch (IOException e) {
        log.error("Could not upload File.");
      }
    }

    Path somePath = Paths.get(destFile.toURI());

    try {
      Files.copy(file.getInputStream(), somePath.resolve(file.getOriginalFilename()));
    } catch (IOException e) {
      log.error("Failed to Copy File");
    }

    return "redirect:/";
  }

  @RequestMapping(value = "/test/upload", method = RequestMethod.GET)
  public String getTest() {
    return "test";
  }


}
