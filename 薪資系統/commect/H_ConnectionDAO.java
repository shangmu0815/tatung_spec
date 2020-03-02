package com.tatung.imis.H.Common.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import com.tatung.imis.S.Common.dao.CommonDBDAO;
/**

 *
 */
public class H_ConnectionDAO {

	protected Connection conn;
	protected PreparedStatement pstmt;
	protected ResultSet rs;
//	----人資測試區 
	private String mydbDriver= "com.microsoft.sqlserver.jdbc.SQLServerDriver";                                  				//2005版
	private final String myurl = "jdbc:sqlserver://DEVIMIS005\\HRMDB1:4808;databasename=badm";  				//2005版
	private final String myaccount = "badm";
	private final String mypassword= "THQtest4482";
//	---人資正式區
	  private String mydbDriver2 = "com.microsoft.sqlserver.jdbc.SQLServerDriver";                           							//2005版
	  private final String myurl2 = "jdbc:sqlserver://139.223.3.207:4482;databasename=badm";          							//2005版
	  private final String myaccount2 = "htpgm";
	  private final String mypassword2 = "htpgm";

////	----DG正式區//for mp 有關人事table
//	private String otherdbDriver = "com.informix.jdbc.IfxDriver";
//	private final String otherurl = "jdbc:informix-sqli://139.223.15.42:1530/db_mp:INFORMIXSERVER=misdbsrv; NEWLOCALE=en_us,zh_tw;NEWCODESET=Big5,8859-1,819;";
//	private final String otheraccount = "mtdba";
//	private final String otherpassword = "dba6628";	
//	
////	----DG正式區//for mt 有關研修table
//	private String otherdbDriver2 = "com.informix.jdbc.IfxDriver";
//	private final String otherurl2 = "jdbc:informix-sqli://139.223.15.42:1530/db_mt:INFORMIXSERVER=misdbsrv; NEWLOCALE=en_us,zh_tw;NEWCODESET=Big5,8859-1,819;";
//	private final String otheraccount2 = "mtdba";
//	private final String otherpassword2 = "dba6628";
	
	public void initalBADMConnection() throws Exception
		{		
			try
			{
				System.out.println("this.myurl->"+this.myurl);
				Class.forName(this.mydbDriver);
				this.conn = DriverManager.getConnection(this.myurl, this.myaccount , this.mypassword);
				System.out.println(this.conn);
				conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			} catch (Exception e)
			{		
					throw e;
			}
		}
	public void initalBADMConnection2() throws Exception
		{		
			try
			{
				Class.forName(this.mydbDriver2);
				this.conn = DriverManager.getConnection(this.myurl2, this.myaccount2 , this.mypassword2);
				System.out.println(this.conn);
				conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			} catch (Exception e)
			{		
					throw e;
			}
		}
			
//	public void initalOtherConnection() throws Exception
//		{		
//			try
//			{
//				Class.forName(this.otherdbDriver);
//				this.conn = DriverManager.getConnection(this.otherurl, this.otheraccount , this.otherpassword);
//				conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
//			} catch (Exception e)
//			{		
//					throw e;
//			}
//		}
//	public void initalOtherConnection2() throws Exception
//		{		
//			try
//			{
//				Class.forName(this.otherdbDriver2);
//				this.conn = DriverManager.getConnection(this.otherurl2, this.otheraccount2 , this.otherpassword2);
//				conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
//			} catch (Exception e)
//			{		
//					throw e;
//			}
//		}

		public void closeAllConnection() throws SQLException
		{
			try
			{
				if (this.rs != null)
				{
					this.rs.close();
				}

				if (this.pstmt != null)
				{
					this.pstmt.close();
				}

				if (this.conn != null)
				{
					this.conn.close();
				}
			} catch (SQLException e)
			{
				e.printStackTrace();
				throw e;
			}
		}
		
	public static void main(String[] args) {
		H_ConnectionDAO daoOBJ = new H_ConnectionDAO();
		try {
			daoOBJ.initalBADMConnection();
			daoOBJ.closeAllConnection();
		} catch (Exception e) {
			// TODO 自動產生 catch 區塊
			e.printStackTrace();
		}
	}		
}
