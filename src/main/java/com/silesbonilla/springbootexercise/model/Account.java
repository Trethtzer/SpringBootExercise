package com.silesbonilla.springbootexercise.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "accounts")
public class Account {
	
	//I decided to make all columns required to avoid complex situations. 
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "currency", nullable = false)
	private int currency;
	
	@Column(name = "money", nullable = false)
	private float money;
	
	@Column(name = "treasury", nullable = false)
	private boolean treasury;
	
	public Account() {
		
	}
	
	public Account(String name, int currency, float money, boolean treasury) {
		this.name = name;
		this.currency = currency;
		this.money = money;
		this.treasury = treasury;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public int getCurrency() {
		return currency;
	}
	public void setCurrency(int currency) {
		this.currency = currency;
	}

	public float getMoney() {
		return money;
	}
	public void setMoney(float money) {
		this.money = money;
	}

	public boolean isTreasury() {
		return treasury;
	}
	public void setTreasury(boolean treasury) {
		this.treasury = treasury;
	}
	
}
