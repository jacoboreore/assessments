package com.jacob.process;

import java.util.ArrayList;

import com.jacob.bizOperations.IoetLogic;

/*
* Get the process needed DTO to process. <br> 
*
* @author FUTURE IOET ENGINEER
*         Jacob Orellana<br>
*         jacobore@hotmail.com<br>
* 
* @date 01/06/2021
* @version 1.0
*/

public class ProcessOperations {

	private static ArrayList<String> processedEmployees = null;

	public static ArrayList<String> getProcessedEmployees() {
		return processedEmployees;
	}

	public static void processFile(String thisFile) throws Exception {

		IoetLogic logic = new IoetLogic();

		logic.getInformationToProcess(thisFile);
		processedEmployees = logic.generateReportToConsole();

	}

}
