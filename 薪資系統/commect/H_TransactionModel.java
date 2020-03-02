package com.tatung.imis.H.Common.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Hashtable;

import com.tatung.imis.H.Common.dao.H_TransactionDAO;
//import com.tatung.imis.batch.Common.dao.TransactionTcpcDao;

public class H_TransactionModel {
	private static H_TransactionModel transactionModel = new H_TransactionModel();
	private Hashtable connContainer = new Hashtable();
	
	private H_TransactionModel(){	
	}
	
	public static H_TransactionModel getInstance(){
		return transactionModel;
	}
	
	public  synchronized String getNewTransaction(Connection conn)throws SQLException{
   		String id = getTransactionID();
   		this.connContainer.put(id,conn);
   		return id;
 	}
	
	public  synchronized String getNewTransaction(int intChoice)throws SQLException{
			System.out.println("intChoice->"+intChoice);
			Connection conn = getNewConnection(intChoice);
			String id = getTransactionID();
			this.connContainer.put(id,conn);
			return id;	
	}		
	
	public Connection getConnection(String id){
		Connection conn = (Connection) this.connContainer.get(id);
		return conn;	
	}
	
	/**
	 * 關閉這條Connection
	 * @param id Trasacton ID
	 * @return boolean 是否有關閉Connection
	 * 
	 */
	public synchronized boolean closeTransaction(String id){
		boolean result = false;
		try{
			Connection conn = (Connection) this.connContainer.get(id);	
			if (conn != null){
				this.connContainer.remove(id);
				conn.close();
				result = true;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean setRollback(String id){
		boolean result= false;
		try{
			Connection conn = (Connection) this.connContainer.get(id);
			if (conn != null){
				conn.rollback();
				result = true;
			}
		}catch(SQLException e){
			e.printStackTrace();	
		}
		return result;
	}
	
	public boolean setCommit(String id){
		boolean result = false;
		try{
			Connection conn = (Connection) this.connContainer.get(id);
			if (conn != null){
				conn.commit();
				result = true;
			}
		}catch(SQLException e){
			e.printStackTrace();	
		}
		return result;
	}
	//intChoice 測試區:1,正式區:2  
	private Connection getNewConnection(int intChoice)throws SQLException{
			System.out.println("intChoice->"+intChoice);
			Connection conn = new H_TransactionDAO().getConnect(intChoice);
			conn.setAutoCommit(false);
			return conn;
	}
	
	private String getTransactionID(){
		Date date = new Date();
		long time = date.getTime();
		double ran = Math.random();
		return String.valueOf(time + ran);		
	}

}
