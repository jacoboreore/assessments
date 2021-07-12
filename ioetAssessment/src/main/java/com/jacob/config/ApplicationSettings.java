package com.jacob.config;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

import com.jacob.dto.PayByHourRangeDto;
import com.jacob.miscellaneous.FileUtil;

/**
 * This class set all the config. <br>
 * 
 * @author FUTURE IOET ENGINEER Jacob Orellana<br>
 *         jacobore@hotmail.com<br>
 * 
 * @date 01/06/2021
 * @version 1.0
 */
public final class ApplicationSettings {

	private static boolean isInitialized = false;

	private static String home;
	private static String threads;
	private static String fullPathLogs;
	private static String fullPathInput;
	private static String fullPathOutput;
	private static String soEol;
	private static ArrayList<PayByHourRangeDto> payByHourRangeSettings;

	public static boolean isInitialized() {
		return isInitialized;
	}

	public static String getHome() {
		return home;
	}

	public static String getThreads() {
		return threads;
	}

	public static String getFullPathLogs() {
		return fullPathLogs;
	}

	public static String getFullPathInput() {
		return fullPathInput;
	}

	public static String getFullPathOutput() {
		return fullPathOutput;
	}

	public static ArrayList<PayByHourRangeDto> getPayByHourRangeSettings() {
		return payByHourRangeSettings;
	}

	/**
	 * Method Initialize necessary things
	 * 
	 * 
	 * @author FUTURE IOET ENGINEER Jacob Orellana<br>
	 *         jacobore@hotmail.com<br>
	 * 
	 * @date 02/06/2021
	 * @version 1.0
	 * @param args
	 * @throws Exception
	 * 
	 */

	public static void init(String[] args) throws Exception {

		home = args[0];

		if (!home.endsWith("/"))
			home += "/";

		if (!home.endsWith("\\"))
			home += "/";

		// get the file to process
		fullPathInput = home + "a_jacob_sample.txt";

		// the line separator for this OS
		soEol = System.lineSeparator();

		// get the pay by hour range
		String payByDayFilePath = home + "pay_by_day.txt";

		// init
		payByHourRangeSettings = new ArrayList<PayByHourRangeDto>();

		// **********************************************************
		// Good luck
		// **********************************************************

		String textFile = FileUtil.readCompleteFile(payByDayFilePath);

		// read the file
		String[] parts;

		// to rerpot malformation
		int lineCounter = 1;

		// ************* parse the configuration
		parts = textFile.split(soEol);

		for (String line : parts) {
			try {
				PayByHourRangeDto newEntry = configureByPayHourRange(line);
				if (newEntry != null) {
					payByHourRangeSettings.add(newEntry);
				}
			} catch (Exception e) {

				// Report a detailed Exception for tracking
				System.out.println("FATAL Error in pay_by_day.txt, line " + lineCounter + " TEXT: " + line);
				System.out.println(e.getMessage());

				// force an exit to the main point
				throw new Exception(e);
			}
		}

		isInitialized = true;

	}

	/**
	 * Method attempts to build a Pay By Hour Range DTO, if the information is
	 * inconsistent, a fatal error is reported
	 * 
	 * @author FUTURE IOET ENGINEER Jacob Orellana<br>
	 *         jacobore@hotmail.com<br>
	 * 
	 * @date 02/06/2021
	 * @version 1.0
	 * @param fromThisInformation
	 * @throws Exception
	 * 
	 */
	private static PayByHourRangeDto configureByPayHourRange(String fromThisInformation) throws Exception {

		PayByHourRangeDto pbhr = new PayByHourRangeDto();

		// this is just a comment
		if (fromThisInformation.startsWith(Constants.COMMENT)) {
			return null;
		}

		// validate the lenght of the string
		int len = fromThisInformation.length();
		if (len != Constants.PERFECT_PAY_BY_HOUR_RANGE_STRING.length()) {
			throw new Exception("Malformed String in the pay by day configuration");
		}

		// inits
		String[] parts;
		int partCounter = 0;
		parts = fromThisInformation.split(Constants.TAB_REGEX);
		partCounter = 1;
		SimpleDateFormat hhmm = null;

		// transform the line from the file in a programmable way to operate with the
		// data
		for (String s : parts) {
			switch (partCounter) {

			// day
			case 1:
				// check a valid entry
				if (!Arrays.asList(Constants.DAYS).contains(s)) {
					throw new Exception("Invalid day at line: " + fromThisInformation);
				}

				// set the day
				pbhr.setDay(s);

				break;

			// start hour
			case 2:

				// parse the hour minute
				hhmm = new SimpleDateFormat(Constants.HOUR_MINUTE_FORMAT);
				hhmm.parse(s);

				// set the from hour
				pbhr.setFromHour(s);

				break;

			// end hour
			case 3:

				// parse the hour minute
				hhmm = new SimpleDateFormat(Constants.HOUR_MINUTE_FORMAT);
				hhmm.parse(s);

				// set the end hour
				pbhr.setToHour(s);

				break;

			// value
			case 4:

				// parse vale, will throw an exception if something is wrong
				float value = Float.valueOf(s);

				// set the value
				pbhr.setValue(value);

				break;
			}

			partCounter++;
		}

		return pbhr;

	}
}