package cs3500.hw04;

import java.util.ArrayList;
import java.util.List;

import cs3500.hw02.Card;
import cs3500.hw02.FreecellModel;
import cs3500.hw02.PileType;

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
    ensureMove(source, destination);
    if (!this.hasGameStarted) {
      throw new IllegalStateException("Game hasn't started.");
    }

    List<Card> from = this.getPile(source, pileNumber);
    List<Card> to = this.getPile(destination, destPileNumber);
    switch (source) {
      case CASCADE:
        moveFromCascade(from, cardIndex, destination, to);
        break;
      case OPEN:
        moveFromOpen(from, cardIndex, destination, to);
        break;
      case FOUNDATION:
        throw new IllegalArgumentException("Cannot move cards from a foundation pile.");
      default:
        throw new IllegalArgumentException("Wrong PileType.");
    }
  }

  /**
   * Moves cards from the given list of cards to the given destination. Assumes that the given
   * list of cards is a OPEN pile.
   *
   * @param openPile the source pile
   * @param cardIdx the card index to signal where tostart getting the cards
   * @param dest the destination PileType
   * @param destPile the destination pile
   */
  private void moveFromOpen(List<Card> openPile, int cardIdx, PileType dest, List<Card> destPile) {
    if (openPile.isEmpty()) {
      throw new IllegalArgumentException("The chosen open pile has no card.");
    }
    if (cardIdx != 0) {
      throw new IllegalArgumentException("Invalid card index for the chosen open pile.");
    }

    moveTo(openPile, cardIdx, dest, destPile);
  }

  /**
   * Moves cards from the given list of cards to the given destination. Assumes that the given
   * list of cards is a CASCADE pile.
   *
   * @param cascadePile the source pile
   * @param cardIdx the card index to signal where tostart getting the cards
   * @param dest the destination PileType
   * @param destPile the destination pile
   */
  private void moveFromCascade(List<Card> cascadePile, int cardIdx, PileType dest,
                               List<Card> destPile) {
    if (cardIdx < 0 || cardIdx >= cascadePile.size()) {
      throw new IllegalArgumentException("Invalid card index: " + cardIdx);
    }
    if (cascadePile.isEmpty()) {
      throw new IllegalArgumentException("Illegal move: pile is empty.");
    }

    List<Card> build = new ArrayList<>();
    for (int i = cardIdx; i < cascadePile.size(); i += 1) {
      build.add(cascadePile.get(i));
    }

    ensureBuild(build);

    moveTo(cascadePile, cardIdx, dest, destPile);
  }

  /**
   * Ensures that the given build is valid. This method will not throw an exception if:
   * <li>It's not null or an empty list</li>
   * <li>It's an alternating-color sequence, going from high to low</li>
   * <li>The number of cards in the build are less than or equal to the (n + 1) * (2 ^ k) where
   * n is the number of free open piles, and k is the number of free cascade piles</li>
   * @param build the build to be checked
   */
  private void ensureBuild(List<Card> build) {
    if (build == null || build.isEmpty()) {
      throw new IllegalArgumentException("Invalid move.");
    }

    for (int i = 0; i < build.size() - 1; i += 1) {
      Card prev = build.get(i);
      Card next = build.get(i + 1);
      if (!prev.isOneHigher(next) || !prev.hasOppositeColors(next)) {
        throw new IllegalArgumentException("Build is not valid.");
      }
    }

    int numFreeOpenPiles = 0;
    for (List<Card> openPile : openPiles) {
      if (openPile.isEmpty()) {
        numFreeOpenPiles += 1;
      }
    }
    int numFreeCascadePiles = 0;
    for (List<Card> cascadePile : cascadePiles) {
      if (cascadePile.isEmpty()) {
        numFreeCascadePiles += 1;
      }
    }

    int mostCards = (numFreeOpenPiles + 1) * (int)Math.pow(2, numFreeCascadePiles);
    if (build.size() > mostCards) {
      throw new IllegalArgumentException("Invalid move: The build has too many cards. "
              + "Number of cards: " + build.size() + ". Maximum cards allowed: " + mostCards);
    }
  }

  /**
   * Moves cards from the given index and pile of cards to the given pile. This method is a
   * wrapper for moveToOpen(), moveToFoundation(), and moveToCascade().
   *
   * @param sourcePile the source pile of cards
   * @param cardIdx the index to get the cards at
   * @param dest the destination PileType
   * @param destPile the destination pile
   */
  private void moveTo(List<Card> sourcePile, int cardIdx, PileType dest, List<Card> destPile) {
    switch (dest) {
      case FOUNDATION:
        moveToFoundation(sourcePile, cardIdx, destPile);
        break;
      case OPEN:
        moveToOpen(sourcePile, cardIdx, destPile);
        break;
      case CASCADE:
        moveToCascade(sourcePile, cardIdx, destPile);
        break;
      default: throw new IllegalArgumentException("Wrong PileType.");
    }
  }

  /**
   * Moves cards from a given list of cards to the given list of pile. The passed in pile
   * is assumed to be an FOUNDATION pile.
   *
   * @param sourcePile the source of cards pile
   * @param cardIdx the index to get the cards at
   * @param destPile the destination list of cards pile
   */
  private void moveToFoundation(List<Card> sourcePile, int cardIdx, List<Card> destPile) {
    Card card = sourcePile.get(cardIdx);
    if (destPile.isEmpty()) {
      if (card.getNumber() == 1) {
        destPile.add(card);
        sourcePile.remove(cardIdx);
        return;
      }
      throw new IllegalArgumentException("Invalid move.");
    }

    Card top = destPile.get(destPile.size() - 1);
    if (card.isOneHigher(top) && card.getSuite() == top.getSuite()) {
      destPile.add(card);
      sourcePile.remove(cardIdx);
      return;
    }

    throw new IllegalArgumentException("Invalid move.");
  }

  /**
   * Moves cards from a given list of cards to the given list of pile. The passed in pile
   * is assumed to be an OPEN pile.
   *
   * @param sourcePile the source of cards pile
   * @param cardIdx the index to get the cards at
   * @param destPile the destination list of cards pile
   */
  private void moveToOpen(List<Card> sourcePile, int cardIdx, List<Card> destPile) {
    if (!destPile.isEmpty()) {
      throw new IllegalArgumentException("Invalid move: Open pile is not empty.");
    }

    destPile.add(sourcePile.get(cardIdx));
    sourcePile.remove(cardIdx);
  }

  /**
   * Moves cards from a given list of cards to the given list of pile. The passed in pile
   * is assumed to be an CASCADE pile.
   *
   * @param sourcePile the source of cards pile
   * @param cardIdx the index to get the cards at
   * @param destPile the destination list of cards pile
   */
  private void moveToCascade(List<Card> sourcePile, int cardIdx, List<Card> destPile) {
    if (destPile.isEmpty()) {
      for (int i = cardIdx; i < sourcePile.size(); i += 1) {
        destPile.add(sourcePile.get(i));
      }
      for (int i = sourcePile.size() - 1; i >= cardIdx; i -= 1) {
        sourcePile.remove(i);
      }
      return;
    }

    Card top = destPile.get(destPile.size() - 1);
    Card head = sourcePile.get(cardIdx);
    if (top.isOneHigher(head) && top.hasOppositeColors(head)) {
      for (int i = cardIdx; i < sourcePile.size(); i += 1) {
        destPile.add(sourcePile.get(i));
      }
      remove(sourcePile, cardIdx);
      return;
    }

    throw new IllegalArgumentException("Invalid move.");
  }

  /**
   * Removes cards from the given source.
   *
   * @param source the source to remove cards from
   * @param startIdx the index to signal where to start removing the cards.
   */
  private void remove(List<Card> source, int startIdx) {
    for (int i = source.size() - 1; i >= startIdx; i -= 1) {
      source.remove(i);
    }
  }
}