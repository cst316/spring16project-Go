package net.sf.memoranda.ui.timer;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AlarmPanel
  extends JPanel
{
  private JLabel timeDisplay;
  private JLabel alarmDisplay;
  private JButton enableCommand;
  private JButton setCommand;
  
  public AlarmPanel(ActionListener listener)
  {
    this.enableCommand = new JButton("Disable");
    this.setCommand = new JButton("Set");
    
    this.enableCommand.addActionListener(listener);
    this.setCommand.addActionListener(listener);
    
    Dimension enableCommandDimension = this.enableCommand.getPreferredSize();
    this.enableCommand.setText("Enable");
    this.enableCommand.setPreferredSize(enableCommandDimension);
    
    this.setCommand.setPreferredSize(new Dimension(67, 26));
    
    this.timeDisplay = new JLabel("00:00:00");
    Font displayFont = new Font("", 1, 34);
    this.timeDisplay.setFont(displayFont);
    
    this.alarmDisplay = new JLabel("00:00:00");
    this.alarmDisplay.setForeground(Color.GRAY);
    this.alarmDisplay.setFont(new Font("", 1, 17));
    
    JPanel displayPanel = new JPanel();
    displayPanel.setLayout(new BoxLayout(displayPanel, 0));
    
    JPanel alarmPanel = new JPanel();
    
    alarmPanel.setBorder(BorderFactory.createEmptyBorder());
    alarmPanel.setLayout(new BoxLayout(alarmPanel, 0));
    JPanel commandPanel = new JPanel();
    commandPanel.setLayout(new FlowLayout(1));
    
    displayPanel.add(this.timeDisplay);
    alarmPanel.add(this.alarmDisplay);
    
    commandPanel.add(this.enableCommand);
    commandPanel.add(this.setCommand);
    
    setLayout(new BoxLayout(this, 1));
    add(displayPanel);
    add(alarmPanel);
    add(commandPanel);
  }
  
  public void setTimeDisplay(String display)
  {
    this.timeDisplay.setText(display);
  }
  
  public void setAlarmDisplay(String display)
  {
    this.alarmDisplay.setText(display);
  }
  
  public void setCommandText(String command)
  {
    this.enableCommand.setText(command);
  }
  
  public void setDisabled()
  {
    this.alarmDisplay.setForeground(Color.GRAY);
  }
  
  public void setEnabled()
  {
    Color color = new Color(29, 135, 57);
    this.alarmDisplay.setForeground(color);
  }
}

