package employeepayrolljdbc;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	//uc2
	public List<EmployeePayrollData> readEmployeePayrollData(IOService ioService) throws SQLException
	{
		if(ioService.equals(IOService.DB_IO))
		{
			this.employeePayrollList = employeePayrollDBService.readData();
		}
		
		return this.employeePayrollList;
	}

	//uc3 uc4
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
		System.out.println(employeePayrollDataList.get(0).equals(getEmployeePayrollData(name)));
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

	//uc5
	public List<EmployeePayrollData> readEmployeePayrollData(IOService ioService, LocalDate startDate, LocalDate endDate) 
	{
		if(ioService.equals(IOService.DB_IO))
		{
			return employeePayrollDBService.getEmployeePayrollForDateRange(startDate, endDate);
		}
		return null;
	}

	//uc6
	public Map<String, Double> readAverageSalaryByGender(IOService ioService) 
	{
		if(ioService.equals(IOService.DB_IO))
		{
			return employeePayrollDBService.getAverageSalaryByGender();
		}
		return null;
	}

	public Map<String, Double> readTotalSalarySumByGender(IOService ioService) 
	{
		if(ioService.equals(IOService.DB_IO))
		{
			return employeePayrollDBService.getTotalSalarySumByGender();
		}
		return null;
	}

	public Map<String, Double> readMinSalaryByGender(IOService ioService) 
	{
		if(ioService.equals(IOService.DB_IO))
		{
			return employeePayrollDBService.getMinSalaryByGender();
		}
		return null;
	}

	public Map<String, Double> readMaxSalaryByGender(IOService ioService) 
	{
		if(ioService.equals(IOService.DB_IO))
		{
			return employeePayrollDBService.getMaxSalaryByGender();
		}
		return null;
	}

	public Map<String, Double> readEmployeeCountByGender(IOService ioService) 
	{
		if(ioService.equals(IOService.DB_IO))
		{
			return employeePayrollDBService.getEmployeeCountByGender();
		}
		return null;
	}

	//uc7 //uc8 //uc9
	public void addEmployeeToPayroll(String name, String gender, double salary, LocalDate start, List<String> department) 
	{
		employeePayrollList.add(employeePayrollDBService.addEmployeeToPayroll(name, gender, salary, start, department));
	}

	//uc8
	public List<EmployeePayrollData> deleteEmployee(String name) throws SQLException 
	{
		employeePayrollDBService.deleteEmployee(name);
		return readEmployeePayrollData(IOService.DB_IO);
	}

	//Thread
	//uct1
	public void addEmployeesToPayroll(List<EmployeePayrollData> employeePayrollDataList) 
	{
		employeePayrollDataList.forEach(employeePayrollData -> {
			System.out.println("Employee Being Added: " + employeePayrollData.name);
			this.addEmployeeToPayroll(employeePayrollData.name, employeePayrollData.gender,
			employeePayrollData.salary, employeePayrollData.startDate, Arrays.asList("Administration"));
			System.out.println("Employee Added: " + employeePayrollData.name);
		});
		System.out.println(this.employeePayrollList);
	}

	public long countEntries(IOService ioService) 
	{
		if(ioService.equals(ioService.DB_IO))
		{
			return employeePayrollList.size();
		}
		return 0;
	}

	//uct2
	public void addEmployeesToPayrollWithThreads(List<EmployeePayrollData> employeePayrollDataList) 
	{
		Map<Integer, Boolean> employeeAdditionStatus = new HashMap<Integer, Boolean>();
		
		employeePayrollDataList.forEach(employeePayrollData -> {
			Runnable task = () -> {
				System.out.println("Employee Being Added: " + Thread.currentThread().getName());
				employeeAdditionStatus.put(employeePayrollData.hashCode(), false);
				this.addEmployeeToPayroll(employeePayrollData.name, employeePayrollData.gender,
				employeePayrollData.salary, employeePayrollData.startDate, Arrays.asList("Administration"));
				employeeAdditionStatus.put(employeePayrollData.hashCode(), true);
				System.out.println("Employee Added: " + Thread.currentThread().getName());
				};
		
				Thread thread = new Thread(task, employeePayrollData.name);
				thread.start();
		});
		
		while(employeeAdditionStatus.containsValue(false))
		{
			try
			{
				Thread.sleep(10);
			}
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
		
		System.out.println(this.employeePayrollList);
	}
}
