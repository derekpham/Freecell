package model;

import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of FreecellOperations that supports moving multi-cards at once.
 * NOTE: The implementation of the move method in this class did not rely much on the move method
 * of the super class mainly because I want to reimplement it using a cleaner approach.
 */
public final class MultiMoveModel extends FreecellModel {
  /**
   * Constructs a {@code MultiMoveModel}.
   */
  public MultiMoveModel() {
    super();
  }
}