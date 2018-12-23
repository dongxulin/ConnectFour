package nyu.edu.pqs.connectfour;

import nyu.edu.pqs.connectfour.GameInfo.Color;
import nyu.edu.pqs.connectfour.GameInfo.PlayerType;

/**
 * this is the player factory that can produce player object, a player is either human or computer
 * @author Dongxu Lin
 *
 */
public class PlayerFactory {
  
  private static int id = 1;
  
  /**
   * method to add a player, all input argument are required not to be null
   * @param type, human or computer
   * @param color, the color associated with the player when making a move on the board
   * @return a newly made player
   */
  static ConnectFourPlayer addPlayer(PlayerType type, Color color) {
    ConnectFourPlayer player;
    if (type.equals(PlayerType.HUMAN)) {
      player = new ConnectFourPlayer(id, true, color);
    }else {
      player = new ConnectFourPlayer(id, false, color);
    }
    id++;
    return player;
  }
  
  /**
   * reset the id counter 
   */
  static void Reset() {
    id = 1;
  }
  
}
