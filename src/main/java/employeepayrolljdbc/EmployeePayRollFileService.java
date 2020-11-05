package employeepayrolljdbc;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import employeepayrolljdbc.EmployeePayrollService.IOService;

public class EmployeePayRollFileService 
{
	public static String PAYROLL_FILE_NAME = "payroll-file.txt";
	
	public List<EmployeePayrollData> readData() 
	{
		List<EmployeePayrollData> list = new ArrayList<EmployeePayrollData>();
		
		try
		{
			Files.lines(new File(PAYROLL_FILE_NAME).toPath()).map(line -> line.trim())
			.forEach(line -> {
				String data[] = line.split(" ");				
				list.add(new EmployeePayrollData(Integer.parseInt(data[2]), data[5], Double.parseDouble(data[8])));
			});
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		return list;
	}
	
	public void writeData(List<EmployeePayrollData> employeePayrollDataList) 
	{
		StringBuffer empBuffer = new StringBuffer();
		employeePayrollDataList.forEach(employee -> 
		{
			String employeeDataString = employee.toString().concat("\n");
			empBuffer.append(employeeDataString);
		});
		
		try
		{
			Files.write(Paths.get(PAYROLL_FILE_NAME), empBuffer.toString().getBytes());
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void printData()
	{
		try
		{
			Files.lines(new File(PAYROLL_FILE_NAME).toPath())
			.forEach(System.out::println);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public long countEntries()
	{
		long entries = 0;
		
		try
		{
			entries = Files.lines(new File(PAYROLL_FILE_NAME).toPath())
					.count();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		return entries;
	}
}
