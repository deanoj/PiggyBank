package com.deanoj.piggybank.data;

import java.math.BigDecimal;

public class AccountDto {
	
	private int rowId;

	private String accountName;
	
	private BigDecimal balance;
	
	public AccountDto(String name, BigDecimal balance)
	{
		this.accountName = name;
		this.balance = balance;
	}
	
	public AccountDto(int rowId, String name, BigDecimal balance)
	{
		this.rowId = rowId;
		this.accountName = name;
		this.balance = balance;
	}

	public int getRowId() {
		return rowId;
	}

	public void setRowId(int rowId) {
		this.rowId = rowId;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String name) {
		this.accountName = name;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
}
