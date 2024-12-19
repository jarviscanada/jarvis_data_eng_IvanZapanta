package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

public class TwoSumTest {

  @Test
  public void testTwoSumLoopValidPair() {
    TwoSum twoSum = new TwoSum();
    int[] nums = {2, 7, 11, 15};
    int target = 9;

    int[] result = twoSum.twoSumLoop(nums, target);

    assertArrayEquals(new int[]{0, 1}, result);
  }

  @Test
  public void testTwoSumMapValidPair() {
    TwoSum twoSum = new TwoSum();
    int[] nums = {2, 7, 11, 15};
    int target = 9;

    int[] result = twoSum.twoSumMap(nums, target);

    assertArrayEquals(new int[]{1, 0}, result);
  }

  @Test
  public void testTwoSumLoopNoSolution() {
    TwoSum twoSum = new TwoSum();
    int[] nums = {1, 2, 3};
    int target = 6;

    int[] result = twoSum.twoSumLoop(nums, target);

    assertArrayEquals(nums, result);
  }

  @Test
  public void testTwoSumMapNoSolution() {
    TwoSum twoSum = new TwoSum();
    int[] nums = {1, 2, 3};
    int target = 6;

    int[] result = twoSum.twoSumMap(nums, target);
    
    assertArrayEquals(nums, result);
  }

}