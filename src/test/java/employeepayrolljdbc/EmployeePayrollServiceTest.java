package employeepayrolljdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

import employeepayrolljdbc.EmployeePayrollService.IOService;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

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
	
	//Thread
	//uct1 //uct2 //uct3 //uct4
	@Test 
	  public void givenNewEmployee_WhenAdded_ShouldMatchEmployeeEntries() throws SQLException 
	  { 
		  EmployeePayrollData arrayOfEmps[] = { 
				  new EmployeePayrollData(1, "Jeff Bezos", "M", 10000.0, LocalDate.now()), 
				  new EmployeePayrollData(2, "Bill Gates", "M", 20000.0, LocalDate.now()), 
				  new EmployeePayrollData(3, "Mark Zuckerberg", "M", 30000.0, LocalDate.now()), 
				  new EmployeePayrollData(4, "Sunder", "M", 60000.0, LocalDate.now()), 
				  new EmployeePayrollData(5, "Mukesh", "M", 10000.0, LocalDate.now()), 
				  new EmployeePayrollData(6, "Anil", "M", 20000.0, LocalDate.now()) 
				  };
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
		 
	  //uct5
	  @Test
	  public void givenNewSalaryOfExistingEmployee_WhenUpdated_ShouldSynWithDB() throws SQLException
	  {
		  Map<String, Double> salaryMap = new HashMap<>();
		  salaryMap.put("Sunder", 80000.0);
		  salaryMap.put("Anil", 15000.0);
		  EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		  employeePayrollService.readEmployeePayrollData(IOService.DB_IO);
		  Instant start = Instant.now();
		  employeePayrollService.updateEmployeePayrollSalary(salaryMap);
		  Instant end = Instant.now();
		  System.out.println("Duration with Thread: " + Duration.between(start, end));
		  boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB(Arrays.asList("Sunder, Anil"));
		  assertTrue(result);
	  }
	  */
	
	//restAPI JSON SERVER
	
	@Before
	public void setup()
	{
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 3000;
	}
	
	private EmployeePayrollData[] getEmployeeList() 
	{
		Response response = RestAssured.get("/employee_payroll");
		System.out.println("EMPLOYEE PAYROLL ENTRIES IN JSONSERVER:\n" + response.asString());
		EmployeePayrollData arrayOfEmps[] = new Gson().fromJson(response.asString(), EmployeePayrollData[].class);
		return arrayOfEmps;
	}
	
	private Response addEmployeeToJsonServer(EmployeePayrollData employeePayrollData) 
	{
		String empJson = new Gson().toJson(employeePayrollData);
		RequestSpecification requestSpecification = RestAssured.given();
		requestSpecification.header("Content-Type", "application/json");
		requestSpecification.body(empJson);
		return requestSpecification.post("/employee_payroll");
	}
	
	//uc1
	@Test
	public void givenEmployeeDataInJSONServer_WhenRetrieved_ShouldMatchTheCount()
	{
		EmployeePayrollData arrayOfEmps[] = getEmployeeList();
		EmployeePayrollService employeePayrollService;
		employeePayrollService = new EmployeePayrollService(Arrays.asList(arrayOfEmps));
		long entries = employeePayrollService.countEntries(IOService.Rest_IO);
		assertEquals(5, entries);
	}
	
	/*
	//uc2
	@Test
	public void givenNewEmployee_WhenAdded_ShouldMatch201ResponseAndCount()
	{
		EmployeePayrollService employeePayrollService;
		EmployeePayrollData arrayOfEmps[] = getEmployeeList();
		employeePayrollService = new EmployeePayrollService(Arrays.asList(arrayOfEmps));
		
		EmployeePayrollData employeePayrollData = new EmployeePayrollData(0, "Mark Zuckerberg", "M",300000.0, LocalDate.now());
		
		Response response = addEmployeeToJsonServer(employeePayrollData);
		int statusCode = response.getStatusCode();
		assertEquals(201, statusCode);
		
		employeePayrollData = new Gson().fromJson(response.asString(), EmployeePayrollData.class);
		employeePayrollService.addEmployeeToPayroll(employeePayrollData, IOService.Rest_IO);
		long entries = employeePayrollService.countEntries(IOService.Rest_IO);
		assertEquals(3, entries);
	}
	
	
	//uc3
	@Test
	public void givenListOfNewEmployees_WhenAdded_Shouldmatch201ResponseAndCount()
	{
		EmployeePayrollService employeePayrollService;
		EmployeePayrollData arrayOfEmps[] = getEmployeeList();
		employeePayrollService = new EmployeePayrollService(Arrays.asList(arrayOfEmps));
		
		EmployeePayrollData arrayOfNewEmps[] = {
				new EmployeePayrollData(0, "Sunder", "M", 600000.0, LocalDate.now()), 
				new EmployeePayrollData(0, "Mukesh", "M", 100000.0, LocalDate.now()), 
				new EmployeePayrollData(0, "Anil", "M", 200000.0, LocalDate.now()) 

		};
		
		for(EmployeePayrollData employeePayrollData : arrayOfNewEmps)
		{
			Response response = addEmployeeToJsonServer(employeePayrollData);
			int statusCode = response.getStatusCode();
			assertEquals(201, statusCode);
			
			employeePayrollData = new Gson().fromJson(response.asString(), EmployeePayrollData.class);
			employeePayrollService.addEmployeeToPayroll(employeePayrollData, IOService.Rest_IO);
		}
		
		long entries = employeePayrollService.countEntries(IOService.Rest_IO);
		assertEquals(6, entries);
	}
	*/

	/*
	//uc4
	@Test
	public void givenNewSalaryForEmployee_WhenUpdated_ShouldMatch200Response()
	{
		EmployeePayrollService employeePayrollService;
		EmployeePayrollData arrayOfEmps[] = getEmployeeList();
		employeePayrollService = new EmployeePayrollService(Arrays.asList(arrayOfEmps));
		
		employeePayrollService.updateEmployeePayrollSalary("Anil", 30000000.0, IOService.Rest_IO);
		EmployeePayrollData employeePayrollData = employeePayrollService.getEmployeePayrollData("Anil");
		
		String empJson = new Gson().toJson(employeePayrollData);
		RequestSpecification requestSpecification = RestAssured.given();
		requestSpecification.header("Content-Type", "application/json");
		requestSpecification.body(empJson);
		
		Response response = requestSpecification.put("/employee_payroll/" + employeePayrollData.id);
		int statusCode = response.getStatusCode();
		assertEquals(200, statusCode);
	}
	*/
	
	//uc5
	@Test
	public void givenEmployeeToDelete_WhenDeleted_ShouldMatch200ResponseCode() throws SQLException
	{
		EmployeePayrollService employeePayrollService;
		EmployeePayrollData arrayOfEmps[] = getEmployeeList();
		employeePayrollService = new EmployeePayrollService(Arrays.asList(arrayOfEmps));
		
		EmployeePayrollData employeePayrollData = employeePayrollService.getEmployeePayrollData("Anil");
		RequestSpecification requestSpecification = RestAssured.given();
		requestSpecification.header("Content-Type", "application/json");
		
		Response response = requestSpecification.delete("/employee_payroll/" + employeePayrollData.id);
		int statusCode = response.getStatusCode();
		assertEquals(200, statusCode);
		
		employeePayrollService.deleteEmployee("Anil", IOService.Rest_IO);
		long entries = employeePayrollService.countEntries(IOService.Rest_IO);
		assertEquals(5, entries);
	}
} 
