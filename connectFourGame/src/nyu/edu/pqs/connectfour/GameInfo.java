package nyu.edu.pqs.connectfour;

/**
 * this class contains information for the game
 * @author Dongxu Lin
 *
 */
public class GameInfo {
  
  static final int ROW = 6;
  static final int COL = 7;
  static final int WINLEN = 4;
  
  /**
   * 2 mode for the game
   */
  public static enum GameMode{
    SINGLE_PLAYER, DOUBLE_PLAYER;
  }
  
  /**
   * a player is either human or computer
   */
  public static enum PlayerType{
    HUMAN, COMPUTER;
  }
  
  /**
   * the color to make a drop move
   */
  public static enum Color{
    RED, GREEN;
  }
    
}
