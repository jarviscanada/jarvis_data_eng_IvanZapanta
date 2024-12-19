package ca.jrvs.practice.testing;

public class SimpleCalculatorImp implements SimpleCalculator{

  @Override
  public int add(int x, int y) {
    int sum = x + y;
    return sum;
  }

  @Override
  public int subtract(int x, int y) {
    int difference = x - y;
    return difference;
  }

  @Override
  public int multiply(int x, int y) {
    int product = x * y;
    return product;
  }

  @Override
  public double divide(int x, int y) {
    if (y == 0) {
      throw new ArithmeticException("Cannot divide by zero");
    }
    return (double) x / y;
  }
}
