/* CHANGE LOG
- 06/02/17 - Removed unused imports.
           - Renamed to AbstractFreecellModelTest to test for both classes
           that implement FreecellOperations.
           - Added SingleMoveTest and MultiMoveTest classes inside
           - Changed tests that test moving cards from foundation piles to throw exceptions
           instead of seeing them as valid moves
           - Moved tests that test for exceptions thrown when multi-card move is attempted
           to MultiMoveTest.java because these tests are only specifically true for FreecellModel
           only
 */

package model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/** Tests for the {@link FreecellOperations} interface. Tests should pass for both the
 * {@link FreecellModel} class and the {@link MultiMoveModel} class. This test class does not have
 * the tests for the move method.
 */
public abstract class FreecellOperationsNonMoveTest {
  protected abstract FreecellOperations factory();

  private final FreecellOperations game1 = factory();
  private final FreecellOperations game2 = factory();

  @Test
  public void testGetDeckSize() {
    List<Card> deck1 = game1.getDeck();
    assertEquals(deck1.size(), 52);

    List<Card> deck2 = game1.getDeck();
    assertEquals(deck2.size(), 52);

    List<Card> deck3 = game2.getDeck();
    assertEquals(deck3.size(), 52);
  }

  @Test
  public void testGetDeckNoDups() {
    List<Card> deck1 = game1.getDeck();
    for (int cardIdx = 0; cardIdx < deck1.size(); cardIdx += 1) {
      Card cur = deck1.get(cardIdx);
      for (int iterIdx = cardIdx + 1; iterIdx < deck1.size(); iterIdx += 1) {
        assertFalse(cur.equals(deck1.get(iterIdx)));
      }
    }

    List<Card> deck2 = game2.getDeck();
    for (int cardIdx = 0; cardIdx < deck2.size(); cardIdx += 1) {
      Card cur = deck2.get(cardIdx);
      for (int iterIdx = cardIdx + 1; iterIdx < deck2.size(); iterIdx += 1) {
        assertFalse(cur.equals(deck2.get(iterIdx)));
      }
    }
  }

  @Test
  public void testGetDeckValidCards() {
    List<Card> deck1 = game1.getDeck();
    for (Card card : deck1) {
      assertFalse(card.getValue() < 1);
      assertFalse(card.getValue() > 13);
      assertFalse(card.getSuite() == null);
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameInvalidDeck() {
    game1.startGame(null, 5, 2, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameInvalidDeck1() {
    game1.startGame(new ArrayList<>(), 5, 2, true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameInvalidDeck2() {
    List<Card> invalidDeck = new ArrayList<>();
    for (int i = 0; i < 52; i += 1) {
      invalidDeck.add(new Card(Rank.TWO, Suite.CLUB));
    }
    game1.startGame(invalidDeck, 5, 2, true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameInvalidDeck3() {
    game1.startGame(Arrays.asList(new Card(Rank.ACE, Suite.SPADE)), 5, 2, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameInvalidCascades() {
    game1.startGame(game1.getDeck(), 3, 5, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameInvalidCascades1() {
    game1.startGame(game1.getDeck(), 1, 5, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameInvalidCascades2() {
    game1.startGame(game1.getDeck(), 0, 5, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameInvalidCascades3() {
    game1.startGame(game1.getDeck(), -2, 5, true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameInvalidOpens() {
    game1.startGame(game1.getDeck(), 4, 0, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameInvalidOpens1() {
    game1.startGame(game1.getDeck(), 4, -1, true);
  }

  @Test
  public void testStartGame() {
    game1.startGame(game1.getDeck(), 4, 4, false);
    assertEquals(game1.getGameState(),
            "F1:\n"
                    + "F2:\n"
                    + "F3:\n"
                    + "F4:\n"
                    + "O1:\n"
                    + "O2:\n"
                    + "O3:\n"
                    + "O4:\n"
                    + "C1: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n"
                    + "C2: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n"
                    + "C3: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n"
                    + "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥");
  }

  @Test
  public void testStartGameMultiples() {
    game1.startGame(game1.getDeck(), 4, 4, false);
    game1.startGame(game1.getDeck(), 4, 4, false);
    game1.startGame(game1.getDeck(), 4, 4, false);
    assertEquals(game1.getGameState(),
            "F1:\n"
                    + "F2:\n"
                    + "F3:\n"
                    + "F4:\n"
                    + "O1:\n"
                    + "O2:\n"
                    + "O3:\n"
                    + "O4:\n"
                    + "C1: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n"
                    + "C2: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n"
                    + "C3: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n"
                    + "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥");
  }

  /*
  06/02/17 - Added to test that the model shuffles when it is asked to
   */
  @Test
  public void testShuffle() {
    game1.startGame(game1.getDeck(), 4, 1, false);
    String noShuffle = game1.getGameState();
    game1.startGame(game1.getDeck(), 4, 1, true);
    String shuffle = game1.getGameState();

    assertNotEquals(noShuffle, shuffle);
  }

  @Test
  public void isGameOver() {
    assertFalse(game1.isGameOver());

    game1.startGame(game1.getDeck(), 7, 5, false);
    assertFalse(game1.isGameOver());

    game1.move(PileType.CASCADE, 0, 7, PileType.OPEN, 2);
    game1.move(PileType.OPEN, 2, 0, PileType.FOUNDATION, 0);
    assertFalse(game1.isGameOver());
  }

  @Test
  public void isGameOverTrue() {
    game1.startGame(game1.getDeck(), 4, 5, false);
    for (int cardIdx = 12; cardIdx >= 0; cardIdx--) {
      for (int pileIdx = 0; pileIdx < 4; pileIdx++) {
        game1.move(PileType.CASCADE, pileIdx, cardIdx, PileType.FOUNDATION, pileIdx);
      }
    }
    assertTrue(game1.isGameOver());
  }

  /*
  06/02/17 - Changed name
             Added test that checks returns empty string if startGame() throws exception
   */
  @Test
  public void getGameState() {
    assertEquals(game1.getGameState(), "");

    game1.startGame(game1.getDeck(), 4, 4, false);
    assertEquals(game1.getGameState(),
            "F1:\n"
                    + "F2:\n"
                    + "F3:\n"
                    + "F4:\n"
                    + "O1:\n"
                    + "O2:\n"
                    + "O3:\n"
                    + "O4:\n"
                    + "C1: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n"
                    + "C2: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n"
                    + "C3: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n"
                    + "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥");
    game1.startGame(game1.getDeck(), 4, 1, true);
    assertTrue(game1.getGameState().contains("F1"));
    assertTrue(game1.getGameState().contains("F2"));
    assertTrue(game1.getGameState().contains("F3"));
    assertTrue(game1.getGameState().contains("F4"));
    assertTrue(!game1.getGameState().contains("F5"));
    assertTrue(game1.getGameState().contains("O1"));
    assertTrue(!game1.getGameState().contains("O2"));
    assertTrue(game1.getGameState().contains("C1"));
    assertTrue(game1.getGameState().contains("C2"));
    assertTrue(game1.getGameState().contains("C3"));
    assertTrue(game1.getGameState().contains("C4"));
    assertTrue(!game1.getGameState().contains("C5"));

    game1.startGame(game1.getDeck(), 7, 4, false);
    for (int i = 1; i <= 7; i += 1) {
      assertTrue(game1.getGameState().contains("C" + i));
    }
    for (int i = 8; i < 10; i += 1) {
      assertTrue(!game1.getGameState().contains("C" + i));
    }
    for (int i = 1; i <= 4; i += 1) {
      assertTrue(game1.getGameState().contains("O" + i));
    }
    for (int i = 5; i < 10; i += 1) {
      assertTrue(!("O" + i).contains(game1.getGameState()));
    }
    assertTrue(!game1.getGameState().contains("O-1"));
    assertTrue(!game1.getGameState().contains("C-2"));

    try {
      game1.startGame(null, 4, 2, true);
    } catch (IllegalArgumentException io) {
      assertEquals(game1.getGameState(), "");
    }
  }

  public static final class SingleMoveTest extends FreecellOperationsNonMoveTest {
    @Override
    protected FreecellOperations factory() {
      return new FreecellModel();
    }
  }


  public static final class MultiMoveTest extends FreecellOperationsNonMoveTest {
    @Override
    protected FreecellOperations factory() {
      return new MultiMoveModel();
    }
  }
}