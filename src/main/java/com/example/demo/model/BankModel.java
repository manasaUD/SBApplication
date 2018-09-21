package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bank")
public class BankModel {

	@Id
	private Long acc_no;
	
	private String fname;
	
	private String lname;
	
	private Long acc_balance;

	public Long getAcc_no() {
		return acc_no;
	}

	public void setAcc_no(Long acc_no) {
		this.acc_no = acc_no;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public Long getAcc_balance() {
		return acc_balance;
	}

	public void setAcc_balance(Long acc_balance) {
		this.acc_balance = acc_balance;
	}
	
}