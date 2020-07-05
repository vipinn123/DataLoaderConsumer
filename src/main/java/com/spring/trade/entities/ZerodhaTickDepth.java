package com.spring.trade.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

//@Entity // This tells Hibernate to make a table out of this class
//@Table(name = "zerodha_tick_depth")
public class ZerodhaTickDepth {
	
	int orders;
	int quantity;
	double price;
	
	//@ManyToOne(fetch = FetchType.LAZY, optional = false)
	//@JoinColumn(name = "tick_id", nullable = false)	  
	private ZerodhaTick tick;

	public ZerodhaTickDepth() {
		// TODO Auto-generated constructor stub
	}

}
