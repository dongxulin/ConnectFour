package nyu.edu.pqs.connectfour;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import nyu.edu.pqs.connectfour.GameInfo.Color;
import nyu.edu.pqs.connectfour.GameInfo.GameMode;
import nyu.edu.pqs.connectfour.GameInfo.PlayerType;

/**
 * this class is the logic for the game, whenever status changes, it will notify the observer,
 * player1 is always human, player2 is either a computer or a human depending on the game mode
 * @author Dongxu Lin
 */
public class GameModel {
  
  private GameMode mode;
  private GameBoard board;
  private List<ConnectFourObserver> observerList = new LinkedList<>();
  
  private ConnectFourPlayer currentPlayer;
  private ConnectFourPlayer player1;
  private ConnectFourPlayer player2;
  
//  for testing
  boolean makeFireMoveEvent;
  boolean makeFireRestartEvent;
  boolean makeFireIllegalMoveEvent;
  boolean makeFireDrawEvent;
  boolean makeFireWinEvent;

  /**
   * create 2 player depending on the game mode
   */
  private void createPlayers() {
    player1 = PlayerFactory.addPlayer(PlayerType.HUMAN, Color.GREEN);
    if (mode.equals(GameMode.SINGLE_PLAYER)) {
      player2 = PlayerFactory.addPlayer(PlayerType.COMPUTER, Color.RED);
    }else if (mode.equals(GameMode.DOUBLE_PLAYER)) {
      player2 = PlayerFactory.addPlayer(PlayerType.HUMAN, Color.RED);
    }
  }
  
  /**
   * start a new game
   * @param m, single player or double player, should not be null
   */
  void gameStart(GameMode m) {
    mode = m;
    board = new GameBoard();
    createPlayers();
    currentPlayer = player1;
  }
  
  /**
   * method to check if it is draw on board
   */
  boolean checkDraw() {
    if (board.getTotalGrid() == 0) {
      fireDrawEvent();
      return true;
    }
    return false;
  }
  
  /**
   * this method perform a drop move
   * @param col, input the column you want to make a drop move
   */
  boolean dropMove(int col) {
    boolean moved = board.newMove(col, currentPlayer);
    if (!moved) {
      fireIllegalMoveEvent();
      return false;
    }else {
      fireMoveEvent();
      boolean doWin = board.checkWin(board.getLastMoveRow(), board.getLastMoveCol());
      if (doWin) {
        fireWinEvent();
      }else {
        checkDraw();
        if (mode == GameMode.DOUBLE_PLAYER) {
          if (currentPlayer == player1) {
            currentPlayer = player2;
          }else {
            currentPlayer = player1;
          }
        }else {
          conmputeMove();
          checkDraw();
        }
      }
      return true;
    }
  }

  /**
   * if it is single player mode, a drop from from human immediately trigger a computer move
   */
  private void conmputeMove() {
    currentPlayer = player2;
    List<ConnectFourPlayer> players = new LinkedList<>();
    players.add(player2);
    players.add(player1);
    
    int bestMove = board.getBestMove(players);
    if (bestMove == -1) {
      int rand = new Random().nextInt(GameInfo.COL);
      boolean moved = board.newMove(rand, player2);
      while (!moved) {
        rand = new Random().nextInt(GameInfo.COL);
        moved = board.newMove(rand, player2);
      }
      fireMoveEvent();

    }else {
      board.newMove(bestMove, currentPlayer);
      fireMoveEvent();
      boolean doWin = board.checkWin(board.getLastMoveRow(), board.getLastMoveCol());
      if (doWin) {
        fireWinEvent();
      }
    }
    currentPlayer = player1;
  }
  
  void addObeserver(ConnectFourView viewer) {
    observerList.add(viewer);
  }
  
  /**
   * @return the current game board
   */
  GameBoard getBoard() {
    return board;
  }
  
  /**
   * @return the list of observers
   */
  List<ConnectFourObserver> getObserverList(){
    return observerList;
  }
  
  /**
   * @return the current player 
   */
  ConnectFourPlayer getCurrentPlayer() {
    return currentPlayer;
  }
  
  /**
   * @return player1
   */
  ConnectFourPlayer getPlayer1() {
    return player1;
  }
  
  /**
   * @return player2
   */
  ConnectFourPlayer getPlayer2() {
    return player2;
  }
 
  
  /**
   * whenever there is a valid move, this method fire the move event to all observers.
   */
  void fireMoveEvent() {
    for (ConnectFourObserver obeserver: observerList) {
      obeserver.move(currentPlayer, board.getLastMoveRow(), board.getLastMoveCol());
      makeFireMoveEvent = true;
    }
  }
  
  /**
   * whenever the restart button is clicked, this method fire a restart event to all observers.
   */
  void fireRestartEvent() {
    for (ConnectFourObserver obeserver: observerList) {
      obeserver.restart();
      makeFireRestartEvent = true;
    }
  }
  
  /**
   *  whenever there is a illegal move, this method fire the illegal move
   *   event to all observers.
   */
  void fireIllegalMoveEvent() {
    for (ConnectFourObserver obeserver: observerList) {
      obeserver.illegalMove();
      makeFireIllegalMoveEvent = true;
    }
  }
  
  /**
   * whenever it is a draw, this method fire the draw event to all observers.
   */
  void fireDrawEvent() {
    for (ConnectFourObserver obeserver: observerList) {
      obeserver.gameDraw();
      makeFireDrawEvent = true;
    }
  }
  
  /**
   * whenever it is a win, this method fire the win event to all observers.
   */
  void fireWinEvent() {
    for (ConnectFourObserver obeserver: observerList) {
      obeserver.someoneWin(currentPlayer);
      makeFireWinEvent = true;
    }
  }
  
}
