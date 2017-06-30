package model;

/**
 * A factory class that supports creating a single-move freecell game
 * or a multi-move freecell game.
 */
public final class FreecellModelCreator {
  /**
   * Constructs the creator object. This method should always throw an exception.
   *
   * @throws RuntimeException if this is ever called
   */
  private FreecellModelCreator() {
    throw new RuntimeException("This class can't be instantiated.");
  }

  /**
   * Returns a FreecellOperations. Based on the given input, the model returned will
   * either be a single-move model, or a multi-move model.
   *
   * @param type the type of the game
   * @return the model
   * @throws IllegalArgumentException if the given {@link GameType} is {@code null}
   */
  public static FreecellOperations<Card> create(GameType type) {
    if (type == null) {
      throw new IllegalArgumentException("Passed in parameter can't be null.");
    }

    switch (type) {
      case SINGLEMOVE:
        return new FreecellModel();
      case MULTIMOVE:
        return new MultiMoveModel();
      default:
        throw new IllegalArgumentException("Wrong argument: " + type);
    }
  }

  /**
   * An enumeration of the types of a freecell game.
   * The two values are: SINGLEMOVE, and MULTIMOVE
   */
  public enum GameType {
    SINGLEMOVE, MULTIMOVE
  }
}
