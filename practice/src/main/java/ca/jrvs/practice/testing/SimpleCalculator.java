package ca.jrvs.practice.testing;

public interface SimpleCalculator {

  /**
   *
   * @param x
   * @param y
   * @return sum
   */
  int add(int x, int y);

  /**
   *
   * @param x
   * @param y
   * @return difference
   */
  int subtract(int x, int y);

  /**
   *
   * @param x
   * @param y
   * @return product
   */
  int multiply(int x, int y);

  /**
   *
   * @param x
   * @param y
   * @return quotient
   */
  double divide(int x, int y);
}
