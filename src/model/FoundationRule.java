package model;

import java.util.List;

/**
 * Created by derek on 7/2/17.
 */
class FoundationRule implements PileRule {
  @Override
  public boolean canAdd(Pile pile, List<Card> build) {
    if (build.size() != 1) {
      return false;
    }

    Card top = pile.get(0);
    Card toMove = build.get(0);

    return toMove.isOneHigher(top) && toMove.getSuite() == top.getSuite();
  }

  @Override
  public boolean canExtract(Pile pile, int startIdx) {
    return false;
  }
}