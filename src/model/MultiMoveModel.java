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

  /*
  Same as the super method, but supports multi-card moves.
   */
  @Override
  public void move(PileType source, int pileNumber, int cardIndex, PileType destination,
                   int destPileNumber) throws IllegalArgumentException {

  }
}