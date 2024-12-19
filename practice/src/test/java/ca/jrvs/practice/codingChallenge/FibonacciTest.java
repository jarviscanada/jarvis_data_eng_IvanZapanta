package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Test;

public class FibonacciTest {

  private Fibonacci fibonacci;

  @Test
  public void testFibonacciBaseCase() throws Exception {
    assertEquals(0, Fibonacci.fibonacci(0));
    assertEquals(1, Fibonacci.fibonacci(1));
  }

  @Test
  public void testFibonacciPositiveNumbers() {
    assertEquals(2, Fibonacci.fibonacci(3));
    assertEquals(3, Fibonacci.fibonacci(4));
    assertEquals(8, Fibonacci.fibonacci(6));
  }

  @Test
  public void testFibonacciMemoization() {
    long firstCall = Fibonacci.fibonacci(10);
    long secondCall = Fibonacci.fibonacci(10);

    assertEquals(firstCall,secondCall);
  }
}