package com.atguigu.jdbc.entity;

public class Employee {
	
	private Integer id;
	private String empName;
	private Integer empNo;
	private String detpId;
	private Integer age;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public Integer getEmpNo() {
		return empNo;
	}
	public void setEmpNo(Integer empNo) {
		this.empNo = empNo;
	}
	public String getDetpId() {
		return detpId;
	}
	public void setDetpId(String detpId) {
		this.detpId = detpId;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Employee() {
		
	}
	public Employee(Integer id, String empName, Integer empNo, String detpId, Integer age) {
		super();
		this.id = id;
		this.empName = empName;
		this.empNo = empNo;
		this.detpId = detpId;
		this.age = age;
	}
	@Override
	public String toString() {
		return "Employee [id=" + id + ", empName=" + empName + ", empNo=" + empNo + ", detpId=" + detpId + ", age="
				+ age + "]";
	}
	
	
	
	

}
