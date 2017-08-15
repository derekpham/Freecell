package model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests for the freecell game.
 */
public final class FreecellModelCreatorTest {
  // TODO maybe a better way is to test whether valid multi-move throws exception or not
  @Test(expected = IllegalArgumentException.class)
  public void testPassNullFail() {
    FreecellModelCreator.create(null);
  }

  @Test
  public void testSingleMove() {
    FreecellOperations singleMove = FreecellModelCreator
            .create(FreecellModelCreator.GameType.SINGLEMOVE);
    assertTrue(singleMove instanceof FreecellModel);
  }

  @Test
  public void testMultiMove() {
    FreecellOperations multi = FreecellModelCreator
            .create(FreecellModelCreator.GameType.MULTIMOVE);
    assertTrue(multi instanceof MultiMoveModel);
  }
}