package net.sf.memoranda.ui.timer;

import java.security.InvalidParameterException;

public class TimerModelSafe extends TimerModel {
	
	 public TimerModelSafe(){
		 restartTimer();
	  }
	 
	 public void restartTimer(){
		 setTime(loadTimeValue());
	  }
	 
	 public void setTime(String time){
		 String safeTime = "00:02:00";
		 String[] times = time.split(":");
		 try{
			 if (times.length != 3){
				 throw new InvalidParameterException();
			 }
			 
			 safeTime = formatTimeSafe(Integer.parseInt(times[0]), Integer.parseInt(times[1]), Integer.parseInt(times[2]));

		 }catch(Exception e){
			 System.out.print("Invalid time value: ");
			 if (e.getClass().equals(InvalidParameterException.class)){
				 System.out.print("(string fomat is invalid) ");
			 }else if(e.getClass().equals(NumberFormatException.class)){
				 System.out.print("(numbers in string not valid) ");
			 }
			 System.out.println("setting time to "+ safeTime);
		 }
		 super.setTime(safeTime);
	  }
	 
	 private String formatTimeSafe(int hours, int minutes, int seconds){
		 int safeHours, safeMinutes, safeSeconds;
		 String safeTime;
		 safeSeconds = seconds % 60;
		 safeMinutes = seconds / 60;
		 safeMinutes += minutes % 60;
		 safeHours = minutes / 60;
		 safeHours += hours % 100;
		 
		 if (safeHours < 10) {
		      safeTime = "0" + safeHours;   
		 }else{
		      safeTime = String.valueOf(safeHours);   
		 }
		 if (safeMinutes < 10) {
		      safeTime = safeTime + ":0" + safeMinutes;   
		 }else{
		      safeTime = safeTime + ":" + safeMinutes;   
		 }
		 if (safeSeconds < 10) {
		      safeTime = safeTime + ":0" + safeSeconds;  
		 }else{
		      safeTime = safeTime + ":" + safeSeconds;   
		 }
		 return safeTime;
	 }
	 
	 public String getTime(){
		 String[] times = super.getTime().split(":");
		 return formatTimeSafe(Integer.parseInt(times[0]), Integer.parseInt(times[1]), Integer.parseInt(times[2]));
		 
	  }
	

}
