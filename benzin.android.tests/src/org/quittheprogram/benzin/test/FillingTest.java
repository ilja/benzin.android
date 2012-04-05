package org.quittheprogram.benzin.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.quittheprogram.benzin.Filling;

public class FillingTest {
	private Filling filling;
	
	@Before
	public void setUp(){
		filling = new Filling(0, 55);
	}
	
	@Test
	public void shouldCalculateLitresPerKilometer() {		 
		
		filling.setEndOdometer(650);
		assertEquals(11.82, (double)filling.calculateLitresPerKilometer(), 0.0);
	}
	
	@Test
	public void calculateLitresPerKilometerShouldReturnZeroWhenNoPreviousAmountIsSet(){		
		assertEquals(0, (double)filling.calculateLitresPerKilometer(), 0.0);
	}
	
	@Test
	public void shouldCalculateTheDistance(){
		filling.setEndOdometer(700);
		assertEquals(700, filling.getDistance());
	}
	
	@Test
	public void shouldCalculateNoDistanceWhenThePreviousOdometerIsNotSet(){		
		assertEquals(0, filling.getDistance());
	}
	
	@Test
	public void shouldCalculateNoDistanceWhenTheOdometerIsNotSet(){		
		filling = new Filling(0,0);
		assertEquals(0, filling.getDistance());
	}

}
