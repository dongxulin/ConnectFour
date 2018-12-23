package nyu.edu.pqs.canvas;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

/**
 * a canvas model has logic for adding canvas viewer, and firing different event to viewers.
 * @author Dongxu Lin
 */
public class CanvasModel {
  
  private Set<CanvasObserver> viewers = new HashSet<>();
 
  /**
   * add a viewer 
   * @param view, the viewer object
   * @return true if the the new viewer is added into the set, false if the viewer has already in
   * the set.
   * @throws IllegalArgumentException, if input arg is null
   */
  boolean addViewer(CanvasObserver viewer) {
    if (viewer == null) {
      throw new IllegalArgumentException("view cannot be null");
    }
    return viewers.add(viewer);
  }
  
  /**
   * it erase event is invoked, it actually change every viewer's current color to white.
   */
  void fireEraseEvent() {
    for (CanvasObserver viewer: viewers) {
      viewer.changeColor(Color.WHITE);
    }
  }

  /**
   * fire mouse press event to viewers
   * @param x value of coordinate
   * @param y value of coordinate
   */
  void fireMousePressedEvent(int x, int y) {
    for (CanvasObserver viewer: viewers) {
      viewer.mousePressed(x, y);
    }
  }

  /**
   * fire mouse drag event to viewers
   * @param x value of coordinate
   * @param y value of coordinate
   */
  void fireMouseDraggedEvent(int x, int y) {
    for (CanvasObserver viewer: viewers) {
      viewer.mouseDragged(x, y);
    }
  }
  
  /**
   * this fire the color change event to all of the viewers.
   * @param c, the new color.
   */
  void fireChangeColorEvent(Color c) {
    for (CanvasObserver viewer: viewers) {
      viewer.changeColor(c);
    }
  }

  /**
   * fire reset for cleaning up the canvas to all of viewers.
   */
  void fireResetEvent() {
    for (CanvasObserver viewer: viewers) {
      viewer.reset();
    }
  }

  /**
   * for testing
   * @return a set of viewers
   */
  Set<CanvasObserver> getViewerSet() {
    return viewers;
  }

}
