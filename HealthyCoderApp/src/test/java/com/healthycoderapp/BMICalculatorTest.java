package com.healthycoderapp;

import static org.junit.Assume.assumeTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;


class BMICalculatorTest {
	
	private String environment = "dev";
	
	@BeforeAll
	static void beforAll() {
		System.out.println("Before all unit test");
	}
	
	@AfterAll
	static void afterAll() {
		System.out.println("After all unit test");

	}
	@Nested
	class IsDietRecommendedTests{
		
		@ParameterizedTest(name = "weight={0}, height={1}")
//		@ValueSource(doubles = {92.0,89.0,95.0,110.0}) this option is only for one value. In order to test both valuse we will use anotation listed below this line
//		@CsvSource(value = {"89.0,1.72","95.0,1.75","110.0,1.78"})
		@CsvFileSource(resources = "/diet-recommended-input-data.csv", numLinesToSkip = 1)
		void should_ReturnTrue_When_DietRecommended(Double coderWeight, Double coderHeight) {
			
			// given
			double weight = coderWeight;
			double height = coderHeight;   
			
			// when
			 boolean recommended = BMICalculator.isDietRecommended(weight, height);
			// then

			assertTrue(recommended);
		}
		
		@Test
		void should_ReturnFalse_When_DietRecommended() {
			
			// given
			
			double weight = 50.0;
			double height = 1.92;
			
			// when
			 boolean recommended = BMICalculator.isDietRecommended(weight, height);
			// then

			assertFalse(recommended);
		}
		
		// This is how we can write test method when our method which we are trying to test throws exception ( Executable use form jupiter library)
		@Test
		void should_ThrowArithmeticException_When_HightZero() {
			
			// given
			
			double weight = 50.0;
			double height = 0.0;
			
			// when
			Executable executable = () -> BMICalculator.isDietRecommended(weight, height);
			// then

			assertThrows(ArithmeticException.class, executable);
		}
		
	}

	
	@Nested
	class FindCoderWithWorstMBITests{
		
		@Test
		void should_ReturnCoderWithWorstBMI_When_CoderListNotEmpty() {
			
			// given
			
			List<Coder> coders = new ArrayList();
			coders.add(new Coder(1.80,60.0));
			coders.add(new Coder(1.82,98.0));
			coders.add(new Coder(1.82,64.7));
			// when
		    Coder coderWorstBMI = BMICalculator.findCoderWithWorstBMI(coders);
			// then

		    assertAll(
		    	() -> assertEquals(1.82, coderWorstBMI.getHeight()),
		    	() -> assertEquals(98.0, coderWorstBMI.getWeight())
		    	
		    		);
		
		}
		@Test
		void should_ReturnCoderWithWorstBMIin1Ms_WhenCoderListHas10000Elements() {
			
			// given
			assumeTrue(BMICalculatorTest.this.environment.equals("prod"));
			List<Coder> coders = new ArrayList<Coder>();
			for (int i = 0; i < 1000; i++) {
				coders.add(new Coder(1.0+i, 10.0 + i));
				
			}
			
			// when
			Executable executable = ()-> BMICalculator.findCoderWithWorstBMI(coders);
			
			// then
			assertTimeout(Duration.ofMillis(80), executable);
			
			
		}
		
		
		@Test
		void should_ReturnWorstBMI_When_CoderListEmpty() {
			
			// given
			List<Coder> coders = new ArrayList();
		
			// when
		    Coder coderWorstBMI = BMICalculator.findCoderWithWorstBMI(coders);
			// then

		    assertNull(coderWorstBMI);
		}
		
		
	}
	

	@Nested
	class GetBMIScoresTests{
		@Test
		void should_ReturnCorrctBMIScoreArray_When_CoderListNotEmpty() {
			
			// given
			List<Coder> coders = new ArrayList();
			coders.add(new Coder(1.80,60.0));
			coders.add(new Coder(1.82,98.0));
			coders.add(new Coder(1.82,64.7));
			double[] expected = {18.52,29.59,19.53};

			// when
			double[] bmiScores = BMICalculator.getBMIScores(coders);
		
			// then

//			assertEquals(expected, bmiScores);
			assertArrayEquals(expected, bmiScores);
			
		   
		}
		
		
		
		// SAIC test class
		
//		import org.javatuples.Pair;
//		import org.junit.jupiter.api.Assertions;
//		import org.junit.jupiter.api.BeforeEach;
//		import org.junit.jupiter.api.Test;
//
//		import java.util.ArrayList;
//		import java.util.Arrays;
//		import java.util.List;
//
//		public class PnrTest {
//		    private Pnr pnr;
//
//		    @BeforeEach
//		    public void setUp() {
//		        // Create a new instance of Pnr before each test
//		        pnr = new Pnr();
//		    }
//
//		    @Test
//		    public void testHasDuplicateBookingsNoDuplicates() {
//		        // Test the scenario when there are no duplicate bookings
//		        List<TravelLeg> legs = Arrays.asList(
//		                new TravelLeg("PortA", "PortB"),
//		                new TravelLeg("PortB", "PortC"),
//		                new TravelLeg("PortC", "PortD")
//		        );
//		        TravelLegSequence sequence = new TravelLegSequence(legs);
//		        pnr.setCurrentItineraryLegs(sequence);
//
//		        Pair<Boolean, TravelLeg> result = pnr.hasDuplicateBookingsCheck();
//
//		        Assertions.assertFalse(result.getValue0(), "Expected no duplicates");
//		        Assertions.assertNull(result.getValue1(), "Expected no duplicate leg");
//		    }
//
//		    @Test
//		    public void testHasDuplicateBookingsWithDuplicates() {
//		        // Test the scenario when there are duplicate bookings
//		        List<TravelLeg> legs = Arrays.asList(
//		                new TravelLeg("PortA", "PortB"),
//		                new TravelLeg("PortB", "PortC"),
//		                new TravelLeg("PortC", "PortD"),
//		                new TravelLeg("PortB", "PortC") // Duplicate leg
//		        );
//		        TravelLegSequence sequence = new TravelLegSequence(legs);
//		        pnr.setCurrentItineraryLegs(sequence);
//
//		        Pair<Boolean, TravelLeg> result = pnr.hasDuplicateBookingsCheck();
//
//		        Assertions.assertTrue(result.getValue0(), "Expected duplicates");
//		        Assertions.assertNotNull(result.getValue1(), "Expected a duplicate leg");
//		    }
//
//		    @Test
//		    public void testHasDuplicateBookingsEmptySequence() {
//		        // Test the scenario when the travel leg sequence is empty
//		        TravelLegSequence sequence = new TravelLegSequence(new ArrayList<>());
//		        pnr.setCurrentItineraryLegs(sequence);
//
//		        Pair<Boolean, TravelLeg> result = pnr.hasDuplicateBookingsCheck();
//
//		        Assertions.assertFalse(result.getValue0(), "Expected no duplicates with an empty sequence");
//		        Assertions.assertNull(result.getValue1(), "Expected no duplicate leg with an empty sequence");
//		    }
//
//		    @Test
//		    public void testHasDuplicateBookingsNullSequence() {
//		        // Test the scenario when the travel leg sequence is null
//		        pnr.setCurrentItineraryLegs(null);
//
//		        Pair<Boolean, TravelLeg> result = pnr.hasDuplicateBookingsCheck();
//
//		        Assertions.assertFalse(result.getValue0(), "Expected no duplicates with a null sequence");
//		        Assertions.assertNull(result.getValue1(), "Expected no duplicate leg with a null sequence");
//		    }
//		}
		
	}
	
	
	

	

}
