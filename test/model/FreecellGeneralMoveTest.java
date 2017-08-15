package model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Includes tests for the move method of the interface. This test class does not include tests
 * multi-move moves.
 */
public abstract class FreecellGeneralMoveTest {
  protected abstract FreecellOperations factory();

  private final FreecellOperations game1 = factory();

  @Test(expected = IllegalStateException.class)
  public void testMoveFailGameNotStart() {
    game1.move(PileType.FOUNDATION, 3, 2, PileType.CASCADE, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveFailNullArg1() {
    game1.startGame(game1.getDeck(), 7, 7, true);
    game1.move(null, 3, 2, PileType.CASCADE, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveFailNullArg2() {
    game1.startGame(game1.getDeck(), 5, 5, false);
    game1.move(PileType.CASCADE, 3, 4, null, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveFailInvalidIdx1() {
    game1.startGame(game1.getDeck(), 5, 5, false);
    game1.move(PileType.CASCADE, -1, 4, PileType.CASCADE, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveFailInvalidIdx2() {
    game1.startGame(game1.getDeck(), 5, 5, false);
    game1.move(PileType.CASCADE, 20, 4, PileType.CASCADE, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveFailInvalidIdx3() {
    game1.startGame(game1.getDeck(), 5, 5, false);
    game1.move(PileType.CASCADE, 2, -1, PileType.CASCADE, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveFailInvalidIdx4() {
    game1.startGame(game1.getDeck(), 5, 5, false);
    game1.move(PileType.CASCADE, 2, 20, PileType.CASCADE, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveFailInvalidIdx5() {
    game1.startGame(game1.getDeck(), 5, 5, false);
    game1.move(PileType.CASCADE, 2, 3, PileType.CASCADE, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveFailInvalidIdx6() {
    game1.startGame(game1.getDeck(), 5, 5, false);
    game1.move(PileType.CASCADE, 2, -1, PileType.CASCADE, 20);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveInvalidCascadeToFoundation() {
    game1.startGame(game1.getDeck(), 5, 5, false);
    game1.move(PileType.CASCADE, 2, 9, PileType.FOUNDATION, 0);
  }

  @Test
  public void testMoveCascadeToFoundation() {
    game1.startGame(game1.getDeck(), 5, 5, false);
    game1.move(PileType.CASCADE, 1, 10, PileType.FOUNDATION, 1);
    game1.move(PileType.CASCADE, 2, 9, PileType.FOUNDATION, 1);
    game1.move(PileType.CASCADE, 0, 10, PileType.FOUNDATION, 0);
    assertEquals(game1.getGameState(),
            "F1: A♠\n"
                    + "F2: A♥, 2♥\n"
                    + "F3:\n"
                    + "F4:\n"
                    + "O1:\n"
                    + "O2:\n"
                    + "O3:\n"
                    + "O4:\n"
                    + "O5:\n"
                    + "C1: K♣, Q♦, J♠, 10♥, 8♣, 7♦, 6♠, 5♥, 3♣, 2♦\n"
                    + "C2: K♦, Q♠, J♥, 9♣, 8♦, 7♠, 6♥, 4♣, 3♦, 2♠\n"
                    + "C3: K♠, Q♥, 10♣, 9♦, 8♠, 7♥, 5♣, 4♦, 3♠\n"
                    + "C4: K♥, J♣, 10♦, 9♠, 8♥, 6♣, 5♦, 4♠, 3♥, A♣\n"
                    + "C5: Q♣, J♦, 10♠, 9♥, 7♣, 6♦, 5♠, 4♥, 2♣, A♦");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidCascadeToFoundation() {
    game1.startGame(game1.getDeck(), 5, 5, false);
    game1.move(PileType.CASCADE, 0, 10, PileType.FOUNDATION, 0);
    game1.move(PileType.CASCADE, 2, 9, PileType.FOUNDATION, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidCascadeToCascade() {
    game1.startGame(game1.getDeck(), 5, 5, false);
    game1.move(PileType.CASCADE, 0, 10, PileType.CASCADE, 1);
  }

  @Test
  public void testCascadeToCascade() {
    game1.startGame(game1.getDeck(), 5, 5, false);
    game1.move(PileType.CASCADE, 0, 10, PileType.CASCADE, 2);
    assertEquals(game1.getGameState(),
            "F1:\n"
                    + "F2:\n"
                    + "F3:\n"
                    + "F4:\n"
                    + "O1:\n"
                    + "O2:\n"
                    + "O3:\n"
                    + "O4:\n"
                    + "O5:\n"
                    + "C1: K♣, Q♦, J♠, 10♥, 8♣, 7♦, 6♠, 5♥, 3♣, 2♦\n"
                    + "C2: K♦, Q♠, J♥, 9♣, 8♦, 7♠, 6♥, 4♣, 3♦, 2♠, A♥\n"
                    + "C3: K♠, Q♥, 10♣, 9♦, 8♠, 7♥, 5♣, 4♦, 3♠, 2♥, A♠\n"
                    + "C4: K♥, J♣, 10♦, 9♠, 8♥, 6♣, 5♦, 4♠, 3♥, A♣\n"
                    + "C5: Q♣, J♦, 10♠, 9♥, 7♣, 6♦, 5♠, 4♥, 2♣, A♦");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCascadeToCascadeInvalid1() {
    game1.startGame(game1.getDeck(), 5, 5, false);
    game1.move(PileType.CASCADE, 0, 10, PileType.CASCADE, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCascadeToCascadeInvalid2() {
    game1.startGame(game1.getDeck(), 5, 5, false);
    game1.move(PileType.CASCADE, 1, 10, PileType.CASCADE, 2);
  }

  @Test
  public void testCascadeToOpen1() {
    game1.startGame(game1.getDeck(), 5, 5, false);
    game1.move(PileType.CASCADE, 0, 10, PileType.OPEN, 2);
    assertEquals(game1.getGameState(),
            "F1:\n"
                    + "F2:\n"
                    + "F3:\n"
                    + "F4:\n"
                    + "O1:\n"
                    + "O2:\n"
                    + "O3: A♠\n"
                    + "O4:\n"
                    + "O5:\n"
                    + "C1: K♣, Q♦, J♠, 10♥, 8♣, 7♦, 6♠, 5♥, 3♣, 2♦\n"
                    + "C2: K♦, Q♠, J♥, 9♣, 8♦, 7♠, 6♥, 4♣, 3♦, 2♠, A♥\n"
                    + "C3: K♠, Q♥, 10♣, 9♦, 8♠, 7♥, 5♣, 4♦, 3♠, 2♥\n"
                    + "C4: K♥, J♣, 10♦, 9♠, 8♥, 6♣, 5♦, 4♠, 3♥, A♣\n"
                    + "C5: Q♣, J♦, 10♠, 9♥, 7♣, 6♦, 5♠, 4♥, 2♣, A♦"
    );
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCascadeToOpenInvalid() {
    game1.startGame(game1.getDeck(), 5, 5, false);
    game1.move(PileType.CASCADE, 0, 10, PileType.OPEN, 2);
    game1.move(PileType.CASCADE, 1, 10, PileType.OPEN, 2);
  }

  @Test
  public void testOpenToCascade() {
    game1.startGame(game1.getDeck(), 7, 5, false);
    game1.move(PileType.CASCADE, 0, 7, PileType.OPEN, 2);
    game1.move(PileType.OPEN, 2, 0, PileType.CASCADE, 4);
    assertEquals(game1.getGameState(),
            "F1:\n"
                    + "F2:\n"
                    + "F3:\n"
                    + "F4:\n"
                    + "O1:\n"
                    + "O2:\n"
                    + "O3:\n"
                    + "O4:\n"
                    + "O5:\n"
                    + "C1: K♣, Q♥, 10♠, 8♦, 6♣, 5♥, 3♠\n"
                    + "C2: K♦, J♣, 10♥, 8♠, 6♦, 4♣, 3♥, A♠\n"
                    + "C3: K♠, J♦, 9♣, 8♥, 6♠, 4♦, 2♣, A♥\n"
                    + "C4: K♥, J♠, 9♦, 7♣, 6♥, 4♠, 2♦\n"
                    + "C5: Q♣, J♥, 9♠, 7♦, 5♣, 4♥, 2♠, A♦\n"
                    + "C6: Q♦, 10♣, 9♥, 7♠, 5♦, 3♣, 2♥\n"
                    + "C7: Q♠, 10♦, 8♣, 7♥, 5♠, 3♦, A♣");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOpenToCascadeInvalid() {
    game1.startGame(game1.getDeck(), 7, 5, false);
    game1.move(PileType.CASCADE, 0, 7, PileType.OPEN, 2);
    game1.move(PileType.OPEN, 2, 0, PileType.CASCADE, 3);
  }

  @Test
  public void testOpenToOpen() {
    game1.startGame(game1.getDeck(), 7, 5, false);
    game1.move(PileType.CASCADE, 0, 7, PileType.OPEN, 2);
    game1.move(PileType.CASCADE, 6, 6, PileType.OPEN, 1);
    game1.move(PileType.OPEN, 2, 0, PileType.OPEN, 0);
    assertEquals(game1.getGameState(),
            "F1:\n"
                    + "F2:\n"
                    + "F3:\n"
                    + "F4:\n"
                    + "O1: A♦\n"
                    + "O2: A♣\n"
                    + "O3:\n"
                    + "O4:\n"
                    + "O5:\n"
                    + "C1: K♣, Q♥, 10♠, 8♦, 6♣, 5♥, 3♠\n"
                    + "C2: K♦, J♣, 10♥, 8♠, 6♦, 4♣, 3♥, A♠\n"
                    + "C3: K♠, J♦, 9♣, 8♥, 6♠, 4♦, 2♣, A♥\n"
                    + "C4: K♥, J♠, 9♦, 7♣, 6♥, 4♠, 2♦\n"
                    + "C5: Q♣, J♥, 9♠, 7♦, 5♣, 4♥, 2♠\n"
                    + "C6: Q♦, 10♣, 9♥, 7♠, 5♦, 3♣, 2♥\n"
                    + "C7: Q♠, 10♦, 8♣, 7♥, 5♠, 3♦");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOpenToOpenInvalid() {
    game1.startGame(game1.getDeck(), 7, 5, false);
    game1.move(PileType.CASCADE, 0, 7, PileType.OPEN, 2);
    game1.move(PileType.CASCADE, 6, 6, PileType.OPEN, 1);
    game1.move(PileType.OPEN, 2, 0, PileType.OPEN, 0);
    game1.move(PileType.OPEN, 0, 0, PileType.OPEN, 1);
  }

  @Test
  public void testOpenToFoundation() {
    game1.startGame(game1.getDeck(), 7, 5, false);
    game1.move(PileType.CASCADE, 0, 7, PileType.OPEN, 2);
    game1.move(PileType.OPEN, 2, 0, PileType.FOUNDATION, 0);
    assertEquals(game1.getGameState(),
            "F1: A♦\n"
                    + "F2:\n"
                    + "F3:\n"
                    + "F4:\n"
                    + "O1:\n"
                    + "O2:\n"
                    + "O3:\n"
                    + "O4:\n"
                    + "O5:\n"
                    + "C1: K♣, Q♥, 10♠, 8♦, 6♣, 5♥, 3♠\n"
                    + "C2: K♦, J♣, 10♥, 8♠, 6♦, 4♣, 3♥, A♠\n"
                    + "C3: K♠, J♦, 9♣, 8♥, 6♠, 4♦, 2♣, A♥\n"
                    + "C4: K♥, J♠, 9♦, 7♣, 6♥, 4♠, 2♦\n"
                    + "C5: Q♣, J♥, 9♠, 7♦, 5♣, 4♥, 2♠\n"
                    + "C6: Q♦, 10♣, 9♥, 7♠, 5♦, 3♣, 2♥\n"
                    + "C7: Q♠, 10♦, 8♣, 7♥, 5♠, 3♦, A♣");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOpenToFoundationInvalid() {
    game1.startGame(game1.getDeck(), 7, 5, false);
    game1.move(PileType.CASCADE, 0, 7, PileType.OPEN, 2);
    game1.move(PileType.CASCADE, 1, 7, PileType.FOUNDATION, 0);
    game1.move(PileType.OPEN, 2, 0, PileType.FOUNDATION, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFoundationToFoundationInvalid() {
    game1.startGame(game1.getDeck(), 7, 5, false);
    game1.move(PileType.CASCADE, 0, 7, PileType.FOUNDATION, 0);
    game1.move(PileType.FOUNDATION, 0, 0, PileType.FOUNDATION, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFoundationToFoundation() {
    game1.startGame(game1.getDeck(), 7, 5, false);
    game1.move(PileType.CASCADE, 0, 7, PileType.FOUNDATION, 0);
    game1.move(PileType.FOUNDATION, 0, 0, PileType.FOUNDATION, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFoundationToCascadeInvalid() {
    game1.startGame(game1.getDeck(), 7, 5, false);
    game1.move(PileType.CASCADE, 0, 7, PileType.FOUNDATION, 0);
    game1.move(PileType.FOUNDATION, 0, 0, PileType.CASCADE, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFoundationToCascade() {
    game1.startGame(game1.getDeck(), 7, 5, false);
    game1.move(PileType.CASCADE, 0, 7, PileType.FOUNDATION, 0);
    game1.move(PileType.FOUNDATION, 0, 0, PileType.CASCADE, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFoundationToOpenInvalid() {
    game1.startGame(game1.getDeck(), 7, 5, false);
    game1.move(PileType.CASCADE, 0, 7, PileType.FOUNDATION, 0);
    game1.move(PileType.CASCADE, 0, 6, PileType.OPEN, 0);
    game1.move(PileType.FOUNDATION, 0, 0, PileType.OPEN, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFoundationToOpen() {
    game1.startGame(game1.getDeck(), 7, 5, false);
    game1.move(PileType.CASCADE, 0, 7, PileType.FOUNDATION, 0);
    game1.move(PileType.FOUNDATION, 0, 0, PileType.OPEN, 0);
  }

  @Test
  public void testMoveLotsOf() {
    game1.startGame(game1.getDeck(), 7, 5, false);
    game1.move(PileType.CASCADE, 0, 7, PileType.OPEN, 0);
    game1.move(PileType.CASCADE, 1, 7, PileType.OPEN, 1);
    game1.move(PileType.CASCADE, 2, 7, PileType.OPEN, 2);
    game1.move(PileType.CASCADE, 6, 6, PileType.OPEN, 3);
    game1.move(PileType.OPEN, 0, 0, PileType.FOUNDATION, 0);
    game1.move(PileType.OPEN, 1, 0, PileType.FOUNDATION, 1);
    game1.move(PileType.OPEN, 2, 0, PileType.FOUNDATION, 2);
    game1.move(PileType.OPEN, 3, 0, PileType.FOUNDATION, 3);
    game1.move(PileType.CASCADE, 2, 6, PileType.CASCADE, 1);
    game1.move(PileType.CASCADE, 6, 5, PileType.OPEN, 0);
    game1.move(PileType.CASCADE, 5, 6, PileType.OPEN, 1);
    assertEquals(game1.getGameState(),
            "F1: A♦\n"
                    + "F2: A♠\n"
                    + "F3: A♥\n"
                    + "F4: A♣\n"
                    + "O1: 3♦\n"
                    + "O2: 2♥\n"
                    + "O3:\n"
                    + "O4:\n"
                    + "O5:\n"
                    + "C1: K♣, Q♥, 10♠, 8♦, 6♣, 5♥, 3♠\n"
                    + "C2: K♦, J♣, 10♥, 8♠, 6♦, 4♣, 3♥, 2♣\n"
                    + "C3: K♠, J♦, 9♣, 8♥, 6♠, 4♦\n"
                    + "C4: K♥, J♠, 9♦, 7♣, 6♥, 4♠, 2♦\n"
                    + "C5: Q♣, J♥, 9♠, 7♦, 5♣, 4♥, 2♠\n"
                    + "C6: Q♦, 10♣, 9♥, 7♠, 5♦, 3♣\n"
                    + "C7: Q♠, 10♦, 8♣, 7♥, 5♠");
  }

  public static final class SingleMoveTest extends FreecellGeneralMoveTest {
    @Override
    protected FreecellOperations factory() {
      return FreecellModelCreator.create(FreecellModelCreator.GameType.SINGLEMOVE);
    }
  }

  public static final class MultiMoveTest extends FreecellGeneralMoveTest {
    @Override
    protected FreecellOperations factory() {
      return FreecellModelCreator.create(FreecellModelCreator.GameType.MULTIMOVE);
    }
  }
}
