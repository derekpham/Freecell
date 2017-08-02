package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * A pile of cards. Has its own rule of adding.
 */
final class Pile implements Iterable<Card> {
  private final List<Card> pile;
  private final PileRule rule;

  Pile(PileRule rule) {
    this.rule = rule;
    this.pile = new ArrayList<>();
  }

  int size() {
    return this.pile.size();
  }

  // doesn't need to follow any rule, just add it
  void add(Card card) {
    this.pile.add(card);
  }

  // doesn't need to follow any rule, just add it
  void addAll(List<Card> cards) {
    for (Card card : cards) {
      this.add(card);
    }
  }

  // returns the cards, starting from the given index
  // actually removing cards from this pile
  List<Card> extractBuild(int startIdx) {
    if (startIdx < 0 || startIdx >= this.size()) {
      throw new IllegalArgumentException("Invalid card index: " + startIdx);
    }

    List<Card> result = new LinkedList<>();

    for (int i = this.size() - 1; i >= startIdx; i -= 1) {
      result.add(this.pile.remove(i));
    }

    return result;
  }

  // checks whether this whole pile can accept the build
  boolean canAdd(List<Card> build) {
    return this.rule.canAdd(this, build);
  }

  // checks whether you can take cards starting from the given index out of this pile
  boolean canExtract(int startIdx) {
    return this.rule.canExtract(this, startIdx);
  }

  Card get(int i) {
    return this.pile.get(i);
  }

  // TODO leaky because of the remove method
  @Override
  public Iterator<Card> iterator() {
    return this.pile.iterator();
  }
}