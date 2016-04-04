package net.sf.memoranda.ui.timer;

public class StopWatchModel
{
  private int hours;
  private int minutes;
  private int seconds;
  
  public StopWatchModel()
  {
    restartStopWatch();
  }
  
  public void restartStopWatch()
  {
    this.hours = 0;
    this.minutes = 0;
    this.seconds = 0;
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
  
  public void timeTick()
  {
    if (this.seconds < 59)
    {
      this.seconds += 1;
    }
    else if (this.minutes < 59)
    {
      this.minutes += 1;
      this.seconds = 0;
    }
    else if (this.hours < 24)
    {
      this.hours += 1;
      this.minutes = 0;
      this.seconds = 0;
    }
    else
    {
      this.hours = 0;
      this.minutes = 0;
      this.seconds = 0;
    }
  }
}
