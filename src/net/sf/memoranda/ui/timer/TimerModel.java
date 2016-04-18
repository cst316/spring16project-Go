package net.sf.memoranda.ui.timer;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TimerModel
{
  private int hours;
  private int minutes;
  private int seconds;
  
  public TimerModel()
  {
    restartTimer();
  }
  
  public void restartTimer(){
	  setTime(loadTimeValue());
  }
  
  public void setTime(String time)
  {
    this.hours = Integer.parseInt(time.substring(0, 2));
    this.minutes = Integer.parseInt(time.substring(3, 5));
    this.seconds = Integer.parseInt(time.substring(6, 8));
    
    storeTimeValue(time);
  }
  
  public String getTime()
  {
    String time;
    //String time;
    if (this.hours < 10) {
      time = "0" + this.hours;
    } else {
      time = String.valueOf(this.hours);
    }
    if (this.minutes < 10) {
      time = time + ":0" + this.minutes;
    } else {
      time = time + ":" + this.minutes;
    }
    if (this.seconds < 10) {
      time = time + ":0" + this.seconds;
    } else {
      time = time + ":" + this.seconds;
    }
    return time;
  }
  
  public boolean isTimeUp()
  {
    if ((this.hours == 0) && (this.minutes == 0) && (this.seconds == 0)) {
      return true;
    }
    return false;
  }
  
  protected void storeTimeValue(String time)
  {
    try
    {
      BufferedWriter fileWriter = new BufferedWriter(new FileWriter("properties.txt"));
      fileWriter.write(time);
      fileWriter.newLine();
      fileWriter.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
  
  protected String loadTimeValue()
  {
    String result = "00:02:00";
    try
    {
      BufferedReader fileReader = new BufferedReader(new FileReader("properties.txt"));
      result = fileReader.readLine();
      fileReader.close();
    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    return result;
  }
  
  public void timeTick()
  {
    if (!isTimeUp()) {
      if (this.seconds != 0)
      {
        this.seconds -= 1;
      }
      else if (this.minutes != 0)
      {
        this.minutes -= 1;
        this.seconds = 59;
      }
      else
      {
        this.hours -= 1;
        this.minutes = 59;
        this.seconds = 59;
      }
    }
  }
}
