package ca.jrvs.practice.testing;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class SimpleCalculator_Test {
  private SimpleCalculator calculator;

  @Before
  public void setup() {
    calculator = new SimpleCalculatorImp();  // Initialize the calculator
  }

  @Test
  public void add() {
    int expected = 2;
    int actual = calculator.add(1, 1);
    assertEquals(expected, actual);
  }

  @Test
  public void subtract() {
    int expected = 1;
    int actual = calculator.subtract(3, 2);
    assertEquals(expected, actual);
  }

  @Test
  public void multiply() {
    int expected = 25;
    int actual = calculator.multiply(5, 5);
    assertEquals(expected, actual);
  }

  @Test
  public void divide() {
    double expected = 2.00;
    double actual = calculator.divide(10,5);
    double delta = 1e-15;
    assertEquals(expected, actual, delta);
  }
}