package ca.jrvs.apps.grep;

import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class JavaGrepImp implements JavaGrep {

  final Logger logger = LoggerFactory.getLogger(JavaGrep.class);

  private String regex;
  private String rootPath;
  private String outFile;

  public static void main(String[] args) {
    if (args.length != 3) {
      throw new IllegalArgumentException("Usage: JavaGrep regex rootPath outFile");
    }

    BasicConfigurator.configure();

    JavaGrepImp javaGrepImp = new JavaGrepImp();
    javaGrepImp.setRegex(args[0]);
    javaGrepImp.setRootPath(args[1]);
    javaGrepImp.setOutFile(args[2]);

    try {
      javaGrepImp.process();
    } catch (Exception ex) {
      javaGrepImp.logger.error("Error: Unable to process", ex);
    }
  }


  @Override
  public void process() throws IOException {
    List<String> lines = new ArrayList<>();
    for (File file : listFiles(rootPath)) {
      for (String line : readLines(file)) {
        if (containsPattern(line)) {
          lines.add(line);
        }
      }
    }
    writeToFile(lines);
  }

  @Override
  public List<File> listFiles(String rootPath) {
    List<File> fileList = new ArrayList<>();
    File root = new File(rootPath);
    File[] filesToCheck;
    if (root.isDirectory()) {
      filesToCheck = root.listFiles();
      if (filesToCheck != null) {
        for (File file : filesToCheck) {
          if (file.isFile()) {
            fileList.add(file);
          } else if (file.isDirectory()) {
            fileList.addAll(listFiles(file.getAbsolutePath()));
          }
        }
      }
    }
    return fileList;
  }

  @Override
  public List<String> readLines(File inputFile) {
    List<String> lines = new ArrayList<>();
    String line;
    try (BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile))) {
      line = bufferedReader.readLine();
      while (line != null) {
        lines.add(line);
        line = bufferedReader.readLine();
      }
    } catch (IOException ex) {
      logger.error(ex.getMessage());
    }
    return lines;
  }

  @Override
  public boolean containsPattern(String line) {
    return Pattern.compile(regex).matcher(line).find();
  }

  @Override
  public void writeToFile(List<String> lines) throws IOException {
    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outFile));

    for (String line : lines) {
      bufferedWriter.write(line + "\n");
    }
    bufferedWriter.close();
  }

  @Override
  public String getRootPath() {
    return rootPath;
  }

  @Override
  public void setRootPath(String rootPath) {
    this.rootPath = rootPath;
  }

  @Override
  public String getRegex() {
    return regex;
  }

  @Override
  public void setRegex(String regex) {
    this.regex = regex;
  }

  @Override
  public String getOutFile() {
    return outFile;
  }

  @Override
  public void setOutFile(String outFile) {
    this.outFile = outFile;
  }

}