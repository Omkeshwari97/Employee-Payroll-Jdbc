package employeepayrolljdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class EmployeePayrollDBService 
{
	private static EmployeePayrollDBService employeePayrollDBService;
	private PreparedStatement employeePayrollDataStatement;
	private int connectionCounter = 0;

	public EmployeePayrollDBService()
	{}
	
	public static EmployeePayrollDBService getInstance()
	{
		if (employeePayrollDBService == null) 
		{
			employeePayrollDBService = new EmployeePayrollDBService();
		}
		
		return employeePayrollDBService;
	}
	
	//uc1 //uct3
	private Connection getConnection() 
	{
		String jdbcURL = "jdbc:mysql://localhost:3306/payroll_service?useSSL=false";
		String userName = "root";
		String password = "root";
		Connection connection = null;
		
		connectionCounter++;
		
		try 
		{
			System.out.println("Processing Thread: " + Thread.currentThread().getName() +
								" Connecting to database with Id: " + connectionCounter);
			connection = DriverManager.getConnection(jdbcURL, userName, password);
			System.out.println("Processing Thread: " + Thread.currentThread().getName() + 
								" Id: " + connectionCounter + " Connection is successful!" + connection);					
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return connection;
	}
	
	//uc2
	public List<EmployeePayrollData> readData()
	{
		String sql = "select *  from employee_payroll;";
		
		return this.getEmployeePayrollDataUsingDB(sql);
	}

	//uc3 uc4
	public int updateEmployeeData(String name, double salary) 
	{
		String sql = String.format("update employee_payroll set salary = %.2f where name = '%s';", salary, name);
		
		try(Connection connection = this.getConnection()) 
		{
			Statement statement = connection.createStatement();
			return statement.executeUpdate(sql);
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return 0;
	}

	public List<EmployeePayrollData> getEmployeePayrollData(String name) 
	{
		List<EmployeePayrollData> employeePayrollList = null;
		
		if(this.employeePayrollDataStatement == null)
		{
			this.prepareStatementForEmployeeData();
		}
		
		try 
		{
			employeePayrollDataStatement.setString(1, name);
			ResultSet resultSet = employeePayrollDataStatement.executeQuery();
			employeePayrollList = this.getEmployeePayrollData(resultSet);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return employeePayrollList;
	}

	private List<EmployeePayrollData> getEmployeePayrollData(ResultSet resultSet) 
	{
		List<EmployeePayrollData> employeePayrollList = new ArrayList<>();
		
		try(Connection connection = this.getConnection()) 
		{			
			while(resultSet.next())
			{
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				double salary = resultSet.getDouble("salary");
				LocalDate startDate = resultSet.getDate("start").toLocalDate();
				employeePayrollList.add(new EmployeePayrollData(id, name, salary, startDate));
			}
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return employeePayrollList;
	}

	private void prepareStatementForEmployeeData() 
	{
		try 
		{
			Connection connection = this.getConnection();
			String sql = "Select * from employee_payroll where name = ?";
			employeePayrollDataStatement = connection.prepareStatement(sql);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
	}
	
	//uc5
	public List<EmployeePayrollData> getEmployeePayrollForDateRange(LocalDate startDate, LocalDate endDate) 
	{
		String sql = String.format("Select * from employee_payroll where start between '%s' and '%s';", 
									Date.valueOf(startDate), Date.valueOf(endDate));
		
		return this.getEmployeePayrollDataUsingDB(sql);
	}

	//uc6
	public Map<String, Double> getAverageSalaryByGender() 
	{
		String sql = String.format("Select gender, avg(salary) as avg_salary from employee_payroll group by gender");
		String column_name = "avg_salary";
		
		return this.getSalaryDetailsByGender(sql, column_name);
	}
	
	public Map<String, Double> getTotalSalarySumByGender() 
	{
		String sql = "select gender, sum(salary) as sum from employee_payroll group by gender;"; 
		String column_name = "sum";
		
		return this.getSalaryDetailsByGender(sql, column_name);
	}
	
	public Map<String, Double> getMinSalaryByGender() 
	{
		String sql = "select gender, min(salary) as min_salary from employee_payroll group by gender;"; 
		String column_name = "min_salary";
		
		return this.getSalaryDetailsByGender(sql, column_name);
	}

	public Map<String, Double> getMaxSalaryByGender() 
	{
		String sql = "select gender, max(salary) as max_salary from employee_payroll group by gender;"; 
		String column_name = "max_salary";
		
		return this.getSalaryDetailsByGender(sql, column_name);
	}
	
	public Map<String, Double> getEmployeeCountByGender() 
	{
		String sql = "select gender, count(*) as count_employee from employee_payroll group by gender;"; 
		String column_name = "count_employee";
		
		return this.getSalaryDetailsByGender(sql, column_name);
	}

	//uc7 //uc8 //uc9
	public EmployeePayrollData addEmployeeToPayroll(String name, String gender, double salary, LocalDate start, List<String> department) 
	{	
		int employeeId = -1;
		EmployeePayrollData employeePayrollData =null;
		Connection connection = null;
		
		try 
		{
			connection = this.getConnection();
			connection.setAutoCommit(false);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		try(Statement statement = connection.createStatement())
		{		
			String sql = String.format("Insert into employee_payroll (name, gender, salary, start) values ('%s', '%s', '%s', '%s')", 
					name, gender, salary, Date.valueOf(start));
			
			int rowAffected = statement.executeUpdate(sql, statement.RETURN_GENERATED_KEYS);
			if(rowAffected == 1)
			{
				ResultSet resultSet = statement.getGeneratedKeys();
				
				if(resultSet.next())
				{
					employeeId = resultSet.getInt(1);
				}
			}
		}  
		catch (SQLException e) 
		{
			e.printStackTrace();
			try 
			{
				connection.rollback();
			}
			catch (SQLException e1) 
			{
				e1.printStackTrace();
			}
		}
		
		try(Statement statement = connection.createStatement())
		{		
			double deductions = salary * 0.2;
			double taxable_pay = salary - deductions;
			double tax = taxable_pay * 0.1;
			double net_pay = salary - tax;
			
			String sql1 = String.format("Insert into payroll_details (employee_id, basic_pay, deductions, taxable_pay, income_tax, net_pay) values ('%s', '%s', '%s', '%s', '%s', '%s')", 
					employeeId, salary, deductions, taxable_pay, tax, net_pay);
			
			statement.executeUpdate(sql1);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			try 
			{
				connection.rollback();
			}
			catch (SQLException e1) 
			{
				e1.printStackTrace();
			}
		}
		
		try(Statement statement = connection.createStatement()) 
		{
			String sql2 = String.format("Insert into department (employee_id, department_name) values ('%s', '%s')", employeeId, department);
			int rowAffected = statement.executeUpdate(sql2);
			if(rowAffected == 1)
			{
				employeePayrollData = new EmployeePayrollData(employeeId, name, gender, salary, start, department);
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			try 
			{
				connection.rollback();
			} 
			catch (SQLException e1) 
			{
				e1.printStackTrace();
			}
		}
		
		try 
		{
			connection.commit();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally 
		{
			if(connection != null)
			{
				try 
				{
					connection.close();
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		}
		return employeePayrollData;
	}
	
	//uc8
	public void deleteEmployee(String name) 
	{
		String sql = String.format("Delete from employee_payroll where name = '%s'", name);
			
		try(Connection connection = this.getConnection()) 
		{
			connection.setAutoCommit(false);
			Statement statement = connection.createStatement();
			statement.executeUpdate(sql);
			connection.commit();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	private List<EmployeePayrollData> getEmployeePayrollDataUsingDB(String sql) 
	{
		List<EmployeePayrollData> employeePayrollList = new ArrayList<>();
		try(Connection connection = this.getConnection()) 
		{
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			employeePayrollList  = this.getEmployeePayrollData(resultSet);
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return employeePayrollList;
	}
	
	private Map<String, Double> getSalaryDetailsByGender(String sql, String column_name) 
	{
		Map<String, Double> genderToColumnMap = new HashMap<String, Double>();
		
		try(Connection connection = this.getConnection()) 
		{
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			
			while(resultSet.next())
			{
				String gender = resultSet.getString("gender");
				double salary = resultSet.getDouble(column_name);
				genderToColumnMap.put(gender, salary);
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return genderToColumnMap;
	}
}
