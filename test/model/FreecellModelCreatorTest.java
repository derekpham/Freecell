package model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests for the freecell game.
 */
public final class FreecellModelCreatorTest {
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

  // an alternate to the above test without using instanceof
  // multi-card move should throw an exception here
  @Test(expected = IllegalArgumentException.class)
  public void testSingleMove1() {
    FreecellOperations singleMove = FreecellModelCreator
            .create(FreecellModelCreator.GameType.SINGLEMOVE);
    singleMove.startGame(singleMove.getDeck(), 5, 4, false);
    singleMove.move(PileType.CASCADE, 2, 9, PileType.OPEN, 1);
    singleMove.move(PileType.CASCADE, 0, 9, PileType.CASCADE, 2);
  }

  @Test
  public void testMultiMove() {
    FreecellOperations multi = FreecellModelCreator
            .create(FreecellModelCreator.GameType.MULTIMOVE);
    assertTrue(multi instanceof MultiMoveModel);
  }

  // an alternate to the above test that doesn't use instanceof
  // multi-card move should be successful here
  @Test
  public void testMultiMove1() {
    FreecellOperations multiMove = FreecellModelCreator
            .create(FreecellModelCreator.GameType.MULTIMOVE);
    multiMove.startGame(multiMove.getDeck(), 5, 4, false);
    multiMove.move(PileType.CASCADE, 2, 9, PileType.OPEN, 1);
    multiMove.move(PileType.CASCADE, 0, 9, PileType.CASCADE, 2);
    assertEquals(multiMove.getGameState(), "F1:\n"
            + "F2:\n"
            + "F3:\n"
            + "F4:\n"
            + "O1:\n"
            + "O2: 2♥\n"
            + "O3:\n"
            + "O4:\n"
            + "C1: K♣, Q♦, J♠, 10♥, 8♣, 7♦, 6♠, 5♥, 3♣\n"
            + "C2: K♦, Q♠, J♥, 9♣, 8♦, 7♠, 6♥, 4♣, 3♦, 2♠, A♥\n"
            + "C3: K♠, Q♥, 10♣, 9♦, 8♠, 7♥, 5♣, 4♦, 3♠, 2♦, A♠\n"
            + "C4: K♥, J♣, 10♦, 9♠, 8♥, 6♣, 5♦, 4♠, 3♥, A♣\n"
            + "C5: Q♣, J♦, 10♠, 9♥, 7♣, 6♦, 5♠, 4♥, 2♣, A♦");
  }
}