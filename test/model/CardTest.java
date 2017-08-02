/* CHANGE LOG
- Removed unused imports - 06/02/17
 */

package model;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/** Tests for the class {@link Card}.
 */
public final class CardTest {
  //TODO consider fuzzing the tests
  private static final Random randGen = new Random();
  private static final Rank[] RANKS = Rank.values();
  private static final Suite[] suites = Suite.values();

  private final Card aceOfDiamonds = new Card(Rank.ACE, Suite.DIAMOND);
  private final Card twoOfSpades = new Card(Rank.TWO, Suite.SPADE);
  private final Card tenOfClubs = new Card(Rank.TEN, Suite.CLUB);
  private final Card jackOfHearts = new Card(Rank.JACK, Suite.HEART);
  private final Card nineOfHearts = new Card(Rank.NINE, Suite.HEART);
  private final Card queenOfSpades = new Card(Rank.QUEEN, Suite.SPADE);
  private final Card kingOfClubs = new Card(Rank.KING, Suite.CLUB);

  @Test(expected = IllegalArgumentException.class)
  public void createFailNull1() {
    new Card(null, Suite.CLUB);
  }

  @Test(expected = IllegalArgumentException.class)
  public void createFailNull2() {
    new Card(Rank.EIGHT, null);
  }

  @Test
  public void testToString() {
    assertEquals(new Card(Rank.ACE, Suite.DIAMOND).toString(), "A♦");
    assertEquals(new Card(Rank.FIVE, Suite.CLUB).toString(), "5♣");
  }

  @Test
  public void testGetSuite() {
    assertEquals(aceOfDiamonds.getSuite(), Suite.DIAMOND);
    assertEquals(twoOfSpades.getSuite(), Suite.SPADE);
    assertEquals(tenOfClubs.getSuite(), Suite.CLUB);
    assertEquals(jackOfHearts.getSuite(), Suite.HEART);
  }

  @Test
  public void testGetValue() {
    assertEquals(aceOfDiamonds.getValue(), 1);
    assertEquals(twoOfSpades.getValue(), 2);
    assertEquals(tenOfClubs.getValue(), 10);
    assertEquals(jackOfHearts.getValue(), 11);
  }

  @Test
  public void testEqualsFalse() {
    assertFalse(twoOfSpades.equals("hi"));
    assertFalse(aceOfDiamonds.equals(jackOfHearts));
    assertFalse(jackOfHearts.equals(aceOfDiamonds));
  }

  @Test
  public void testEqualsReflexive() {
    assertTrue(aceOfDiamonds.equals(aceOfDiamonds));
    assertTrue(twoOfSpades.equals(twoOfSpades));
  }

  @Test
  public void testEqualsSymmetric() {
    Card anotherAce = new Card(Rank.ACE, Suite.DIAMOND);
    assertTrue(anotherAce.equals(aceOfDiamonds));
    assertTrue(aceOfDiamonds.equals(anotherAce));
  }

  @Test
  public void testHashcodeLogic() {
    assertEquals(aceOfDiamonds.hashCode(), aceOfDiamonds.hashCode());
    Card anotherAce = new Card(Rank.ACE, Suite.DIAMOND);
    assertEquals(aceOfDiamonds.hashCode(), anotherAce.hashCode());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testHasOppositeColorsFail() {
    aceOfDiamonds.hasOppositeColors(null);
  }

  @Test
  public void testHasOppositeColorsDiamond() {
    assertFalse(aceOfDiamonds.hasOppositeColors(aceOfDiamonds));
    assertFalse(aceOfDiamonds.hasOppositeColors(new Card(Rank.TWO, Suite.DIAMOND)));
    assertFalse(aceOfDiamonds.hasOppositeColors(jackOfHearts));
    assertFalse(jackOfHearts.hasOppositeColors(aceOfDiamonds));

    assertTrue(aceOfDiamonds.hasOppositeColors(twoOfSpades));
    assertTrue(aceOfDiamonds.hasOppositeColors(tenOfClubs));
  }

  @Test
  public void testHasOppositeColorsSpade() {
    assertFalse(twoOfSpades.hasOppositeColors(twoOfSpades));
    assertFalse(twoOfSpades.hasOppositeColors(tenOfClubs));
    assertFalse(twoOfSpades.hasOppositeColors(new Card(Rank.QUEEN, Suite.SPADE)));

    assertTrue(twoOfSpades.hasOppositeColors(aceOfDiamonds));
    assertTrue(twoOfSpades.hasOppositeColors(jackOfHearts));
  }

  @Test
  public void testHasOppositeColorsClub() {
    assertFalse(tenOfClubs.hasOppositeColors(tenOfClubs));
    assertFalse(tenOfClubs.hasOppositeColors(twoOfSpades));
    assertFalse(tenOfClubs.hasOppositeColors(new Card(Rank.TWO, Suite.CLUB)));

    assertTrue(tenOfClubs.hasOppositeColors(aceOfDiamonds));
    assertTrue(tenOfClubs.hasOppositeColors(jackOfHearts));
  }

  @Test
  public void testHasOppositeColorsHeart() {
    assertFalse(jackOfHearts.hasOppositeColors(jackOfHearts));
    assertFalse(jackOfHearts.hasOppositeColors(aceOfDiamonds));
    assertFalse(jackOfHearts.hasOppositeColors(new Card(Rank.THREE, Suite.HEART)));

    assertTrue(jackOfHearts.hasOppositeColors(tenOfClubs));
    assertTrue(jackOfHearts.hasOppositeColors(twoOfSpades));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIsOneHigherFail() {
    aceOfDiamonds.isOneHigher(null);
  }

  @Test
  public void testIsOneHigherTrue() {
    assertTrue(tenOfClubs.isOneHigher(nineOfHearts));
    assertTrue(tenOfClubs.isOneHigher(new Card(Rank.NINE, Suite.SPADE)));
    assertTrue(tenOfClubs.isOneHigher(new Card(Rank.NINE, Suite.DIAMOND)));
    assertTrue(tenOfClubs.isOneHigher(new Card(Rank.NINE, Suite.CLUB)));
  }

  @Test
  public void testIsOneHigherFalse() {
    assertFalse(nineOfHearts.isOneHigher(tenOfClubs));
    assertFalse(twoOfSpades.isOneHigher(tenOfClubs));
    assertFalse(aceOfDiamonds.isOneHigher(tenOfClubs));
    assertFalse(jackOfHearts.isOneHigher(queenOfSpades));
    assertFalse(queenOfSpades.isOneHigher(kingOfClubs));
    assertFalse(twoOfSpades.isOneHigher(jackOfHearts));
    assertFalse(aceOfDiamonds.isOneHigher(kingOfClubs));
  }
}