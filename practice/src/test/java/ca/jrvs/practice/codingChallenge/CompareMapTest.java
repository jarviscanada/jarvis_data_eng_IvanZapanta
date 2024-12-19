package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class CompareMapTest {

  @Test
  public void testCompareMapsEqual() {
    Map<String, Integer> m1 = new HashMap<>();
    m1.put("one", 1);
    m1.put("two", 2);

    Map<String, Integer> m2 = new HashMap<>();
    m2.put("one", 1);
    m2.put("two", 2);

    assertTrue(CompareMap.compareMaps(m1, m2));
  }

  @Test
  public void testCompareMapsDifferent() {
    Map<String, Integer> m1 = new HashMap<>();
    m1.put("one", 1);
    m1.put("two", 2);

    Map<String, Integer> m2 = new HashMap<>();
    m2.put("one", 1);
    m2.put("three", 3);

    assertFalse(CompareMap.compareMaps(m1, m2));
  }

  @Test
  public void testCompareMapsEmpty() {
    Map<String, Integer> m1 = new HashMap<>();

    Map<String, Integer> m2 = new HashMap<>();

    assertTrue(CompareMap.compareMaps(m1, m2));
  }

  @Test
  public void testCompareMapsOneEmpty() {
    Map<String, Integer> m1 = new HashMap<>();
    m1.put("one", 1);
    m1.put("two", 2);

    Map<String, Integer> m2 = new HashMap<>();

    assertFalse(CompareMap.compareMaps(m1, m2));
  }

  @Test
  public void testCompareMapsNull() {
    Map<String, Integer> m1 = new HashMap<>();
    m1.put("one", 1);

    assertFalse(CompareMap.compareMaps(m1, null));
  }
}