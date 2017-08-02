package model;

import java.util.List;

/**
 * A rule class to determine whether a pile can add a build.
 */
interface PileRule {
  boolean canAdd(Pile pile, List<Card> build);

  boolean canExtract(Pile pile, int startIdx);
}
