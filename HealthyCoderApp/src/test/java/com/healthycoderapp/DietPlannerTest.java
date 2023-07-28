package com.healthycoderapp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

class DietPlannerTest {

	private DietPlanner dietPlanner;
	
	@BeforeEach
	void setup() {
		this.dietPlanner = new DietPlanner (20,30,50);
	}
	
	@AfterEach
	void afterEach() {
		System.out.println("A unit test was finished.");
	}
	
	@Test
	// if want to repeat test we use bellow annotation
	@RepeatedTest(value = 10, name = RepeatedTest.LONG_DISPLAY_NAME)
	void should_returnCorrectDietPlan_When_CorrectCoder() {
	
		// given
		Coder coder = new Coder(1.82,75.0,26,Gender.MALE);
		DietPlan expected = new DietPlan(2202,110,73,275);
		// when
		DietPlan actual = dietPlanner.calculateDiet(coder);
		
		// then
//		assertEquals(expected, actual); not working since two different objects in memory can not be equal
		assertAll(
		
		() -> assertEquals(expected.getCalories(), actual.getCalories()),
		() -> assertEquals(expected.getProtein(), actual.getProtein()),
		() -> assertEquals(expected.getFat(), actual.getFat()),
		() -> assertEquals(expected.getCarbohydrate(), actual.getCarbohydrate())
		
		);
	}   

}
