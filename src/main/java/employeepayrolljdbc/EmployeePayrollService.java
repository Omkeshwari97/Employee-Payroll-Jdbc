package employeepayrolljdbc;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class EmployeePayrollService 
{
	public enum IOService{FILE_IO, DB_IO}
	
	private List<EmployeePayrollData> employeePayrollList;
	private EmployeePayrollDBService employeePayrollDBService;
	
	public EmployeePayrollService()
	{
		employeePayrollDBService = EmployeePayrollDBService.getInstance();
	}
	
	public EmployeePayrollService(List<EmployeePayrollData> employeePayrollList) 
	{
		this();
		this.employeePayrollList = employeePayrollList;
	}
	
	public List<EmployeePayrollData> readEmployeePayrollData(IOService ioService) throws SQLException
	{
		if(ioService.equals(IOService.DB_IO))
		{
			this.employeePayrollList = employeePayrollDBService.readData();
		}
		
		return this.employeePayrollList;
	}

	public void updateEmployeePayrollSalary(String name, double salary) 
	{
		int result = employeePayrollDBService.updateEmployeeData(name, salary);
		
		if(result == 0)
		{
			return;
		}
		
		EmployeePayrollData employeePayrollData = this.getEmployeePayrollData(name);
		
		if (employeePayrollData != null) 
		{
			employeePayrollData.salary = salary;
		}
	}
	
	public boolean checkEmployeePayrollInSyncWithDB(String name) 
	{
		List<EmployeePayrollData> employeePayrollDataList = employeePayrollDBService.getEmployeePayrollData(name);
		return employeePayrollDataList.get(0).equals(getEmployeePayrollData(name));
	}

	private EmployeePayrollData getEmployeePayrollData(String name) 
	{
		EmployeePayrollData employeePayrollData = this.employeePayrollList.stream()
								.filter(employeePayrollDataItem -> employeePayrollDataItem.name.equals(name))
								.findFirst()
								.orElse(null);
		return employeePayrollData;
	}

	public List<EmployeePayrollData> readEmployeePayrollData(IOService ioService, LocalDate startDate, LocalDate endDate) 
	{
		if(ioService.equals(IOService.DB_IO))
		{
			return employeePayrollDBService.getEmployeePayrollForDateRange(startDate, endDate);
		}
		return null;
	}
}
