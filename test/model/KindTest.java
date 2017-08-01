package model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Includes units tests for the Kind enum.
 */
public final class KindTest {
  @Test
  public void testToStringEdge() throws Exception {
    assertEquals(Kind.ACE.toString(), "A");
    assertEquals(Kind.JACK.toString(), "J");
    assertEquals(Kind.QUEEN.toString(), "Q");
    assertEquals(Kind.KING.toString(), "K");
  }

  @Test
  public void testToStringNorm() throws Exception {
    for (Kind kind : Kind.values()) {
      if (kind.getValue() > 1 && kind.getValue() < 11) {
        assertEquals(kind.toString(), "" + kind.getValue());
      }
    }
  }

  @Test
  public void testGetValue() throws Exception {
    int curr = 1;

    for (Kind kind : Kind.values()) {
      assertEquals(kind.getValue(), curr);
      curr++;
    }
  }

  @Test
  public void testValToKind() throws Exception {
    for (Kind kind : Kind.values()) {
      assertEquals(Kind.valToKind(kind.getValue()), kind);
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void valToKindFail1() throws Exception {
    Kind.valToKind(0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void valToKindFail2() throws Exception {
    Kind.valToKind(14);
  }
}