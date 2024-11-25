package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class OddEvenTest {
  private OddEven oddEven;

  @Before
  public void setUp() throws Exception {
    oddEven = new OddEven();
  }

  @Test
  public void oddEvenMod() {
    assertEquals("odd", oddEven.oddEvenMod(3));
    assertEquals("even", oddEven.oddEvenMod(2));
    assertEquals("even", oddEven.oddEvenMod(-4));
  }

  @Test
  public void oddEvenBit() {
    assertNull(oddEven.oddEvenBit(2));
  }


}