package model;

import java.util.List;

/**
 * A cascade rule for single move.
 */
class SingleMoveCascadeRule implements PileRule {
  @Override
  public boolean canAdd(Pile pile, List<Card> build) {
    if (build.size() != 1) {
      return false;
    }

    if (pile.size() == 0) {
      return true;
    }

    Card last = pile.get(pile.size() - 1);
    Card toAdd = build.get(0);
    return last.isOneHigher(toAdd) && last.hasOppositeColors(toAdd);
  }

  @Override
  public boolean canExtract(Pile pile, int startIdx) {
    return startIdx >= 0 && pile.size() == startIdx + 1;
  }
}
