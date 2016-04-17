package net.sf.memoranda.ui.timer;

import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TimerPanel
  extends JPanel
{
  private JLabel timerDisplay;
  private JButton startCommand;
  private JButton resetButton;
  private JButton setButton;
  
  public TimerPanel(ActionListener listener)
  {
    this.startCommand = new JButton("Start");
    this.resetButton = new JButton("Reset");
    this.setButton = new JButton("Set");
    this.startCommand.addActionListener(listener);
    this.resetButton.addActionListener(listener);
    this.setButton.addActionListener(listener);
    
    this.startCommand.setPreferredSize(this.resetButton.getPreferredSize());
    this.setButton.setPreferredSize(this.resetButton.getPreferredSize());
    
    this.timerDisplay = new JLabel("00:00:00");
    this.timerDisplay.setFont(new Font("", 1, 44));
    
    JPanel displayPanel = new JPanel();
    JPanel commandPanel = new JPanel();
    
    displayPanel.add(this.timerDisplay);
    
    commandPanel.add(this.startCommand);
    commandPanel.add(this.resetButton);
    commandPanel.add(Box.createHorizontalStrut(20));
    commandPanel.add(this.setButton);
    
    setLayout(new BoxLayout(this, 1));
    add(displayPanel);
    add(commandPanel);
  }
  
  public void setDisplay(String display)
  {
    this.timerDisplay.setText(display);
  }
  
  public void setCommandText(String command)
  {
    this.startCommand.setText(command);
  }
}
//Alexander Calderon Review Notes: I feel like there should
//be a summery in comment of what each block of code is doing, 
//Though I think the naming the methods helps with that 
