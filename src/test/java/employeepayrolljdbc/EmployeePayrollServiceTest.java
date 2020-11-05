package employeepayrolljdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

import employeepayrolljdbc.EmployeePayrollService.IOService;

public class EmployeePayrollServiceTest 
{
	/*
	//uc2
	@Test
	public void givenEmployeePayrollInDB_WhenRetrieved_ShouldMatchEployeeCount() throws SQLException
	{
		EmployeePayrollService employeePayrollService = new EmployeePayrollService(); 
		List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollData(IOService.DB_IO);
		assertEquals(3, employeePayrollData.size());
	}
	
	//uc3 uc4
	@Test
	public void givenNewSalaryForEmployee_WhenUpdated_ShouldSyncWithDb() throws SQLException
	{
		EmployeePayrollService employeePayrollService = new EmployeePayrollService(); 
		List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollData(IOService.DB_IO);
		employeePayrollService.updateEmployeePayrollSalary("Suruchi", 70000.00);
		boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB("Suruchi");
		assertEquals(3, employeePayrollData.size());
	}
	
	//uc5
	@Test
	public void givenDataRange_WhenRetrieved_ShouldMatchEmployeeCount() throws SQLException
	{
		EmployeePayrollService employeePayrollService = new EmployeePayrollService(); 
		employeePayrollService.readEmployeePayrollData(IOService.DB_IO);
		LocalDate startDate = LocalDate.of(2018, 01, 01);
		LocalDate endDate = LocalDate.now();
		List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollData(IOService.DB_IO, startDate, endDate);
		assertEquals(3, employeePayrollData.size());
	}
	
	//uc6
	@Test
	public void givenPayrollData_WhenAverageSalaryRetrievedByGender_ShouldReturnProperValue() throws SQLException
 	{
		EmployeePayrollService employeePayrollService = new EmployeePayrollService(); 
		employeePayrollService.readEmployeePayrollData(IOService.DB_IO);
		Map<String, Double> averageSalaryByGender = employeePayrollService.readAverageSalaryByGender(IOService.DB_IO);
		assertTrue(averageSalaryByGender.get("F").equals(55000.00) && averageSalaryByGender.get("M").equals(60000.00));
 	}
	
	@Test
	public void givenPayrollData_WhenTotalSalarySumRetrievedByGender_ShouldReturnProperValue() throws SQLException
 	{
		EmployeePayrollService employeePayrollService = new EmployeePayrollService(); 
		employeePayrollService.readEmployeePayrollData(IOService.DB_IO);
		Map<String, Double> totalSalarySumByGender = employeePayrollService.readTotalSalarySumByGender(IOService.DB_IO);
		assertTrue(totalSalarySumByGender.get("F").equals(110000.00) && totalSalarySumByGender.get("M").equals(60000.00));
 	}
	
	@Test
	public void givenPayrollData_WhenMinimumSalaryRetrievedByGender_ShouldReturnProperValue() throws SQLException
 	{
		EmployeePayrollService employeePayrollService = new EmployeePayrollService(); 
		employeePayrollService.readEmployeePayrollData(IOService.DB_IO);
		Map<String, Double> minSalaryByGender = employeePayrollService.readMinSalaryByGender(IOService.DB_IO);
		assertTrue(minSalaryByGender.get("F").equals(40000.00) && minSalaryByGender.get("M").equals(60000.00));
 	}
	
	@Test
	public void givenPayrollData_WhenMaximumSalaryRetrievedByGender_ShouldReturnProperValue() throws SQLException
 	{
		EmployeePayrollService employeePayrollService = new EmployeePayrollService(); 
		employeePayrollService.readEmployeePayrollData(IOService.DB_IO);
		Map<String, Double> maxSalaryByGender = employeePayrollService.readMaxSalaryByGender(IOService.DB_IO);
		assertTrue(maxSalaryByGender.get("F").equals(70000.00) && maxSalaryByGender.get("M").equals(60000.00));
 	}
	
	@Test
	public void givenPayrollData_WhenCountOfEmployeesRetrievedByGender_ShouldReturnProperValue() throws SQLException
 	{
		EmployeePayrollService employeePayrollService = new EmployeePayrollService(); 
		employeePayrollService.readEmployeePayrollData(IOService.DB_IO);
		Map<String, Double> employeeCountByGender = employeePayrollService.readEmployeeCountByGender(IOService.DB_IO);
		assertTrue(employeeCountByGender.get("F").equals(2.00) && employeeCountByGender.get("M").equals(1.00));
 	}
	
	//uc7 //uc8 
	  @Test 
	  public void givenNewEmployee_WhenAdded_ShouldSyncWithDB() throws SQLException
	  { 
		  EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		  employeePayrollService.readEmployeePayrollData(IOService.DB_IO);
		  employeePayrollService.addEmployeeToPayroll("Mital", "M", 60000.00, LocalDate.now(), Arrays.asList("Sales")); 
		  boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB("Mital");
		  assertTrue(result); 
	  }
	 
	
	//uc8
	  @Test 
	  public void givenEmployeeDB_WhenAnEmployeeIsDeleted_ShouldSyncWithDB() throws SQLException 
	  { 
		EmployeePayrollService employeeService = new EmployeePayrollService();
		employeeService.readEmployeePayrollData(IOService.DB_IO);
		List<EmployeePayrollData> employeePayrollData =	employeeService.deleteEmployee("Mital");
	  	assertEquals(2,employeePayrollData.size()); 
	  }
	 	

	//uc9
	  @Test 
	  public void givenNewEmployeeWithDepartment_WhenAdded_ShouldSyncWithDB() throws SQLException 
	  { 
		  EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		  employeePayrollService.readEmployeePayrollData(IOService.DB_IO);
		  employeePayrollService.addEmployeeToPayroll("Mital", "M", 60000.00, LocalDate.now(), Arrays.asList("Sales", "Marketing")); 
		  boolean result =
		  employeePayrollService.checkEmployeePayrollInSyncWithDB("Mital");
		  assertTrue(result); 
	  }
	*/
	
	//Thread
	//uc1 //uc2
	  @Test public void givenNewEmployee_WhenAdded_ShouldMatchEmployeeEntries() throws SQLException 
	  { 
		  EmployeePayrollData arrayOfEmps[] = { 
		  new EmployeePayrollData(1, "Jeff Bezos", "M", 10000.0, LocalDate.now()), 
		  new EmployeePayrollData(2, "Bill Gates", "M", 20000.0, LocalDate.now()), 
		  new EmployeePayrollData(3, "Mark Zuckerberg", "M", 30000.0, LocalDate.now()), 
		  new EmployeePayrollData(4, "Sunder", "M", 60000.0, LocalDate.now()), 
		  new EmployeePayrollData(5, "Mukesh", "M", 10000.0, LocalDate.now()), 
		  new EmployeePayrollData(6, "Anil", "M", 20000.0, LocalDate.now()) };
		  EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		  employeePayrollService.readEmployeePayrollData(IOService.DB_IO); 
		  Instant start = Instant.now();
		  employeePayrollService.addEmployeesToPayroll(Arrays.asList(arrayOfEmps));
		  Instant end = Instant.now(); 
		  System.out.println("Duration without Thread: " + Duration.between(start, end)); 
		  Instant threadStart = Instant.now();
		  employeePayrollService.addEmployeesToPayrollWithThreads(Arrays.asList(arrayOfEmps));
		  Instant threadEnd = Instant.now(); 
		  System.out.println("Duration with Thread: " + Duration.between(threadStart, threadEnd)); 
		  assertEquals(15, employeePayrollService.countEntries(IOService.DB_IO)); 
	 }
		 
}
