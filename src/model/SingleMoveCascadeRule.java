package model;

import java.util.LinkedList;
import java.util.List;

/**
 * A cascade rule for single move.
 */
class SingleMoveCascadeRule implements PileRule {
  @Override
  public boolean canAdd(Pile pile, List<Card> build) {
    return false;
  }
}
