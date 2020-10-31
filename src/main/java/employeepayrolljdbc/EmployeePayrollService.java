package employeepayrolljdbc;

import java.sql.SQLException;
import java.util.List;

public class EmployeePayrollService 
{
	public enum IOService{FILE_IO, DB_IO}
	
	private List<EmployeePayrollData> employeePayrollList;
	
	public List<EmployeePayrollData> readEmployeePayrollData(IOService ioService) throws SQLException
	{
		if(ioService.equals(IOService.DB_IO))
		{
			this.employeePayrollList = new EmployeePayrollDBService().readData();
		}
		
		return this.employeePayrollList;
	}
}
