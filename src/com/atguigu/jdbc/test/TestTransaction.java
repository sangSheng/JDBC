package com.atguigu.jdbc.test;

import static org.junit.Assert.*;

import java.sql.Connection;

import org.apache.commons.dbutils.QueryRunner;
import org.junit.Test;

import com.atguigu.jdbc.utils.JDBCUtils;

public class TestTransaction {
	
	// 如果要删除一个部门，必须将这个部门的所有员工也一起删除
	@Test
	public void testTransaction() throws Exception {
		
		QueryRunner queryRunner = new QueryRunner();
		
		Connection conn = JDBCUtils.getConn();
		
		
		try {
			// 将自动提交关闭  set autocommit=0
			conn.setAutoCommit(false);
			
			//开启事务 begin
			// 编写事务块语句
			String sql1="delete  from t_dept  where id=1";
			
			String sql2="delete from t_emp where deptid=1";
			
			 queryRunner.update(conn, sql1);
			 
			 queryRunner.update(conn, sql2);
			 
			 //提交事务！
			 conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			// 一旦发生异常，回滚事务到最初状态
			conn.rollback();
		}finally {
			
			JDBCUtils.close(null, null, conn);
		}
		 
		
	}

}
