package com.imaginnovate.controller;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.imaginnovate.exception.AppError;
import com.imaginnovate.modal.Employee;
import com.imaginnovate.modal.ErrorResponse;
import com.imaginnovate.modal.TaxDeduction;

/**
 * @author saiganesh
 *
 */
@RestController
@RequestMapping("/api")
public class EmployeeController {

	private final Map<Long, Employee> empMap = new ConcurrentHashMap<>();

	/**
	 * Method for creating the employee
	 * 
	 * @param employee
	 * @return
	 */
	@PostMapping("/createEmployee")
	public ResponseEntity<?> createEmployee(@RequestBody Employee employee) {
		String error = doCheckError(employee);
		if (StringUtils.isNotBlank(error)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(error));
		}
		empMap.put(employee.getEmployeeId(), employee);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@GetMapping("/getTaxDeduction")
	public ResponseEntity<?> getTaxDeductionsForCurrentYear() {
		List<TaxDeduction> taxDeductions = new ArrayList<>();

		// Calculate tax deductions for each employee..
		empMap.values().forEach(employee -> {

			double yearlySalary = calculateYearlySalary(employee);
			double taxAmount = calculateTaxAmount(yearlySalary);
			double cessAmount = calculateCessAmount(yearlySalary);

			taxDeductions.add(new TaxDeduction(employee.getEmployeeId(), employee.getFirstName(),
					employee.getLastName(), yearlySalary, taxAmount, cessAmount));
		});

		return ResponseEntity.ok(taxDeductions);
	}

	private double calculateYearlySalary(Employee employee) {
		LocalDate doj = employee.getDateOfJoining().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		double salary = employee.getSalary();
		LocalDate currentDate = LocalDate.now();
		Period between = Period.between(doj.withDayOfMonth(1), currentDate);
		int monthsOfWork = (int) between.toTotalMonths();
		if (between.getDays() > 0) {
			monthsOfWork = monthsOfWork + 1;
		}

		if (monthsOfWork <= 0) {
			return 0.0;
		} else {
			double salaryPerDay = salary / 30;
			// Exclude the current day
			int totalDaysOfWork = (currentDate.getDayOfMonth() - 1) + (30 - doj.getDayOfMonth());
			return salaryPerDay * totalDaysOfWork;
		}
	}

	private double calculateTaxAmount(double salary) {
		if (salary <= 250000) {
			return 0.0;
		} else if (salary <= 500000) {
			return (salary - 250000) * 0.05;
		} else if (salary <= 1000000) {
			return (salary - 500000) * 0.1 + 25000;
		} else {
			return (salary - 1000000) * 0.2 + 125000;
		}
	}

	private double calculateCessAmount(double salary) {
		if (salary > 2500000) {
			double excessAmount = salary - 2500000;
			return excessAmount * 0.02;
		}
		return 0.0;
	}

	private String doCheckError(Employee employee) {
		if (employee.getEmployeeId() <= 0) {
			return getError(AppError.MANDATORY, "employeeId");
		}
		if (StringUtils.isEmpty(employee.getFirstName())) {
			return getError(AppError.MANDATORY, "firstName");
		}
		if (StringUtils.isEmpty(employee.getLastName())) {
			return getError(AppError.MANDATORY, "lastName");
		}
		if (StringUtils.isEmpty(employee.getEmail())) {
			return getError(AppError.MANDATORY, "email");
		}
		if (CollectionUtils.isEmpty(employee.getPhoneNumbers())) {
			return getError(AppError.MANDATORY, "phoneNumbers");
		}
		if (employee.getDateOfJoining() == null) {
			return getError(AppError.MANDATORY, "dateOfJoining");
		}
		if (StringUtils.isEmpty(String.valueOf(employee.getSalary()))) {
			return getError(AppError.MANDATORY, "salary");
		}
		return "";
	}

	public String getError(AppError errCode, String parm) {
		errCode.setErrDesc(String.format(errCode.getErrDesc(), parm));
		return errCode.getErrDesc();
	}
}