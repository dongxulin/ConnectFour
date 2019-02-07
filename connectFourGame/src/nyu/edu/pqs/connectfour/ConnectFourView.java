package nyu.edu.pqs.connectfour;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import nyu.edu.pqs.connectfour.GameInfo.GameMode;

/**
 * the class is a view for the game, it contains mainly 2 frame, one is welcome frame for choosing 
 * a game mode, once selected, the game logic start and trigger another frame called game frame where
 * we can make move.
 * @author Dongxu Lin
 *
 */
public class ConnectFourView implements ConnectFourObserver {
  
  private GameModel model;
  private JFrame gameFrame;
  private JFrame welcomeFrame;
  private JPanel gridPanel;
  private JPanel gameFrameTopPanel;
  private JButton gridButtons[][];
  private JButton reset;
  
  /**
   * get a new view
   * @param game model, input the model for this view, should not be null
   */
  public ConnectFourView(GameModel model) {
    model.addObeserver(this);
    this.model = model;
    gridButtons = new JButton[GameInfo.ROW + 1][GameInfo.COL];
  }
  
  /**
   * listener for the user to choose game mode on the welcome frame
   * @param mode, single player or double player, should not be null
   */
  private ActionListener modeListener(GameInfo.GameMode mode) {
    ActionListener modeListener = new ActionListener() {
      
      @Override
      public void actionPerformed(ActionEvent welcomeButtonActionEvent) {
        model.gameStart(mode);
        welcomeFrame.dispose();
        launchGameFrame();
      }
    };
    return modeListener;
  }


  /**
   * the listener for the restart button, once press, it restart the game
   */
  private ActionListener restartListener() {
    ActionListener resetListener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent startButtonActionEvent) {
        gameFrame.setVisible(false);
        gameFrame.dispose();
        model.fireRestartEvent();
        
        PlayerFactory.Reset();
        ConnectFourApp app = new ConnectFourApp();
        app.run();
      }
    };
    return resetListener;
  }
  
  /**
   * the listener for making a drop move on the board
   */
  private ActionListener dropButtonListener() {
    ActionListener dropListener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent dropButtonActionEvent) {
        JButton dropClicked= (JButton)dropButtonActionEvent.getSource();
        int columnClicked = (int) dropClicked.getClientProperty("col");
        model.dropMove(columnClicked);
      }
    };
    
    return dropListener;
  }

  private JPanel gameFrameBoardPanel() {
    gridPanel = new JPanel(new GridLayout(GameInfo.ROW + 1, GameInfo.COL));
    for (int i = 0; i < GameInfo.ROW + 1; i++) {
      for (int j = 0; j < GameInfo.COL; j++) {
        JButton button = new JButton();
        if (i == 0) {
          button.setEnabled(true);
          button.setText("drop");
          button.setForeground(Color.GRAY);
          button.addActionListener(dropButtonListener());
          button.putClientProperty("col", j);
        }else {
          button.setEnabled(false);
          button.setBackground(Color.WHITE);
        }
        button.setOpaque(true);
        gridButtons[i][j] = button;
        gridPanel.add(button);
      }
    }
    return gridPanel;
  }

  
  private JPanel gameFrameTopPanel() {
    gameFrameTopPanel = new JPanel(new BorderLayout());
    reset = new JButton("restart");
    reset.addActionListener(restartListener());
    gameFrameTopPanel.add(reset, BorderLayout.WEST);
    return gameFrameTopPanel;
  }

  /**
   * the method is to launch the game frame where we can play.
   */
  void launchGameFrame() {
    gameFrame = new JFrame("Connect Four");
    gameFrame.setSize(700, 700);
    gameFrame.setLocationRelativeTo(null);
    gameFrame.setResizable(false);
    gameFrame.setLayout(new BorderLayout());
    gameFrame.add(gameFrameBoardPanel(), BorderLayout.CENTER);
    gameFrame.add(gameFrameTopPanel(), BorderLayout.NORTH);
    gameFrame.setVisible(true);
    gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
  
  
  /**
   * the method is to launch a welcome frame for choosing game mode
   */
  void launchWelcomeFrame() {
    welcomeFrame = new JFrame("Connect Four");
    welcomeFrame.setSize(700, 700);
    welcomeFrame.setResizable(false);
    welcomeFrame.setLocationRelativeTo(null);
    welcomeFrame.setLayout(new BorderLayout());
    JPanel welcomeFramePanel = new JPanel(new GridLayout());
    JButton singlePlayer = new JButton("Single Player");
    JButton doublePlayer = new JButton("Double Players");
    
    singlePlayer.addActionListener(modeListener(GameMode.SINGLE_PLAYER));
    doublePlayer.addActionListener(modeListener(GameMode.DOUBLE_PLAYER));
    
    welcomeFramePanel.add(singlePlayer);
    welcomeFramePanel.add(doublePlayer);
    
    welcomeFrame.add(welcomeFramePanel, BorderLayout.CENTER);
    welcomeFrame.setVisible(true);
    welcomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  @Override
  public void move(ConnectFourPlayer player, int row, int col) {
    GameInfo.Color c = player.getColor();
    
    if (c == GameInfo.Color.RED) {
      gridButtons[row + 1][col].setBackground(Color.RED);
    } else if (c == GameInfo.Color.GREEN){
      gridButtons[row + 1][col].setBackground(Color.GREEN);
    }
  }
  

  @Override
  public void restart() {
    gameFrame.setVisible(false);
    gameFrame.dispose();
  }

  @Override
  public void gameDraw() {
    JOptionPane.showMessageDialog(null, "game draw");
  }
  
  @Override
  public void someoneWin(ConnectFourPlayer player) {
    String message = "Player" + player.getID() + " wins";
    if (!player.getType()) {
      message = "computer wins";
    }
    JOptionPane.showMessageDialog(null, message);
    for (int i = 0; i < GameInfo.COL; i++) {
      gridButtons[0][i].setEnabled(false);
    }
  }

  @Override
  public void illegalMove() {
    JOptionPane.showMessageDialog(null, "you cannot drop here");
  }

}
