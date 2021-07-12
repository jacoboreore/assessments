package com.jacob.dto;

import java.util.ArrayList;

public class EmployeeWork {

	private String name;
	private ArrayList<EmployeeWorkedHours> workedHours;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<EmployeeWorkedHours> getWorkedHours() {
		return workedHours;
	}

	public void setWorkedHours(ArrayList<EmployeeWorkedHours> workedHours) {
		this.workedHours = workedHours;
	}

}
