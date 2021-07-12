package com.jacob.process;

import com.jacob.config.ApplicationSettings;

/*
* Class for app initialization. <br> 
*
* @author FUTURE IOET ENGINEER
*         Jacob Orellana<br>
*         jacobore@hotmail.com<br>
* 
* @date 01/06/2021
* @version 1.0
*/


public class AppInitialization {
	
	/**
	 * Input validations.
	 * 
	 * @author Jacob Orellana<br>
	 * 
	 * @date 12/07/2021
	 * @version 1.0
	 * 
	 * @param args
	 */

	public static void validateInputs(String[] args) throws Exception {

		// valido entrada de parámetros
		if (args.length != 1) {
			throw new Exception("Invalid arguments: [NUMBER OF THREADS]");
		}

	}
	
	/**
	 * Execute the complete initialization.
	 * 
	 * @author Jacob Orellana<br>
	 * 
	 * @date 12/07/2021
	 * @version 1.0
	 * 
	 * @param args
	 */

	public static void initConfig(String[] args) throws Exception {

		// Inicializo settings de la aplicación
		ApplicationSettings.init(args);
	

	}

}
