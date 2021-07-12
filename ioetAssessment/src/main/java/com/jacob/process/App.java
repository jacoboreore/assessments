package com.jacob.process;

import com.jacob.config.ApplicationSettings;

/*
* Main class, entry point. <br> 
*
* @author FUTURE IOET ENGINEER
*         Jacob Orellana<br>
*         jacobore@hotmail.com<br>
* 
* @date 01/06/2021
* @version 1.0
*/

public class App {
	public static void main(String[] args) throws Exception {

		try {

			AppInitialization.validateInputs(args);
			AppInitialization.initConfig(args);

			// process the thing
			ProcessOperations.processFile(ApplicationSettings.getFullPathInput());

			for (String s : ProcessOperations.getProcessedEmployees()) {
				System.out.println(s);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

	}
}