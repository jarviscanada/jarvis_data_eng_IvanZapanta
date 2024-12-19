package ca.jrvs.practice.codingChallenge;

import java.util.HashMap;
import java.util.Map;

public class Fibonacci {

  /**
   *
   * recursive call - method calls itself over and over till it reached its base case.
   * base case - is a condition inside recursive method, when that condition is met it stops the recursive call. (to prevent recursing infinitely)
   * base case = 0, 1
   *
   * fibonacci formula : f(n) = f(n - 1) + f(n - 2)
   *
   * memoization - storing function results to avoid redoing calculations, making programs faster.
   *
   * @param args
   */

  // memoization map to store fibonacci numbers
  private static Map<Integer, Long> memo = new HashMap<>();

  public static long fibonacci(int n) {

    // base case
    if (n <= 1) {
      return n;
    }

    // check if result is already computed
    if (memo.containsKey(n)) {
      return memo.get(n);
    }

    // recursive call, fibonacci formula
    long nthFibonacciNumber = (fibonacci(n - 1) + fibonacci(n - 2));
    // store the result in memo
    memo.put(n, nthFibonacciNumber);

    return nthFibonacciNumber;
  }

}
