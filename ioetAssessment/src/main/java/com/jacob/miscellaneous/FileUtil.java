package com.jacob.miscellaneous;

import java.io.File;
import java.io.FileInputStream;


import com.jacob.config.Constants;

/*
* Class for File operations. <br> 
*
* @author FUTURE IOET ENGINEER
*         Jacob Orellana<br>
*         jacobore@hotmail.com<br>
* 
* @date 01/06/2021
* @version 1.0
*/

public class FileUtil {

	/**
	 * Read a file.
	 * 
	 * @author Jacob Orellana<br>
	 * 
	 * @date 12/07/2021
	 * @version 1.0
	 * 
	 * @param thisFile
	 */

	public static String readCompleteFile(String thisFile) throws Exception {

		File file = new File(thisFile);
		FileInputStream fis = new FileInputStream(file);
		byte[] data = new byte[(int) file.length()];
		fis.read(data);
		fis.close();

		String toReturn = new String(data, Constants.UTF_8);
		return toReturn;

	}

}
