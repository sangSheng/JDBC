package com.atguigu.jdbc.test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import org.junit.Test;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.atguigu.jdbc.utils.JDBCUtils;


/*
 * 1. 使用数据库连接池
 * 			好处： ①省去了自己创建连接时间
 * 				②节省资源，用完的连接放回池中，达到复用
 * 
 * 
 * 
 * 
 */
public class TestConnectionPoll {

	@SuppressWarnings("resource")
	@Test
	public void test() throws SQLException {
		
		// ①创建Druid提供的DataSource对象
		DruidDataSource dataSource = new DruidDataSource();
		
		// 设置连接池的属性
		dataSource.setMaxActive(20);
		
		dataSource.setMinIdle(5);
		
		dataSource.setInitialSize(10);
		
		// 设置核心参数，数据库url,用户名，密码，驱动
		dataSource.setUsername("joe");
		
		dataSource.setPassword("123456");
		
		dataSource.setUrl("jdbc:mysql://192.168.6.3:3306/mydb2");
		
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		
		// 获取连接
	Connection connection = dataSource.getConnection();
	
	Statement statement = connection.createStatement();
	
	String sql="select * from t_emp";
	
	ResultSet resultSet = statement.executeQuery(sql); // 执行查询语句
	
	while(resultSet.next()) {
		
		// 完成结果集的遍历
		int id = resultSet.getInt(1);
		String name = resultSet.getString("name");
		String deptId = resultSet.getString(4);
		int age = resultSet.getInt(3);
		int empNo = resultSet.getInt(5);
		
		System.out.println(id+" "+name+" "+age+" "+deptId+" "+empNo);
	}
	
	resultSet.close();
	
	statement.close();
	// 将连接返回池中
	connection.close();
	
	}
	
	@Test
	public void test2() throws Exception {
		
		Properties properties = new Properties();
		
		properties.load(TestConnectionPoll.class.getClassLoader().getResourceAsStream("db.properties"));
		
		DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
		
		//System.out.println(dataSource);
		Connection connection = dataSource.getConnection();
		
		Statement statement = connection.createStatement();
		
		String sql="select * from t_emp";
		
		ResultSet resultSet = statement.executeQuery(sql); // 执行查询语句
		
		while(resultSet.next()) {
			
			// 完成结果集的遍历
			int id = resultSet.getInt(1);
			String name = resultSet.getString("name");
			String deptId = resultSet.getString(4);
			int age = resultSet.getInt(3);
			int empNo = resultSet.getInt(5);
			
			System.out.println(id+" "+name+" "+age+" "+deptId+" "+empNo);
		}
		
		resultSet.close();
		
		statement.close();
		// 将连接返回池中
		connection.close();
		
	}
	
	@Test
	public void testJdbcUtil() throws Exception {
		
		Connection conn = JDBCUtils.getConn();
		
		Statement statement = conn.createStatement();
		
		String sql="select * from t_emp";
		
		ResultSet resultSet = statement.executeQuery(sql); // 执行查询语句
		
		while(resultSet.next()) {
			
			// 完成结果集的遍历
			int id = resultSet.getInt(1);
			String name = resultSet.getString("name");
			String deptId = resultSet.getString(4);
			int age = resultSet.getInt(3);
			int empNo = resultSet.getInt(5);
			
			System.out.println(id+" "+name+" "+age+" "+deptId+" "+empNo);
		}
		
		JDBCUtils.close(resultSet, statement, conn);
		
	}

}
