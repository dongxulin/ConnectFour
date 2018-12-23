package nyu.edu.pqs.canvas;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Color;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ModelTest {
  private CanvasModel model;
  
  @Before
  public void setup() {
    model = new CanvasModel();
  }
  
  @After
  public void teardown() {
    model = null;
  }
  
  public class TestViewer implements CanvasObserver{
    CanvasModel model;
    int preX;
    int preY;
    Color curColor;
    boolean isReset;
    
    public TestViewer(CanvasModel model) {
      this.model = model;
    }
    
    @Override
    public void changeColor(Color color) {
      curColor = color;
    }

    @Override
    public void reset() {
      isReset = true;
    }

    @Override
    public void mousePressed(int x, int y) {
      preX = x;
      preY = y;
    }

    @Override
    public void mouseDragged(int x, int y) {
      preX = x;
      preY = y;
    }
  }
  
  @Test
  public void testAddViewer() {
    CanvasView view = new CanvasView(model);
    model.addViewer(view);
    boolean flag = false;
    for (CanvasObserver ob: model.getViewerSet()) {
      if (ob == view) {
        flag = true;
      }
    }
    assertTrue(flag);
  }
  
  @Test (expected = IllegalArgumentException.class)
  public void testAddNullViewer() {
    CanvasView view = null;
    model.addViewer(view);
  }
  
  @Test
  public void testFireEraseEvent() {
    TestViewer view = new TestViewer(model);
    model.addViewer(view);
    model.fireEraseEvent();
    assertEquals(view.curColor, Color.WHITE);
  }
  
  @Test
  public void testFireMousePressedEvent() {
    TestViewer view = new TestViewer(model);
    model.addViewer(view);
    model.fireMousePressedEvent(1,1);
    assertEquals(1, view.preX);
    assertEquals(1, view.preY);

  }
  
  @Test
  public void testFireMouseDraggedEvent() {
    TestViewer view = new TestViewer(model);
    model.addViewer(view);
    model.fireMouseDraggedEvent(1,1);
    assertEquals(1, view.preX);
    assertEquals(1, view.preY);
  }
  
  @Test
  public void testFireChangeColorEvent() {
    TestViewer view = new TestViewer(model);
    model.addViewer(view);
    model.fireChangeColorEvent(Color.RED);
    assertEquals(view.curColor, Color.RED);
  }
  
  @Test
  public void testFireResetEvent() {
    TestViewer view = new TestViewer(model);
    model.addViewer(view);
    model.fireResetEvent();
    assertTrue(view.isReset);
  }
  
  
}
