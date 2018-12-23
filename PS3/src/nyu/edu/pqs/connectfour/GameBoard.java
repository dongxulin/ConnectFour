package nyu.edu.pqs.connectfour;

import java.util.List;

/**
 * this class is about the game board we play on, it contains basic newMove method, and some
 * logic to get best move and check if it is a win.
 * @author Dongxu Lin
 *
 */
public class GameBoard {

  private int[][] board = new int[GameInfo.ROW][GameInfo.COL];
  private int totalGrid = GameInfo.ROW * GameInfo.COL;
  private int lastMoveRow;
  private int lastMoveCol;
  
  
  /**
   * this method make a new move on board
   * @param col, which column the move want to drop
   * @param player, which player do it, should not be null
   */
  boolean newMove(int col, ConnectFourPlayer player) {
    int row = -1;
    for (int i = GameInfo.ROW - 1; i >= 0; i--) {
      if (board[i][col] == 0) {
        row = i;
        board[i][col] = player.getID();
        break;
      }
      if (i == 0) {
        return false;
      }
    }
    lastMoveCol = col;
    lastMoveRow = row;
    
    totalGrid--;
    return true;
  }
  
  

  /**
   * this is the method to check, whether is move result in a win, vertically, horizontally or diagonally.
   * @param row, index of the row of the move
   * @param col, index of the column of the move
   * @return true if this move result in a win, false not a win, the game 
   * is still on
   */
  boolean checkWin(int row, int col) {
    if (checkHorizontal(row, col) || checkVertical(row, col) || checkDiagonal(row, col)) {
      return true;
    }
    return false;
  }
  
  /**
   * this checks if there is continous vertically 4 balls 
   * @param row, index of row
   * @param col, index of col
   * @return true if there is vertical continous 4, false there isn't
   */
  private boolean checkVertical(int row, int col) {
    int color = board[row][col];
    int count = 0;
    
    while (row < GameInfo.ROW) {
      if (board[row][col] != color) {
        break;
      }
      count++;
      row++;
      if (count >= GameInfo.WINLEN) {
        return true;
      }
    }
    return false;
  }
  
  /**
   * this checks if there is continous horizontally 4 balls
   * @param row, index of row
   * @param col, index of col
   * @return true if there is horizontally continous 4, false there isn't
   */
  private boolean checkHorizontal(int row, int col) {
    int color = board[row][col];
    int leftMost = col;
    int rightMost = col;
    
    while (rightMost < GameInfo.COL && board[row][rightMost] == color) {
      rightMost++;
    }
    
    while (leftMost >= 0 && board[row][leftMost] == color) {
      leftMost--;
    }
    
    if (rightMost - leftMost - 1 >= GameInfo.WINLEN) {
      return true;
    }else {
      return false;
    }
  }
  
  /**
   * this checks if there is continous diagonally 4 balls
   * @param row, index of row
   * @param col, index of col
   * @return true if there is diagonally continous 4, false there isn't
   */
  private boolean checkDiagonal(int row, int col) {
    int color = board[row][col];
    
    int upperLeftRow = row;
    int upperLeftCol = col;
    int countUpperLeft = 0;
    
    while (upperLeftRow >=0 && upperLeftCol >= 0 && board[upperLeftRow][upperLeftCol] == color) {
      upperLeftCol--;
      upperLeftRow--;
      countUpperLeft++;
    }
    
    int bottomRightRow = row;
    int bottomRightCol = col;
    int countBottomRight = 0;
    
    while (bottomRightRow < GameInfo.ROW && bottomRightCol < GameInfo.COL && board[bottomRightRow][bottomRightCol] == color) {
      bottomRightCol++;
      bottomRightRow++;
      countUpperLeft++;
    }
    
    
    int upperRightRow = row;
    int upperRightCol = col;
    int countUpperRight = 0;
    
    while (upperRightRow >= 0 && upperRightCol < GameInfo.COL && board[upperRightRow][upperRightCol] == color) {
      upperRightCol++;
      upperRightRow--;
      countUpperRight++;
    }
    
    int bottomLeftRow = row;
    int bottomLeftCol = col;
    int countBottomLeft = 0;
    
    while (bottomLeftRow < GameInfo.ROW && bottomLeftCol >= 0 && board[bottomLeftRow][bottomLeftCol] == color) {
      bottomLeftCol--;
      bottomLeftRow++;
      countBottomLeft++;
    }
    
    if (countUpperLeft + countBottomRight - 1 >= GameInfo.WINLEN || countUpperRight + countBottomLeft - 1 >= GameInfo.WINLEN) {
      return true;
    }else {
      return false;
    }
  }
  
  private int findColForWin(int playerID) {
    int resCol = -1;

    for (int i = 0; i < GameInfo.COL; i++) {
      int tempRow = -1;
      for (int j = GameInfo.ROW - 1; j >= 0; j--) {
        if (board[j][i] == 0) {
          tempRow = j;
          break;
        }
      }
      
      if (tempRow == -1) {
        continue;
      }
      
      board[tempRow][i] = playerID;
      boolean win = checkWin(tempRow, i);
      if (win) {
        board[tempRow][i] = 0;
        resCol = i;
        break;
      }
      board[tempRow][i] = 0;
    }
    return resCol;
  }
  
  /**
   * this method try to get the best move for current situation, either result in a win or prevent
   * the opponent to win
   * @param players, list of players playing the game, should not be null
   * @return the col is the best for a win or prevent the opponent to win, -1 means no such col.
   */
  int getBestMove(List<ConnectFourPlayer> players) {
    int resCol = -1;
    for (ConnectFourPlayer player: players) {
      resCol = findColForWin(player.getID());
      if (resCol != -1) {
        break;
      }
    }
    return resCol;
  }

  /**
   * @return number of columns of the board
   */
  public int getColumn() {
    return GameInfo.COL;
  }

  /**
   * @return number of rows of the board
   */
  public int getRow() {
    return GameInfo.ROW;
  }

  /**
   * @return the row index of the last move 
   */
  public int getLastMoveRow() {
    return lastMoveRow;
  }


  /**
   * @return the column index of the last move 
   */
  public int getLastMoveCol() {
    return lastMoveCol;
  }

  /**
   * @return the total number of grid
   */
  public int getTotalGrid() {
    return totalGrid;
  }
  
  /**
   * @return the board of int[][]
   */
  public int[][] getBoard() {
    return board;
  }
  
//  for testing
  void setBoard(int row, int col, int colorID) {
    board[row][col] = colorID;
  }
  
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < GameInfo.ROW; i++) {
      for (int j = 0; j < GameInfo.COL; j++) {
        sb.append(board[i][j]);
        if (j != GameInfo.COL - 1) {
          sb.append(",");
        }
      }
      sb.append("\n");
    }
    return sb.toString();
  
  }
}
