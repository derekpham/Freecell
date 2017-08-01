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
 * {@link FreecellModel} class and the {@link MultiMoveModel} class.
 */
public abstract class AbstractFreecellModelTest {
  protected abstract FreecellOperations factory();

  FreecellOperations game1 = factory();
  FreecellOperations game2 = factory();

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
      invalidDeck.add(new Card(Kind.TWO, Suite.CLUB));
    }
    game1.startGame(invalidDeck, 5, 2, true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameInvalidDeck3() {
    game1.startGame(Arrays.asList(new Card[] {new Card(Kind.ACE, Suite.SPADE)}), 5, 2, false);
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
    for (int cardIdx = 12; cardIdx >= 0; cardIdx -= 1) {
      for (int pileIdx = 0; pileIdx < 4; pileIdx += 1) {
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
    assertTrue(game1.getGameState().indexOf("F1") != -1);
    assertTrue(game1.getGameState().indexOf("F2") != -1);
    assertTrue(game1.getGameState().indexOf("F3") != -1);
    assertTrue(game1.getGameState().indexOf("F4") != -1);
    assertTrue(game1.getGameState().indexOf("F5") == -1);
    assertTrue(game1.getGameState().indexOf("O1") != -1);
    assertTrue(game1.getGameState().indexOf("O2") == -1);
    assertTrue(game1.getGameState().indexOf("C1") != -1);
    assertTrue(game1.getGameState().indexOf("C2") != -1);
    assertTrue(game1.getGameState().indexOf("C3") != -1);
    assertTrue(game1.getGameState().indexOf("C4") != -1);
    assertTrue(game1.getGameState().indexOf("C5") == -1);

    game1.startGame(game1.getDeck(), 7, 4, false);
    for (int i = 1; i <= 7; i += 1) {
      assertTrue(game1.getGameState().indexOf("C" + i) != -1);
    }
    for (int i = 8; i < 10; i += 1) {
      assertTrue(game1.getGameState().indexOf("C" + i) == -1);
    }
    for (int i = 1; i <= 4; i += 1) {
      assertTrue(game1.getGameState().indexOf("O" + i) != -1);
    }
    for (int i = 5; i < 10; i += 1) {
      assertTrue(game1.getGameState().indexOf("O" + i) == -1);
    }
    assertTrue(game1.getGameState().indexOf("O-1") == -1);
    assertTrue(game1.getGameState().indexOf("C-2") == -1);

    try {
      game1.startGame(null, 4, 2, true);
    } catch (IllegalArgumentException io) {
      assertEquals(game1.getGameState(), "");
    }
  }

  public static final class SingleMoveTest extends AbstractFreecellModelTest {
    @Override
    protected FreecellOperations factory() {
      return new FreecellModel();
    }
  }

  public static final class MultiMoveTest extends AbstractFreecellModelTest {
    @Override
    protected FreecellOperations factory() {
      return new MultiMoveModel();
    }
  }
}