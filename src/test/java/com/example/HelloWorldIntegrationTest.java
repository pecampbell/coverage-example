/**
   Copyright 2010 David Green

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package com.example;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

/**
 * @author GreenD
 *
 */
@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class })
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/conf/applicationContext.xml"})
public class HelloWorldIntegrationTest {
	
	// The webdriver
	private static WebDriver driver;

	@BeforeClass
	public static void initWebDriver() {
		driver = new FirefoxDriver();		
	}
	
	@AfterClass
	public static void stopSeleniumClent() {
		try {
			if(driver != null){
				driver.close();
				driver.quit();				
			}
		} catch( Throwable t ) {
			// Catch error & log, not critical for tests
			System.err.println("Error stopping driver: "+t.getMessage());
			t.printStackTrace(System.err);
		}
	}
	
	@Test
	public void testHelloWorld() {
		// Start from the homepage
		driver.get("http://localhost:9080/helloworld/");
		HomePage homePage = new HomePage(driver);
		HelloWorldPage helloWorldPage = homePage.clickMessageLink();
		
		for(int i=0; i < 100000; i++){
			assertEquals("Hello world",helloWorldPage.getMessage());
		}
	}
	
}
