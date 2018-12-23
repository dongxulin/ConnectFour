package nyu.edu.pqs.connectfour;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Test;

import nyu.edu.pqs.connectfour.GameInfo.Color;
import nyu.edu.pqs.connectfour.GameInfo.PlayerType;

public class PlayerTest {
  ConnectFourPlayer player1;
  ConnectFourPlayer player2;
  
  
  @After
  public void tearDown() {
    player1 = null;
    player2 = null;
    PlayerFactory.Reset();
  }
  
  @Test
  public void testPlayerEquals() {
    player1 = PlayerFactory.addPlayer(PlayerType.HUMAN, Color.GREEN);
    PlayerFactory.Reset();
    player2 = PlayerFactory.addPlayer(PlayerType.HUMAN, Color.GREEN);
    assertTrue(player1.equals(player2));
  }
  
  @Test
  public void testPlayerIDNotEquals() {
    player1 = PlayerFactory.addPlayer(PlayerType.HUMAN, Color.GREEN);
    player2 = PlayerFactory.addPlayer(PlayerType.HUMAN, Color.GREEN);
    assertFalse(player1.equals(player2));
  }
  
  @Test
  public void testPlayerTypeNotEquals() {
    player1 = PlayerFactory.addPlayer(PlayerType.COMPUTER, Color.GREEN);
    PlayerFactory.Reset();
    player2 = PlayerFactory.addPlayer(PlayerType.HUMAN, Color.GREEN);
    assertFalse(player1.equals(player2));
  }
  
  @Test
  public void testPlayerColorNotEquals() {
    player1 = PlayerFactory.addPlayer(PlayerType.HUMAN, Color.RED);
    PlayerFactory.Reset();
    player2 = PlayerFactory.addPlayer(PlayerType.HUMAN, Color.GREEN);
    assertFalse(player1.equals(player2));
  }
}
