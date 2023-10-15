package ca.jrvs.apps.practice;

import java.util.regex.Pattern;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegexExcImp implements RegexExc{
  final Logger logger = LoggerFactory.getLogger(RegexExc.class);
  public static void main(String[] args) {
    BasicConfigurator.configure();

    RegexExcImp regexExcImp = new RegexExcImp();
    regexExcImp.logger.info("Check if 'pic.jpg' is valid : " + regexExcImp.matchJpeg("pic.jpg"));
    regexExcImp.logger.info("Check if 'pic2.pdf' is valid: " + regexExcImp.matchJpeg("pic2.jpq"));
    regexExcImp.logger.info("Check if 'pic3.jpeg' is valid: " + regexExcImp.matchJpeg("pic3.jpeg"));

    regexExcImp.logger.info("Check if '0.0.0.0' is valid: " + regexExcImp.matchIp("0.0.0.0"));
    regexExcImp.logger.info("Check if '999.999.999.999' is valid: " + regexExcImp.matchIp("999.999.999.999"));
    regexExcImp.logger.info("Check if '7777.7777.7777.7777' is valid: " + regexExcImp.matchIp("7777.7777.7777.7777"));

    regexExcImp.logger.info("Check if '   ' is valid : " + regexExcImp.isEmptyLine("  "));
    regexExcImp.logger.info("Check if '' is valid : " + regexExcImp.isEmptyLine(""));
    regexExcImp.logger.info("Check if 'test' is valid : " + regexExcImp.isEmptyLine("test"));
    regexExcImp.logger.info("Check if '\\n' is valid : " + regexExcImp.isEmptyLine("\n"));

  }

  @Override
  public boolean matchJpeg(String filename) {
    Pattern jpegpattern = Pattern.compile("^\\w+\\.(jpg|jpeg)$");
    return jpegpattern.matcher(filename).matches();
  }

  @Override
  public boolean matchIp(String ip) {
    Pattern ippattern = Pattern.compile("^\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}$");
    return ippattern.matcher(ip).matches();
  }

  @Override
  public boolean isEmptyLine(String line) {
    Pattern emptylinepattern = Pattern.compile("^\\s*$");
    return emptylinepattern.matcher(line).matches();
  }
}
