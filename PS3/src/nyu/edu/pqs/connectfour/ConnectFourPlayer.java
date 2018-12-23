package nyu.edu.pqs.connectfour;

import nyu.edu.pqs.connectfour.GameInfo.Color;

/**
 * A player can be either computer or human, a player has an id and associated color. 
 * All fields are required when creating a player.
 * @author Dongxu Lin
 *
 */
public class ConnectFourPlayer {
  
  private final int id;
  private final boolean isHuman;
  private final GameInfo.Color color;
  
  /**
   * create a new player
   * @param id, player id
   * @param isHuman, true for human, false for computer
   * @param c, the color
   */
  public ConnectFourPlayer(int id, boolean isHuman, Color c) {
    this.id = id;
    this.isHuman = isHuman;
    color = c;
  }

  /**
   * @return id of the player
   */
  public int getID() {
    return id;
  }

  /**
   * @return the color of player
   */
  public GameInfo.Color getColor() {
    return color;
  }
  
  /**
   * @return true if it is human, false if computer.
   */
  public boolean getType() {
    return isHuman;
  }
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int res = 1;
    res = prime * res + this.id;
    res = prime * res + this.getColor().hashCode();
    res = prime * res + (this.isHuman == true? 1: 0);
    return res;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    ConnectFourPlayer player2 = (ConnectFourPlayer)obj;
    if (player2.getID() != this.id || player2.getColor() != this.getColor()) {
      return false;
    }else if ((player2.isHuman == true && this.isHuman == false)|| 
        player2.isHuman == false && this.isHuman == true) {
      return false;
    }
    return true;
  }
}
