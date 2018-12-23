package nyu.edu.pqs.canvas;

import java.awt.Color;

/**
 * this is the interface for a canvas observer, a observer can be triggered the following event:
 * color change, reset, mouse pressed, mouse dragged. 
 * @author Dongxu Lin
 */
public interface CanvasObserver {
 
  /**
   * the observer can be notified of color changed
   * @param color, the new current color should be input, if it is in erase mode, while color 
   * should be input.
   */
  public void changeColor(Color color);
  
  
  /**
   * the observer can be notified of cleaning up the canvas
   */
  public void reset();
  
  
  /**
   * the observer can be notified of the mouse is pressed on one of the canvas
   * @param x, the x value of the point 
   * @param y, the y value of the point
   */
  public void mousePressed(int x, int y);
  
  
  /**
   * the observer can be notified of the mouse has dragged on one of the canvas
   * @param x, the x value of the point
   * @param y, the y value of the point
   */
  public void mouseDragged(int x, int y);

}
