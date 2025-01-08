package ca.jrvs.practice.codingChallenge;

public class BruteForceExample {
  public static void main(String[] args) {
    int[] nums = {3, 1, 4, 1, 5, 9};
    int max = findMax(nums);
    System.out.println("The maximum number is: " + max);

  }

  // Brute force method to find the maximum number in the array
  public static int findMax(int[] nums) {
    int max = nums[0];  // Initialize max with the first element
    for (int i = 1; i < nums.length; i++) {
      if (nums[i] > max) {
        max = nums[i];  // Update max if current element is larger
      }
    }
    return max;
  }
}
