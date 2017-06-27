package cs3500.hw03;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import cs3500.hw02.Card;
import cs3500.hw02.FreecellOperations;
import cs3500.hw02.PileType;
import cs3500.hw04.MultiMoveModel;

/**
 * A simple text-based controller for the freecell game.
 */
public class FreecellController implements IFreecellController<Card> {
  /*
  06/02/17 - make these fields final
   */
  private final Readable readable;
  private final Appendable appendable;

  /**
   * Constructs a freecell controller.
   *
   * @param rd the input for the controller
   * @param ap the output for the controller
   * @throws IllegalStateException if one of the parameters is not initialized
   */
  public FreecellController(Readable rd, Appendable ap) {
    if (rd == null || ap == null) {
      throw new IllegalStateException("One of the parameters has not been initialized");
    }
    this.readable = rd;
    this.appendable = ap;
  }

  @Override
  public void playGame(List<Card> deck, FreecellOperations<Card> model, int numCascades,
                       int numOpens, boolean shuffle) throws IllegalArgumentException {
    if (deck == null || model == null) {
      throw new IllegalArgumentException("One of the parameters has not been initialized");
    }

    try {
      model.startGame(deck, numCascades, numOpens, shuffle);
    } catch (IllegalArgumentException e) {
      this.appendMessage("Could not start game.");
      return;
    }

    Scanner scanner = new Scanner(this.readable);
    InputFlag curr = InputFlag.SOURCE_PILE;

    // initializes variables so Java doesn't complain about it not being initialized later
    PileType sourcePile = PileType.CASCADE;
    int sourcePileIdx = 0;
    int cardIdx = 0;
    PileType destPile;
    int destPileIdx;

    this.appendMessage(model.getGameState());
    while (scanner.hasNext()) {
      String nextInput = scanner.next();
      if (containsQ(nextInput)) {
        this.appendMessage("\nGame quit prematurely.");
        return;
      }
      switch (curr) {
        case SOURCE_PILE:
          if (validPileInput(nextInput)) {
            sourcePile = getPileType(nextInput.charAt(0));
            sourcePileIdx = Integer.parseInt(nextInput.substring(1)) - 1;
            curr = InputFlag.CARD_INDEX;
          } else {
            this.appendMessage("\nInvalid input for source pile. Try again.");
          }
          break;
        case CARD_INDEX:
          if (isNaturalNumber(nextInput)) {
            cardIdx = Integer.parseInt(nextInput) - 1;
            curr = InputFlag.DEST_PILE;
          } else {
            this.appendMessage("\nInvalid card index. Try again.");
          }
          break;
        case DEST_PILE:
          if (validPileInput(nextInput)) {
            destPile = getPileType(nextInput.charAt(0));
            destPileIdx = Integer.parseInt(nextInput.substring(1)) - 1;
            try {
              model.move(sourcePile, sourcePileIdx, cardIdx, destPile, destPileIdx);
              this.appendMessage("\n" + model.getGameState());
            } catch (IllegalArgumentException e) {
              this.appendMessage("\nInvalid move. Try again: ");
              this.appendMessage(e.getMessage());
            }
            curr = InputFlag.SOURCE_PILE;
          } else {
            this.appendMessage("\nInvalid input for destination pile. Try again.");
          }
          break;
        default:
          throw new IllegalArgumentException("Illegal input flag: " + curr);
      }

      if (model.isGameOver()) {
        this.appendMessage("\nGame over.");
        return;
      }
    }
  }

  /**
   * Checks whether a given input is a valid input for a pile.
   * The first letter has to correspond to a {@link PileType}. The rest has to be a natural number.
   *
   * @param input the input to be checked
   * @return whether in forms to a pile input
   */
  private static boolean validPileInput(String input) {
    return input != null && input.length() >= 2
            && isCharAPileType(input.charAt(0)) && isNaturalNumber(input.substring(1));
  }

  /**
   * Checks whether a character corresponds to a PileType.
   *
   * @param ch the character to check over
   * @return whether the character corresponds to a PileType
   */
  private static boolean isCharAPileType(char ch) {
    return ch == 'C' || ch == 'F' || ch == 'O';
  }

  /**
   * Gets the {@link PileType} based on the given character.
   *
   * @param ch the character to get the {@link PileType} from
   * @return the {@link PileType} that corresponds to the character
   */
  private static PileType getPileType(char ch) {
    switch (ch) {
      case 'C':
        return PileType.CASCADE;
      case 'F':
        return PileType.FOUNDATION;
      case 'O':
        return PileType.OPEN;
      default:
        throw new IllegalArgumentException("Invalid character used: " + ch);
    }
  }

  /**
   * Checks whether a String contains the letter 'q' or 'Q'.
   *
   * @param input the String to be checked
   * @return whether it contains 'q' or 'Q'
   */
  private static boolean containsQ(String input) {
    return input != null && (input.indexOf('q') != -1 || input.indexOf('Q') != -1);
  }

  /**
   * Checks whether a String is a non-negative number.
   *
   * @param input the String to be checked
   * @return whether the String is a non-negative number
   */
  private static boolean isNaturalNumber(String input) {
    if (input == null || input.equals("")) {
      return false;
    }

    for (char ch : input.toCharArray()) {
      if (!Character.isDigit(ch)) {
        return false;
      }
    }

    return true;
  }

  /**
   * Appends the given message to this object's appendable object.
   *
   * @param msg  the message to be sent to
   * @throws IllegalArgumentException if the given parameter is null
   */
  private void appendMessage(String msg) {
    if (msg == null) {
      throw new IllegalArgumentException("Can't append null.");
    }

    try {
      this.appendable.append(msg);
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }

  /**
   * An {@code InputFlag} that represents what kind of input the controller is expecting from
   * the user.
   */
  private enum InputFlag {
    SOURCE_PILE, CARD_INDEX, DEST_PILE
  }
}