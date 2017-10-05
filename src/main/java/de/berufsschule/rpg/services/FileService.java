package de.berufsschule.rpg.services;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FileService {

  public File getGameDirectory() {
    FileSystemResource fsr = new FileSystemResource("games");
    if(fsr.exists()){
      return fsr.getFile();
    }else{
      fsr.getFile().mkdir();
      return fsr.getFile();
    }
  }

  public List<String> getFileNames(){
    List<String> fileNames = new ArrayList<>();
    File directory = getGameDirectory();
    File[] listOfFiles = directory.listFiles();

    if (listOfFiles == null)
      return new ArrayList<>();

    for (File listOfFile : listOfFiles) {
      if (listOfFile.isFile()) {
        fileNames.add(listOfFile.getName());
      }
    }
    return fileNames;
  }

  public File getGameByFileName(String fileName){
    File directory = getGameDirectory();
    if (directory != null){
      try {
        for (File game : directory.listFiles()) {
          if (game.getName().equals(fileName)) {
            return game;
          }
        }
      } catch (Exception e) {
        log.error("Failed to loop directory files. Error: " + e.getMessage());
      }
    }
    return null;
  }
}
