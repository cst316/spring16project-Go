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

  public PreferencesDialog pDialog = new PreferencesDialog();

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
    assertTrue(pDialog.minTaskbarRB_isSelected());
    assertTrue(pDialog.closeHideRB_isSelected());
    assertTrue(pDialog.enSystrayChB_isSelected());
    assertTrue(pDialog.startMinimizedChB_isSelected());
    assertFalse(pDialog.minHideRB_isSelected());
    assertFalse(pDialog.closeExitRB_isSelected());
    assertFalse(pDialog.enSplashChB_isSelected());
  }

  @Test
  public void casualUserTest()
  {
    pDialog.casualUserRB_actionPerformed();
    assertTrue(pDialog.minTaskbarRB_isSelected());
    assertTrue(pDialog.closeExitRB_isSelected());
    assertTrue(pDialog.enSplashChB_isSelected());
    assertFalse(pDialog.minHideRB_isSelected());
    assertFalse(pDialog.closeHideRB_isSelected());
    assertFalse(pDialog.enSystrayChB_isSelected());
    assertFalse(pDialog.startMinimizedChB_isSelected());
  }

  @Test
  public void minGroupTest()
  {
    pDialog.minTaskbarRB_setSelected(true);
    pDialog.minHideRB_setSelected(true);
    assertFalse(pDialog.minTaskbarRB_isSelected());
    pDialog.minTaskbarRB_setSelected(true);
    assertFalse(pDialog.minHideRB_isSelected());
  }

  @Test
  public void closeGroupTest()
  {
    pDialog.closeExitRB_setSelected(true);
    pDialog.closeHideRB_setSelected(true);
    assertFalse(pDialog.closeExitRB_isSelected());
    pDialog.closeExitRB_setSelected(true);
    assertFalse(pDialog.closeHideRB_isSelected());
  }
}
