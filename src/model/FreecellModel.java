package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
07/31/17 - Adapted this class to the newly added Rank enum.
 */

/**
 * This class implements the {@link FreecellOperations} interface with the single-card move
 * functionality.
 */
public class FreecellModel implements FreecellOperations {
  private static final int NUM_FOUNDATIONS = 4;

  List<Pile> foundationPiles;
  List<Pile> cascadePiles;
  List<Pile> openPiles;
  protected boolean hasGameStarted;

  /*
  06/02/17 - used init() inside this constructor
   */
  public FreecellModel() {
    this.init();
  }

  /*
  06/02/17 - Added this method
  */
  /**
   * Initializes the fields in this class.
   */
  private void init() {
    this.cascadePiles = null;
    this.openPiles = null;
    this.foundationPiles = null;
    this.hasGameStarted = false;
  }

  @Override
  public List<Card> getDeck() {
    List<Card> result = new ArrayList<>();

    for (int num = 13; num >= 1; num--) { // TODO makes this loop nicer
      for (Suite suite : Suite.values()) {
        result.add(new Card(Rank.intToRank(num), suite));
      }
    }

    return result;
  }

  /*
  06/02/17 - Used an ensureStartGame method to check for illegal arguments instead of directly
             checking inside the method
           - Moved hasGameStarted to the end of the method
   */
  @Override
  public void startGame(List<Card> deck, int numCascadePiles, int numOpenPiles, boolean shuffle)
          throws IllegalArgumentException {
    ensureStartGame(deck, numCascadePiles, numOpenPiles);

    if (shuffle) {
      Collections.shuffle(deck);
    }

    this.cascadePiles = new ArrayList<>(numCascadePiles);
    for (int i = 0; i < numCascadePiles; i += 1) {
      this.cascadePiles.add(new Pile(new SingleMoveCascadeRule()));
    }

    for (int i = 0; i < deck.size(); i += 1) {
      int cascadePileIdx = i % numCascadePiles;
      this.cascadePiles.get(cascadePileIdx).add(deck.get(i));
    }

    this.openPiles = new ArrayList<>(numOpenPiles);
    for (int i = 0; i < numOpenPiles; i += 1) {
      this.openPiles.add(new Pile(new OpenRule()));
    }

    this.foundationPiles = new ArrayList<>(NUM_FOUNDATIONS);
    for (int i = 0; i < 4; i += 1) {
      this.foundationPiles.add(new Pile(new FoundationRule()));
    }

    this.hasGameStarted = true;
  }

  /*
  06/02/17 - Added
   */
  /**
   * Ensures that the arguments are valid.
   */
  private void ensureStartGame(List<Card> deck, int numCascadePiles, int numOpenPiles) {
    if (deck == null) {
      init();
      throw new IllegalArgumentException("Invalid input for deck: null");
    }
    if (!FreecellModel.isValidDeck(deck)) {
      init();
      throw new IllegalArgumentException("Deck is invalid!");
    }
    if (numCascadePiles < 4) {
      init();
      throw new IllegalArgumentException("Invalid number of cascade piles: " + numCascadePiles);
    }
    if (numOpenPiles < 1) {
      init();
      throw new IllegalArgumentException("Invalid number of open piles: " + numOpenPiles);
    }
  }

  /**
   * Checks whether the given deck of cards is valid.
   *
   * @param deck the deck to be checked
   * @return whether the given deck of cards is valid
   */
  private static boolean isValidDeck(List<Card> deck) {
    if (deck.size() != 52) {
      return false;
    }

    for (int cardIdx = 0; cardIdx < 52; cardIdx += 1) {
      Card check = deck.get(cardIdx);
      if (check == null) {
        return false;
      }
      for (int searchIdx = cardIdx + 1; searchIdx < 52; searchIdx += 1) {
        if (deck.get(searchIdx).equals(check)) {
          return false;
        }
      }
    }

    return true;
  }

  /*
  06/02/17 - Used switch instead of if
           - Refactored ensuring everything passed in works into ensureMove() method
   */
  @Override
  public void move(PileType source, int pileNumber, int cardIndex,
                   PileType destination, int destPileNumber) throws IllegalArgumentException {
    ensureMove(source, destination);

    Pile from = this.getPile(source, pileNumber);
    Pile to = this.getPile(destination, destPileNumber);

    if (!from.canExtract(cardIndex)) {
      throw new IllegalArgumentException("Cannot move cards out of the given pile.");
    }

    List<Card> build = from.extractBuild(cardIndex);
    if (!to.canAdd(build)) {
      from.addAll(build);
      throw new IllegalArgumentException("Cannot move the cards to the given pile.");
    }

    to.addAll(build);
  }

  /*
  06/02/17 - Added to make code more readable
   */
  private void ensureMove(PileType source, PileType destination) {
    if (!this.hasGameStarted) {
      throw new IllegalStateException("Game hasn't started.");
    }
    if (source == null) {
      throw new IllegalArgumentException("Invalid source pile.");
    }
    if (destination == null) {
      throw new IllegalArgumentException("Invalid destination pile.");
    }
  }

  /*
  06/02/17 - Changed accessor from private to protected. This will be helpful for classes that
  extend this class and try to override the move() method
   */
  /**
   * Gets the pile based on the given pile type and number.
   *
   * @param pileType the type of the pile
   * @param pileNumber the index of the pile
   * @return the pile of cards
   * @throws IllegalArgumentException if the given pile type is null or the pile number is invalid
   */
  private Pile getPile(PileType pileType, int pileNumber) {
    if (pileType == null) {
      throw new IllegalArgumentException("Invalid pile type: null");
    }

    if (pileType == PileType.CASCADE) {
      if (pileNumber < 0 || pileNumber >= this.cascadePiles.size()) {
        throw new IllegalArgumentException("Invalid pile number for cascade pile: " + pileNumber);
      } else {
        return this.cascadePiles.get(pileNumber);
      }
    } else if (pileType == PileType.FOUNDATION) {
      if (pileNumber < 0 || pileNumber >= this.foundationPiles.size()) {
        throw new IllegalArgumentException("Invalid pile number for foundation pile: "
                + pileNumber);
      } else {
        return this.foundationPiles.get(pileNumber);
      }
    } else {
      if (pileNumber < 0 || pileNumber >= this.openPiles.size()) {
        throw new IllegalArgumentException("Invalid pile number for open pile: " + pileNumber);
      } else {
        return this.openPiles.get(pileNumber);
      }
    }
  }

  @Override
  public boolean isGameOver() {
    if (!this.hasGameStarted) {
      return false;
    }

    for (int i = 0; i < 4; i += 1) {
      if (this.foundationPiles.get(i).size() != 13) {
        return false;
      }
    }

    return true;
  }

  @Override
  public String getGameState() {
    String result = "";

    if (!this.hasGameStarted) {
      return result;
    }

    for (int i = 0; i < 4; i += 1) {
      result = FreecellModel.printPile(PileType.FOUNDATION, this.foundationPiles.get(i),
              result, i + 1);
    }

    for (int i = 0; i < this.openPiles.size(); i += 1) {
      result = FreecellModel.printPile(PileType.OPEN, this.openPiles.get(i), result, i + 1);
    }

    for (int i = 0; i < this.cascadePiles.size(); i += 1) {
      result = FreecellModel.printPile(PileType.CASCADE, this.cascadePiles.get(i), result, i + 1);
    }

    return result.substring(0, result.length() - 1);
  }

  /**
   * Returns a String representation of the given pile based on the pile type and the count
   * that it's given.
   *
   * @param pileType the type of the pile
   * @param pile the list of cards to print from
   * @param acc the accumulator string so far
   * @param count the count for this pile
   * @return the String representation of the pile built on the accumulator
   */
  private static String printPile(PileType pileType, Pile pile, String acc, int count) {
    switch (pileType) {
      case OPEN:
        acc += "O";
        break;
      case CASCADE:
        acc += "C";
        break;
      case FOUNDATION:
        acc += "F";
        break;
      default:
        throw new IllegalArgumentException("Cannot print null!");
    }
    acc += count + ":";

    if (pile.size() != 0) {
      acc += " ";
      for (int i = 0; i < pile.size(); i += 1) {
        acc += pile.get(i).toString() + ", ";
      }

      return acc.substring(0, acc.length() - 2) + '\n';
    } else {
      return acc + '\n';
    }
  }
}
