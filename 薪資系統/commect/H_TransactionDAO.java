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
					//���հ�
					this.initalBADMConnection(); 
					break;
			  case 2: 
			  		System.out.println("initalBADMConnection2(); ");
					//������
					  initalBADMConnection2(); 
					break;
			}
		} catch (Exception e) {
			// TODO �۰ʲ��� catch �϶�
			e.printStackTrace();
		}
		return conn;	
	}
}
