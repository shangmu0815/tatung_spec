/*
 * �b 2008/9/11 �إ�
 *
 * �Y�n�ܧ�o�Ӳ��ͪ��ɮת��d���A�в���
 * ���� > �ߦn�]�w > Java > �{���X���� > �{���X�M����
 */
package com.tatung.imis.H.HS.HS7.dao;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import com.tatung.imis.H.Common.dao.H_ConnectionDAO;
import com.tatung.imis.H.Common.data.H_tableVO;
import com.tatung.imis.H.Common.model.H_TransactionModel;
import com.tatung.imis.H.Common.util.HS_Source;


/**
 * @author Administrator
 *
 * �Y�n�ܧ�o�Ӳ��ͪ����O���Ѫ��d���A�в���
 * ���� > �ߦn�]�w > Java > �{���X���� > �{���X�M����
 */
public class HS_72DAO extends H_ConnectionDAO{
	private static boolean DEBUG = true;
	private String transactionId = null;
	public HS_72DAO(String transactionId){
		this.transactionId = transactionId;
	}

	public H_tableVO qryHstReferVO() throws Exception,SQLException{
			Statement stmt = null;
			Vector v=new Vector();
			H_tableVO vo=null;
			try
			{	
				H_TransactionModel transaction = H_TransactionModel.getInstance();
				System.out.println("this.transactionId->"+this.transactionId);
				conn = transaction.getConnection(this.transactionId);			
				stmt = conn.createStatement();
				StringBuffer sql= new StringBuffer();
				sql.append("SELECT ");
				sql.append("H_ATDYM ");
				sql.append("FROM ");
				sql.append(HS_Source.schema+"HST_REFER ");
				stmt=conn.createStatement();
				rs=stmt.executeQuery(sql.toString());
				if(DEBUG) System.out.println("sql->"+sql);
				while(rs.next())
				{		
					vo=new H_tableVO();				
					for(int i=1;i<=1;i++){
						System.out.println("rs.getString("+i+")->"+rs.getString(i));
						vo.setFld(i,rs.getString(i)==null?"":rs.getString(i));  							
					}						
				}
			}
			catch (Exception e){			
				throw new Exception("�d���~��Ѽ��ɧ@�~qryHstReferVO����-->"+e.toString());
			}
			finally
			{
				stmt.close();
			}
			 return vo;			
	}		
	public Vector qryHstPayrollNowV(String [] strSource) throws Exception,SQLException{
			Statement stmt = null;
			Vector v=new Vector();
			H_tableVO vo=null;
			try
			{	
				H_TransactionModel transaction = H_TransactionModel.getInstance();
				conn = transaction.getConnection(this.transactionId);			
				stmt = conn.createStatement();
				StringBuffer sql= new StringBuffer();
				sql.append("SELECT ");
				sql.append("H_SEQNO ");
				sql.append(",H_SATDYM ");				
				sql.append(",H_IDNO ");
				sql.append(",SUBSTRING(H_DEPTNO,2,8) ");
				sql.append(",H_CCLASS ");
				sql.append(",H_RCODE ");
				sql.append(",H_BASEPAY ");
				sql.append(",H_ADDPAY ");
				sql.append("FROM ");
				sql.append(HS_Source.schema+"HST_PAYROLL_NOW ");
				sql.append("WHERE ");
				sql.append("H_SALCYCE  BETWEEN  '"+(strSource[0].trim())+"' AND  '"+(strSource[1].trim())+"' ");
				sql.append("AND ");
				sql.append("H_SATDYM = '"+(strSource[2].trim())+"' ");
				sql.append("ORDER BY ");
				sql.append("SUBSTRING(H_DEPTNO,8,1) ASC ");
				sql.append(",SUBSTRING(H_DEPTNO,2,6) ASC ");
				sql.append(",SUBSTRING(H_CCLASS,1,1) DESC ");
				sql.append(",H_RCODE DESC ");
				sql.append(",SUBSTRING(H_CCLASS,2,1) DESC ");
				sql.append(",H_BASEPAY DESC ");
				sql.append(",H_ADDPAY DESC ");	
				stmt=conn.createStatement();
				rs=stmt.executeQuery(sql.toString());
				if(DEBUG) System.out.println("sql->"+sql);
				while(rs.next())
				{		
					vo=new H_tableVO();				
					for(int i=1;i<=8;i++){
						System.out.println("rs.getString("+i+")->"+rs.getString(i));
						vo.setFld(i,rs.getString(i)==null?"":rs.getString(i));  							
					}						
					v.add(vo);
				}
			}
			catch (Exception e){			
				throw new Exception("�d�߷�뵲����~������ɧ@�~qryHstPayrollNowV����-->"+e.toString());
			}
			finally
			{
				stmt.close();
			}
			 return v;			
	}		
//	�ק��뵲����~������ɧ@�~	*
	public Vector updHstPayrollNowV(Vector dataV)throws  Exception,SQLException
	{
		Statement stmt = null;
		int updateNum = 0;
		int errorNum = 0;
		Vector finalVector=new Vector();
		String strUpdateFailed="";
		String strSatdym="";
		String strIdno="";
		int intSeqno=1;
	  try{
		H_TransactionModel transaction = H_TransactionModel.getInstance();
		conn = transaction.getConnection(this.transactionId);			
		stmt = conn.createStatement();  
		StringBuffer sql= new StringBuffer();
		for(int i=0;i<dataV.size();i++){
			H_tableVO vo=(H_tableVO)dataV.get(i);
			//�~��~��
			strSatdym=vo.getFld(2);
			//�����Ҹ�   
			strIdno=vo.getFld(3);   	  
			sql.append("UPDATE  "+HS_Source.schema+"HST_PAYROLL_NOW ");
			sql.append("SET ");
			sql.append("H_SEQNO=");
			sql.append(intSeqno).append(" ");
			sql.append("WHERE  H_SATDYM='"+strSatdym+"' AND H_IDNO='"+strIdno+"' ");	
			//��s�Ǹ���		 
			intSeqno+=1;  
			if(DEBUG) System.out.println("updHstPayrollNowV sql->"+sql);
			stmt.addBatch(sql.toString());	
		}
		int[] result=stmt.executeBatch();
		for(int j=0;j<result.length;j++){
			System.out.println("j->"+j);	
			int intResult = result[j]; 
			System.out.println("intResult->"+intResult);
			if(intResult<=0){
				//���~����
				errorNum++;
				//���~�����Ҹ�						
				strUpdateFailed+=strIdno+";";				
			}else{
				//���\����
				updateNum+=intResult;
			}
		  }
	  }
	  catch (Exception e){
		  e.printStackTrace();
		  throw new Exception("�ק��뵲����~������ɧ@�~updHstPayrollNowV����->"+e.toString());
	  }
	  finally{
		finalVector.add(0,String.valueOf(updateNum));
		finalVector.add(1,String.valueOf(errorNum));
		if(strUpdateFailed!=null){
			if(strUpdateFailed.equals("")){
				strUpdateFailed="�L";
			}
		}
		finalVector.add(2,"�g�ɦ��~�������Ҹ� : "+strUpdateFailed);
		stmt.close();
	  }
	  return finalVector;
	}	

//	�ק��뵲����~������ɧ@�~	*
	public Vector updHstPayrollNow2V(Vector dataV)throws  Exception,SQLException
	{
		Statement stmt = null;
		int updateNum = 0;
		int errorNum = 0;
		Vector finalVector=new Vector();
		String strUpdateFailed="";
		String strSatdym="";
		String strIdno="";
		int intSeqno=1;
	  try{
		H_TransactionModel transaction = H_TransactionModel.getInstance();
		conn = transaction.getConnection(this.transactionId);			
		stmt = conn.createStatement();  
		StringBuffer sql= new StringBuffer();
		for(int i=0;i<dataV.size();i++){
			H_tableVO vo=(H_tableVO)dataV.get(i);
			//�~��~��
			strSatdym=vo.getFld(2);
			//�����Ҹ�   
			strIdno=vo.getFld(3);   	  
			sql.append("UPDATE  "+HS_Source.schema+"HST_PAYROLL_NOW ");
			sql.append("SET ");
			sql.append("H_SEQNO=");
			sql.append(intSeqno).append(" ");
			sql.append("WHERE  H_SATDYM='"+strSatdym+"' AND H_IDNO='"+strIdno+"' ");	
			//��s�Ǹ���		 
			intSeqno+=1;  
			if(DEBUG) System.out.println("updHstPayrollNowV sql->"+sql);
			stmt.addBatch(sql.toString());	
		}
		int[] result=stmt.executeBatch();
		for(int j=0;j<result.length;j++){
			System.out.println("j->"+j);	
			int intResult = result[j]; 
			System.out.println("intResult->"+intResult);
			if(intResult<=0){
				//���~����
				errorNum++;
				//���~�����Ҹ�						
				strUpdateFailed+=strIdno+";";				
			}else{
				//���\����
				updateNum+=intResult;
			}
		  }
	  }
	  catch (Exception e){
		  e.printStackTrace();
		  throw new Exception("�ק��뵲����~������ɧ@�~updHstPayrollNowV����->"+e.toString());
	  }
	  finally{
		finalVector.add(0,String.valueOf(updateNum));
		finalVector.add(1,String.valueOf(errorNum));
		if(strUpdateFailed!=null){
			if(strUpdateFailed.equals("")){
				strUpdateFailed="�L";
			}
		}
		finalVector.add(2,"�g�ɦ��~�������Ҹ� : "+strUpdateFailed);
		stmt.close();
	  }
	  return finalVector;
	}		
		
////	�����
//	public Vector qryDGPCLASSDataV()throws Exception, SQLException{
//		backupDGPCLASSToHBTCLASSVO vo=null;
//		Vector  dataV=new Vector();
//		Statement stmt = null;
//		initalOtherConnection();
//		try
//		{
//			String sql=null;						
//			sql= "SELECT " +
//					"* "+
//					"FROM PCLASS ";					
////			System.out.println("sql->"+sql);
//			stmt = conn.createStatement();
//			rs=stmt.executeQuery(sql);
//			while(rs.next())
//			{
//				vo=new backupDGPCLASSToHBTCLASSVO();
//				vo.setPCLASSNO(rs.getString("PCLASSNO"));
//				vo.setPDESC(rs.getString("PDESC"));
//				vo.setPDETL(rs.getString("PDETL"));
//				dataV.addElement(vo);
//			}
//		}
//		catch (SQLException e){
//			e.printStackTrace();
//			throw new Exception("�d��DG ¾�إN�X�ɧ@�~����-->"+e.toString());
//		}catch (Exception e){	
//			e.printStackTrace();	
//			throw new Exception("�d��DG ¾�إN�X�ɧ@�~����-->"+e.toString());
//		}
//		finally
//		{
//			closeAllConnection();
//		}
//		 return dataV;
//	}
//	
//	
////	----�s�W��IMIS������HBT_CLASS¾�إN�X�ɤ���� 
//	  public Vector addDGtoIMISHbtClassV(Vector addHbtClassV)throws Exception, SQLException{			
//		  int iResult=0;
//		  int ibatchValue=0;
//		  int finalResult=0;
//		String strUpdateFailed="";
//		Vector finalVector=new Vector();		
//		  try{
//				initalBADMConnection2();			//�Ncommection�}��
//				conn.setAutoCommit(false);     //�Nconnection��commit���������
//				Statement stmt = null;
//				stmt = conn.createStatement();				
//				String sql=null;
//				sql="SELECT COUNT(H_CCLASS) AS CCLASS FROM badm.dbo.HBT_CLASS ";
//				rs=stmt.executeQuery(sql);
//				rs.next();				
//				if(rs.getInt("CCLASS")>0){
//					sql = "DELETE FROM badm.dbo.HBT_CLASS  ";
//					iResult =stmt.executeUpdate(sql);
//					if(iResult==0){
//						throw new Exception("�R��������¾�إN�X�ɥ���!");
//					}
//				}
//					for(int i=0;i<addHbtClassV.size();i++){
//					backupDGPCLASSToHBTCLASSVO vo=(backupDGPCLASSToHBTCLASSVO)addHbtClassV.get(i);		
//					StringBuffer sql1= new StringBuffer(
//						  "INSERT INTO " +
//						  "badm.dbo.HBT_CLASS " +
//						  "(H_CCLASS,H_DESC,H_DETL) ");
//					sql1.append("values (");
//					sql1.append(vo.getPCLASSNO()==null?"''":(("'").concat(vo.getPCLASSNO()).concat("'"))).append(","); //¾�إN�X
//					sql1.append(vo.getPDESC()==null?"''":(("'").concat(vo.getPDESC()).concat("'"))).append(","); //¾�ئW��
//					sql1.append(vo.getPDETL()==null?"''":(("'").concat(vo.getPDETL()).concat("'"))); //�Բӻ���
//					sql1.append(" ) ");					
//					String strPclassno=vo.getPCLASSNO();
//					  int iresult = stmt.executeUpdate(sql1.toString());
//					  if(iresult!=1){
//						  strUpdateFailed=strUpdateFailed+strPclassno+";";
//					  }
//					   ibatchValue=ibatchValue+iresult;
//				}
//				  conn.commit();		//�����]��ok�ɰ�commit()
//				  finalResult=ibatchValue;
//		}catch (Exception e){
////			e.printStackTrace();
//			conn.rollback();		//���ҥ~�o�ͤ��ɪ���rollback
//			throw new Exception("¾�إN�X�ɼg�ɧ@�~����"+e.toString());
//		  }
//		  finally{
//			finalVector.add(0,String.valueOf(finalResult));
//			if(strUpdateFailed!=null){
//				if(strUpdateFailed.equals("")){
//					strUpdateFailed="�L";
//				}
//			}
//			finalVector.add(1,"�g�ɦ��~��¾�إN�X:"+strUpdateFailed);
//			closeAllConnection();	//�Ncommection����
//		  }
//		  return finalVector;
//	  }	



	
}
