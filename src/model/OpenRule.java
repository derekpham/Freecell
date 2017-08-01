package model;

import java.util.List;

/**
 * Rule of adding for an open pile.
 */
class OpenRule implements PileRule {
  @Override
  public boolean canAdd(Pile pile, List<Card> build) {
    return pile.size() == 0 && build.size() == 1;
  }
}