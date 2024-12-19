package ca.jrvs.practice.codingChallenge;


/**
 * Recursion
 * time complexity is exponential O(2^n)
 * because of repeated subproblems.
 *
 * space complexity is O (n) due to the function call stack.
 *
 * Dynamic programming
 * time complexity is O(n) since we only calculate
 * each value once and store the intermediate results.
 *
 * space complexity is O (1), since we only store a few variables for the last two results.
 */
public class ClimbingStairs {

  // recursive approach
  public static int climbStairsRecursive(int n) {
    if (n <= 2) {
      return n;
    }

    return climbStairsRecursive(n - 1) + climbStairsRecursive(n - 2);
  }

  // dynamic programming approach (bottom-up)
  public static int climbStairsDP(int n) {
    if (n <= 1) {
      return n;
    }

    // Using two variables to store the previous two results
    int prev1 = 1, prev2 = 1;
    int current = 0;

    for (int i = 2; i <= n; i++) {
      current = prev1 + prev2;
      prev2 = prev1;
      prev1 = current;
    }

    return current;
  }
}
