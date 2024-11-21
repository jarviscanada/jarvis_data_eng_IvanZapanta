package ca.jrvs.apps.practice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LambaStreamExcImp implements LambdaStreamExc {

  public static void main(String[] args) {
    LambaStreamExcImp lambaStreamExcImp = new LambaStreamExcImp();

    // Test createStrStream and toUpperCase
    String[] strings = {"apple", "banana", "cherry"};
    lambaStreamExcImp.toUpperCase(strings)
        .forEach(System.out::println);

    // Test filter
    Stream<String> stringStream = lambaStreamExcImp.createStrStream("apple", "banana", "cherry");
    lambaStreamExcImp.filter(stringStream, "a")
        .forEach(System.out::println);

    // Test createIntStream and toList
    int[] intArray = {1, 2, 3, 4, 5};
    IntStream intStream = lambaStreamExcImp.createIntStream(intArray);
    List<Integer> intList = lambaStreamExcImp.toList(intStream);
    System.out.println(intList);

    // Test createIntStream (range)
    IntStream rangeStream = lambaStreamExcImp.createIntStream(1, 5);
    rangeStream.forEach(System.out::println);

    // Test squareRootIntStream
    IntStream intStreamForSqrt = lambaStreamExcImp.createIntStream(4, 16);
    lambaStreamExcImp.squareRootIntStream(intStreamForSqrt)
        .forEach(System.out::println);

    // Test getOdd
    IntStream intStreamForOdd = lambaStreamExcImp.createIntStream(1, 10);
    lambaStreamExcImp.getOdd(intStreamForOdd)
        .forEach(System.out::println);

    // Test getLambdaPrinter
    Consumer<String> printer = lambaStreamExcImp.getLambdaPrinter("start>", "<end");
    printer.accept("Message body");
    System.out.println();

    // Test printMessages
    String[] messages = {"a", "b", "c"};
    lambaStreamExcImp.printMessages(messages, lambaStreamExcImp.getLambdaPrinter("msg:", "!"));
    System.out.println();

    // Test printOdd
    lambaStreamExcImp.printOdd(lambaStreamExcImp.createIntStream(0, 5),
        lambaStreamExcImp.getLambdaPrinter("odd number:", "!"));

  }

  /**
   * Create a String stream from array
   * <p>
   * note: arbitrary number of value will be stored in an array
   *
   * @param strings
   * @return
   */
  @Override
  public Stream<String> createStrStream(String... strings) {
    return Arrays.stream(strings);
  }

  /**
   * Convert all strings to uppercase please use createStrStream
   *
   * @param strings
   * @return
   */
  @Override
  public Stream<String> toUpperCase(String... strings) {
    return this.createStrStream(strings).map(s -> s.toUpperCase());
  }

  /**
   * filter strings that contains the pattern e.g. filter(stringStream, "a") will return another
   * stream which no element contains a
   *
   * @param stringStream
   * @param pattern
   * @return
   */
  @Override
  public Stream<String> filter(Stream<String> stringStream, String pattern) {
    Pattern regex = Pattern.compile(pattern);
    return stringStream.filter(s -> !regex.matcher(s).matches());
  }

  /**
   * Create a intStream from a arr[]
   *
   * @param arr
   * @return
   */
  @Override
  public IntStream createIntStream(int[] arr) {
    return Arrays.stream(arr);
  }

  /**
   * Convert a stream to list
   *
   * @param stream
   * @param <E>
   * @return
   */
  @Override
  public <E> List<E> toList(Stream<E> stream) {
    List<E> list = stream.collect(Collectors.toList());
    return new ArrayList<E>(list);
  }

  /**
   * Convert a intStream to list
   *
   * @param intStream
   * @return
   */
  @Override
  public List<Integer> toList(IntStream intStream) {
    List<Integer> list = intStream.mapToObj(Integer::valueOf).collect(Collectors.toList());
    return new ArrayList<Integer>(list);
  }

  /**
   * Create a IntStream range from start to end inclusive
   *
   * @param start
   * @param end
   * @return
   */
  @Override
  public IntStream createIntStream(int start, int end) {
    return IntStream.rangeClosed(start, end);
  }

  /**
   * Convert a intStream to a doubleStream and compute square root of each element
   *
   * @param intStream
   * @return
   */
  @Override
  public DoubleStream squareRootIntStream(IntStream intStream) {
    return intStream.asDoubleStream().map(s -> Math.sqrt(s));
  }

  /**
   * filter all even number and return odd numbers from a intStream
   *
   * @param intStream
   * @return
   */
  @Override
  public IntStream getOdd(IntStream intStream) {
    return intStream.filter(i -> i % 2 == 1);
  }

  /**
   * Return a lambda function that print a message with a prefix and suffix This lambda can be
   * useful to format logs
   * <p>
   * You will learn: - functional interface http://bit.ly/2pTXRwM & http://bit.ly/33onFig - lambda
   * syntax
   * <p>
   * e.g. LambdaStreamExc lse = new LambdaStreamImp(); Consumer<String> printer =
   * lse.getLambdaPrinter("start>", "<end"); printer.accept("Message body");
   * <p>
   * sout: start>Message body<end
   *
   * @param prefix prefix str
   * @param suffix suffix str
   * @return
   */
  @Override
  public Consumer<String> getLambdaPrinter(String prefix, String suffix) {
    Consumer<String> lambdaPrinter = s -> System.out.print(prefix + s + suffix);
    return lambdaPrinter;
  }

  /**
   * Print each message with a given printer Please use `getLambdaPrinter` method
   * <p>
   * e.g. String[] messages = {"a","b", "c"}; lse.printMessages(messages,
   * lse.getLambdaPrinter("msg:", "!") );
   * <p>
   * sout: msg:a! msg:b! msg:c!
   *
   * @param messages
   * @param printer
   */
  @Override
  public void printMessages(String[] messages, Consumer<String> printer) {
    createStrStream(messages).forEach(printer);
  }

  /**
   * Print all odd number from a intStream. Please use `createIntStream` and `getLambdaPrinter`
   * methods
   * <p>
   * e.g. lse.printOdd(lse.createIntStream(0, 5), lse.getLambdaPrinter("odd number:", "!"));
   * <p>
   * sout: odd number:1! odd number:3! odd number:5!
   *
   * @param intStream
   * @param printer
   */
  @Override
  public void printOdd(IntStream intStream, Consumer<String> printer) {
    getOdd(intStream).mapToObj(Integer::toString).forEach(printer);
  }

  /**
   * Square each number from the input. Please write two solutions and compare difference - using
   * flatMap
   *
   * @param ints
   * @return
   */
  @Override
  public Stream<Integer> flatNestedInt(Stream<List<Integer>> ints) {
    return ints.flatMap(list -> list.stream()).map(i -> i * i);
  }
}
