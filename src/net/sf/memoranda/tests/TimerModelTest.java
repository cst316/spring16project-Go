package net.sf.memoranda.tests;

import org.junit.*;
import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.*;
import java.util.Calendar;
import net.sf.memoranda.ui.timer.TimerModel;

import static org.junit.Assert.*;

import net.sf.memoranda.ui.timer.AlarmModel;

public class TimerModelTest {
	public TimerModel timerModel;
	
	//execute for each test, before executing test
	@Before
	public void before(){
		timerModel = new TimerModel();
	}
		
	@Test
	public void restartTimerTest() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		int hours, minutes, seconds;
		Field field;
		String time = "00:02:00";
		
		timerModel.restartTimer();
		
		field = timerModel.getClass().getDeclaredField("hours");
		field.setAccessible(true);
		hours = field.getInt(timerModel);
		
		field = timerModel.getClass().getDeclaredField("minutes");
		field.setAccessible(true);
		minutes = field.getInt(timerModel);
		
		field = timerModel.getClass().getDeclaredField("seconds");
		field.setAccessible(true);
		seconds = field.getInt(timerModel);
		
	    try
	    {
	      BufferedReader fileReader = new BufferedReader(new FileReader("properties.txt"));
	      time = fileReader.readLine();
	      fileReader.close();
	    }
	    catch (FileNotFoundException e){}
	    catch (Exception e)
	    {
	      e.printStackTrace();
	    }
	    
	    assertEquals(Integer.parseInt(time.substring(0, 2)), hours);
	    assertEquals(Integer.parseInt(time.substring(3, 5)), minutes);
	    assertEquals(Integer.parseInt(time.substring(6, 8)), seconds);
	}
	
	@Test
	public void setTimeTest() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		
		int hours, minutes, seconds;
		
		timerModel.setTime("05:05:05");
		
		Field field = timerModel.getClass().getDeclaredField("hours");
		field.setAccessible(true);
		hours = field.getInt(timerModel);
		
		field = timerModel.getClass().getDeclaredField("minutes");
		field.setAccessible(true);
		minutes = field.getInt(timerModel);
		
		field = timerModel.getClass().getDeclaredField("seconds");
		field.setAccessible(true);
		seconds = field.getInt(timerModel);
		
		assertEquals(hours,	(int) 5);
		assertEquals(minutes, (int) 5);
		assertEquals(seconds, (int) 5);
	}
	
	@Test
	public void getTimeTest() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		Field field = timerModel.getClass().getDeclaredField("hours");
		field.setAccessible(true);
		field.setInt(timerModel, 5);
		
		field = timerModel.getClass().getDeclaredField("minutes");
		field.setAccessible(true);
		field.setInt(timerModel, 5);
		
		field = timerModel.getClass().getDeclaredField("seconds");
		field.setAccessible(true);
		field.setInt(timerModel, 5);
		
		assertEquals(timerModel.getTime(), "05:05:05");
		
		field = timerModel.getClass().getDeclaredField("hours");
		field.setAccessible(true);
		field.setInt(timerModel, 20);
		
		field = timerModel.getClass().getDeclaredField("minutes");
		field.setAccessible(true);
		field.setInt(timerModel, 61);
		
		field = timerModel.getClass().getDeclaredField("seconds");
		field.setAccessible(true);
		field.setInt(timerModel, 61); 
		
		assertEquals(timerModel.getTime(), "21:02:01");
	}

}
