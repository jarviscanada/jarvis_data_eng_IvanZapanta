package ca.jrvs.practice.codingChallenge;

import java.util.HashMap;
import java.util.Map;

public class TwoSum {


  public int[] twoSumLoop(int[] nums, int target) {
    for (int i = 0; i < nums.length; i++) {

      for (int j = i + 1; j < nums.length; j++) {
        if(nums[i] + nums[j] == target){
          return new int[]{i,j};
        }
      }
    }
    return nums;
  }

  public int[] twoSumMap(int[] nums, int target) {

    Map<Integer, Integer> complements = new HashMap<>();
    for (int i = 0; i < nums.length; i++) {
      // gets the complement of index
      Integer complementIndex = complements.get(nums[i]);
      if (complementIndex != null){
        return new int[]{i, complementIndex};
      }
      complements.put(target - nums[i], i);
    }
    return nums;
  }
}
