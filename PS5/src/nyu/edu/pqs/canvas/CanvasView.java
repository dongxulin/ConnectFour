package nyu.edu.pqs.canvas;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * this is the viewer object contains the logic of listening for mouse event, as well as
 * the GUI of the game.
 * @author Dongxu Lin
 */
public class CanvasView implements CanvasObserver {
  
  private CanvasModel model;
  private JFrame mainFrame;
  
  private CanvasPanel canvasPanel;
  private JPanel buttonPanel;
  
  //for testing
  boolean isReset;
      
  /**
   * create a new Canvas view.
   * @param model
   */
  public CanvasView(CanvasModel model){
    this.model = model;
    canvasPanel = new CanvasPanel();
    model.addViewer(this);
  }
  
  /**
   * generate the button panel above the main frame
   * @return the button panel
   */
  private JPanel genButtonPanel() {

    buttonPanel = new JPanel(new FlowLayout());
    
    JButton reset = new JButton("RESET");
    JButton erase = new JButton("ERASER");
    JButton redPaint = new JButton("RED");
    JButton yellowPaint = new JButton("YELLOW");
    JButton bluePaint = new JButton("BLUE");
    
    redPaint.setOpaque(true);
    yellowPaint.setOpaque(true);
    bluePaint.setOpaque(true);
    
    redPaint.setBackground(Color.RED);
    yellowPaint.setBackground(Color.YELLOW);
    bluePaint.setBackground(Color.BLUE);

    reset.addActionListener(resetListener());
    erase.addActionListener(eraseListener());
    redPaint.addActionListener(colorListener(Color.RED));
    yellowPaint.addActionListener(colorListener(Color.YELLOW));
    bluePaint.addActionListener(colorListener(Color.BLUE));

    buttonPanel.add(reset);
    buttonPanel.add(erase);
    buttonPanel.add(redPaint);
    buttonPanel.add(yellowPaint);
    buttonPanel.add(bluePaint);

    return buttonPanel;
  }
  
  
  /**
   * this is the canvas panel object inherites from jpanel, it override the paint component
   * method to achieve painting feature.
   */
  class CanvasPanel extends JPanel{
    private Graphics2D graph;
    private Image img;
    private Color curColor;
    
    private int preX;
    private int preY;
    
    public CanvasPanel() {
      setBorder(BorderFactory.createLineBorder(Color.black));
  
      addMouseListener(new MouseAdapter() {
        public void mousePressed(MouseEvent e) {
          model.fireMousePressedEvent(e.getX(), e.getY());
        }
      });
  
      addMouseMotionListener(new MouseAdapter() {
        public void mouseDragged(MouseEvent e) {
          model.fireMouseDraggedEvent(e.getX(), e.getY());
        }
      });
    }
    
    /* (non-Javadoc)
     * @see javax.swing.JComponent#getPreferredSize()
     */
    public Dimension getPreferredSize() {
      return new Dimension(600, 600);
    }
  
    
    /* (non-Javadoc)
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    @Override
    protected void paintComponent(Graphics g) {
      if (img == null) {
        img = createImage(getSize().width, getSize().height);
        graph = (Graphics2D) img.getGraphics();
        resetCanvas();
      }
      canvasPanel.graph.setStroke(new BasicStroke(10));
      g.drawImage(img, 0, 0, null);
    }
    
    private void resetCanvas(){
      graph.setColor(Color.WHITE);
      graph.fillRect(0, 0, getSize().width, getSize().height);
      repaint();
      
      graph.setColor(curColor);
    }
    
//    for testing
    Color getCurColor() {
      return curColor;
    }
    
//  for testing
    int getPreX() {
      return preX;
    }
    
//  for testing
    int getPreY() {
      return preY;
    }
  }


  private ActionListener colorListener(Color c) {
    ActionListener colorListener = new ActionListener() {
      
      @Override
      public void actionPerformed(ActionEvent colorButtonActionEvent) {
        model.fireChangeColorEvent(c);
      }
    };
    return colorListener;
  }

  private ActionListener eraseListener() {
    ActionListener eraseListener = new ActionListener() {
      
      @Override
      public void actionPerformed(ActionEvent eraseButtonActionEvent) {
        model.fireEraseEvent();
      }
    };
    return eraseListener;
  }

  private ActionListener resetListener() {
      ActionListener resetListener = new ActionListener() {
        
        @Override
        public void actionPerformed(ActionEvent resetButtonActionEvent) {
          canvasPanel.setBackground(Color.WHITE);
          model.fireResetEvent();
        }
      };
      return resetListener;
  }

  
  /**
   * launch the main frame, the frame has 2 JPanels, one is button panel, another is the canvas
   * panel.
   */
  void launchFrame() {
    mainFrame = new JFrame("CANVAS");
    mainFrame.setSize(700, 700);
    mainFrame.setLocationRelativeTo(null);
    mainFrame.setResizable(false);
    mainFrame.setLayout(new BorderLayout());
    mainFrame.add(genButtonPanel(), BorderLayout.NORTH);
    mainFrame.add(canvasPanel, BorderLayout.CENTER);
    mainFrame.pack();
    mainFrame.setVisible(true);
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }


  @Override
  public void mousePressed(int x, int y) {
    canvasPanel.preX = x;
    canvasPanel.preY = y;
  }

  @Override
  public void mouseDragged(int x, int y) {
    canvasPanel.graph.drawLine(canvasPanel.preX, canvasPanel.preY, x, y);
    
    canvasPanel.repaint();
    canvasPanel.preX = x;
    canvasPanel.preY = y;
  }

  @Override
  public void changeColor(Color color) {
    canvasPanel.curColor = color;
    canvasPanel.graph.setColor(color);
  }

  @Override
  public void reset() {
    canvasPanel.curColor = Color.WHITE;
    canvasPanel.resetCanvas();
    canvasPanel.repaint();
    isReset = true;
  }
  
  
  /**
   * for testing
   * @return JPanel
   */
  JPanel getCanvasPanel() {
    return canvasPanel;
  }


}
