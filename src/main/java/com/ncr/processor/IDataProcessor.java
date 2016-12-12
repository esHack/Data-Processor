package com.ncr.processor;

import java.util.List;

import com.ncr.exception.InvalidInputException;

/**
 * @author eswar
 * Library to process group of Strings
 *
 */
public interface IDataProcessor {

	/**
	 * Saves the data to a map
	 * 
	 * @param String
	 * @return boolean true if data is inserted or false if data is already
	 *         present
	 * @throws InvalidInputException
	 */
	boolean saveData(String data) throws InvalidInputException;

	/**
	 * 
	 * returns number of duplicate groups in the map
	 * 
	 * for example [1, 2, 3] is considered as a duplicate of [2, 1, 3]
	 * 
	 * @return integer
	 */
	int countDuplicates();

	/**
	 * 
	 * returns number of unique groups in the map
	 * 
	 * @return
	 */
	int countUniqueSets();

	/**
	 * 
	 * returns most frequent groups. More than one group can exist with the
	 * highest frequency
	 * 
	 * @return List<String>
	 */
	List<String> mostFrequentGroupsTillNow();

	/**
	 * 
	 * @return list of invalid groups
	 * 
	 *         An invalid group can be any string with out following
	 *         \\d+(?:,\\s*\\d+)* format
	 * 
	 *         ex: [1, 2, 3] is valid where as [A, 1, 2] is invalid
	 */
	List<String> listOfInvalidInputs();

}
