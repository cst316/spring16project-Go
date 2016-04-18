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
import net.sf.memoranda.ui.timer.TimerModelSafe;

import static org.junit.Assert.*;

import net.sf.memoranda.ui.timer.AlarmModel;

public class TimerModelTest {
	public TimerModel timerModel;
	public Class baseClass;
	
	//execute for each test, before executing test
	@Before
	public void before(){
		timerModel = new TimerModelSafe();
		baseClass = timerModel.getClass();
		while (baseClass.equals(TimerModel.class)== false){
			baseClass = baseClass.getSuperclass();
		}
	}
		
	@Test
	public void restartTimerTest() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		int hours, minutes, seconds;
		Field field;
		String time = "00:02:00";
		
		timerModel.restartTimer();
		
		
		field = baseClass.getDeclaredField("hours");
		field.setAccessible(true);
		hours = field.getInt(timerModel);
		
		field = baseClass.getDeclaredField("minutes");
		field.setAccessible(true);
		minutes = field.getInt(timerModel);
		
		field = baseClass.getDeclaredField("seconds");
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
		
		Field field = baseClass.getDeclaredField("hours");
		field.setAccessible(true);
		hours = field.getInt(timerModel);
		
		field = baseClass.getDeclaredField("minutes");
		field.setAccessible(true);
		minutes = field.getInt(timerModel);
		
		field = baseClass.getDeclaredField("seconds");
		field.setAccessible(true);
		seconds = field.getInt(timerModel);
		
		assertEquals((int)5, hours);
		assertEquals((int)5, minutes);
		assertEquals((int)5, seconds);
		
		timerModel.setTime("20:61:61");
		
		field = baseClass.getDeclaredField("hours");
		field.setAccessible(true);
		hours = field.getInt(timerModel);
		
		field = baseClass.getDeclaredField("minutes");
		field.setAccessible(true);
		minutes = field.getInt(timerModel);
		
		field = baseClass.getDeclaredField("seconds");
		field.setAccessible(true);
		seconds = field.getInt(timerModel);
		
		assertEquals((int)21, hours);
		assertEquals((int)2, minutes);
		assertEquals((int)1, seconds);
	}
	
	@Test
	public void getTimeTest() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		Field field = baseClass.getDeclaredField("hours");
		field.setAccessible(true);
		field.setInt(timerModel, 5);
		
		field = baseClass.getDeclaredField("minutes");
		field.setAccessible(true);
		field.setInt(timerModel, 5);
		
		field = baseClass.getDeclaredField("seconds");
		field.setAccessible(true);
		field.setInt(timerModel, 5);
		
		assertEquals("05:05:05", timerModel.getTime());
		
		field = baseClass.getDeclaredField("hours");
		field.setAccessible(true);
		field.setInt(timerModel, 20);
		
		field = baseClass.getDeclaredField("minutes");
		field.setAccessible(true);
		field.setInt(timerModel, 61);
		
		field = baseClass.getDeclaredField("seconds");
		field.setAccessible(true);
		field.setInt(timerModel, 61); 
		
		assertEquals("21:02:01", timerModel.getTime());
	}

}
