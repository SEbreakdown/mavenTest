package de.se.tinf11b3.breakdown.tests;

import cucumber.junit.Cucumber;

import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@Cucumber.Options(format = { "pretty", "html:target/cucumber-html-report" })
public class RunCukesTest {
}
