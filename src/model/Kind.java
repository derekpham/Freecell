package model;

/*
- 07/31/17 - Added to make the Card class cleaner
 */

import java.util.HashMap;
import java.util.Map;

/**
 * A kind of a card (ace, two, ..., jack, queen, king).
 * A value of a card is 1-indexed. An ace has a low value of 1, and a king has a high value of 13.
 */
public enum Kind {
  ACE(1, "A"), TWO(2, "2"), THREE(3, "3"), FOUR(4, "4"), FIVE(5, "5"), SIX(6, "6"), SEVEN(7, "7"),
  EIGHT(8, "8"), NINE(9, "9"), TEN(10, "10"), JACK(11, "J"), QUEEN(12, "Q"), KING(13, "K");

  private static final Map<Integer, Kind> valKindMap;
  static {
    valKindMap = new HashMap<>();
    for (Kind kind : values()) {
      valKindMap.put(kind.getValue(), kind);
    }
  }

  private final String symbol;
  private final int value;

  Kind(int value, String symbol) {
    this.symbol = symbol;
    this.value = value;
  }

  @Override
  public String toString() {
    return this.symbol;
  }

  /**
   * Returns the value of this kind. An ace has a low value of 1, and a king has a high value
   * of 12.
   *
   * @return the value of the kind
   */
  public int getValue() {
    return this.value;
  }

  /**
   * Returns the Kind that corresponds to the given number.
   *
   * @param val the value
   * @return the kind
   */
  public static Kind valToKind(int val) {
    if (!valKindMap.containsKey(val)) {
      throw new IllegalArgumentException("Invalid number.");
    }

    return valKindMap.get(val);
  }
}