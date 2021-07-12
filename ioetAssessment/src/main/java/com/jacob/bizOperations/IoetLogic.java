package com.jacob.bizOperations;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;

import com.jacob.config.ApplicationSettings;
import com.jacob.config.Constants;
import com.jacob.dto.EmployeeWork;
import com.jacob.dto.EmployeeWorkedHours;
import com.jacob.dto.PayByHourRangeDto;
import com.jacob.miscellaneous.FileUtil;

/*
* Class for biz logc. <br> 
*
* @author FUTURE IOET ENGINEER
*         Jacob Orellana<br>
*         jacobore@hotmail.com<br>
* 
* @date 01/06/2021
* @version 1.0
*/

public class IoetLogic {

	// all employees as a class
	ArrayList<EmployeeWork> allEmployees;

	/**
	 * Process the employees.
	 * 
	 * @author Jacob Orellana<br>
	 * 
	 * @date 02/06/20021
	 * @version 1.0
	 * 
	 * 
	 */
	public ArrayList<String> generateReportToConsole() throws Exception {

		ArrayList<String> toReturn = new ArrayList<String>();

		for (EmployeeWork e : allEmployees) {
			String s = processEmployee(e);
			toReturn.add(s);

		}

		return toReturn;

	}

	/**
	 * Read the information from the file and build necessary objects.
	 * 
	 * @author Jacob Orellana<br>
	 * 
	 * @date 02/06/20021
	 * @version 1.0
	 * 
	 * 
	 */
	public void getInformationToProcess(String fromThisFile) throws Exception {

		// System EOL
		String soEol = System.lineSeparator();

		// get the pay by hour range
		String textFile = FileUtil.readCompleteFile(fromThisFile);

		// read the file
		String[] fileLines;

		allEmployees = new ArrayList<EmployeeWork>();

		// parse the file
		fileLines = textFile.split(soEol);

		for (String line : fileLines) {

			EmployeeWork ew = new EmployeeWork();

			// parse the data
			String name = getEmployeeName(line);
			ArrayList<EmployeeWorkedHours> workedHours = getWorkedHours(line);

			ew.setName(name);
			ew.setWorkedHours(workedHours);

			// put this guy or girl to process
			allEmployees.add(ew);

		}

	}

	/**
	 * From a specific line of data, this try to read the employee
	 * 
	 * @author Jacob Orellana<br>
	 * 
	 * @date 02/06/20021
	 * @version 1.0
	 * 
	 * 
	 */
	private String getEmployeeName(String fromThisString) {

		String toReturn = "";

		// get the name from the String (everything before the first "=")
		toReturn = fromThisString.split(Constants.EQUALS_REGEX)[0];

		return toReturn;

	}

	/**
	 * From a specific line of data, this try to read the worked data
	 * 
	 * @author Jacob Orellana<br>
	 * 
	 * @date 02/06/20021
	 * @version 1.0
	 * 
	 * 
	 */
	private ArrayList<EmployeeWorkedHours> getWorkedHours(String fromThisString) {

		ArrayList<EmployeeWorkedHours> toReturn = new ArrayList<EmployeeWorkedHours>();

		// get the worked hours string (everything after the "=")
		String allWorkedHours = fromThisString.split(Constants.EQUALS_REGEX)[1];

		// split the worked hours
		String[] listOfWorkedHours = allWorkedHours.split(Constants.COMMA_REGEX);

		// process worked hours, any errors will cause an exception as we are using
		// indexes
		for (String s : listOfWorkedHours) {

			// get the information in this example format MO10:00-12:00
			String day = s.substring(0, 2);
			String fromHour = s.substring(2, 7);
			String toHour = s.substring(8);

			// create the object
			EmployeeWorkedHours ewh = new EmployeeWorkedHours();
			ewh.setDay(day);
			ewh.setFromHour(fromHour);
			ewh.setToHour(toHour);

			// add To The list
			toReturn.add(ewh);
		}

		return toReturn;

	}

	/**
	 * After parse the input file this process the employee
	 * 
	 * @author Jacob Orellana<br>
	 * 
	 * @date 02/06/20021
	 * @version 1.0
	 * 
	 * 
	 */

	private String processEmployee(EmployeeWork thisEmployee) throws Exception {

		String toReturn = "";
		float total = 0;

		SimpleDateFormat hhmm = null;
		hhmm = new SimpleDateFormat(Constants.HOUR_MINUTE_FORMAT);

		// let check the ranges the employee worked
		for (PayByHourRangeDto pbhr : ApplicationSettings.getPayByHourRangeSettings()) {

			Date configuredFromHour = hhmm.parse(pbhr.getFromHour());
			Date configuredToHour = hhmm.parse(pbhr.getToHour());

			// maybe he/she worked in various ranges in one shot
			EmployeeWorkedHours ewh = null;

			// this operation will take these cases
			// ----> the employee worked in one range
			// ----> the employee takes many ranges in one shot, need to create subranges

			for (EmployeeWorkedHours wh : thisEmployee.getWorkedHours()) {

				// the day he/she worked
				if (pbhr.getDay().contentEquals(wh.getDay())) {

					Date fromHour = hhmm.parse(wh.getFromHour());
					Date toHour = null;

					// ad - hoc case 00:00 hours causes inconsistencies
					if ("00:00".equals(wh.getToHour())) {
						toHour = hhmm.parse("23:59");
					} else {
						toHour = hhmm.parse(wh.getToHour());
					}

					// ad-hoc cases
					// if the fromHour is equal to configuredToHour, it causes an inconsistency.
					// The system will move the time by one minute to catch the next range

					if (fromHour.compareTo(configuredToHour) == 0) {
						fromHour = addAMinute(fromHour, 1);
						String newHour = hhmm.format(fromHour);
						wh.setFromHour(newHour);
						wh.setFixed(true);
					}

					// check the range
					if (fromHour.compareTo(configuredFromHour) >= 0 && fromHour.compareTo(configuredToHour) <= 0) {

						long diff = 0;

						// now check if the final hour is in this range
						if (toHour.compareTo(configuredToHour) <= 0) {
							// great, we do not need to check another range
							// get the total hours
							if (wh.isFixed())
								diff = toHour.getTime() - addAMinute(fromHour, -1).getTime();
							else
								diff = toHour.getTime() - fromHour.getTime();

						} else {

							// No?, I need add a new "subrange" to be calculated in another configured range
							// of hours

							// get the total hours
							if (wh.isFixed())
								diff = configuredToHour.getTime() - addAMinute(fromHour, -1).getTime();
							else
								diff = configuredToHour.getTime() - fromHour.getTime();

							// add a new subrange
							ewh = new EmployeeWorkedHours();
							ewh.setDay(wh.getDay());
							configuredToHour = addAMinute(configuredToHour, 1);
							String newHour = hhmm.format(configuredToHour);
							ewh.setFromHour(newHour);
							ewh.setToHour(wh.getToHour());
							ewh.setFixed(true);
						}

						long diffHours = diff / (60 * 60 * 1000);

						// get the amount
						total += diffHours * pbhr.getValue();
					}
				}
			}

			// Add the additional range to be calculated
			if (ewh != null) {
				thisEmployee.getWorkedHours().add(ewh);
			}

		}

		// OK, let report the result
		toReturn = thisEmployee.getName() + "=" + total;
		return toReturn;

	}

	/**
	 * Add a minute to a Date
	 * 
	 * @author Jacob Orellana<br>
	 * 
	 * @date 02/06/20021
	 * @version 1.0
	 * 
	 * 
	 */

	private Date addAMinute(Date thisDate, long minute) {

		// a java 8 thing
		LocalDateTime ld = thisDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		LocalDateTime newDateTime = ld.plus(Duration.of(minute, ChronoUnit.MINUTES));
		thisDate = Date.from(newDateTime.atZone(ZoneId.systemDefault()).toInstant());

		return thisDate;
	}

}
