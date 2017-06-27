/* CHANGE LOG
- 06/02/17 - Added in order to specifically test for FreecellModel not being able to do
             multi cards move at a time.
 */

package cs3500.hw02;

import org.junit.Test;

/**
 * This class is a test class specifically for the FreecellModel implementation of the
 * FreecellOperations interface. It tests that the model should not be able to do multi-card moves.
 */
public final class MultiMoveTest {
  FreecellOperations<Card> game1 = new FreecellModel();

  @Test(expected = IllegalArgumentException.class)
  public void testMoveFailInvalidIdx7() {
    game1.startGame(game1.getDeck(), 4, 4, false);
    game1.move(PileType.CASCADE, 2, 4, PileType.OPEN, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveFailInvalidIdx8() {
    game1.startGame(game1.getDeck(), 4, 4, false);
    game1.move(PileType.CASCADE, 2, 0, PileType.OPEN, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveFailWrongBuild() {
    game1.startGame(game1.getDeck(), 4, 4, false);
    game1.move(PileType.CASCADE, 1, 2, PileType.CASCADE, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveFailWrongBuild1() {
    game1.startGame(game1.getDeck(), 5, 4, false);
    game1.move(PileType.CASCADE, 3, 2, PileType.CASCADE, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveFailRightBuildRightMove() {
    game1.startGame(game1.getDeck(), 5, 4, false);
    game1.move(PileType.CASCADE, 2, 9, PileType.OPEN, 0);
    game1.move(PileType.CASCADE, 0, 9, PileType.CASCADE, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveFailRightBuildWrongMove() {
    game1.startGame(game1.getDeck(), 5, 4, false);
    game1.move(PileType.CASCADE, 2, 9, PileType.OPEN, 0);
    game1.move(PileType.CASCADE, 0, 9, PileType.OPEN, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveFailRightBuildWrongMove1() {
    game1.startGame(game1.getDeck(), 5, 4, false);
    game1.move(PileType.CASCADE, 2, 9, PileType.OPEN, 0);
    game1.move(PileType.CASCADE, 0, 9, PileType.FOUNDATION, 2);
  }
}