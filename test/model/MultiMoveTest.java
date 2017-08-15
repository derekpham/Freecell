package model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test class for the MultiMoveModel class specifically. Inside this class are test methods
 * that test specifically moves that involve moving multiple cards.
 */
/**
public final class MultiMoveTest {
  FreecellOperations model = new MultiMoveModel();

  @Test(expected = IllegalArgumentException.class)
  public void moveInvalidBuild1() {
    model.startGame(model.getDeck(), 4, 1, false);
    model.move(PileType.CASCADE, 0, 11, PileType.CASCADE, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveInvalidBuild2() {
    model.startGame(model.getDeck(), 4, 1, false);
    model.move(PileType.CASCADE, 0, 11, PileType.OPEN, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveInvalidBuild3() {
    model.startGame(model.getDeck(), 4, 1, false);
    model.move(PileType.CASCADE, 0, 11, PileType.FOUNDATION, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveValidBuildInvalidMove() {
    model.startGame(model.getDeck(), 5, 1, false);
    model.move(PileType.CASCADE, 0, 9, PileType.CASCADE, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveValidBuildInvalidMove1() {
    model.startGame(model.getDeck(), 5, 1, false);
    model.move(PileType.CASCADE, 0, 9, PileType.OPEN, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveValidBuildInvalidMove2() {
    model.startGame(model.getDeck(), 5, 1, false);
    model.move(PileType.CASCADE, 0, 9, PileType.FOUNDATION, 1);
  }

  @Test
  public void moveValidBuildValidMove() {
    model.startGame(model.getDeck(), 5, 4, false);
    model.move(PileType.CASCADE, 2, 9, PileType.OPEN, 0);
    model.move(PileType.CASCADE, 0, 9, PileType.CASCADE, 2);
    assertEquals(model.getGameState(), "F1:\n"
            + "F2:\n"
            + "F3:\n"
            + "F4:\n"
            + "O1: 2♥\n"
            + "O2:\n"
            + "O3:\n"
            + "O4:\n"
            + "C1: K♣, Q♦, J♠, 10♥, 8♣, 7♦, 6♠, 5♥, 3♣\n"
            + "C2: K♦, Q♠, J♥, 9♣, 8♦, 7♠, 6♥, 4♣, 3♦, 2♠, A♥\n"
            + "C3: K♠, Q♥, 10♣, 9♦, 8♠, 7♥, 5♣, 4♦, 3♠, 2♦, A♠\n"
            + "C4: K♥, J♣, 10♦, 9♠, 8♥, 6♣, 5♦, 4♠, 3♥, A♣\n"
            + "C5: Q♣, J♦, 10♠, 9♥, 7♣, 6♦, 5♠, 4♥, 2♣, A♦");
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveValidBuildNotEnoughFree() {
    model.startGame(model.getDeck(), 5, 1, false);
    model.move(PileType.CASCADE, 2, 9, PileType.OPEN, 0);
    model.move(PileType.CASCADE, 0, 9, PileType.CASCADE, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveValidBuildsInvalidMove1() {
    model.startGame(model.getDeck(), 5, 4, false);
    assertEquals(model.getGameState(), "F1:\n"
            + "F2:\n"
            + "F3:\n"
            + "F4:\n"
            + "O1:\n"
            + "O2:\n"
            + "O3:\n"
            + "O4:\n"
            + "C1: K♣, Q♦, J♠, 10♥, 8♣, 7♦, 6♠, 5♥, 3♣, 2♦, A♠\n"
            + "C2: K♦, Q♠, J♥, 9♣, 8♦, 7♠, 6♥, 4♣, 3♦, 2♠, A♥\n"
            + "C3: K♠, Q♥, 10♣, 9♦, 8♠, 7♥, 5♣, 4♦, 3♠, 2♥\n"
            + "C4: K♥, J♣, 10♦, 9♠, 8♥, 6♣, 5♦, 4♠, 3♥, A♣\n"
            + "C5: Q♣, J♦, 10♠, 9♥, 7♣, 6♦, 5♠, 4♥, 2♣, A♦");

    model.move(PileType.CASCADE, 0, 6, PileType.OPEN, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveValidBuildsInvalidMove2() {
    model.startGame(model.getDeck(), 5, 4, false);
    assertEquals(model.getGameState(), "F1:\n"
            + "F2:\n"
            + "F3:\n"
            + "F4:\n"
            + "O1:\n"
            + "O2:\n"
            + "O3:\n"
            + "O4:\n"
            + "C1: K♣, Q♦, J♠, 10♥, 8♣, 7♦, 6♠, 5♥, 3♣, 2♦, A♠\n"
            + "C2: K♦, Q♠, J♥, 9♣, 8♦, 7♠, 6♥, 4♣, 3♦, 2♠, A♥\n"
            + "C3: K♠, Q♥, 10♣, 9♦, 8♠, 7♥, 5♣, 4♦, 3♠, 2♥\n"
            + "C4: K♥, J♣, 10♦, 9♠, 8♥, 6♣, 5♦, 4♠, 3♥, A♣\n"
            + "C5: Q♣, J♦, 10♠, 9♥, 7♣, 6♦, 5♠, 4♥, 2♣, A♦");

    model.move(PileType.CASCADE, 0, 6, PileType.FOUNDATION, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveValidBuildsInvalidMove3() {
    model.startGame(model.getDeck(), 5, 4, false);
    model.move(PileType.CASCADE, 4, 9, PileType.FOUNDATION, 0);

    model.move(PileType.CASCADE, 0, 6, PileType.FOUNDATION, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveValidBuildInvalidMove4() {
    model.startGame(model.getDeck(), 5, 4, false);
    model.move(PileType.CASCADE, 0, 10, PileType.OPEN, 2);
    model.move(PileType.CASCADE, 1, 7, PileType.OPEN, 2);
  }

  @Test
  public void testMoveMultipleToEmptyCascade() {
    model.startGame(model.getDeck(), 4, 10, false);
    for (int i = 12; i >= 0; i -= 1) {
      model.move(PileType.CASCADE,0, i, PileType.FOUNDATION, 0);
    }
    assertEquals(model.getGameState(), "F1: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣\n"
            + "F2:\n"
            + "F3:\n"
            + "F4:\n"
            + "O1:\n"
            + "O2:\n"
            + "O3:\n"
            + "O4:\n"
            + "O5:\n"
            + "O6:\n"
            + "O7:\n"
            + "O8:\n"
            + "O9:\n"
            + "O10:\n"
            + "C1:\n"
            + "C2: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n"
            + "C3: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n"
            + "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥");

    model.move(PileType.CASCADE, 1, 12, PileType.CASCADE, 0);
    assertEquals(model.getGameState(), "F1: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣\n"
            + "F2:\n"
            + "F3:\n"
            + "F4:\n"
            + "O1:\n"
            + "O2:\n"
            + "O3:\n"
            + "O4:\n"
            + "O5:\n"
            + "O6:\n"
            + "O7:\n"
            + "O8:\n"
            + "O9:\n"
            + "O10:\n"
            + "C1: A♦\n"
            + "C2: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦\n"
            + "C3: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n"
            + "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥");
  }

  @Test
  public void testMoveCascadeCascade() {
    model.startGame(model.getDeck(), 7, 10, false);
    model.move(PileType.CASCADE, 1, 7, PileType.FOUNDATION, 0);
    model.move(PileType.CASCADE, 2, 6, PileType.CASCADE, 1);
    model.move(PileType.CASCADE, 0, 7, PileType.FOUNDATION, 1);
    model.move(PileType.CASCADE, 3, 6, PileType.FOUNDATION, 1);
    model.move(PileType.CASCADE, 1, 6, PileType.CASCADE, 3);
    model.move(PileType.CASCADE, 0, 6, PileType.OPEN, 1);
    model.move(PileType.CASCADE, 3, 5, PileType.CASCADE, 0);
    assertEquals(model.getGameState(), "F1: A♠\n"
            + "F2: A♦, 2♦\n"
            + "F3:\n"
            + "F4:\n"
            + "O1:\n"
            + "O2: 3♠\n"
            + "O3:\n"
            + "O4:\n"
            + "O5:\n"
            + "O6:\n"
            + "O7:\n"
            + "O8:\n"
            + "O9:\n"
            + "O10:\n"
            + "C1: K♣, Q♥, 10♠, 8♦, 6♣, 5♥, 4♠, 3♥, 2♣, A♥\n"
            + "C2: K♦, J♣, 10♥, 8♠, 6♦, 4♣\n"
            + "C3: K♠, J♦, 9♣, 8♥, 6♠, 4♦\n"
            + "C4: K♥, J♠, 9♦, 7♣, 6♥\n"
            + "C5: Q♣, J♥, 9♠, 7♦, 5♣, 4♥, 2♠\n"
            + "C6: Q♦, 10♣, 9♥, 7♠, 5♦, 3♣, 2♥\n"
            + "C7: Q♠, 10♦, 8♣, 7♥, 5♠, 3♦, A♣");
  }

  // same test as above, exception different number of open piles
  @Test(expected = IllegalArgumentException.class)
  public void moveCascadeCascadeNotEnoughFree() {
    model.startGame(model.getDeck(), 7, 2, false);
    model.move(PileType.CASCADE, 1, 7, PileType.FOUNDATION, 0);
    model.move(PileType.CASCADE, 2, 6, PileType.CASCADE, 1);
    model.move(PileType.CASCADE, 0, 7, PileType.FOUNDATION, 1);
    model.move(PileType.CASCADE, 3, 6, PileType.FOUNDATION, 1);
    model.move(PileType.CASCADE, 1, 6, PileType.CASCADE, 3);
    model.move(PileType.CASCADE, 0, 6, PileType.OPEN, 1);
    model.move(PileType.CASCADE, 3, 5, PileType.CASCADE, 0);
  }

  // same test as above, except trying to move less, should still fail
  @Test(expected = IllegalArgumentException.class)
  public void notEnoughFree1() {
    model.startGame(model.getDeck(), 7, 2, false);
    model.move(PileType.CASCADE, 1, 7, PileType.FOUNDATION, 0);
    model.move(PileType.CASCADE, 2, 6, PileType.CASCADE, 1);
    model.move(PileType.CASCADE, 0, 7, PileType.FOUNDATION, 1);
    model.move(PileType.CASCADE, 3, 6, PileType.FOUNDATION, 1);
    model.move(PileType.CASCADE, 1, 6, PileType.CASCADE, 3);
    model.move(PileType.CASCADE, 0, 6, PileType.OPEN, 1);
    model.move(PileType.CASCADE, 3, 6, PileType.CASCADE, 1);
  }

  @Test
  public void barelyEnoughFree() {
    model.startGame(model.getDeck(), 7, 2, false);
    model.move(PileType.CASCADE, 1, 7, PileType.FOUNDATION, 0);
    model.move(PileType.CASCADE, 2, 6, PileType.CASCADE, 1);
    model.move(PileType.CASCADE, 0, 7, PileType.FOUNDATION, 1);
    model.move(PileType.CASCADE, 3, 6, PileType.FOUNDATION, 1);
    model.move(PileType.CASCADE, 1, 6, PileType.CASCADE, 3);
    model.move(PileType.CASCADE, 0, 6, PileType.OPEN, 1);
    model.move(PileType.CASCADE, 6, 6, PileType.FOUNDATION, 2);
    model.move(PileType.CASCADE, 3, 7, PileType.CASCADE, 6);
    assertEquals(model.getGameState(), "F1: A♠\n"
            + "F2: A♦, 2♦\n"
            + "F3: A♣\n"
            + "F4:\n"
            + "O1:\n"
            + "O2: 3♠\n"
            + "C1: K♣, Q♥, 10♠, 8♦, 6♣, 5♥\n"
            + "C2: K♦, J♣, 10♥, 8♠, 6♦, 4♣\n"
            + "C3: K♠, J♦, 9♣, 8♥, 6♠, 4♦\n"
            + "C4: K♥, J♠, 9♦, 7♣, 6♥, 4♠, 3♥\n"
            + "C5: Q♣, J♥, 9♠, 7♦, 5♣, 4♥, 2♠\n"
            + "C6: Q♦, 10♣, 9♥, 7♠, 5♦, 3♣, 2♥\n"
            + "C7: Q♠, 10♦, 8♣, 7♥, 5♠, 3♦, 2♣, A♥");
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveToFreeCascadeFail() {
    model.startGame(model.getDeck(), 5, 11, false);
    for (int i = 10; i >= 0; i -= 1) {
      model.move(PileType.CASCADE, 0, i, PileType.OPEN, i);
    }
    assertEquals(model.getGameState(), "F1:\n"
            + "F2:\n"
            + "F3:\n"
            + "F4:\n"
            + "O1: K♣\n"
            + "O2: Q♦\n"
            + "O3: J♠\n"
            + "O4: 10♥\n"
            + "O5: 8♣\n"
            + "O6: 7♦\n"
            + "O7: 6♠\n"
            + "O8: 5♥\n"
            + "O9: 3♣\n"
            + "O10: 2♦\n"
            + "O11: A♠\n"
            + "C1:\n"
            + "C2: K♦, Q♠, J♥, 9♣, 8♦, 7♠, 6♥, 4♣, 3♦, 2♠, A♥\n"
            + "C3: K♠, Q♥, 10♣, 9♦, 8♠, 7♥, 5♣, 4♦, 3♠, 2♥\n"
            + "C4: K♥, J♣, 10♦, 9♠, 8♥, 6♣, 5♦, 4♠, 3♥, A♣\n"
            + "C5: Q♣, J♦, 10♠, 9♥, 7♣, 6♦, 5♠, 4♥, 2♣, A♦");
    model.move(PileType.CASCADE, 1, 8, PileType.CASCADE, 0);
  }

  // same test as above, except trying to move 1 less card
  @Test
  public void moveToFreeCascadeSuccess() {
    model.startGame(model.getDeck(), 5, 11, false);
    for (int i = 10; i >= 0; i -= 1) {
      model.move(PileType.CASCADE, 0, i, PileType.OPEN, i);
    }
    model.move(PileType.CASCADE, 1, 9, PileType.CASCADE, 0);
    assertEquals(model.getGameState(), "F1:\n"
            + "F2:\n"
            + "F3:\n"
            + "F4:\n"
            + "O1: K♣\n"
            + "O2: Q♦\n"
            + "O3: J♠\n"
            + "O4: 10♥\n"
            + "O5: 8♣\n"
            + "O6: 7♦\n"
            + "O7: 6♠\n"
            + "O8: 5♥\n"
            + "O9: 3♣\n"
            + "O10: 2♦\n"
            + "O11: A♠\n"
            + "C1: 2♠, A♥\n"
            + "C2: K♦, Q♠, J♥, 9♣, 8♦, 7♠, 6♥, 4♣, 3♦\n"
            + "C3: K♠, Q♥, 10♣, 9♦, 8♠, 7♥, 5♣, 4♦, 3♠, 2♥\n"
            + "C4: K♥, J♣, 10♦, 9♠, 8♥, 6♣, 5♦, 4♠, 3♥, A♣\n"
            + "C5: Q♣, J♦, 10♠, 9♥, 7♣, 6♦, 5♠, 4♥, 2♣, A♦");
  }
}**/