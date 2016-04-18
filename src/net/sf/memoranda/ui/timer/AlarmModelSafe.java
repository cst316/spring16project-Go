package net.sf.memoranda.ui.timer;

import java.security.InvalidParameterException;

public class AlarmModelSafe extends AlarmModel {

	
	public void setAlarmTime(String time){
		 String safeTime = super.getTime();
		 String[] times = time.split(":");
		 try{
			 if (times.length != 3){
				 throw new InvalidParameterException();
			 }
			 
			 safeTime = formatTimeSafe(Integer.parseInt(times[0]), Integer.parseInt(times[1]), Integer.parseInt(times[2]));//Alexander Code review notes: Longer then 200 characeter

		 }catch(Exception e){
			 System.out.print("Invalid time value: ");
			 if (e.getClass().equals(InvalidParameterException.class)){
				 System.out.print("(string fomat is invalid) ");
			 }else if(e.getClass().equals(NumberFormatException.class)){
				 System.out.print("(numbers in string not valid) ");
			 }
			 System.out.println("setting time to "+ safeTime);
		 }
		 super.setAlarmTime(safeTime);
	  }
	 
	 private String formatTimeSafe(int hours, int minutes, int seconds){
		 int safeHours, safeMinutes, safeSeconds;
		 String safeTime;
		 safeSeconds = seconds % 60;
		 safeMinutes = seconds / 60;
		 safeMinutes += minutes % 60;
		 safeHours = minutes / 60;
		 safeHours += hours % 24;
		 
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
	 public String getAlarmTime(){
		 String[] times = super.getAlarmTime().split(":");
		 return formatTimeSafe(Integer.parseInt(times[0]), Integer.parseInt(times[1]), Integer.parseInt(times[2]));  //Alexander Code review notes: Longer then 200 characeter
		 
	  }
}
