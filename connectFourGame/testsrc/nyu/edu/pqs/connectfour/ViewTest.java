package nyu.edu.pqs.connectfour;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ViewTest {
  private ConnectFourView view;
  private GameModel model;
  
  @Before
  public void setup() {
    model = new GameModel();
    view = new ConnectFourView(model);

  }
  
  @After
  public void tearDown() {
    model = null;
    view = null;
  }
  
  @Test
  public void testLaunchWelcomeFrame() {
    view.launchWelcomeFrame();
  }
  
}
