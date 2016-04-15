package net.sf.memoranda.ui.timer;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.Timer;

import net.sf.memoranda.ui.AppFrame;
import net.sf.memoranda.util.Configuration;

public class GUI
  extends JFrame
{
  private TimerModel timerModel = new TimerModelSafe();
  private StopWatchModel stopWatchModel = new StopWatchModel();
  private AlarmModel alarmModel = new AlarmModel();
  private TimerPanel timerPanel;
  private StopWatchPanel stopWatchPanel;
  private AlarmPanel alarmPanel;
  
  public GUI()
  {
    initialization();
  }
  
  private void initialization()
  {
	  this.setIconImage(new ImageIcon(AppFrame.class.getResource(
              "resources/icons/jnotes16.png"))
              .getImage());
    this.timerPanel = new TimerPanel(new TimerListener());
    this.timerPanel.setDisplay(this.timerModel.getTime());
    
    this.stopWatchPanel = new StopWatchPanel(new StopWatchListener());
    
    this.alarmPanel = new AlarmPanel(new AlarmListener());
    
    JTabbedPane tabbedPane = new JTabbedPane();
    tabbedPane.add("Timer", this.timerPanel);
    tabbedPane.add("StopWatch", this.stopWatchPanel);
    tabbedPane.add("Alarm", this.alarmPanel);
    
    add(tabbedPane, "Center");
    
    setTitle("Timer");
    setVisible(true);
    pack();
  }
  
  protected void processWindowEvent(WindowEvent e) {
      if (e.getID() == WindowEvent.WINDOW_CLOSING) {
    	  this.dispose();
      }
  }
        
 
  
  class TimerListener
    implements ActionListener
  {
    private Timer swingTimerTicker;
    
    TimerListener() {}
    
    public void actionPerformed(ActionEvent e)
    {
      if (e.getActionCommand() == "Start")
      {
        GUI.this.timerPanel.setCommandText("Stop");
        GUI.this.timerPanel.setDisplay(GUI.this.timerModel.getTime());
        this.swingTimerTicker = new Timer(1000, this);
        this.swingTimerTicker.start();
      }
      if (e.getActionCommand() == "Stop")
      {
        GUI.this.timerPanel.setCommandText("Start");
        this.swingTimerTicker.stop();
      }
      if (e.getActionCommand() == "Reset")
      {
        GUI.this.timerModel.restartTimer();
        GUI.this.timerPanel.setDisplay(GUI.this.timerModel.getTime());
      }
      if (e.getActionCommand() == "Set")
      {
        String s = JOptionPane.showInputDialog("Input timer time", GUI.this.timerModel.getTime());
        GUI.this.timerModel.setTime(s);
        GUI.this.timerPanel.setDisplay(GUI.this.timerModel.getTime());
      }
      if (e.getSource() == this.swingTimerTicker) {
        if (GUI.this.timerModel.isTimeUp())
        {
          this.swingTimerTicker.stop();
          GUI.this.timerModel.restartTimer();
          GUI.this.timerPanel.setDisplay(GUI.this.timerModel.getTime());
          GUI.this.timerPanel.setCommandText("Start");
          
          new Thread(new Runnable()
          {
            public void run()
            {
              new SoundEngine().playSound();
            }
          })
          
            .start();
        }
        else
        {
          GUI.this.timerModel.timeTick();
          GUI.this.timerPanel.setDisplay(GUI.this.timerModel.getTime());
        }
      }
    }
  }
  
  class StopWatchListener
    implements ActionListener
  {
    private Timer swingTimerTicker;
    
    StopWatchListener() {}
    
    public void actionPerformed(ActionEvent e)
    {
      if (e.getActionCommand() == "Start")
      {
        GUI.this.stopWatchPanel.setCommandText("Stop");
        GUI.this.stopWatchPanel.setDisplay(GUI.this.stopWatchModel.getTime());
        this.swingTimerTicker = new Timer(1000, this);
        this.swingTimerTicker.start();
      }
      if (e.getActionCommand() == "Stop")
      {
        GUI.this.stopWatchPanel.setCommandText("Start");
        this.swingTimerTicker.stop();
      }
      if (e.getActionCommand() == "Reset")
      {
        GUI.this.stopWatchModel.restartStopWatch();
        GUI.this.stopWatchPanel.setDisplay(GUI.this.stopWatchModel.getTime());
      }
      if (e.getSource() == this.swingTimerTicker)
      {
        GUI.this.stopWatchModel.timeTick();
        GUI.this.stopWatchPanel.setDisplay(GUI.this.stopWatchModel.getTime());
      }
    }
  }
  
  class AlarmListener
    implements ActionListener
  {
    private Timer swingTimerTicker;
    
    public AlarmListener()
    {
      this.swingTimerTicker = new Timer(1000, this);
      this.swingTimerTicker.start();
    }
    
    public void actionPerformed(ActionEvent e)
    {
      if (e.getActionCommand() == "Enable")
      {
        GUI.this.alarmPanel.setCommandText("Disable");
        GUI.this.alarmPanel.setEnabled();
        GUI.this.alarmModel.enable();
      }
      if (e.getActionCommand() == "Disable")
      {
        GUI.this.alarmPanel.setCommandText("Enable");
        GUI.this.alarmPanel.setDisabled();
        GUI.this.alarmModel.disable();
      }
      if (e.getActionCommand() == "Set")
      {
        String s = JOptionPane.showInputDialog("Input timer time", GUI.this.alarmModel.getTime());
        GUI.this.alarmModel.setAlarmTime(s);
        GUI.this.alarmPanel.setAlarmDisplay(GUI.this.alarmModel.getAlarmTime());
      }
      if (e.getSource() == this.swingTimerTicker)
      {
        GUI.this.alarmPanel.setTimeDisplay(GUI.this.alarmModel.getTime());
        if (GUI.this.alarmModel.isTimeUp()) {
          new Thread(new Runnable()
          {
            public void run()
            {
              new SoundEngine().playSound();
            }
          })
          
            .start();
        }
      }
    }
  }
}
