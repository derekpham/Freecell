/* CHANGE LOG
- Removed unused imports - 06/02/17
 */

package model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/** Tests for the class {@link Card}.
 */
public final class CardTest {
  Card aceOfDiamonds = new Card(1, Suite.DIAMOND);
  Card twoOfSpades = new Card(2, Suite.SPADE);
  Card tenOfClubs = new Card(10, Suite.CLUB);
  Card jackOfHearts = new Card(11, Suite.HEART);
  Card queenOfSpades = new Card(12, Suite.SPADE);
  Card kingOfClubs = new Card(13, Suite.CLUB);
  Card nineOfHearts = new Card(9, Suite.HEART);

  @Test(expected = IllegalArgumentException.class)
  public void constructorFail1() {
    new Card(-1, Suite.DIAMOND);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorFail2() {
    new Card(0, Suite.DIAMOND);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorFail3() {
    new Card(-10, Suite.DIAMOND);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorFail4() {
    new Card(14, Suite.DIAMOND);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorFail5() {
    new Card(20, Suite.DIAMOND);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorFail6() {
    new Card(3, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorFail7() {
    new Card(-1, null);
  }

  @Test
  public void testToString() {
    assertEquals(aceOfDiamonds.toString(), "A♦");
    assertEquals(twoOfSpades.toString(), "2♠");
    assertEquals(tenOfClubs.toString(), "10♣");
    assertEquals(jackOfHearts.toString(), "J♥");
    assertEquals(queenOfSpades.toString(), "Q♠");
    assertEquals(kingOfClubs.toString(), "K♣");
  }

  @Test
  public void testGetSuite() {
    assertEquals(aceOfDiamonds.getSuite(), Suite.DIAMOND);
    assertEquals(twoOfSpades.getSuite(), Suite.SPADE);
    assertEquals(tenOfClubs.getSuite(), Suite.CLUB);
    assertEquals(jackOfHearts.getSuite(), Suite.HEART);
  }

  @Test
  public void testGetNumber() {
    assertEquals(aceOfDiamonds.getNumber(), 1);
    assertEquals(twoOfSpades.getNumber(), 2);
    assertEquals(tenOfClubs.getNumber(), 10);
    assertEquals(jackOfHearts.getNumber(), 11);
    assertEquals(queenOfSpades.getNumber(), 12);
    assertEquals(kingOfClubs.getNumber(), 13);
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
    Card anotherAce = new Card(1, Suite.DIAMOND);
    assertTrue(anotherAce.equals(aceOfDiamonds));
    assertTrue(aceOfDiamonds.equals(anotherAce));
  }

  @Test
  public void testHashcodeLogic() {
    assertEquals(aceOfDiamonds.hashCode(), aceOfDiamonds.hashCode());
    Card anotherAce = new Card(1, Suite.DIAMOND);
    assertEquals(aceOfDiamonds.hashCode(), anotherAce.hashCode());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testHasOppositeColorsFail() {
    aceOfDiamonds.hasOppositeColors(null);
  }

  @Test
  public void testHasOppositeColorsDiamond() {
    assertFalse(aceOfDiamonds.hasOppositeColors(aceOfDiamonds));
    assertFalse(aceOfDiamonds.hasOppositeColors(new Card(2, Suite.DIAMOND)));
    assertFalse(aceOfDiamonds.hasOppositeColors(jackOfHearts));
    assertFalse(jackOfHearts.hasOppositeColors(aceOfDiamonds));

    assertTrue(aceOfDiamonds.hasOppositeColors(twoOfSpades));
    assertTrue(aceOfDiamonds.hasOppositeColors(tenOfClubs));
  }

  @Test
  public void testHasOppositeColorsSpade() {
    assertFalse(twoOfSpades.hasOppositeColors(twoOfSpades));
    assertFalse(twoOfSpades.hasOppositeColors(tenOfClubs));
    assertFalse(twoOfSpades.hasOppositeColors(queenOfSpades));

    assertTrue(twoOfSpades.hasOppositeColors(aceOfDiamonds));
    assertTrue(twoOfSpades.hasOppositeColors(jackOfHearts));
  }

  @Test
  public void testHasOppositeColorsClub() {
    assertFalse(tenOfClubs.hasOppositeColors(tenOfClubs));
    assertFalse(tenOfClubs.hasOppositeColors(twoOfSpades));
    assertFalse(tenOfClubs.hasOppositeColors(new Card(2, Suite.CLUB)));

    assertTrue(tenOfClubs.hasOppositeColors(aceOfDiamonds));
    assertTrue(tenOfClubs.hasOppositeColors(jackOfHearts));
  }

  @Test
  public void testHasOppositeColorsHeart() {
    assertFalse(jackOfHearts.hasOppositeColors(jackOfHearts));
    assertFalse(jackOfHearts.hasOppositeColors(aceOfDiamonds));
    assertFalse(jackOfHearts.hasOppositeColors(new Card(3, Suite.HEART)));

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
    assertTrue(tenOfClubs.isOneHigher(new Card(9, Suite.SPADE)));
    assertTrue(tenOfClubs.isOneHigher(new Card(9, Suite.DIAMOND)));
    assertTrue(tenOfClubs.isOneHigher(new Card(9, Suite.CLUB)));
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