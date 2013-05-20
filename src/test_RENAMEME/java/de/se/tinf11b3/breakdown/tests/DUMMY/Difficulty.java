package de.se.tinf11b3.breakdown.tests.DUMMY;

import com.thoughtworks.selenium.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.regex.Pattern;

public class Difficulty {
	private Selenium selenium;

	@Before
	public void setUp() throws Exception {
		selenium = new DefaultSelenium("localhost", 4444, "*firefox", "http://localhost:8080/Breakdown/");
		selenium.start();
	}

	@Test
	public void testDifficulty() throws Exception {
		
		//Leicht
		selenium.open("/Breakdown/");
		selenium.click("css=span.v-button-wrap");	//Spielstart
		selenium.click("css=span.v-button-wrap");	//Leicht
		
		//Mittel
		selenium.open("/Breakdown/");
		selenium.click("css=span.v-button-wrap");	//Spielstart
		selenium.click("//div[@id='Breakdown-106497537']/div/div[2]/div/div/div/div/div/div/div[2]/div/div/span");	//Mittel
		
		//Schwer
		selenium.click("css=span.v-button-wrap");	//Spielstart
		selenium.click("//div[@id='Breakdown-106497537']/div/div[2]/div/div/div/div/div/div/div[3]/div/div/span");	//Schwer
	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
}