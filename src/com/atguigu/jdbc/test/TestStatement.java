package com.atguigu.jdbc.test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

import com.atguigu.jdbc.utils.JDBCUtils;

public class TestStatement {
	
	// 目的是根据用户输入的姓名查出对应的记录
	public void queryEmpByName(String name) throws Exception {
		
		String sql="select * from t_emp  where name='"+name+"'";
		
		Connection conn = JDBCUtils.getConn();
		
		Statement statement = conn.createStatement();
		
		ResultSet resultSet = statement.executeQuery(sql);
		
		while(resultSet.next()) {
			
			// 完成结果集的遍历
			int id = resultSet.getInt(1);
			String nameStr = resultSet.getString("name");
			String deptId = resultSet.getString(4);
			int age = resultSet.getInt(3);
			int empNo = resultSet.getInt(5);
			
			System.out.println(id+" "+nameStr+" "+age+" "+deptId+" "+empNo);
		}
		
		JDBCUtils.close(resultSet, statement, conn);
		
	}
	
public void queryEmpByName2(String name) throws Exception {
		
		String sql="select * from t_emp  where id=?";
		
		Connection conn = JDBCUtils.getConn();
		
		//Statement statement = conn.createStatement();
		
		// 预编译
		PreparedStatement statement = conn.prepareStatement(sql);
		
		// 填充占位符
		statement.setString(1, name);
		
		System.out.println(statement);
		
		//执行
		ResultSet resultSet = statement.executeQuery(sql);
		
		while(resultSet.next()) {
			
			// 完成结果集的遍历
			int idStr = resultSet.getInt(1);
			String nameStr = resultSet.getString("name");
			String deptId = resultSet.getString(4);
			int age = resultSet.getInt(3);
			int empNo = resultSet.getInt(5);
			
			System.out.println(idStr+" "+nameStr+" "+age+" "+deptId+" "+empNo);
		}
		
		JDBCUtils.close(resultSet, statement, conn);
		
	}
	
	// SQL注入： 通过传入的参数，改变了SQL的功能！不安全！
	@Test
	public void testStatement() throws Exception {
		
		queryEmpByName("令狐冲 '  or name <> '令狐冲");
		
		//queryEmpByName("令狐冲 ' ; truncate  t_emp #");
		
	}
	
	// 解决： 使用PreparedStatement
	/*
	 * 1. 要求在编写SQL时，将参数位置使用？作为占位符进行占位
	 * 2. 在执行之前，预编译之后，调用提供的setXXX()来填充占位符。需要注意根据参数的类型找到合适的方法！
	 * 3. 填充之后，再执行SQL语句
	 * 
	 * 
	 * 解决SQL注入的原理：  通过传入的占位符参数的类型，拼接SQL，将传入的特殊字符，进行转义，防止SQL注入！
	 * 
	 * 优势：  如果一条语句多次执行，只需要编译一次即可。提前编译的SQL会保存在PreparedStatement，可以重复使用！
	 */
	@Test
	public void testPreparedStatement() throws Exception {
		
		queryEmpByName2("令狐冲 '  or name <> '令狐冲");
		
		//queryEmpByName("令狐冲");
		
	}

}
