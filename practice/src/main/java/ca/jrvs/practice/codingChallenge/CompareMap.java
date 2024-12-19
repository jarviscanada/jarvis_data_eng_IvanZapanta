package ca.jrvs.practice.codingChallenge;

import java.util.HashMap;
import java.util.Map;

public class CompareMap {

  /**
   * this method returns true if maps are equal
   * otherwise, returns false
   *
   * @param m1 map 1
   * @param m2 map 2
   * @return boolean if maps are equal
   * @param <K> keys
   * @param <V> values
   */
  public static  <K, V> boolean compareMaps(Map<K,V> m1, Map<K,V> m2){
    return m1.equals(m2);
  }

  public static void main(String[] args) {

    HashMap<String, String> mapOne = new HashMap<String, String>();
    mapOne.put("apple", "banana");

    HashMap<String, String> mapTwo = new HashMap<String, String>();
//    mapTwo.put("hi", "hello"); //false
    mapTwo.put("apple", "banana"); //true

    System.out.println(compareMaps(mapOne, mapTwo));
  }
}
