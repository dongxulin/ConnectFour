package nyu.edu.pqs.connectfour;

/**
 * this is the class to start the game, it has the main method. 
 * @author Dongxu Lin
 */
public class ConnectFourApp {
  
  /**
   * the method to start the game.
   * if multiple views/window for one model is needed, except for the first one which should launchWelcomeFrame, others
   * should lauchGameFrame.
   */
  public void run() {
    GameModel model = new GameModel();
    ConnectFourView view = new ConnectFourView(model);
    view.launchWelcomeFrame();
  }
  
  /**
   * main method to start
   * @param args, no input args is needed
   */
  public static void main(String[] args) {
    ConnectFourApp app = new ConnectFourApp();
    app.run();
  }
}
