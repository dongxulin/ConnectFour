package nyu.edu.pqs.canvas;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Color;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import nyu.edu.pqs.canvas.CanvasView.CanvasPanel;

public class ViewTest {
  private CanvasModel model;
  private CanvasView canvasView;
  
  @Before
  public void setup() {
    model = new CanvasModel();
    canvasView = new CanvasView(model);
    canvasView.launchFrame();
  }
  
  @After
  public void teardown() {
    model = null;
    canvasView = null;
  }
  
  @Test
  public void testMousePressed() {
    canvasView.mousePressed(1, 1);
    assertEquals(1, ((CanvasPanel) canvasView.getCanvasPanel()).getPreX());
    assertEquals(1, ((CanvasPanel) canvasView.getCanvasPanel()).getPreY());
  }
  
  @Test
  public void testMouseDragged() throws InterruptedException {
    Thread.sleep(100);

    canvasView.mouseDragged(1, 1);
    assertEquals(1, ((CanvasPanel) canvasView.getCanvasPanel()).getPreX());
    assertEquals(1, ((CanvasPanel) canvasView.getCanvasPanel()).getPreY());
  }
  
  @Test
  public void testChangeColor() throws InterruptedException {
    Thread.sleep(100);
    canvasView.changeColor(Color.BLUE);
    assertEquals(Color.BLUE, ((CanvasPanel) canvasView.getCanvasPanel()).getCurColor());
  }
  
  @Test
  public void testReset() throws InterruptedException {
    Thread.sleep(100);
    canvasView.isReset = false;
    canvasView.reset();
    assertTrue(canvasView.isReset);
  }

  
}
