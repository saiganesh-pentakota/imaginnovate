package com.imaginnovate.modal;

/**
 * @author saiganesh
 *
 */
public class TaxDeduction {

	private long employeeId = 0;
	private String firstName;
	private String lastName;
	private double yearlySalary;
	private double taxAmount;
	private double cessAmount;

	public TaxDeduction() {
		super();
	}

	public TaxDeduction(long employeeId2, String firstName2, String lastName2, double yearlySalary2, double taxAmount2,
			double cessAmount2) {
		this.employeeId = employeeId2;
		this.firstName = firstName2;
		this.lastName = lastName2;
		this.yearlySalary = yearlySalary2;
		this.taxAmount = taxAmount2;
		this.cessAmount = cessAmount2;
	}

	public long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public double getYearlySalary() {
		return yearlySalary;
	}

	public void setYearlySalary(double yearlySalary) {
		this.yearlySalary = yearlySalary;
	}

	public double getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(double taxAmount) {
		this.taxAmount = taxAmount;
	}

	public double getCessAmount() {
		return cessAmount;
	}

	public void setCessAmount(double cessAmount) {
		this.cessAmount = cessAmount;
	}

}
