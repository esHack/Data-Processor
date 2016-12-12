package com.ncr.processor;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.Test;

import com.ncr.exception.InvalidInputException;
import com.ncr.main.App;

/**
 * @author eswar
 *
 *         Class to test the DataProcessor Class and validate its functionality
 */
public class DataProcessorImplTest {

	DataProcessor impl = new DataProcessor();

	/**
	 * 
	 * For testing valid/invalid inputs
	 */
	@Test
	public void testValidate() {

		assertEquals(true, impl.validate("1, 3, 2"));
		assertEquals(false, impl.validate("1,,3"));
		assertEquals(false, impl.validate("1,3,"));
		assertEquals(false, impl.validate(","));
		assertEquals(false, impl.validate("A,B,C"));
		assertEquals(false, impl.validate("----"));
		assertEquals(false, impl.validate(",1,6,78"));
		assertEquals(true, impl.validate("1, 6, 78"));
	}

	/**
	 * 
	 * To test whether data is saved and returns correct count of unique and
	 * duplicate sets
	 * 
	 * @throws InvalidInputException
	 */
	@Test
	public void testSaveData() throws InvalidInputException {

		assertEquals(true, impl.saveData("829"));

		assertEquals(1, impl.countUniqueSets());

		assertEquals(true, impl.saveData("1,3"));
		assertEquals(2, impl.countUniqueSets());
		assertEquals(false, impl.saveData("1,3"));
		assertEquals(1, impl.countDuplicates());
		assertEquals(1, impl.countUniqueSets());

		assertEquals(true, impl.saveData("1,3,2"));
		assertEquals(false, impl.saveData("1,2,3"));
		assertEquals(false, impl.saveData("1,2, 3"));
		assertEquals(2, impl.countDuplicates());
		assertEquals(1, impl.countUniqueSets());

	}

	/**
	 * 
	 * To test Count Duplicate method
	 * 
	 * @throws InvalidInputException
	 */
	@Test
	public void testCountDuplicates() throws InvalidInputException {

		assertEquals(true, impl.saveData("1,3"));
		assertEquals(1, impl.countUniqueSets());
		assertEquals(false, impl.saveData("1,3"));
		assertEquals(1, impl.countDuplicates());

		assertEquals(true, impl.saveData("1,3,2"));
		assertEquals(false, impl.saveData("1,2,3"));
		assertEquals(false, impl.saveData("1,2, 3"));
		assertEquals(2, impl.countDuplicates());
	}

	/**
	 * 
	 * To test if library returns list of invalid inputs
	 * 
	 * @throws InvalidInputException
	 */
	@Test(expected = InvalidInputException.class)
	public void testListOfInvalidInputs() throws InvalidInputException {

		assertEquals(new InvalidInputException("Invalid Input"), impl.saveData("A,B,C"));
		assertEquals(new InvalidInputException("Invalid Input"), impl.saveData("-------"));
		assertEquals(new InvalidInputException("Invalid Input"), impl.saveData(",1,3,2"));
		assertEquals(new InvalidInputException("Invalid Input"), impl.saveData(","));
		assertEquals(new InvalidInputException("Invalid Input"), impl.saveData("THIS IS TEST"));

		assertEquals("A,B,C", impl.listOfInvalidInputs().get(0));
		assertEquals("-------", impl.listOfInvalidInputs().get(1));
		assertEquals(",1,3,2", impl.listOfInvalidInputs().get(2));
		assertEquals(",", impl.listOfInvalidInputs().get(3));
		assertEquals("THIS IS TEST", impl.listOfInvalidInputs().get(4));
	}

	@Test
	public void testMostFrequetGroupTillNow() throws InvalidInputException {

		assertEquals(true, impl.saveData("1,12,13,14"));
		assertEquals(false, impl.saveData("12,1,13,14"));
		assertEquals(false, impl.saveData("13,14,12,1"));
		assertEquals(false, impl.saveData("14,13,1,12"));
		assertEquals(false, impl.saveData("14,1,13,12"));

		assertEquals("1, 12, 13, 14", impl.mostFrequentGroupsTillNow().get(0));

	}

	/**
	 * 
	 * Method to test the Input file in the resources folder and returns number
	 * of unique/duplicate groups.
	 * 
	 * @throws URISyntaxException
	 * @throws InvalidInputException
	 */
	@Test(expected = InvalidInputException.class)
	public void testInputFile() throws URISyntaxException, InvalidInputException {

		DataProcessor impl = new DataProcessor();
		String fileName = "/input.txt";

		try {
			File file = new File(App.class.getResource(fileName).toURI());
			FileReader fr = new FileReader(file);
			BufferedReader bufr = new BufferedReader(fr);
			String line;
			while ((line = bufr.readLine()) != null) {
				impl.saveData(line);
			}
			bufr.close();

			assertEquals(25, impl.countDuplicates());
			assertEquals(1, impl.countUniqueSets());
			assertEquals(",,,,", impl.listOfInvalidInputs().get(0));
			assertEquals("A, B, C", impl.listOfInvalidInputs().get(1));
			assertEquals("3, 5, 11, 23, 24, 88, 189", impl.mostFrequentGroupsTillNow().get(0));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

}
