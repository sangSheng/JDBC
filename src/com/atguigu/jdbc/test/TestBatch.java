package com.atguigu.jdbc.test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.junit.Test;

import com.atguigu.jdbc.utils.JDBCUtils;

// 批量掺入10000条SQL
public class TestBatch {
	
	//63464ms
	@Test
	public void testWithOutBatch() throws Exception {
		
		long start = System.currentTimeMillis();
		
		Connection conn = JDBCUtils.getConn();
		
		String sql="insert into t_dept(deptName,ceo,address) values(?,?,?) ";
		
		PreparedStatement statement = conn.prepareStatement(sql);
		
		for (int i = 0; i < 10000; i++) {
			
			statement.setString(1, "dev"+i);
			statement.setInt(2, i);
			statement.setString(3, "爱党路"+i+"号");
			
			
			statement.executeUpdate();
		}
		
		
		long end = System.currentTimeMillis();
		
		System.out.println(end-start);
		
		JDBCUtils.close(null, statement, conn);
		
	}
	
	//52527   //690
	@Test
	public void testWithBatch() throws Exception {
		
		long start = System.currentTimeMillis();
		
		Connection conn = JDBCUtils.getConn();
		
		String sql="insert into t_dept(deptName,ceo,address) values(?,?,?) ";
		
		PreparedStatement statement = conn.prepareStatement(sql);
		
		for (int i = 0; i < 10000; i++) {
			
			statement.setString(1, "dev"+i);
			statement.setInt(2, i);
			statement.setString(3, "爱党路"+i+"号");
			
			//先将当前的sql添加到批处理中，添加完毕一次性执行
			statement.addBatch();
			
			// 向一批中加入不同的sql
			//statement.addBatch(sql);
		}
		
		// 批量执行
		statement.executeBatch();
		
		long end = System.currentTimeMillis();
		
		System.out.println(end-start);
		
		JDBCUtils.close(null, statement, conn);
		
	}

}
