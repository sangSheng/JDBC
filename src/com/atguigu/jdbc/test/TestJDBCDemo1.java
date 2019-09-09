package com.atguigu.jdbc.test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.atguigu.jdbc.entity.Employee;
import com.mysql.jdbc.Driver;


/*
 * 1. 通用的方式
 * 		sqlyog客户端
 * 			①输入用户，密码，连接的主机的ip地址及要连接哪个库等信息
 * 			②连接-----和数据库服务器通信，尝试获取一个Connection
 * 			③如果已经获取到Connection
 * 					在界面编写sql语句
 * 
 * 			④没有获取到连接----无法执行sql语句
 * 		mysql -u xxx -p xx -h xxxx -P xxx 
 * 			> sql语句
 * 
 * 		查询完毕之后，会执行exit;退出客户端，直接点击叉号退出sqlyog！
 * 			数据库服务器同时保持的连接数是固定的！
 * 				如果当前保持的连接数已经到达极限，新的客户端将无法获取连接。
 * 
 * 2. JDBC的方式
 * 		 使用面向对象的思想来实现连接数据库的步骤！
 * 		
 * 		Driver :   定义不同厂商提供的驱动类的总接口。
 * 					实现，根据厂商提供具体的实现。
 * 
 * 		①Connection :  连接对象。需要根据连接的数据库不同使用不同厂商提供的jar包。
 * 				jar包中，最核心的就是驱动类，驱动类负责和mysqlserver进行通信
 * 			
 * 		②Statement :   语句对象，通过语句对象可以完成sql语句的执行，获取执行的结果			
 * 				通过Connection对象获取
 * 
 * 		③执行完，释放资源
 * 
 * 步骤： 
 * 		①注册驱动
 * 	    ②根据注册的驱动获取连接对象
 * 		③根据连接对象获取语句对象
 * 		④调用语句对象的方法，执行CRUD
 * 		⑤执行完毕释放资源
 * 
 * 3. 注册驱动方式的改善
 * 		
 * 			Mysql提供的Driver类在初始化时，已经自动注册了驱动，无需重复注册！
 * 
 * 			只需要保证Driver可以加载到虚拟机，就会自动注册驱动。
 * 
 * 			Class.forName("xxxx") 来实现驱动类的加载！
 * 
 * 4. 查询的结果集对象
 * 		ResultSet：代表数据库返回的查询结果集。
 * 					自带一个指针，开始时，指针指向第一行的前面。
 * 					调用next()移动指针到下一行，如果next()返回false说明没有下一行记录。在while中迭代结果集。
 * 					next()只能向下读，可以使用getXXX()，通过列名或列的Index(从1开始)获取列值。
 * 
 * 
 * 
 * 
 * 
 * 
 */
public class TestJDBCDemo1 {

	@Test
	public void testConnect() throws Exception {
		
			//①注册驱动
			//DriverManager.registerDriver(new Driver());
		
			Class.forName("com.mysql.jdbc.Driver");
			
			String url="jdbc:mysql://192.168.6.3:3306/mydb2";
			
			//②根据注册的驱动获取对应数据库服务器提供的Connection
			Connection connection = DriverManager.getConnection(url, "joe", "123456");
			
			//com.mysql.jdbc.JDBC4Connection
			System.out.println(connection.getClass().getName());
			
			//③通过连接获取Statement对象
			Statement statement = connection.createStatement();
			
			//④通过statement执行sql
			//statement.execute(sql); // 执行任意sql，返回boolean类型，即成功或失败 // DDL(建库，建表，改库，改表)或update语句
			
			//statement.executeQuery(sql); // 执行查询语句
			
			String sql1="update t_emp set age=94 where id=1";
			String sql2="update t_emp set age=94 where id=2";
			
			// 影响的行数
			int rows1 = statement.executeUpdate(sql1); // 执行更新语句
			int rows2 = statement.executeUpdate(sql2); // 执行更新语句
			
			//System.out.println(rows>0 ? "运行成功！" : "没有更新！");
			// ⑤释放资源
			statement.close();
			
			connection.close();
		
	}
	
	@Test
	public void testQuery() throws Exception {
		
		Class.forName("com.mysql.jdbc.Driver");
		
		String url="jdbc:mysql://192.168.6.3:3306/mydb2";
		
		Connection connection = DriverManager.getConnection(url, "joe", "123456");
		
		Statement statement = connection.createStatement();
		
		String sql="select * from t_emp";
		
		ResultSet resultSet = statement.executeQuery(sql); // 执行查询语句
		
		List<Employee> emps=new ArrayList<Employee>();
		
		Employee e=null;
		
		while(resultSet.next()) {
		
			
			// 完成结果集的遍历
			int id = resultSet.getInt(1);
			String name = resultSet.getString("name");
			String deptId = resultSet.getString(4);
			int age = resultSet.getInt(3);
			int empNo = resultSet.getInt(5);
			
			// 封装为一个Employee对象
			e=new Employee(id, name, empNo,deptId, age);
			
			//System.out.println(e);
			
			emps.add(e);
			
			//System.out.println(id+" "+name+" "+age+" "+deptId+" "+empNo);
		}
		
		System.out.println(emps);
		
		// resultSet使用完毕也需要关闭
		resultSet.close();
		
		statement.close();
		
		connection.close();
		
	}
	
	

}
