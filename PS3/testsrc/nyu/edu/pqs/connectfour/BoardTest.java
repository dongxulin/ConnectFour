package nyu.edu.pqs.connectfour;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import nyu.edu.pqs.connectfour.GameInfo.Color;

public class BoardTest {
  
  GameBoard boardInst;
  ConnectFourPlayer player1;
  ConnectFourPlayer player2;
  List<ConnectFourPlayer> players = new LinkedList<>();
   
  @Before
  public void setup() {
    boardInst = new GameBoard();
    player1 = PlayerFactory.addPlayer(GameInfo.PlayerType.HUMAN, Color.GREEN);
    player2 = PlayerFactory.addPlayer(GameInfo.PlayerType.HUMAN, Color.RED);
    players.add(player1);
    players.add(player2);
  }
  
  @After
  public void tearDown() {
    boardInst = null;
    player1 = null;
    PlayerFactory.Reset();
  }
  
  
  @Test
  public void testSucNewMove() {
    boolean done = boardInst.newMove(0, player1);
    assertEquals(done, true);
    
    for (int i = 0; i < boardInst.getRow(); i++) {
      for (int j = 0; j < boardInst.getColumn(); j++) {
        if (j == 0 && i == boardInst.getRow() - 1) {
          assertEquals(1, boardInst.getBoard()[i][j]);
        }else {
          assertEquals(0, boardInst.getBoard()[i][j]);
        }
      }
    }
  }
  
  @Test
  public void testFailNewMove() {
    for (int i = 0; i < boardInst.getRow(); i++) {
      boardInst.newMove(0, player1);
    }
    boolean done = boardInst.newMove(0, player1);
    assertEquals(done, false);
  }
  
  @Test
  public void testHorizontalWinForCheckWin() {
    for (int i = 0; i < GameInfo.WINLEN; i++) {
      boardInst.newMove(i, player1);
    }
    boolean doWin = boardInst.checkWin(boardInst.getLastMoveRow(), boardInst.getLastMoveCol());
    assertEquals(doWin, true);
  }
  
  @Test
  public void testHorizontalNotWinForCheckWin() {
    for (int i = 0; i < GameInfo.WINLEN - 1; i++) {
      boardInst.newMove(i, player1);
    }
    boolean doWin = boardInst.checkWin(boardInst.getLastMoveRow(), boardInst.getLastMoveCol());
    assertEquals(doWin, false);
  }
  
  @Test
  public void testVerticalWinForCheckWin() {
    for (int i = 0; i < GameInfo.WINLEN; i++) {
      boardInst.newMove(0, player1);
    }
    boolean doWin = boardInst.checkWin(boardInst.getLastMoveRow(), boardInst.getLastMoveCol());
    assertEquals(doWin, true);
  }
  
  @Test
  public void testVerticalNotWinForCheckWin() {
    for (int i = 0; i < GameInfo.WINLEN - 1; i++) {
      boardInst.newMove(0, player1);
    }
    boolean doWin = boardInst.checkWin(boardInst.getLastMoveRow(), boardInst.getLastMoveCol());
    assertEquals(doWin, false);
    
    boardInst.newMove(0, player2);
    doWin = boardInst.checkWin(boardInst.getLastMoveRow(), boardInst.getLastMoveCol());
    assertEquals(doWin, false);

  }
  
  @Test
  public void testDiagonalWinForCheckWin() {
    for (int i = 0; i < GameInfo.WINLEN; i++) {
      for (int j = i; j < GameInfo.WINLEN; j++ ) {
        if (i == 0 && j == 0) {
          boardInst.newMove(i, player2);
          continue;
        }
        boardInst.newMove(i, player1);
      }
    }
    boolean doWin = boardInst.checkWin(boardInst.getLastMoveRow(), boardInst.getLastMoveCol());
    assertEquals(doWin, true);
    
  }
  
  @Test
  public void testDiagonalNotWinForCheckWin() {
    for (int i = 0; i < GameInfo.WINLEN; i++) {
      for (int j = i; j < GameInfo.WINLEN - 1; j++ ) {
        boardInst.newMove(i, player1);
      }
    }
    boolean doWin = boardInst.checkWin(boardInst.getLastMoveRow(), boardInst.getLastMoveCol());
    assertEquals(doWin, false);
  }
  
  @Test
  public void testNotWinForCheckWin() {
    boardInst.newMove(0, player1);
    boolean doWin = boardInst.checkWin(boardInst.getLastMoveRow(), boardInst.getLastMoveCol());
    assertEquals(doWin, false);
  }
  
  @Test
  public void testGetBestMoveVer() {
    for (int i = 0; i < GameInfo.ROW; i++) {
      if (i % 2 == 1) {
        boardInst.newMove(0, player1);
      }else {
        boardInst.newMove(0, player2);
      }
    }
    
    for (int i = 0; i < GameInfo.WINLEN - 1; i++) {
      boardInst.newMove(1, player1);
    }
    
    int col = boardInst.getBestMove(players);
    assertEquals(col, 1);
  }
  
  @Test
  public void testGetBestMoveHor() {
    for (int i = 0; i < GameInfo.WINLEN - 1; i++) {
      boardInst.newMove(i, player2);
    }
    int col = boardInst.getBestMove(players);
    assertEquals(col, 3);
  }
  
  @Test
  public void testTotalGrid() {
    boardInst.newMove(0, player1);
    assertEquals(boardInst.getTotalGrid(), GameInfo.ROW * GameInfo.COL - 1);
  }
  
  @Test
  public void testToString() {
    for (int i = 0; i < GameInfo.ROW; i++) {
      for (int j = 0; j < GameInfo.COL; j++) {
        boardInst.newMove(j, player1);
      }
    }
    String tar = new String("1,1,1,1,1,1,1\n" + 
        "1,1,1,1,1,1,1\n" +
        "1,1,1,1,1,1,1\n" + 
        "1,1,1,1,1,1,1\n" + 
        "1,1,1,1,1,1,1\n" + 
        "1,1,1,1,1,1,1\n");

    assertEquals(boardInst.toString(), tar);
    
  }
  

}
