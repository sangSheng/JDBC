package com.atguigu.jdbc.utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.atguigu.jdbc.test.TestConnectionPoll;


public class JDBCUtils {
	
	private static DataSource dataSource;
	
	static {
		
		Properties properties = new Properties();
		
		try {
			properties.load(TestConnectionPoll.class.getClassLoader().getResourceAsStream("db.properties"));
			
			 dataSource = DruidDataSourceFactory.createDataSource(properties);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	//获取Connection
	public static Connection getConn() throws SQLException {
		
		return dataSource.getConnection();
		
	}
	
	// 释放资源
	public static void  close(ResultSet rs,Statement s,Connection c) throws Exception {
		
		if (rs !=null) {
			rs.close();
		}
		
		if (s != null) {
			s.close();
		}
		
		if (c !=null) {
			c.close();
		}
		
		
	}

}
