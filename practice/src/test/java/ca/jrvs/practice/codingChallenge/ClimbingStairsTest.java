package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Test;

public class ClimbingStairsTest {

  @Test
  public void testClimbStairsRecursive() {
    assertEquals(0, ClimbingStairs.climbStairsRecursive(0));
    assertEquals(1, ClimbingStairs.climbStairsRecursive(1));
    assertEquals(2, ClimbingStairs.climbStairsRecursive(2));
    assertEquals(3, ClimbingStairs.climbStairsRecursive(3));
    assertEquals(5, ClimbingStairs.climbStairsRecursive(4));
    assertEquals(8, ClimbingStairs.climbStairsRecursive(5));
  }

  @Test
  public void testClimbStairsDP() {
    assertEquals(0, ClimbingStairs.climbStairsDP(0));
    assertEquals(1, ClimbingStairs.climbStairsDP(1));
    assertEquals(2, ClimbingStairs.climbStairsDP(2));
    assertEquals(3, ClimbingStairs.climbStairsDP(3));
    assertEquals(5, ClimbingStairs.climbStairsDP(4));
    assertEquals(8, ClimbingStairs.climbStairsDP(5));
  }

  @Test
  public void testClimbStairsEdgeCase() {
    assertEquals(1, ClimbingStairs.climbStairsRecursive(1));
    assertEquals(1, ClimbingStairs.climbStairsDP(1));
    assertEquals(0, ClimbingStairs.climbStairsRecursive(0));
    assertEquals(0, ClimbingStairs.climbStairsDP(0));
  }
}