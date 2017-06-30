/* CHANGE LOG
- 06/02/17: - Removed unused imports
            - Changed the Java doc for the class's description
            - Changed access modifiers for certain fields and methods, so that classes that extend
            this class can override the move() method more easily.
 */

package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class implements the {@link FreecellOperations} interface with the single-card move
 * functionality.
 */
public class FreecellModel implements FreecellOperations<Card> {
  /*
  06/02/17 - Changed the piles' access modifiers to protected, so that classes that extend this
  class can use them.
   */
  protected List<List<Card>> cascadePiles;
  protected List<List<Card>> openPiles;
  protected List<List<Card>> foundationPiles;
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

    for (int num = 13; num >= 1; num -= 1) {
      result.add(new Card(num, Suite.CLUB));
      result.add(new Card(num, Suite.DIAMOND));
      result.add(new Card(num, Suite.SPADE));
      result.add(new Card(num, Suite.HEART));
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

    this.cascadePiles = new ArrayList<>(numCascadePiles);
    for (int i = 0; i < numCascadePiles; i += 1) {
      this.cascadePiles.add(new ArrayList<>());
    }

    this.openPiles = new ArrayList<>(numOpenPiles);
    for (int i = 0; i < numOpenPiles; i += 1) {
      this.openPiles.add(new ArrayList<>(1));
    }

    if (shuffle) {
      Collections.shuffle(deck);
    }

    for (int i = 0; i < deck.size(); i += 1) {
      int cascadePileIdx = i % numCascadePiles;
      this.cascadePiles.get(cascadePileIdx).add(deck.get(i));
    }

    this.foundationPiles = new ArrayList<>(4);
    for (int i = 0; i < 4; i += 1) {
      this.foundationPiles.add(new ArrayList<>(1));
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
    if (source == PileType.FOUNDATION) {
      throw new IllegalArgumentException("Illegal move: Cannot move cards from foundation pile.");
    }

    List<Card> from = this.getPile(source, pileNumber);
    Card get = this.getCard(from, cardIndex);
    List<Card> to = this.getPile(destination, destPileNumber);

    switch (destination) {
      case CASCADE:
        if (to.isEmpty()) {
          to.add(get);
        } else {
          Card last = to.get(to.size() - 1);
          if (last.hasOppositeColors(get) && last.isOneHigher(get)) {
            to.add(get);
          } else {
            throw new IllegalArgumentException("Illegal move.");
          }
        }
        break;
      case FOUNDATION:
        if (to.isEmpty()) {
          if (get.getNumber() == 1) {
            to.add(get);
          } else {
            throw new IllegalArgumentException("Illegal move.");
          }
        } else {
          Card last = to.get(to.size() - 1);
          if (get.isOneHigher(last) && get.getSuite() == last.getSuite()) {
            to.add(get);
          } else {
            throw new IllegalArgumentException("Illegal move.");
          }
        }
        break;
      case OPEN:
        if (to.isEmpty()) {
          to.add(get);
        } else {
          throw new IllegalArgumentException("Illegal move.");
        }
        break;
      default: throw new IllegalArgumentException("Not a PileType");
    }

    from.remove(from.size() - 1);
  }

  /*
  06/02/17 - Added to make code more readable
   */
  protected void ensureMove(PileType source, PileType destination) {
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
  protected List<Card> getPile(PileType pileType, int pileNumber) {
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

  /**
   * Gets the card based on its index and the given pile of cards.
   *
   * @param pile the pile to get the card from
   * @param cardIndex the index to get at
   * @return the {@code Card} to get from
   * @throws IllegalArgumentException if the index is illegal
   */
  private Card getCard(List<Card> pile, int cardIndex) {
    if (cardIndex != pile.size() - 1) {
      throw new IllegalArgumentException("Illegal card index: " + cardIndex);
    }

    return pile.get(cardIndex);
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
  private static String printPile(PileType pileType, List<Card> pile, String acc, int count) {
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
