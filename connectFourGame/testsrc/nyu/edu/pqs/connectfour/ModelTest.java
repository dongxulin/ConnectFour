package nyu.edu.pqs.connectfour;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import nyu.edu.pqs.connectfour.GameInfo.Color;

public class ModelTest {
  private GameModel model;
  
  
  @Before
  public void setup() {
    model = new GameModel();
  }
  
  @After
  public void tearDown() {
    model = null;
    PlayerFactory.Reset();
  }
  
  @Test
  public void testGameStart() {
    model.gameStart(GameInfo.GameMode.SINGLE_PLAYER);
    model.gameStart(GameInfo.GameMode.DOUBLE_PLAYER);
    assertEquals(model.getCurrentPlayer(), model.getPlayer1());
  }
  
  @Test
  public void testAddObserver() {
    ConnectFourView view = new ConnectFourView(model);
    model.addObeserver(view);
    boolean flag = false;
    for (ConnectFourObserver ob: model.getObserverList()) {
      if (ob == view) {
        flag = true;
      }
    }
    assertTrue(flag);
  }
  
  @Test
  public void testCheckDraw() {
    model.gameStart(GameInfo.GameMode.SINGLE_PLAYER);
    assertFalse(model.checkDraw());
    for (int i = 0; i < GameInfo.ROW; i++) {
      for (int j = 0; j < GameInfo.COL; j++) {
        model.getBoard().newMove(j, model.getPlayer1());
      }
    }
    assertTrue(model.checkDraw());
  }
  
  @Test
  public void testSinglePlayerDropMove() {
    model.gameStart(GameInfo.GameMode.SINGLE_PLAYER);
    for (int i = 0; i < GameInfo.ROW; i++) {
      model.getBoard().newMove(0, model.getPlayer2());
    }
    boolean done = model.dropMove(0);
    assertFalse(done);
    
    done = model.dropMove(1);
    assertTrue(done);
  }
  
  @Test
  public void testSinglePlayerWinDropMove() {
    model.gameStart(GameInfo.GameMode.SINGLE_PLAYER);
    Color c = model.getPlayer1().getColor();
    assertEquals(c, GameInfo.Color.GREEN);
    for (int i = 0; i < GameInfo.WINLEN - 1; i++) {
      model.getBoard().newMove(0, model.getPlayer1());
    }
    model.dropMove(0);
  }
  
  @Test
  public void testSinglePlayerComputerBestMove() {
    model.gameStart(GameInfo.GameMode.SINGLE_PLAYER);
    for (int i = 0; i < GameInfo.WINLEN - 1; i++) {
      model.getBoard().setBoard(GameInfo.ROW - 1, i, model.getPlayer2().getID());
    }
    model.dropMove(0);
  }
  
  @Test
  public void testDoublePlayerWinDropMove() {
    model.gameStart(GameInfo.GameMode.DOUBLE_PLAYER);
    for (int i = 0; i < GameInfo.WINLEN - 1; i++) {
      model.dropMove(0);
    }
  }
  
  @Test
  public void testFireMoveEvent() {
    ConnectFourView view = new ConnectFourView(model);
    view.launchGameFrame();
    model.gameStart(GameInfo.GameMode.DOUBLE_PLAYER);
    model.addObeserver(view);
    model.makeFireMoveEvent = false;
    model.fireMoveEvent();
    assertTrue(model.makeFireMoveEvent);
  }
  
  @Test
  public void testFireIllegalMoveEvent() {
    ConnectFourView view = new ConnectFourView(model);
    view.launchGameFrame();
    model.gameStart(GameInfo.GameMode.DOUBLE_PLAYER);
    model.addObeserver(view);
    model.makeFireIllegalMoveEvent = false;
    model.fireIllegalMoveEvent();
    assertTrue(model.makeFireIllegalMoveEvent);

  }
  
  @Test
  public void testFireDrawEvent() {
    ConnectFourView view = new ConnectFourView(model);
    view.launchGameFrame();
    model.gameStart(GameInfo.GameMode.DOUBLE_PLAYER);
    model.addObeserver(view);
    model.makeFireDrawEvent = false;
    model.fireDrawEvent();
    assertTrue(model.makeFireDrawEvent);
  }
  
  @Test
  public void testFireWinEvent() {
    ConnectFourView view = new ConnectFourView(model);
    view.launchGameFrame();
    model.gameStart(GameInfo.GameMode.DOUBLE_PLAYER);
    model.addObeserver(view);
    model.makeFireWinEvent = false;
    model.fireWinEvent();
    assertTrue(model.makeFireWinEvent);
  }
  
  @Test
  public void testFireRestartEvent() {
    ConnectFourView view = new ConnectFourView(model);
    view.launchGameFrame();
    model.gameStart(GameInfo.GameMode.DOUBLE_PLAYER);
    model.addObeserver(view);
    model.makeFireRestartEvent = false;
    model.fireRestartEvent();
    assertTrue(model.makeFireRestartEvent);
  }

  
}
