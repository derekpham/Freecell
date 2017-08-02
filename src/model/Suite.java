/* CHANGE LOG
06/02/17 - Added char representation for each enum
 */

package model;

/**
 * This class represents a Suite for the four types of suite in the game of freecell.
 */
public enum Suite {
  CLUB('♣'), DIAMOND('♦'), SPADE('♠'), HEART('♥');

  private final char representation;

  Suite(char representation) {
    this.representation = representation;
  }

  /*
  06/02/17 - Added in order to make abstraction easier
   */
  @Override
  public String toString() {
    return this.representation + "";
  }
}
