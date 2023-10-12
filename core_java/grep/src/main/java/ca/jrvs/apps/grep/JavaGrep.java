package ca.jrvs.apps.grep;

import java.io.*;
import java.util.*;

import java.io.IOException;

public interface JavaGrep {

  /**
   * Top level search workflow
   *
   * @throws IOException
   */
  void process() throws IOException;

  /**
   * Traverse a given directory and return all files
   *
   * @param rootDir
   * @return
   */
  List<File> listFiles(String rootDir);

  /**
   * Read a file and return all the lines
   * <p>
   * FileReader: Reads characters from a file in Java. BufferedReader: Efficiently reads text from a
   * file by buffering data. Character Encoding: Specifies how characters are represented in binary
   * form.
   *
   * @param inputFile
   * @return
   */
  List<String> readLines(File inputFile);


  /**
   * check if a line contains regex pattern (passed by user)
   *
   * @param line
   * @return
   */
  boolean containsPattern(String line);

  /**
   * Write lines to a file
   *
   * @param lines
   * @throws IOException
   */
  void writeToFile(List<String> lines) throws IOException;

  String getRootPath();

  void setRootPath(String rootPath);

  String getRegex();

  void setRegex(String regex);

  String getOutFile();

  void setOutFile(String outFile);

}