package com.ncr.processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.ncr.exception.InvalidInputException;

/**
 * 
 * Implementation of IDataProcessor
 * 
 * @author yaga
 *
 */
public class DataProcessor implements IDataProcessor {

	private static final String VALID_INPUT_REGEX = "\\d+(?:,\\s*\\d+)*";
	private Map<String, Integer> dataMap = new HashMap<String, Integer>();
	private List<String> listOfInvalidInputs = new ArrayList<String>();

	/**
	 * 
	 * Will save the data to a map
	 * 
	 * throws exception in case of a invalid input
	 * 
	 * returns true/false if data is unique/duplicate
	 * 
	 */
	@Override
	public boolean saveData(String data) throws InvalidInputException {
		if (!validate(data)) {
			listOfInvalidInputs.add(data);
			throw new InvalidInputException("Invaild Input");
		}
		String sortedData = sortData(data);
		if (dataMap.containsKey(sortedData)) {
			dataMap.put(sortedData, dataMap.get(sortedData) + 1);
			return false;
		} else {
			dataMap.put(sortedData, 1);
			return true;
		}
	}

	/**
	 * 
	 * returns the number of duplicates till now
	 * 
	 */
	@Override
	public int countDuplicates() {
		int count = 0;
		for (Entry<String, Integer> entry : dataMap.entrySet()) {
			if (entry.getValue() > 1)
				count++;
		}
		return count;
	}

	/**
	 * 
	 * returns List of most frequent groups
	 */
	@Override
	public List<String> mostFrequentGroupsTillNow() {

		List<String> listOfMostFreqGrps = new ArrayList<String>();
		int maxValueInMap = Collections.max(dataMap.values());
		for (Entry<String, Integer> entry : dataMap.entrySet()) {
			if (entry.getValue() == maxValueInMap)
				listOfMostFreqGrps.add(entry.getKey());
		}
		return listOfMostFreqGrps;
	}

	@Override
	public int countUniqueSets() {
		int count = 0;
		for (Entry<String, Integer> entry : dataMap.entrySet()) {
			if (entry.getValue() == 1)
				count++;
		}
		return count;
	}

	@Override
	public List<String> listOfInvalidInputs() {
		return listOfInvalidInputs;
	}

	/**
	 * 
	 * Method returns the sorted data
	 * 
	 * @param data
	 * @return
	 */
	private String sortData(String data) {
		String[] strArr = data.split(",\\s*");
		int[] array = Arrays.stream(strArr).mapToInt(Integer::parseInt).toArray();
		Arrays.sort(array);
		String element = (Arrays.toString(array));
		return element.substring(1, element.length() - 1);
	}

	/**
	 * Method to check if the input is valid or not
	 * 
	 * @param data
	 * @return
	 */
	public boolean validate(String data) {
		return data.matches(VALID_INPUT_REGEX);
	}

}
