package employeepayrolljdbc;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import employeepayrolljdbc.EmployeePayrollService.IOService;

public class EmployeePayrollServiceTest 
{
	@Test
	public void givenEmployeePayrollInDB_WhenRetrieved_ShouldMatchEployeeCount() throws SQLException
	{
		EmployeePayrollService employeePayrollService = new EmployeePayrollService(); 
		List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollData(IOService.DB_IO);
		assertEquals(3, employeePayrollData.size());
	}
	
}
