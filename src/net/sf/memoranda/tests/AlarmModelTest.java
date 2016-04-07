package net.sf.memoranda.tests;

import org.junit.*;
import static org.junit.Assert.*;
import java.lang.reflect.*;
import java.util.Calendar;
import net.sf.memoranda.ui.timer.AlarmModel;


public class AlarmModelTest {
	public AlarmModel alarmModel;
	
	//execute for each test, before executing test
	@Before
	public void before(){
		alarmModel = new AlarmModel();
	}
	
	@Test
	public void setAlarmTimeTest() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		
		int hours, minutes, seconds;
		
		alarmModel.setAlarmTime("05:05:05");
		
		Field field = alarmModel.getClass().getDeclaredField("hours");
		field.setAccessible(true);
		hours = field.getInt(alarmModel);
		
		field = alarmModel.getClass().getDeclaredField("minutes");
		field.setAccessible(true);
		minutes = field.getInt(alarmModel);
		
		field = alarmModel.getClass().getDeclaredField("seconds");
		field.setAccessible(true);
		seconds = field.getInt(alarmModel);
		
		assertEquals(hours,	(int) 5);
		assertEquals(minutes, (int) 5);
		assertEquals(seconds, (int) 5);
	}
	
	@Test
	public void getAlarmTimeTest() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		Field field = alarmModel.getClass().getDeclaredField("hours");
		field.setAccessible(true);
		field.setInt(alarmModel, 5);
		
		field = alarmModel.getClass().getDeclaredField("minutes");
		field.setAccessible(true);
		field.setInt(alarmModel, 5);
		
		field = alarmModel.getClass().getDeclaredField("seconds");
		field.setAccessible(true);
		field.setInt(alarmModel, 5);
		
		assertEquals(alarmModel.getAlarmTime(), "05:05:05");
		
		field = alarmModel.getClass().getDeclaredField("hours");
		field.setAccessible(true);
		field.setInt(alarmModel, 20);
		
		field = alarmModel.getClass().getDeclaredField("minutes");
		field.setAccessible(true);
		field.setInt(alarmModel, 61);
		
		field = alarmModel.getClass().getDeclaredField("seconds");
		field.setAccessible(true);
		field.setInt(alarmModel, 61); 
		
		assertEquals(alarmModel.getAlarmTime(), "21:02:01");
		
	}
	@Test
	public void enableTest() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		alarmModel.enable();
		Field field = alarmModel.getClass().getDeclaredField("enabled");
		field.setAccessible(true);
		assertTrue(field.getBoolean(alarmModel));
	}
	@Test
	public void disableTest() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		alarmModel.disable();
		Field field = alarmModel.getClass().getDeclaredField("enabled");
		field.setAccessible(true);
		assertFalse(field.getBoolean(alarmModel));	
	}
	
}
