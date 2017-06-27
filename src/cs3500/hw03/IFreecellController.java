package cs3500.hw03;

import java.util.List;

import cs3500.hw02.FreecellOperations;

/**
 * To represent a freecell game controller.
 */
public interface IFreecellController<K> {
  /*
  06/02/17 - Changed documentation
   */
  /**
   * Plays the game of freecell by parsing user's input.
   *
   * @param deck the deck to play with
   * @param model the model to play with
   * @param numCascades the number of cascade piles
   * @param numOpens the number of open piles
   * @param shuffle whether to shuffle or not
   */
  void playGame(List<K> deck, FreecellOperations<K> model, int numCascades, int numOpens,
                boolean shuffle) throws IllegalArgumentException;
}
