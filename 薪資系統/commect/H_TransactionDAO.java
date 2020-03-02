package com.tatung.imis.H.Common.dao;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author clshan
 *
 *
 * Description
 * 	This Class is be use to get a connection of Oracle.
  * 
 * Copyright Notice
 */
public class H_TransactionDAO extends H_ConnectionDAO{//xxxDAO
	
	public Connection getConnect(int intChoice)throws SQLException{
		try {
			intChoice=1;
			switch (intChoice)
			{
			  case 1: 
			  		System.out.println("initalBADMConnection(); ");
					//測試區
					this.initalBADMConnection(); 
					break;
			  case 2: 
			  		System.out.println("initalBADMConnection2(); ");
					//正式區
					  initalBADMConnection2(); 
					break;
			}
		} catch (Exception e) {
			// TODO 自動產生 catch 區塊
			e.printStackTrace();
		}
		return conn;	
	}
}
