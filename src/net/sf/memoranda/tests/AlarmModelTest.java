package net.sf.memoranda.tests;

import org.junit.*;
import static org.junit.Assert.*;
import java.lang.reflect.*;
import java.util.Calendar;
import net.sf.memoranda.ui.timer.AlarmModel;
import net.sf.memoranda.ui.timer.AlarmModelSafe;


public class AlarmModelTest {
	public AlarmModel alarmModel;
	public Class baseClass;
	
	//execute for each test, before executing test
	@Before
	public void before(){
		alarmModel = new AlarmModelSafe();
		baseClass = alarmModel.getClass();
		while (baseClass.equals(AlarmModel.class)== false){
			baseClass = baseClass.getSuperclass();
		}
	}
	
	@Test
	public void setAlarmTimeTest() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		
		int hours, minutes, seconds;
		
		alarmModel.setAlarmTime("05:05:05");
		
		Field field = baseClass.getDeclaredField("hours");
		field.setAccessible(true);
		hours = field.getInt(alarmModel);
		
		field = baseClass.getDeclaredField("minutes");
		field.setAccessible(true);
		minutes = field.getInt(alarmModel);
		
		field = baseClass.getDeclaredField("seconds");
		field.setAccessible(true);
		seconds = field.getInt(alarmModel);
		
		assertEquals((int)5, hours);
		assertEquals((int)5, minutes);
		assertEquals((int)5, seconds);
		
		alarmModel.setAlarmTime("20:61:61");
		
		field = baseClass.getDeclaredField("hours");
		field.setAccessible(true);
		hours = field.getInt(alarmModel);
		
		field = baseClass.getDeclaredField("minutes");
		field.setAccessible(true);
		minutes = field.getInt(alarmModel);
		
		field = baseClass.getDeclaredField("seconds");
		field.setAccessible(true);
		seconds = field.getInt(alarmModel);
		
		assertEquals((int)21, hours);
		assertEquals((int)2, minutes);
		assertEquals((int)1, seconds);
		
		
	}
	
	@Test
	public void getAlarmTimeTest() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		Field field = baseClass.getDeclaredField("hours");
		field.setAccessible(true);
		field.setInt(alarmModel, 5);
		
		field = baseClass.getDeclaredField("minutes");
		field.setAccessible(true);
		field.setInt(alarmModel, 5);
		
		field = baseClass.getDeclaredField("seconds");
		field.setAccessible(true);
		field.setInt(alarmModel, 5);
		
		assertEquals("05:05:05", alarmModel.getAlarmTime());
		
		field = baseClass.getDeclaredField("hours");
		field.setAccessible(true);
		field.setInt(alarmModel, 20);
		
		field = baseClass.getDeclaredField("minutes");
		field.setAccessible(true);
		field.setInt(alarmModel, 61);
		
		field = baseClass.getDeclaredField("seconds");
		field.setAccessible(true);
		field.setInt(alarmModel, 61); 
		
		assertEquals("21:02:01", alarmModel.getAlarmTime());
		
	}
	@Test
	public void enableTest() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		alarmModel.enable();
		Field field = baseClass.getDeclaredField("enabled");
		field.setAccessible(true);
		assertTrue(field.getBoolean(alarmModel));
	}
	@Test
	public void disableTest() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		alarmModel.disable();
		Field field = baseClass.getDeclaredField("enabled");
		field.setAccessible(true);
		assertFalse(field.getBoolean(alarmModel));	
	}
	
}
