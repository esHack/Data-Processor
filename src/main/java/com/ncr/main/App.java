package com.ncr.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ncr.exception.InvalidInputException;
import com.ncr.processor.DataProcessor;

/**
 * 
 * Sample app to test the library
 * 
 * Will retrieve the results once file is processed
 * 
 * @author eswar
 *
 */
public class App {

	private static final Logger logger = LogManager.getLogger(App.class);

	public static void main(String[] args)  {

		DataProcessor impl = new DataProcessor();
		String fileName = "/input.txt";

		try {
			BufferedReader bufr = new BufferedReader(new InputStreamReader(App.class.getResourceAsStream(fileName)));

			String line;
			while ((line = bufr.readLine()) != null) {

				try {
					impl.saveData(line);
				} catch (InvalidInputException e) {
					logger.warn("Invalid Input:  "+ line);
				}
			}
			bufr.close();

			logger.info("Total Duplicates so far..." + impl.countDuplicates());
			logger.info("Total Unique Groups so far..." + impl.countUniqueSets());
			logger.info("List of Invalid Inputs..." + impl.listOfInvalidInputs());
			logger.info("Frequent Groups so far..." + impl.mostFrequentGroupsTillNow());

		} catch (IOException e) {
			logger.info("File not found " + e.getMessage());
		} 

	}

}
