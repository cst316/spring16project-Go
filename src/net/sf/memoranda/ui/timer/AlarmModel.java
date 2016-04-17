package net.sf.memoranda.ui.timer;

import java.util.Calendar;

public class AlarmModel
{
  private int hours;
  private int minutes;
  private int seconds;
  private boolean enabled = false;
  
  public void setAlarmTime(String time)
  {
    this.hours = Integer.parseInt(time.substring(0, 2));
    this.minutes = Integer.parseInt(time.substring(3, 5));
    this.seconds = Integer.parseInt(time.substring(6, 8));
  }
  
  public String getTime()
  {
    Calendar calendar = Calendar.getInstance();
    int hours = calendar.get(11);
    int minutes = calendar.get(12);
    int seconds = calendar.get(13);
    String time;
    
    if (hours < 10) {
      time = "0" + hours;
    } else {
      time = String.valueOf(hours);
    }
    if (minutes < 10) {
      time = time + ":0" + minutes;
    } else {
      time = time + ":" + minutes;
    }
    if (seconds < 10) {
      time = time + ":0" + seconds;
    } else {
      time = time + ":" + seconds;
    }
    return time;
  }
  
  public String getAlarmTime()
  {
    String time;
    
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
    Calendar calendar = Calendar.getInstance();
    int hours = calendar.get(11);
    int minutes = calendar.get(12);
    int seconds = calendar.get(13);
    
    //Alexander Calderon Notes: Possible to do this.hour hour = new this.hour or timeUp = this.hour && this.minutes && this.seconds
    //to make this likes of code shorter?
    if ((this.hours == hours) && (this.minutes == minutes) && (this.seconds == seconds) && (this.enabled)) {
      return true;
    }
    return false;
  }
  
  public void enable()
  {
    this.enabled = true;
  }
  
  public void disable()
  {
    this.enabled = false;
  }
}
