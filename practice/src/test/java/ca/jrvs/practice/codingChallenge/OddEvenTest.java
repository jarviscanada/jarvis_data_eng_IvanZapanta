package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Test;

public class OddEvenTest {

  @Test
  public void testEvenNumber() {
    assertEquals("even", OddEven.oddEvenMod(4));
  }

  @Test
  public void testOddNumber() {
    assertEquals("odd", OddEven.oddEvenMod(1));
  }

  @Test
  public void testNegativeNumber() {
    assertEquals("even", OddEven.oddEvenMod(-2));
    assertEquals("odd", OddEven.oddEvenMod(-1));
  }

  @Test
  public void testZero() {
    assertEquals("even", OddEven.oddEvenMod(0));
  }

}