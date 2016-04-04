package net.sf.memoranda.ui.timer;


import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StopWatchPanel
  extends JPanel
{
  private JLabel stopWatchDisplay;
  private JButton startCommand;
  private JButton resetButton;
  
  public StopWatchPanel(ActionListener listener)
  {
    this.startCommand = new JButton("Start");
    this.resetButton = new JButton("Reset");
    
    this.startCommand.addActionListener(listener);
    this.resetButton.addActionListener(listener);
    
    this.startCommand.setPreferredSize(this.resetButton.getPreferredSize());
    
    this.stopWatchDisplay = new JLabel("00:00:00");
    this.stopWatchDisplay.setFont(new Font("", 1, 44));
    
    JPanel displayPanel = new JPanel();
    JPanel commandPanel = new JPanel();
    commandPanel.setLayout(new FlowLayout(0));
    
    displayPanel.add(this.stopWatchDisplay);
    
    commandPanel.add(this.startCommand);
    commandPanel.add(this.resetButton);
    
    setLayout(new BoxLayout(this, 1));
    add(displayPanel);
    add(commandPanel);
  }
  
  public void setDisplay(String display)
  {
    this.stopWatchDisplay.setText(display);
  }
  
  public void setCommandText(String command)
  {
    this.startCommand.setText(command);
  }
}
