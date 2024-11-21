package ca.jrvs.apps.grep;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaGrepLambdaImp extends JavaGrepImp {

  final Logger logger = LoggerFactory.getLogger(JavaGrepLambdaImp.class);
  public static void main(String[] args) {
    if (args.length != 3) {
      throw new IllegalArgumentException("Usage: JavaGrep regex rootPath outFile");
    }

    BasicConfigurator.configure();

    JavaGrepLambdaImp javaGrepLambdaImp = new JavaGrepLambdaImp();
    javaGrepLambdaImp.setRegex(args[0]);
    javaGrepLambdaImp.setRootPath(args[1]);
    javaGrepLambdaImp.setOutFile(args[2]);

      try {
        javaGrepLambdaImp.process();
      } catch (IOException ex) {
        javaGrepLambdaImp.logger.error("Error: Unable to process", ex);
      }
    }

  @Override
  public List<String> readLines(File inputFile) {
    List<String> lines = new ArrayList<>();
    try (Stream<String> lineStream = Files.lines(inputFile.toPath())) {
      lines = lineStream.collect(Collectors.toList());
    } catch (IOException | UncheckedIOException ex) {
      logger.error("Error: Cannot read file " + inputFile.getName());
      logger.error(ex.getMessage());
    }
    return lines;
  }


  @Override
  public List<File> listFiles(String rootPath) {
    List<File> files = new ArrayList<>();
    try (Stream<Path> pathStream = Files.walk(Paths.get(rootPath))) {
      files = pathStream
          .filter(Files::isRegularFile)
          .map(Path::toFile)
          .collect(Collectors.toList());
    } catch (IOException ex) {
      logger.error(ex.getMessage());
    }
    return files;
  }
}

