package model;

/*
- 07/31/17 - Added to make the Card class cleaner
- 08/01/17 - Changed the name of the class from Kind to Rank
 */

import java.util.HashMap;
import java.util.Map;

/**
 * A rank of a card (ace, two, ..., jack, queen, king).
 * A value of a card is 1-indexed. An ace has a low value of 1, and a king has a high value of 13.
 */
public enum Rank {
  ACE(1, "A"), TWO(2, "2"), THREE(3, "3"), FOUR(4, "4"), FIVE(5, "5"), SIX(6, "6"), SEVEN(7, "7"),
  EIGHT(8, "8"), NINE(9, "9"), TEN(10, "10"), JACK(11, "J"), QUEEN(12, "Q"), KING(13, "K");

  private static final Map<Integer, Rank> valKindMap;
  static {
    valKindMap = new HashMap<>();
    for (Rank rank : values()) {
      valKindMap.put(rank.getValue(), rank);
    }
  }

  private final String symbol;
  private final int value;

  Rank(int value, String symbol) {
    this.symbol = symbol;
    this.value = value;
  }

  @Override
  public String toString() {
    return this.symbol;
  }

  /**
   * Returns the value of this {@code Rank}. An ace has a low value of 1, and a king has a high
   * value
   * of 12.
   *
   * @return the value of the kind
   */
  public int getValue() {
    return this.value;
  }

  /**
   * Returns the Rank that corresponds to the given number.
   *
   * @param val the value
   * @return the corresponding {@code Rank}
   */
  public static Rank intToRank(int val) {
    if (!valKindMap.containsKey(val)) {
      throw new IllegalArgumentException("Invalid number.");
    }

    return valKindMap.get(val);
  }
}