package net.sf.memoranda.tests;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TimingTest {
	public static void main(String[] args) {
		TimerModelTest test = new TimerModelTest();
		try {
			test.before();
			test.restartTimerTest();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Result result = JUnitCore.runClasses(TimerModelTest.class);
		for (Failure failure : result.getFailures()) {
	         System.out.println(failure.toString());
	      }
	      System.out.println(result.wasSuccessful());
	}

}
