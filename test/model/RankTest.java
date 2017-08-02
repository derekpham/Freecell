package model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Includes units tests for the Rank enum.
 */
public final class RankTest {
  @Test
  public void testToStringEdge() throws Exception {
    assertEquals(Rank.ACE.toString(), "A");
    assertEquals(Rank.JACK.toString(), "J");
    assertEquals(Rank.QUEEN.toString(), "Q");
    assertEquals(Rank.KING.toString(), "K");
  }

  @Test
  public void testToStringNorm() throws Exception {
    for (Rank rank : Rank.values()) {
      if (rank.getValue() > 1 && rank.getValue() < 11) {
        assertEquals(rank.toString(), "" + rank.getValue());
      }
    }
  }

  @Test
  public void testGetValue() throws Exception {
    int curr = 1;

    for (Rank rank : Rank.values()) {
      assertEquals(rank.getValue(), curr);
      curr++;
    }
  }

  @Test
  public void testValToKind() throws Exception {
    for (Rank rank : Rank.values()) {
      assertEquals(Rank.intToRank(rank.getValue()), rank);
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void valToKindFail1() throws Exception {
    Rank.intToRank(0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void valToKindFail2() throws Exception {
    Rank.intToRank(14);
  }
}