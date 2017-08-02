package controller;

import org.junit.Test;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import model.Card;
import model.FreecellModel;
import model.FreecellOperations;
import model.Rank;
import model.Suite;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Tests for the FreecellController class.
 */
public class FreecellControllerTest {
  FreecellOperations model = new FreecellModel();
  Appendable output = new StringBuilder();
  IFreecellController<Card> controller =
          new FreecellController(new StringReader("k"), output);

  @Test(expected = IllegalStateException.class)
  public void constructorFail1() {
    new FreecellController(null, new StringBuilder());
  }

  @Test(expected = IllegalStateException.class)
  public void constructorFail2() {
    new FreecellController(new StringReader(""), null);
  }

  @Test(expected = IllegalStateException.class)
  public void constructorFail3() {
    new FreecellController(null, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void playGameFailNullDeck() {
    controller.playGame(null, model, 5, 3, true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void playGameFailNullModel() {
    controller.playGame(model.getDeck(), null, 5, 3, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void playGameFailNull() {
    controller.playGame(null, null, 5, 3, true);
  }

  @Test
  public void testStartGameFailBadDeck() {
    controller.playGame(new ArrayList<>(), model, 5, 3, false);
    assertEquals(output.toString(), "Could not start game.");
  }

  @Test
  public void testStartGameFailHasDupsDeck() {
    List<Card> cards = new LinkedList<>();
    for (int i = 0; i < cards.size(); i += 1) {
      cards.add(new Card(Rank.ACE, Suite.CLUB));
    }

    controller.playGame(cards, model, 5, 3, true);
    assertEquals(output.toString(), "Could not start game.");
  }

  @Test
  public void testStartGameFailDeckFullOfNulls() {
    List<Card> cards = new ArrayList<>(52);
    for (int i = 0; i < 52; i += 1) {
      cards.add(null);
    }

    controller.playGame(cards, model, 5, 3, false);
    assertEquals(output.toString(), "Could not start game.");
  }

  @Test
  public void testStartGameFailBadNumCascades() {
    controller.playGame(model.getDeck(), model, 3, 4, true);
    assertEquals(output.toString(), "Could not start game.");

    controller.playGame(model.getDeck(), model, 0, 5, false);
    assertEquals(output.toString(), "Could not start game.Could not start game.");

    controller.playGame(model.getDeck(), model, -3, 4, false);
    assertEquals(output.toString(), "Could not start game."
            + "Could not start game."
            + "Could not start game.");
  }

  @Test
  public void testStartGameFailBadNumOpens() {
    controller.playGame(model.getDeck(), model, 4, 0, true);
    assertEquals(output.toString(), "Could not start game.");

    controller.playGame(model.getDeck(), model, 5, -1, false);
    assertEquals(output.toString(), "Could not start game."
            + "Could not start game.");
  }

  @Test
  public void testStartGameShuffles() {
    StringBuilder output1 = new StringBuilder();
    IFreecellController<Card> controller1 =
            new FreecellController(new StringReader("C1 q"), output1);
    controller1.playGame(model.getDeck(), model, 4, 1, false);

    StringBuilder output2 = new StringBuilder();
    IFreecellController<Card> controller2 =
            new FreecellController(new StringReader("C2 Q"), output2);
    controller2.playGame(model.getDeck(), model, 4, 1, true);

    assertNotEquals(output1.toString(), output2.toString());
    assertNotEquals(output1.indexOf("\nGame quit prematurely."), -1);
    assertNotEquals(output2.indexOf("\nGame quit prematurely"), -1);
    assertNotEquals(output1.indexOf("O1"), -1);
    assertNotEquals(output1.indexOf("F4"), -1);
    assertNotEquals(output1.indexOf("C3"), -1);
    assertNotEquals(output2.indexOf("O1"), -1);
    assertNotEquals(output2.indexOf("F4"), -1);
    assertNotEquals(output2.indexOf("C3"), -1);
  }

  @Test
  public void testStartGameNoInput() {
    IFreecellController<Card> controller = new FreecellController(new StringReader(""), output);
    controller.playGame(model.getDeck(), model, 4, 1, false);
    assertEquals(output.toString(), "F1:\n"
            + "F2:\n"
            + "F3:\n"
            + "F4:\n"
            + "O1:\n"
            + "C1: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n"
            + "C2: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n"
            + "C3: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n"
            + "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥");
  }

  // helper to get the number of times a string appears within a string
  private int getCount(String source, String find) {
    int count = 0;
    int prev = source.indexOf(find);

    while (prev != -1) {
      count += 1;
      prev = source.indexOf(find, prev + 1);
    }

    return count;
  }

  @Test
  public void testQuitPremature() {
    IFreecellController<Card> controller = new FreecellController(new StringReader("q"), output);
    controller.playGame(model.getDeck(), model, 4, 1, true);
    int idx = output.toString().indexOf("\nGame quit prematurely.");
    assertNotEquals(idx, -1);
    assertEquals(output.toString().substring(idx), "\nGame quit prematurely.");

    IFreecellController<Card> controller1 = new FreecellController(new StringReader("Q"), output);
    controller1.playGame(model.getDeck(), model, 4, 1, false);
    int idx1 = output.toString().lastIndexOf("\nGame quit prematurely.");
    assertNotEquals(idx, idx1);
    assertEquals(output.toString().substring(idx1), "\nGame quit prematurely.");
  }

  @Test
  public void testQuitPremature1() {
    IFreecellController<Card> controller =
            new FreecellController(new StringReader("fdsf 3323 fdsQfdsf 432423 qqqq QQQ"), output);
    controller.playGame(model.getDeck(), model, 4, 1, true);
    int idx = output.toString().indexOf("\nGame quit prematurely.");
    assertNotEquals(idx, -1);
    assertEquals(output.toString().substring(idx), "\nGame quit prematurely.");
    assertEquals(getCount(output.toString(), "\nGame quit prematurely."), 1);
  }

  @Test
  public void testQuitPremature2() {
    IFreecellController<Card> controller = new FreecellController(
            new StringReader("fsd !!!! 2332 dfsdfEEEEEqqqq 3243 234 12, 1 qq QQ fsQ"),
            output);
    controller.playGame(model.getDeck(), model, 4, 1, true);
    int idx = output.toString().indexOf("\nGame quit prematurely.");
    assertNotEquals(idx, -1);
    assertEquals(output.toString().substring(idx), "\nGame quit prematurely.");
    assertEquals(getCount(output.toString(), "\nGame quit prematurely."), 1);
  }

  @Test
  public void testMultipleQuits() {
    IFreecellController<Card> controller =
            new FreecellController(new StringReader("q Q qqq a4qw"), output);
    controller.playGame(model.getDeck(), model, 4, 1, true);
    assertEquals(getCount(output.toString(), "\nGame quit prematurely."), 1);
  }

  @Test
  public void testValidMoves() {
    IFreecellController<Card> controller =
            new FreecellController(new StringReader("C1 13 O1 C2 13 F2 O1 1 F1 q"), output);
    assertEquals(output.toString(), "");
    controller.playGame(model.getDeck(), model, 4, 1, false);
    assertEquals(output.toString(), "F1:\n"
            + "F2:\n"
            + "F3:\n"
            + "F4:\n"
            + "O1:\n"
            + "C1: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n"
            + "C2: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n"
            + "C3: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n"
            + "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n"
            + "F1:\n"
            + "F2:\n"
            + "F3:\n"
            + "F4:\n"
            + "O1: A♣\n"
            + "C1: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣\n"
            + "C2: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n"
            + "C3: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n"
            + "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n"
            + "F1:\n"
            + "F2: A♦\n"
            + "F3:\n"
            + "F4:\n"
            + "O1: A♣\n"
            + "C1: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣\n"
            + "C2: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦\n"
            + "C3: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n"
            + "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥"
            + "\nF1: A♣\n"
            + "F2: A♦\n"
            + "F3:\n"
            + "F4:\n"
            + "O1:\n"
            + "C1: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣\n"
            + "C2: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦\n"
            + "C3: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n"
            + "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n"
            + "Game quit prematurely.");
  }

  @Test
  public void testInvalidMoves() {
    IFreecellController<Card> controller =
            new FreecellController(new StringReader("C1 13 O1 C1 12 O1"), output);
    controller.playGame(model.getDeck(), model, 4, 1, false);
    assertEquals(output.toString(), "F1:\n"
            + "F2:\n"
            + "F3:\n"
            + "F4:\n"
            + "O1:\n"
            + "C1: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n"
            + "C2: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n"
            + "C3: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n"
            + "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n"
            + "F1:\n"
            + "F2:\n"
            + "F3:\n"
            + "F4:\n"
            + "O1: A♣\n"
            + "C1: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣\n"
            + "C2: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n"
            + "C3: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n"
            + "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n"
            + "Invalid move. Try again: Illegal move.");

    StringBuilder output1 = new StringBuilder();
    IFreecellController<Card> controller1 =
            new FreecellController(new StringReader("C1 13 F1 C1 12 F2"), output1);
    controller1.playGame(model.getDeck(), model, 4, 1, false);
    assertEquals(output1.toString(), "F1:\n"
            + "F2:\n"
            + "F3:\n"
            + "F4:\n"
            + "O1:\n"
            + "C1: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n"
            + "C2: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n"
            + "C3: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n"
            + "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n"
            + "F1: A♣\n"
            + "F2:\n"
            + "F3:\n"
            + "F4:\n"
            + "O1:\n"
            + "C1: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣\n"
            + "C2: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n"
            + "C3: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n"
            + "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n"
            + "Invalid move. Try again: Illegal move.");
  }

  @Test
  public void testSourceReinput1() {
    IFreecellController<Card> controller =
            new FreecellController(new StringReader("fdsjk C1"), output);
    controller.playGame(model.getDeck(), model, 4, 1, false);
    assertEquals(output.toString(), "F1:\n"
            + "F2:\n"
            + "F3:\n"
            + "F4:\n"
            + "O1:\n"
            + "C1: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n"
            + "C2: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n"
            + "C3: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n"
            + "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n"
            + "Invalid input for source pile. Try again.");

    StringBuilder output1 = new StringBuilder();
    IFreecellController<Card> controller1 =
            new FreecellController(new StringReader("C7fd! C102 123 F1231"), output1);
    controller1.playGame(model.getDeck(), model, 4, 2, false);
    assertEquals(output1.toString(), "F1:\n"
            + "F2:\n"
            + "F3:\n"
            + "F4:\n"
            + "O1:\n"
            + "O2:\n"
            + "C1: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n"
            + "C2: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n"
            + "C3: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n"
            + "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n"
            + "Invalid input for source pile. Try again.\n"
            + "Invalid move. Try again: Invalid pile number for cascade pile: 101");
  }

  @Test
  public void testSourceMultipleReinputs() {
    IFreecellController<Card> controller =
            new FreecellController(new StringReader("5 ## fds cfds c724 Ca! C-1 C0.5 C0"), output);
    controller.playGame(model.getDeck(), model, 4, 1, false);
    assertEquals(getCount(output.toString(), "\nInvalid input for source pile. Try again"), 8);
  }

  @Test
  public void testSourceMultipleReinputs1() {
    IFreecellController<Card> controller =
            new FreecellController(new StringReader("5o 5c 5C 5f 5F 5 "
                    + "## fds cfds c724 Ca! C-1 C0.5 F5"), output);
    controller.playGame(model.getDeck(), model, 4, 1, false);
    assertEquals(getCount(output.toString(), "\nInvalid input for source pile. Try again"), 13);
  }

  @Test
  public void testSourceMultipleReinputsQuit() {
    IFreecellController<Card> controller =
            new FreecellController(new StringReader("C-1.5 Ca! c7 C7, 5 C7: 3 C8,5 2q"), output);
    controller.playGame(model.getDeck(), model, 4, 1, true);
    int idx = output.toString().indexOf("\nGame quit prematurely.");
    assertNotEquals(idx, -1);
    assertEquals(output.toString().substring(idx), "\nGame quit prematurely.");
    assertEquals(getCount(output.toString(), "\nInvalid input for source pile. Try again."), 8);
  }

  @Test
  public void testSourceMultipleReinputsQuit1() {
    IFreecellController<Card> controller =
            new FreecellController(new StringReader("Ca! c7 C7, 5 C7: 3 C8,5 O3Q"), output);
    controller.playGame(model.getDeck(), model, 4, 1, true);
    int idx = output.toString().indexOf("\nGame quit prematurely.");
    assertNotEquals(idx, -1);
    assertEquals(output.toString().substring(idx), "\nGame quit prematurely.");
    assertEquals(getCount(output.toString(), "\nInvalid input for source pile. Try again."), 7);
  }

  @Test
  public void testCardIdxValidInput() {
    IFreecellController<Card> controller =
            new FreecellController(new StringReader("C1 5 q"), output);
    controller.playGame(model.getDeck(), model, 4, 1, false);
    assertEquals(output.toString(), "F1:\n"
            + "F2:\n"
            + "F3:\n"
            + "F4:\n"
            + "O1:\n"
            + "C1: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n"
            + "C2: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n"
            + "C3: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n"
            + "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n"
            + "Game quit prematurely.");
  }

  @Test
  public void testCardIdxReinput() {
    IFreecellController<Card> controller =
            new FreecellController(new StringReader("F100 C1 4"), output);
    controller.playGame(model.getDeck(), model, 4, 1, true);
    assertEquals(getCount(output.toString(), "\nInvalid card index. Try again"), 1);
  }

  /*
  06/02/17 - Added this test to test that playGame returns after hits q
   */
  @Test
  public void testCardIdxQuit() {
    IFreecellController<Card> controller =
            new FreecellController(new StringReader("F100 q 300 C1"), output);
    controller.playGame(model.getDeck(), model, 4, 1, false);
    assertEquals(output.toString(), "F1:\n"
            + "F2:\n"
            + "F3:\n"
            + "F4:\n"
            + "O1:\n"
            + "C1: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n"
            + "C2: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n"
            + "C3: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n"
            + "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n"
            + "Game quit prematurely.");
  }

  @Test
  public void testCardIdxMultipleReinputs() {
    IFreecellController<Card> controller =
            new FreecellController(new StringReader("C1 0.5 -4 asdf a! $$^ 1022"), output);
    controller.playGame(model.getDeck(), model, 4, 1, true);
    assertEquals(getCount(output.toString(), "\nInvalid card index. Try again."), 5);
  }

  @Test
  public void testCardIdxMultipleReinputsQuit() {
    IFreecellController<Card> controller =
            new FreecellController(new StringReader("O123 4! -4 0.5 c5 ^^ 4a C3 4a ??qA"), output);
    controller.playGame(model.getDeck(), model, 4, 1, true);
    assertEquals(getCount(output.toString(), "\nInvalid card index. Try again."), 8);
    int idx = output.toString().indexOf("\nGame quit prematurely.");
    assertNotEquals(idx, -1);
    assertEquals(output.toString().substring(idx), "\nGame quit prematurely.");
  }

  @Test
  public void testCardIdxMultipleReinputsQuit1() {
    IFreecellController<Card> controller =
            new FreecellController(new StringReader("C123 4! -4 0.5 c5 ^^ 4a C3 4a ?QQ!"), output);
    controller.playGame(model.getDeck(), model, 4, 1, true);
    assertEquals(getCount(output.toString(), "\nInvalid card index. Try again."), 8);
    int idx = output.toString().indexOf("\nGame quit prematurely.");
    assertNotEquals(idx, -1);
    assertEquals(output.toString().substring(idx), "\nGame quit prematurely.");
  }

  @Test
  public void testDestReinput() {
    IFreecellController<Card> controller =
            new FreecellController(new StringReader("C123 4 , C7"), output);
    controller.playGame(model.getDeck(), model, 4, 1, true);
    assertEquals(getCount(output.toString(), "\nInvalid input for destination pile."), 1);
  }

  @Test
  public void testDestMultipleReinput1() {
    IFreecellController<Card> controller =
            new FreecellController(new StringReader("F123 4 o5 o% f5 f$ 5f 5F O5"), output);
    controller.playGame(model.getDeck(), model, 4, 1, true);
    assertEquals(getCount(output.toString(), "\nInvalid input for destination pile."), 6);
  }

  @Test
  public void testDestMultipleReinput2() {
    IFreecellController<Card> controller =
            new FreecellController(new StringReader("O123 4 o5 o% f5 f$ 5f 5F F5"), output);
    controller.playGame(model.getDeck(), model, 4, 1, true);
    assertEquals(getCount(output.toString(), "\nInvalid input for destination pile."), 6);
  }

  @Test
  public void testDestQuits1() {
    IFreecellController<Card> controller =
            new FreecellController(new StringReader("F5 3 fdQ"), output);
    controller.playGame(model.getDeck(), model, 4, 1, true);
    int idx = output.toString().indexOf("\nGame quit prematurely.");
    assertNotEquals(idx, -1);
    assertEquals(output.toString().substring(idx), "\nGame quit prematurely.");
  }

  @Test
  public void testDestQuits2() {
    IFreecellController<Card> controller =
            new FreecellController(new StringReader("F5 3 fdq!!!!"), output);
    controller.playGame(model.getDeck(), model, 4, 1, true);
    int idx = output.toString().indexOf("\nGame quit prematurely.");
    assertNotEquals(idx, -1);
    assertEquals(output.toString().substring(idx), "\nGame quit prematurely.");
  }

  /*
  06/02/17 - Added to test that playGame() returns after hits q
   */
  @Test
  public void testDestQuits3() {
    IFreecellController<Card> controller =
            new FreecellController(new StringReader("F5 3 Q C1"), output);
    controller.playGame(model.getDeck(), model, 4, 1, false);
    assertEquals(output.toString(), "F1:\n"
            + "F2:\n"
            + "F3:\n"
            + "F4:\n"
            + "O1:\n"
            + "C1: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n"
            + "C2: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n"
            + "C3: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n"
            + "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n"
            + "Game quit prematurely.");
  }

  @Test
  public void testPlayGame1Input() {
    IFreecellController<Card> controller = new FreecellController(new StringReader("C1"), output);
    controller.playGame(model.getDeck(), model, 4, 1, false);
    assertEquals(output.toString(), "F1:\n"
            + "F2:\n"
            + "F3:\n"
            + "F4:\n"
            + "O1:\n"
            + "C1: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n"
            + "C2: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n"
            + "C3: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n"
            + "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥");
  }

  @Test
  public void testPlayGameValid1() {
    IFreecellController<Card> controller = new FreecellController(new StringReader("C1 13 F1 C2 q"),
            output);
    controller.playGame(model.getDeck(), model, 4, 1, false);
    assertEquals(output.toString(), "F1:\n"
            + "F2:\n"
            + "F3:\n"
            + "F4:\n"
            + "O1:\n"
            + "C1: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n"
            + "C2: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n"
            + "C3: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n"
            + "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥"
            + "\nF1: "
            + "A♣\n"
            + "F2:\n"
            + "F3:\n"
            + "F4:\n"
            + "O1:\n"
            + "C1: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣\n"
            + "C2: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n"
            + "C3: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n"
            + "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n"
            + "Game quit prematurely.");
  }

  @Test
  public void testLotsOfInputs() {
    Readable input = new StringReader("C1    \n -5 13 F1 o5 C1 12.5\n3.5 a 12 a O1 O1 1   F1 "
            + "[]C2qf");
    IFreecellController<Card> controller = new FreecellController(input, output);
    controller.playGame(model.getDeck(), model, 4, 1, false);
    assertEquals(output.toString(), "F1:\nF2:\nF3:\nF4:\nO1:\n"
            + "C1: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n"
            + "C2: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n"
            + "C3: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n"
            + "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n"
            + "Invalid card index. Try again.\n"
            + "F1: A♣\nF2:\nF3:\n"
            + "F4:\n"
            + "O1:\n"
            + "C1: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣\n"
            + "C2: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n"
            + "C3: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n"
            + "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n"
            + "Invalid input for source pile. Try again.\n"
            + "Invalid card index. Try again.\n"
            + "Invalid card index. Try again.\n"
            + "Invalid card index. Try again.\n"
            + "Invalid input for destination pile. Try again.\n"
            + "F1: A♣\n"
            + "F2:\n"
            + "F3:\n"
            + "F4:\n"
            + "O1: 2♣\n"
            + "C1: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣\n"
            + "C2: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n"
            + "C3: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n"
            + "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n"
            + "F1: A♣, 2♣\n"
            + "F2:\n"
            + "F3:\n"
            + "F4:\n"
            + "O1:\n"
            + "C1: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣\n"
            + "C2: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n"
            + "C3: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n"
            + "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n"
            + "Game quit prematurely.");
  }

  @Test
  public void testGameOver() {
    StringBuilder input = new StringBuilder();
    for (int i = 13; i >= 1; i -= 1) {
      for (int j = 1; j <= 4; j += 1) {
        input.append("C" + j + " " + i + " F" + j + " ");
      }
    }
    IFreecellController<Card> controller = new FreecellController(
            new StringReader(input.toString()), output);
    assertNotEquals(output.toString(), "\nGame over.", -1);
    controller.playGame(model.getDeck(), model, 4, 1, false);

    assertNotEquals(output.toString().indexOf("\nGame over."), -1);
    int i = output.toString().indexOf("\nGame over.");
    assertEquals(output.toString().substring(i), "\nGame over.");
    assertEquals(i, output.toString().lastIndexOf("\nGame over."));
  }

  @Test
  public void testGameOver1() {
    IFreecellController<Card> controller =
            new FreecellController(new StringReader("C1 13 O1 q"), output);
    assertEquals(output.toString(), "");
    controller.playGame(model.getDeck(), model, 4, 1, false);
    assertNotEquals(output.toString().length(), 0);
    assertEquals(output.toString().indexOf("\nGame over."), -1);
  }
}