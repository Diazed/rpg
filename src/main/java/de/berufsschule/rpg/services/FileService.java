package de.berufsschule.rpg.services;

import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
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
      for(File game : directory.listFiles())
        if (game.getName().equals(fileName))
          return game;
    }
    return null;
  }

}
