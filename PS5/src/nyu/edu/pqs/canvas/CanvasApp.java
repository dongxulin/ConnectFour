package nyu.edu.pqs.canvas;


/**
 * Canvas app is the main class to launch the app. For the app, a user can draw using different
 * color pen, erase the drawing and clean up the canvas. For the erase feature, actually it is 
 * achieved by drawing with a white pen.
 * @author Dongxu Lin
 */
public class CanvasApp {

  /**
   * this method is to run the app, by default, there are 2 viewers.
   */
  void run() {
    CanvasModel model = new CanvasModel();
    new CanvasView(model).launchFrame();
    new CanvasView(model).launchFrame();
  }
  
  public static void main(String[] args) {
    CanvasApp app = new CanvasApp();
    app.run();
  }

}
