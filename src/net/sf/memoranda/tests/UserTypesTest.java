package net.sf.memoranda.tests;

import static org.junit.Assert.*;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import net.sf.memoranda.ui.PreferencesDialog;

public class UserTypesTest {

  PreferencesDialog pDialog = new PreferencesDialog();
  //ActionEvent e = new ActionEvent();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

  @Test
  public void heavyUserTest()
  {
    pDialog.heavyUserRB_actionPerformed();
    assertTrue(pDialog.heavyUserRB_profiled());
  }

  @Test
  public void casualUserTest()
  {
    pDialog.casualUserRB_actionPerformed();
    assertTrue(pDialog.casualUserRB_profiled());
  }
}
