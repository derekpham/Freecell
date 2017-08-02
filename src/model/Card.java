package model;

import java.util.Objects;

// TODO Objects.requireNonNull() or throws?

/*
06/02/17 - Make this class final
07/31/17 - Refactored the Rank enum into this class instead of using numbers to represent values
           -> delegates several methods to the Rank class
         - Renamed getNumber() method to getValue()
 */

/**
 * To represent a card in the game of Freecell.
 * Valid cards must be from 2 to 10, jack, queen, king, or ace,
 * and whose suits are CLUB, DIAMOND, SPADE, and HEART.
 * This card represents an ace with a value of 1, a Jack with a value of 11, a queen
 * with a value of 12, and a king with a value of 13.
 */
public final class Card {
  private final Suite suite;
  private final Rank rank;

  /**
   * Constructs a card based on the given number and suite.
   *
   * @param rank the rank of the card such as ace, two, jack, queen, king
   * @param suite the suite of the card
   * @throws IllegalArgumentException if the given number is less than 1 or greater than 13
   * @throws IllegalArgumentException if the given suite is null
   */
  public Card(Rank rank, Suite suite) {
    // TODO maybe have a method that checks for any numbers of arguments
    if (rank == null || suite == null) {
      throw new IllegalArgumentException("One of the arguments has not been initialized.");
    }

    this.rank = rank;
    this.suite = suite;
  }

  /**
   * Formats this card with its number, or its letter (if the number is 1, 11, 12, or 13),
   * followed by its suite.
   *
   * @return the formatted string that represents {@code Card}.
   */
  public String toString() {
    return this.rank.toString() + this.suite.toString();
  }

  /**
   * Returns the suite of this {@code Card}.
   *
   * @return the suite of this {@code Card}
   */
  public Suite getSuite() {
    return this.suite;
  }

  /**
   * Returns the number of this {@code Card}.
   *
   * @return the number of this {@code Card}
   */
  public int getValue() {
    return this.rank.getValue();
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }

    if (!(other instanceof Card)) {
      return false;
    }

    Card that = (Card)other;
    return this.rank == that.rank && this.suite == that.suite;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.rank, this.suite);
  }

  /**
   * Returns whether the given {@code Card} has the opposite color to this {@code Card}'s.
   *
   * @param other the other {@code Card} whose color is to be compared with
   * @return whether this {@code Card} and the given {@code Card} have the opposite colors
   * @throws IllegalArgumentException if the given {@code Card} is {@code null}
   */
  public boolean hasOppositeColors(Card other) {
    if (other == null) {
      throw new IllegalArgumentException("Invalid input: null");
    }

    if (this.suite == Suite.DIAMOND || this.suite == Suite.HEART) {
      return other.suite == Suite.SPADE || other.suite == Suite.CLUB;
    } else {
      return other.suite == Suite.DIAMOND || other.suite == Suite.HEART;
    }
  }

  /**
   * Returns whether this {@code Card}'s value is one higher than the given {@code Card}'s.
   *
   * @param other the other {@code Card} to be compared with.
   * @return whether this {@code Card}'s value is one higher than the given {@code Card}
   */
  public boolean isOneHigher(Card other) {
    if (other == null) {
      throw new IllegalArgumentException("One of the arguments has not been initialized.");
    }

    return this.getValue() - other.getValue() == 1;
  }
}