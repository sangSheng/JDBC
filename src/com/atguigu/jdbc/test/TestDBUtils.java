package com.atguigu.jdbc.test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Test;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.atguigu.jdbc.entity.Employee;
import com.atguigu.jdbc.utils.JDBCUtils;

/*
 * ORM(Object relational Mapping) : 对象关系映射。
 * 			java是面向对象的语言。 在一个java程序中，如果要向关系型数据库保存数据。
 * 
 * 			通常是根据关系型数据库的表，创建对应的POJO(plain Old java object)(javabean)
 * 
 *  		orm要求：  关系型数据库和java对象 一一映射（关联）
 *  					表  -------->  集合对象（list,map）
 *  				       一 行 ---------> 一个对象
 *  				   一列-----------> 对象的一个属性
 * 
 * javabean:  ①必须有空参构造器 ②属性私有，提供getter,setter方法访问私有属性
 * 
 * javabean如果专门用来封装你的业务模型，这个javabean称为pojo(entity实体类)
 * 
 * ORM映射，将数据库的列名和 java的属性名的setXXX()一一对应。
 * 
 * 		name-----java对象的setName()
 * 
 * 
 * 实体类：  在做业务时，需要将业务中用到的数据，存储到mysql中。	
 * 			将mysql中的表，分为实体表，维度表。
 * 			实体表： 用来描述一个真实存在的物体。  举例：  用户，商品表(手机)，订单表
 * 			维度表： 维度表用来描述实体的部分特点或特征。
 * 						手机  一级分类  电子产品  二级 分类 移动电子产品
 *				  商品分类表
 *
 *				addidas :  一级分类 : 纺织品  二级： 运动纺织品  三级 ：鞋
 */
public class TestDBUtils {
	
	
	
	// 增删改
	@Test
	public void testUpdate() throws Exception {
		
		QueryRunner queryRunner = new QueryRunner();
		
		Connection conn = JDBCUtils.getConn();
		
		String sql="update t_emp set age=90 where id=1";
		
		 queryRunner.update(conn, sql);
		 
		JDBCUtils.close(null, null, conn);
		
	}
	
	//单行查询
	
	public void queryAnEmployee(int id) throws Exception {
		
		QueryRunner queryRunner = new QueryRunner();
		
		Connection conn = JDBCUtils.getConn();
		
		String sql="select id,name empName,empNo,age,deptId detpId from t_emp where id="+id;
		
		// 封装为一个bean
		//Employee employee = queryRunner.query(conn, sql, new BeanHandler<>(Employee.class));
		
		// 封装为一个Map<列名-列值>
		Map<String, Object> map = queryRunner.query(conn, sql, new MapHandler());
		
		System.out.println(map);
		
		JDBCUtils.close(null, null, conn);
		
	}
	
	//多行查询
	
	public void queryEmployees(int id) throws Exception {
			
			QueryRunner queryRunner = new QueryRunner();
			
			Connection conn = JDBCUtils.getConn();
			
			String sql="select id,name empName,empNo,age,deptId detpId from t_emp where id<"+id;
			
			// 返回List集合
			 //List<Employee> emps = queryRunner.query(conn, sql, new BeanListHandler<>(Employee.class));
			 // 返回Map的List集合
			  List<Map<String, Object>> emps = queryRunner.query(conn, sql, new MapListHandler());
			
			System.out.println(emps);
			
			JDBCUtils.close(null, null, conn);
			
		}
	
	//单一值（一行一列）查询
	public void querySingleValue() throws Exception {
		
		QueryRunner queryRunner = new QueryRunner();
		
		Connection conn = JDBCUtils.getConn();
		
		String sql="select count(*) from t_emp" ;
		
		
		Object result = queryRunner.query(conn, sql, new ScalarHandler());
		
		System.out.println(result);
		
		JDBCUtils.close(null, null, conn);
		
	}
	
	@Test
	public void testQueryAnEMployee() throws Exception {
		
		//queryAnEmployee(1);
		
		//queryEmployees(5);
		
		querySingleValue();
		
	}
	
	

}
