package nyu.edu.pqs.connectfour;

/**
 * this is the interface for connect four game that observer should implement. An observer
 * can be triggered several event: move, gameDraw, someoneWin, illegalMove and restart.
 * @author Dongxu Lin
 *
 */
public interface ConnectFourObserver {
  
  /**
   * this method is to notify making a new move.
   * 
   * @param player, the person make movement
   * @param row, the row index
   * @param col, the column index
   */
  void move(ConnectFourPlayer player, int row, int col);

  
  /**
   * this method indicates the game end and it is a draw
   */
  void gameDraw();

  
  /**
   * this method indicates one player win, and game end.
   * @param player, winner
   */
  void someoneWin(ConnectFourPlayer player);
  
  /**
   * this method indicates the move is illegal
   */
  void illegalMove();
  
  /**
   * this method is to restart the game
   */
  void restart();
  
}
