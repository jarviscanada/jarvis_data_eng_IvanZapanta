package ca.jrvs.practice.codingChallenge;

/**
 * https://www.notion.so/jarvisdev/Sample-Check-if-a-number-is-even-or-odd-15ccc128f9de818892fbcfbcce09fdb9?pvs=4
 */
public class OddEven {

  /**
   * this method uses modulo to check if a number is odd or even.
   *
   * @param i - number to be assessed
   * @return string odd or even
   */
  public static String oddEvenMod(int i) {
    return i % 2 == 0 ? "even" : "odd";
  }

}
