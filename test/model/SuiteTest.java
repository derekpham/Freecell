package model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Include unit tests for the Suite enum.
 */
public final class SuiteTest {
  @Test
  public void testToString() throws Exception {
    assertEquals(Suite.CLUB.toString(), "♣");
    assertEquals(Suite.DIAMOND.toString(), "♦");
    assertEquals(Suite.HEART.toString(), "♥");
    assertEquals(Suite.SPADE.toString(), "♠");
  }
}