package de.se.tinf11b3.breakdown.tests.DUMMY;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

public class Seleniumdummy {

	static Selenium selenium;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		selenium = new DefaultSelenium("localhost", 4444, "*firefox", "http://www.google.com");
		
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
	public void test() {
		selenium.start();
		selenium.open("http://www.google.com/");
		selenium.waitForPageToLoad("30000");
		selenium.waitForPageToLoad("30000");
	
	
	}

}
