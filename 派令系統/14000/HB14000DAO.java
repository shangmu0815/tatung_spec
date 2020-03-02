/*
 * 在 2009/5/6 建立
 *
 * 若要變更這個產生的檔案的範本，請移至
 * 視窗 > 喜好設定 > Java > 程式碼產生 > 程式碼和註解
 */
package com.tatung.imis.H.HB.dao;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import com.tatung.imis.Common.dao.CommonDAO;
import com.tatung.imis.H.HB.data.HB14000VO;
import com.tatung.imis.H.HB.exception.HbException;

/**
 * @author Administrator
 *
 * 若要變更這個產生的類別註解的範本，請移至
 * 視窗 > 喜好設定 > Java > 程式碼產生 > 程式碼A和註解
 */
public class HB14000DAO extends CommonDAO{
	private boolean DEBUG = false;

//	-------將欄位中之部分data以部分資料取代之function--replaceTheBlank(原始資料欄位,欲取代之資料 ex.全形空白,取代成之資料 ex.空字串)--------------------------
	  public String replaceTheBlank(String strFieldValue,String strBeforeReplace,String strReplacementName){
		  int replaceStrting=-1;
		  do{
			  replaceStrting=strFieldValue.indexOf(strBeforeReplace);
			  if(replaceStrting!=-1)
			  strFieldValue=strFieldValue.substring(0,replaceStrting)+strReplacementName+strFieldValue.substring(replaceStrting+1);
		  }while(replaceStrting!=-1);
		return strFieldValue;
	  }
	/**
	 * 將字串(數值)不足前補n 個0 , 多的截掉
	 * @param value  值
	 * @param number 位數 
	 * @return
	 */
	public  String fillZero(String value , int number){
		if(value==null) value = "";
		StringBuffer valueSB = new StringBuffer("");
		
		//--若整數大於原來Size 取傳入size 大小
		 if(value.length()>number){
			  valueSB.append(value.substring( (value.length()-number), value.length()) );
		  }else{
			  valueSB.append(value);
		  }
			  			
		int iCycle = number - valueSB.length();
		for(int i = 0 ; i <iCycle ; i++){
			 valueSB.insert(0, "0");
		}				
		return valueSB.toString();
	}

	//查詢訊電派令檔
	public Vector qryHbtTransinfoV(String strTrand,String strApproval)throws HbException, SQLException{
		Vector dataV=new Vector();
		initalHDBAConnection();
		try{
			HB14000VO vo=null;
			Statement stmt = null;
			stmt=conn.createStatement();
			String sbWhere= "";
			StringBuffer sbWhereClause = new StringBuffer();
			StringBuffer sql= new StringBuffer(
			"SELECT " +
			"CONVERT(CHAR(8),A.H_TRAND,112) AS TRAND "+			
			",ISNULL(A.H_TRANSNO,'') AS TRANSNO "+
			",A.H_IDNO AS IDNO "+
			",NAME=ISNULL((SELECT H_NAME FROM badm.dbo.HBT_PERSON WHERE H_IDNO=A.H_IDNO),'') "+
			",ISNULL(A.H_OCTRNO,'') AS OCTRNO "+
			",OCTR=ISNULL((SELECT H_CTR FROM badm.dbo.HAT_DEPTFIL WHERE H_DEPTNO=A.H_ODEPTNO),'') "+			
			",ISNULL(A.H_OPERSONTITLE,'') AS OPERSONTITLE "+
			",ISNULL(A.H_CTRNO,'') AS CTRNO "+
			",CTR=ISNULL((SELECT H_CTR FROM badm.dbo.HAT_DEPTFIL WHERE H_DEPTNO=A.H_DEPTNO),'') "+
			",ISNULL(A.H_PERSONTITLE,'') AS PERSONTITLE "+
			",ISNULL(A.H_APPROVAL,'') AS APPROVAL "+
			"FROM badm.dbo.HBT_TRANSINFO A  ");
			System.out.println("strTrand->"+strTrand+"-");
			System.out.println("strApproval->"+strApproval+"-");
			sbWhereClause.append("WHERE ");
			if(strTrand.length()==6){
				sbWhereClause.append("(SUBSTRING(CONVERT(CHAR(8), H_TRAND, 112), 1, 6) = '"+strTrand+"')   AND  ");					
			}
			if(strTrand.length()==8){
				sbWhereClause.append("H_TRAND = '"+strTrand+"'   AND  ");					
			}			
			if(!strApproval.trim().equals("")){
				sbWhereClause.append("A.H_APPROVAL ='"+strApproval+"'  AND  ");
			}
			//sbWhereClause的長度減去 AND 或 WHERE
			sbWhere=sbWhereClause.substring(0,(sbWhereClause.length()-6));
			sql.append(sbWhere);
			sql.append("ORDER BY A.H_TRANSNO,A.H_CTRNO ");
			if(DEBUG) System.out.println("sql->"+sql);
			rs=stmt.executeQuery(sql.toString());
			while(rs.next()){
				vo=new HB14000VO();
				vo.setNEWTRAND(rs.getString("TRAND")==null?"":replaceTheBlank(rs.getString("TRAND"),"　","").trim());
				vo.setNEWTRANSNO(rs.getString("TRANSNO")==null?"":replaceTheBlank(rs.getString("TRANSNO"),"　","").trim());
				vo.setH_IDNO(rs.getString("IDNO")==null?"":replaceTheBlank(rs.getString("IDNO"),"　","").trim());
				vo.setH_NAME(rs.getString("NAME")==null?"":replaceTheBlank(rs.getString("NAME"),"　","").trim());
				vo.setH_CTRNO(rs.getString("OCTRNO")==null?"":replaceTheBlank(rs.getString("OCTRNO"),"　","").trim());
				vo.setH_CTR(rs.getString("OCTR")==null?"":replaceTheBlank(rs.getString("OCTR"),"　","").trim());
				vo.setH_PERSONTITLE(rs.getString("OPERSONTITLE")==null?"":replaceTheBlank(rs.getString("OPERSONTITLE"),"　","").trim());
				vo.setNEWCTRNO(rs.getString("CTRNO")==null?"":replaceTheBlank(rs.getString("CTRNO"),"　","").trim());
				vo.setNEWCTR(rs.getString("CTR")==null?"":replaceTheBlank(rs.getString("CTR"),"　","").trim());
				vo.setNEWPERSONTITLE(rs.getString("PERSONTITLE")==null?"":replaceTheBlank(rs.getString("PERSONTITLE"),"　","").trim());
				vo.setH_APPROVAL(rs.getString("APPROVAL")==null?"":replaceTheBlank(rs.getString("APPROVAL"),"　","").trim());
				dataV.add(vo);
			}
		}
		catch (Exception e){	
			e.printStackTrace();	
			throw new HbException("查詢訊電派令檔qryHbtTransinfoV作業失敗-->"+e.toString());
		}
		finally
		{
			closeAllConnection();
		}
		 return dataV;
	}
	//查詢作業*
	public Vector qryHbtPersonHbtTransinfoV(String strTransno)throws HbException, SQLException{
			HB14000VO qryVO=null;
			Vector qryV=new Vector();		  
			  try
				  {
					initalHDBAConnection();
					Statement stmt = null;
					StringBuffer sql1= new StringBuffer(
					"SELECT " +
					"ISNULL(t2.H_IDNO,'') AS IDNO "+
					",ISNULL(t2.H_TRANSNO,'') AS  NEWTRANSNO "+
					",CONVERT(CHAR(8),t2.H_PUBLISHDAY,112) AS NEWPUBLISHDAY "+
					",CONVERT(CHAR(8),t2.H_TRAND,112) AS NEWTRAND "+
					",ISNULL(t2.H_CTRNO,'') AS  NEWCTRNO "+
					",ISNULL(t2.H_DEPTNO,'') AS  NEWDEPTNO "+
					",ISNULL(t2.H_TRANSDEPT2,'') AS NEWTRANSDEPT2 "+
					",ISNULL(t2.H_PCODE,'') AS NEWPCODE "+
					",ISNULL(t2.H_PERSONTITLE,'') AS NEWPERSONTITLE "+
					",ISNULL(t2.H_JCODE,'') AS NEWJCODE "+
					",ISNULL(t2.H_SALCYCE,'') AS NEWSALCYCE "+
					",ISNULL(t2.H_SHIFT,'') AS NEWSHIFT "+
					",ISNULL(t2.H_AVEBONUS,'') AS NEWAVEBONUS "+					
					",ISNULL(t2.H_FOREBONUS,'') AS NEWFOREBONUS "+					
					",ISNULL(t2.H_MARK,'') AS NEWMARK "+					
					",ISNULL(t2.H_STATUS,'') AS STATUS "+					
					",ISNULL(t2.H_NOTE,'') AS NOTE "+					
					",CONVERT(CHAR(8),t2.H_OTRAND,112) AS OTRAND "+					
					",ISNULL(t2.H_OCTRNO,'') AS OCTRNO "+					
					",ISNULL(t2.H_ODEPTNO,'') AS ODEPTNO "+					
					",ODEPT2=ISNULL((SELECT H_DEPT2 FROM badm.dbo.HAT_DEPTFIL WHERE H_DEPTNO=t2.H_ODEPTNO),'') "+				
					",ISNULL(t2.H_OTRANSDEPT2,'') AS OTRANSDEPT2 "+					
					",ISNULL(t2.H_OPCODE,'') AS OPCODE "+					
					",ISNULL(t2.H_OPERSONTITLE,'') AS OPERSONTITLE "+					
					",ISNULL(t2.H_OJCODE,'') AS OJCODE "+					
					",ISNULL(t2.H_OSALCYCE,'') AS OSALCYCE "+					
					",ISNULL(t2.H_OSHIFT,'') AS OSHIFT "+					
					",ISNULL(t2.H_OAVEBONUS,'') AS OAVEBONUS "+					
					",ISNULL(t2.H_OFOREBONUS,'') AS OFOREBONUS "+					
					",ISNULL(t2.H_OMARK,'') AS OMARK "+					
					",ISNULL(t2.H_OSTATUS,'') AS OSTATUS "+					
					",ISNULL(t2.H_ONOTE,'') AS ONOTE "+					
					",ISNULL(t2.H_APPROVAL,'') AS APPROVAL "+										
					",ISNULL(t1.H_NAME,'') AS NAME "+					
					",ISNULL(t1.H_BASEPAY,0) AS BASEPAY "+					
					",ISNULL(t1.H_ADDPAY,0) AS ADDPAY "+					
					",ISNULL(t1.H_MANGPAY,0) AS MANGPAY "+					
					",ISNULL(t1.H_CCLASS,'') AS CCLASS "+					
					",ISNULL(t1.H_RCODE,'') AS RCODE "+					
					",CONVERT(CHAR(8),t1.H_RDATE,112) AS RDATE "+					
					",ISNULL(t1.H_ECODE,'') AS ECODE "+					
					",CONVERT(CHAR(8),t1.H_EDATE,112) AS EDATE "+					
					",ISNULL(t1.H_CATTR,'') AS CATTR "+
					",CONVERT(CHAR(8),t1.H_REPORT,112) AS REPORT "+					
					",CONVERT(CHAR(8),t1.H_REGIST,112) AS REGIST "+					
					",CONVERT(CHAR(8),t1.H_RESIGN,112) AS RESIGN "+					
					",CONVERT(CHAR(8),t1.H_RETURN,112) AS RETURNDAY "+					
					",ISNULL(t1.H_EDUCAT1,'') AS EDUCAT1  "+
					",ISNULL(t1.H_SCHOOL1,'') AS SCHOOL1 "+
					",ISNULL(t1.H_MAJOR1,'') AS MAJOR1 "+
					",ISNULL(t1.H_GRAD1,'') AS GRAD1 "+
					",ISNULL(t1.H_WKDIS,0) AS WKDIS "+					
					"FROM badm.dbo.HBT_PERSON t1 INNER JOIN badm.dbo.HBT_TRANSINFO t2 ON t1.H_IDNO=t2.H_IDNO " );
					sql1.append("WHERE t2.H_TRANSNO='"+strTransno+"' "); 
						String sql=sql1.toString();
						if(DEBUG) System.out.println("model  sql->"+sql);	
						stmt=conn.createStatement();	
						rs=stmt.executeQuery(sql); 
						while(rs.next())
						{
							qryVO=new HB14000VO();
							qryVO.setH_IDNO(rs.getString("IDNO")==null?"":replaceTheBlank(rs.getString("IDNO"),"　","").trim());
							qryVO.setNEWTRANSNO(rs.getString("NEWTRANSNO")==null?"":replaceTheBlank(rs.getString("NEWTRANSNO"),"　","").trim());
							qryVO.setNEWPUBLISHDAY(rs.getString("NEWPUBLISHDAY")==null?"":replaceTheBlank(rs.getString("NEWPUBLISHDAY"),"　","").trim());
							qryVO.setNEWTRAND(rs.getString("NEWTRAND")==null?"":replaceTheBlank(rs.getString("NEWTRAND"),"　","").trim());
							qryVO.setNEWCTRNO(rs.getString("NEWCTRNO")==null?"":replaceTheBlank(rs.getString("NEWCTRNO"),"　","").trim());
							qryVO.setNEWDEPTNO(rs.getString("NEWDEPTNO")==null?"":replaceTheBlank(rs.getString("NEWDEPTNO"),"　","").trim());
							qryVO.setNEWTRANSDEPT2(rs.getString("NEWTRANSDEPT2")==null?"":replaceTheBlank(rs.getString("NEWTRANSDEPT2"),"　","").trim());							
							qryVO.setNEWPCODE(rs.getString("NEWPCODE")==null?"":replaceTheBlank(rs.getString("NEWPCODE"),"　","").trim());
							qryVO.setNEWPERSONTITLE(rs.getString("NEWPERSONTITLE")==null?"":replaceTheBlank(rs.getString("NEWPERSONTITLE"),"　","").trim());
							qryVO.setNEWJCODE(rs.getString("NEWJCODE")==null?"":replaceTheBlank(rs.getString("NEWJCODE"),"　","").trim());
							qryVO.setNEWSALCYCE(rs.getString("NEWSALCYCE")==null?"":replaceTheBlank(rs.getString("NEWSALCYCE"),"　","").trim());
							qryVO.setNEWSHIFT(rs.getString("NEWSHIFT")==null?"":replaceTheBlank(rs.getString("NEWSHIFT"),"　","").trim());
							qryVO.setNEWAVEBONUS(rs.getString("NEWAVEBONUS")==null?"":replaceTheBlank(rs.getString("NEWAVEBONUS"),"　","").trim());
							qryVO.setNEWFOREBONUS(rs.getString("NEWFOREBONUS")==null?"":replaceTheBlank(rs.getString("NEWFOREBONUS"),"　","").trim());
							qryVO.setNEWMARK(rs.getString("NEWMARK")==null?"":replaceTheBlank(rs.getString("NEWMARK"),"　","").trim());
							qryVO.setNEWSTATUS(rs.getString("STATUS")==null?"":replaceTheBlank(rs.getString("STATUS"),"　","").trim());
							qryVO.setNEWNOTE(rs.getString("NOTE")==null?"":replaceTheBlank(rs.getString("NOTE"),"　","").trim());
							qryVO.setH_TRAND(rs.getString("OTRAND")==null?"":replaceTheBlank(rs.getString("OTRAND"),"　","").trim());
							qryVO.setH_CTRNO(rs.getString("OCTRNO")==null?"":replaceTheBlank(rs.getString("OCTRNO"),"　","").trim());
							qryVO.setH_DEPTNO(rs.getString("ODEPTNO")==null?"":replaceTheBlank(rs.getString("ODEPTNO"),"　","").trim());
							qryVO.setH_DEPT2(rs.getString("ODEPT2")==null?"":replaceTheBlank(rs.getString("ODEPT2"),"　","").trim());
							qryVO.setH_TRANSDEPT2(rs.getString("OTRANSDEPT2")==null?"":replaceTheBlank(rs.getString("OTRANSDEPT2"),"　","").trim());
							qryVO.setH_PCODE(rs.getString("OPCODE")==null?"":replaceTheBlank(rs.getString("OPCODE"),"　","").trim());
							qryVO.setH_PERSONTITLE(rs.getString("OPERSONTITLE")==null?"":replaceTheBlank(rs.getString("OPERSONTITLE"),"　","").trim());
							qryVO.setH_JCODE(rs.getString("OJCODE")==null?"":replaceTheBlank(rs.getString("OJCODE"),"　","").trim());
							qryVO.setH_SALCYCE(rs.getString("OSALCYCE")==null?"":replaceTheBlank(rs.getString("OSALCYCE"),"　","").trim());							
							qryVO.setH_SHIFT(rs.getString("OSHIFT")==null?"":replaceTheBlank(rs.getString("OSHIFT"),"　","").trim());							
							qryVO.setH_AVEBONUS(rs.getString("OAVEBONUS")==null?"":replaceTheBlank(rs.getString("OAVEBONUS"),"　","").trim());							
							qryVO.setH_FOREBONUS(rs.getString("OFOREBONUS")==null?"":replaceTheBlank(rs.getString("OFOREBONUS"),"　","").trim());														
							qryVO.setH_MARK(rs.getString("OMARK")==null?"":replaceTheBlank(rs.getString("OMARK"),"　","").trim());							
							qryVO.setH_STATUS(rs.getString("OSTATUS")==null?"":replaceTheBlank(rs.getString("OSTATUS"),"　","").trim());							
							qryVO.setH_NOTE(rs.getString("ONOTE")==null?"":replaceTheBlank(rs.getString("ONOTE"),"　","").trim());							
							qryVO.setH_APPROVAL(rs.getString("APPROVAL")==null?"":replaceTheBlank(rs.getString("APPROVAL"),"　","").trim());														
							qryVO.setH_NAME(rs.getString("NAME")==null?"":replaceTheBlank(rs.getString("NAME"),"　","").trim());							
							qryVO.setH_BASEPAY(rs.getString("BASEPAY")==null?"":replaceTheBlank(rs.getString("BASEPAY"),"　","").trim());							
							qryVO.setH_ADDPAY(rs.getString("ADDPAY")==null?"":replaceTheBlank(rs.getString("ADDPAY"),"　","").trim());							
							qryVO.setH_MANGPAY(rs.getString("MANGPAY")==null?"":replaceTheBlank(rs.getString("MANGPAY"),"　","").trim());							
							qryVO.setH_CCLASS(rs.getString("CCLASS")==null?"":replaceTheBlank(rs.getString("CCLASS"),"　","").trim());							
							qryVO.setH_RCODE(rs.getString("RCODE")==null?"":replaceTheBlank(rs.getString("RCODE"),"　","").trim());							
							qryVO.setH_RDATE(rs.getString("RDATE")==null?"":replaceTheBlank(rs.getString("RDATE"),"　","").trim());							
							qryVO.setH_ECODE(rs.getString("ECODE")==null?"":replaceTheBlank(rs.getString("ECODE"),"　","").trim());							
							qryVO.setH_EDATE(rs.getString("EDATE")==null?"":replaceTheBlank(rs.getString("EDATE"),"　","").trim());							
							qryVO.setH_CATTR(rs.getString("CATTR")==null?"":replaceTheBlank(rs.getString("CATTR"),"　","").trim());							
							qryVO.setH_REPORT(rs.getString("REPORT")==null?"":replaceTheBlank(rs.getString("REPORT"),"　","").trim());							
							qryVO.setH_REGIST(rs.getString("REGIST")==null?"":replaceTheBlank(rs.getString("REGIST"),"　","").trim());							
							qryVO.setH_RESIGN(rs.getString("RESIGN")==null?"":replaceTheBlank(rs.getString("RESIGN"),"　","").trim());							
							qryVO.setH_RETURNDAY(rs.getString("RETURNDAY")==null?"":replaceTheBlank(rs.getString("RETURNDAY"),"　","").trim());							
							qryVO.setH_EDUCAT1(rs.getString("EDUCAT1")==null?"":replaceTheBlank(rs.getString("EDUCAT1"),"　","").trim());
							qryVO.setH_SCHOOL1(rs.getString("SCHOOL1")==null?"":replaceTheBlank(rs.getString("SCHOOL1"),"　","").trim());
							qryVO.setH_MAJOR1(rs.getString("MAJOR1")==null?"":replaceTheBlank(rs.getString("MAJOR1"),"　","").trim());
							qryVO.setH_GRAD1(rs.getString("GRAD1")==null?"":replaceTheBlank(rs.getString("GRAD1"),"　","").trim());
							qryVO.setH_WKDIS(rs.getString("WKDIS")==null?"":replaceTheBlank(rs.getString("WKDIS"),"　","").trim());
							qryV.add(qryVO);
						}
				 }
				 catch (Exception e){
					e.printStackTrace();
					 throw new HbException("查詢作業qryHbtPersonHbtTransinfoV失敗-->"+e.toString());
				 }
				 finally
				 {			 	
				   closeHDBAConnection();
				 }
				  return qryV;	
			 }
	//查詢訊電派令檔
	public Vector qryHbtTransinfo2V(String strTransno)throws HbException,SQLException{
		Vector dataV=new Vector();
		initalHDBAConnection();
		try{
			HB14000VO vo=null;
			Statement stmt = null;
			stmt=conn.createStatement();
			StringBuffer sql= new StringBuffer(
			"SELECT " +
			"H_IDNO AS IDNO "+
			",ISNULL(H_TRANSNO,'') AS TRANSNO "+
			",CONVERT(CHAR(8),H_TRAND,112) AS TRAND "+
			",ISNULL(H_CTRNO,'') AS CTRNO "+			
			",ISNULL(H_DEPTNO,'') AS DEPTNO "+
			",ISNULL(H_TRANSDEPT2,'') AS TRANSDEPT2 "+			
			",ISNULL(H_PCODE,'') AS PCODE "+
			",ISNULL(H_PERSONTITLE,'') AS PERSONTITLE "+			
			",ISNULL(H_JCODE,'') AS JCODE "+
			",ISNULL(H_SALCYCE,'') AS SALCYCE "+
			",ISNULL(H_SHIFT,'') AS SHIFT "+
			",ISNULL(H_AVEBONUS,'') AS AVEBONUS "+
			",ISNULL(H_FOREBONUS,'') AS FOREBONUS "+			
			",ISNULL(H_MARK,'') AS MARK "+
			"FROM badm.dbo.HBT_TRANSINFO " +
			"WHERE H_TRANSNO='"+strTransno+"' ");
			if(DEBUG) System.out.println("sql->"+sql);
			rs=stmt.executeQuery(sql.toString());
			while(rs.next()){
				vo=new HB14000VO(); 
				vo.setH_IDNO(rs.getString("IDNO")==null?"":replaceTheBlank(rs.getString("IDNO"),"　","").trim());
//				vo.setH_TRANSNO(rs.getString("TRANSNO"));
				vo.setH_TRAND(rs.getString("TRAND")==null?"":replaceTheBlank(rs.getString("TRAND"),"　","").trim());
				vo.setH_CTRNO(rs.getString("CTRNO")==null?"":replaceTheBlank(rs.getString("CTRNO"),"　","").trim());
				vo.setH_DEPTNO(rs.getString("DEPTNO")==null?"":replaceTheBlank(rs.getString("DEPTNO"),"　","").trim());
				vo.setH_TRANSDEPT2(rs.getString("TRANSDEPT2")==null?"":replaceTheBlank(rs.getString("TRANSDEPT2"),"　","").trim());
				vo.setH_PCODE(rs.getString("PCODE")==null?"":replaceTheBlank(rs.getString("PCODE"),"　","").trim());
				vo.setH_PERSONTITLE(rs.getString("PERSONTITLE")==null?"":replaceTheBlank(rs.getString("PERSONTITLE"),"　","").trim());			
				vo.setH_JCODE(rs.getString("JCODE")==null?"":replaceTheBlank(rs.getString("JCODE"),"　","").trim());
				vo.setH_SALCYCE(rs.getString("SALCYCE")==null?"":replaceTheBlank(rs.getString("SALCYCE"),"　","").trim());				
				vo.setH_SHIFT(rs.getString("SHIFT")==null?"":replaceTheBlank(rs.getString("SHIFT"),"　","").trim());
				vo.setH_AVEBONUS(rs.getString("AVEBONUS")==null?"":replaceTheBlank(rs.getString("AVEBONUS"),"　","").trim());
				vo.setH_FOREBONUS(rs.getString("FOREBONUS")==null?"":replaceTheBlank(rs.getString("FOREBONUS"),"　","").trim());							
				vo.setH_MARK(rs.getString("MARK")==null?"":replaceTheBlank(rs.getString("MARK"),"　","").trim());		
				dataV.add(vo);
			}
		}
		catch (Exception e){	
			e.printStackTrace();	
			throw new HbException("查詢訊電派令檔qryHbtTransinfo2V作業失敗-->"+e.toString());
		}
		finally
		{
			closeAllConnection();
		}
		 return dataV;
	}
	
	public HB14000VO qryHatDeptfilVO(String strDeptno)throws HbException, SQLException{
		HB14000VO qryVO=null;
			Vector qryV=new Vector();		  
			  try
				  {
						initalHDBAConnection();
						Statement stmt = null;
						StringBuffer sql1= new StringBuffer(
						"SELECT " +
						"ISNULL(H_DEPT1,'') AS DEPT1 "+
						",ISNULL(H_DEPT2,'') AS DEPT2 "+
						",ISNULL(H_DEPT,'') AS DEPT "+
						"FROM badm.dbo.HAT_DEPTFIL  " );
						sql1.append("WHERE H_DEPTNO='"+strDeptno+"'  "); 
						String sql=sql1.toString();
						if(DEBUG) System.out.println("model  sql->"+sql);	
						stmt=conn.createStatement();	
						rs=stmt.executeQuery(sql); 
						while(rs.next())
						{
							qryVO=new HB14000VO();
							qryVO.setH_DEPT1(rs.getString("DEPT1")==null?"":this.replaceTheBlank(rs.getString("DEPT1"),"　","").trim());
							qryVO.setH_DEPT2(rs.getString("DEPT2")==null?"":this.replaceTheBlank(rs.getString("DEPT2"),"　","").trim());	
							qryVO.setH_DEPT(rs.getString("DEPT")==null?"":this.replaceTheBlank(rs.getString("DEPT"),"　","").trim());			
							
						}
				 }catch (Exception e){
					 e.printStackTrace();
					 throw new HbException("查詢作業qryHatDeptfilVO失敗-->"+e.toString());
				 }
				 finally
				 {			 	
				   closeHDBAConnection();
				 }
				  return qryVO;	
			 }	
//	---check人事主檔存在否*
	  public boolean checkHbtPersonInfo(String strIdno)throws HbException, SQLException{
		  boolean checkInfo= false;
		Statement stmt = null;
		try
		{
		  this.initalHDBAConnection();
			String sql=null;
			StringBuffer sql1= new StringBuffer(
				"SELECT * "+
				"FROM badm.dbo.HBT_PERSON ");
			sql1.append("WHERE H_IDNO='"+strIdno+"' ");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql1.toString());
			checkInfo=rs.next();
		   }catch (Exception e){
			  throw new HbException("check人事主檔作業checkHbtPersonInfo失敗-->"+e.toString());
		  }finally{
			closeHDBAConnection();	
		  }
		  return checkInfo;
	  }
//	---check新進人員檔存在否*
	  public boolean checkHbtNewpsnInfo(String strIdno)throws HbException, SQLException{
		  boolean checkInfo= false;
		Statement stmt = null;
		try
		{
		  this.initalHDBAConnection();
			String sql=null;
			StringBuffer sql1= new StringBuffer(
				"SELECT * "+
				"FROM badm.dbo.HBT_NEWPSN ");
			sql1.append("WHERE H_IDNO='"+strIdno+"' ");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql1.toString());
			checkInfo=rs.next();
		   }catch (Exception e){
			  throw new HbException("check新進人員檔作業checkHbtNewpsnInfo失敗-->"+e.toString());
		  }finally{
			closeHDBAConnection();	
		  }
		  return checkInfo;
	  }
//	---check人員資訊檔存在否*
	  public boolean checkHbtPersoninfo(String strIdno)throws HbException, SQLException{
		  boolean checkInfo= false;
		Statement stmt = null;
		try
		{
		  this.initalHDBAConnection();
			String sql=null;
			StringBuffer sql1= new StringBuffer(
				"SELECT * "+
				"FROM badm.dbo.HBT_PERSONINFO ");
			sql1.append("WHERE H_IDNO='"+strIdno+"' ");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql1.toString());
			checkInfo=rs.next();
		   }catch (Exception e){
			  throw new HbException("check人員資訊檔作業checkHbtPersonInfo失敗-->"+e.toString());
		  }finally{
			closeHDBAConnection();	
		  }
		  return checkInfo;
	  }	  
//	---check出勤主檔存在否*
	  public boolean checkHctEmplatdInfo(String strIdno)throws HbException, SQLException{
		  boolean checkInfo= false;
		Statement stmt = null;
		try
		{
		  this.initalHDBAConnection();
			String sql=null;
			StringBuffer sql1= new StringBuffer(
				"SELECT * "+
				"FROM badm.dbo.HCT_EMPLATD ");
			sql1.append("WHERE H_IDNO='"+strIdno+"' ");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql1.toString());
			checkInfo=rs.next();
		   }catch (Exception e){
			  throw new HbException("check出勤主檔檔作業checkHctEmplatdInfo失敗-->"+e.toString());
		  }finally{
			closeHDBAConnection();	
		  }
		  return checkInfo;
	  }
//	---check異動歷史檔存在否*
	  public boolean checkHbtShiftInfo(String strIdno,String strTrandte,String strStatus,String strNote)throws HbException, SQLException{
		  boolean checkInfo= false;
		Statement stmt = null;
		try
		{
		  this.initalHDBAConnection();
			String sql=null;
			StringBuffer sql1= new StringBuffer(
				"SELECT * "+
				"FROM badm.dbo.HBT_SHIFT ");
			sql1.append("WHERE H_IDNO='"+strIdno+"' AND H_TRANDTE='"+strTrandte+"' AND H_STATUS='"+strStatus+"' AND H_NOTE='"+strNote+"' ");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql1.toString());
			checkInfo=rs.next();
		   }catch (Exception e){
			  e.printStackTrace();
			  throw new HbException("check異動歷史檔作業checkHbtShiftInfo失敗-->"+e.toString());
		  }finally{
			closeHDBAConnection();	
		  }
		  return checkInfo;
	  }
//	---check派令檔存在否*
	  public boolean checkHbtTransinfo(String strIdno,String strNewTransno)throws HbException, SQLException{
		  boolean checkInfo= false;
		Statement stmt = null;
		try
		{
		  this.initalHDBAConnection();
			String sql=null;
			StringBuffer sql1= new StringBuffer(
				"SELECT * "+
				"FROM badm.dbo.HBT_TRANSINFO ");
			sql1.append("WHERE H_IDNO='"+strIdno+"' AND H_TRANSNO='"+strNewTransno+"' ");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql1.toString());
			checkInfo=rs.next();
		   }catch (Exception e){
			  throw new HbException("check派令檔作業checkHbtTransInfo失敗-->"+e.toString());
		  }finally{
			closeHDBAConnection();	
		  }
		  return checkInfo;
	  }
//	---check人事異動檔存在否*
	  public boolean checkHbtTransInfo(String strIdno,String strDate,String strStatus,String strNote)throws HbException, SQLException{
		  boolean checkInfo= false;
		Statement stmt = null;
		try
		{
		  this.initalHDBAConnection();
			String sql=null;
			StringBuffer sql1= new StringBuffer(
				"SELECT * "+
				"FROM badm.dbo.HBT_TRANS ");
			sql1.append("WHERE H_IDNO='"+strIdno+"' AND H_DATE='"+strDate+"' AND H_STATUS='"+strStatus+"' AND H_NOTE='"+strNote+"' ");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql1.toString());
			checkInfo=rs.next();
		   }catch (Exception e){
			  e.printStackTrace();
			  throw new HbException("check人事異動檔作業checkHbtTransInfo失敗-->"+e.toString());
		  }finally{
			closeHDBAConnection();	
		  }
		  return checkInfo;
	  }
//	修改HBT_PERSON(人事主檔)作業*	
	public int updHbtPersonV(Vector needUpdPersonV)throws HbException, SQLException
	{

	int iResult = 0;
	  try{
		  this.initalHDBAConnection();
		Statement stmt = null;
		stmt = conn.createStatement();
		String sql=null;		  
		for(int i=0;i<needUpdPersonV.size();i++){
		  HB14000VO vo=(HB14000VO)needUpdPersonV.get(i);
		  String strIdno=vo.getH_IDNO();
		  String strApproval=vo.getH_APPROVAL();
		  String strDeptno="";
		  String strCtrno="";
		  String strPcode="";
		  String strJcode="";
		  String strTrand="";
		  String strStatus="";
		  String strNote="";		  
		  if(strApproval.equals("Y")){
				strDeptno=vo.getNEWDEPTNO();
				strCtrno=vo.getNEWCTRNO();
				strPcode=vo.getNEWPCODE();
				strJcode=vo.getNEWJCODE();
				strTrand=vo.getNEWTRAND();
				strStatus="0";
				strNote="00";		  	
		  }else if(strApproval.equals("N")){
				strDeptno=vo.getH_DEPTNO();
				strCtrno=vo.getH_CTRNO();
				strPcode=vo.getH_PCODE();
				strJcode=vo.getH_JCODE();
				strTrand=vo.getH_TRAND();
				strStatus=vo.getH_STATUS();
				strNote=vo.getH_NOTE();		  	
		  }

		  String strCop=vo.getH_COP();
		  String strDop=vo.getH_DOP();		  
		  StringBuffer sql2 = new StringBuffer("UPDATE  badm.dbo.HBT_PERSON ");
		  sql2.append("SET ");
		  sql2.append("H_DEPTNO=");
		  sql2.append((strDeptno==null||strDeptno.equals(""))?"''":("'").concat(strDeptno).concat("'")).append(",");
		  sql2.append("H_CTRNO=");
		  sql2.append((strCtrno==null||strCtrno.equals(""))?"''":("'").concat(strCtrno).concat("'")).append(",");
		  sql2.append("H_PCODE=");
		  sql2.append((strPcode==null||strPcode.equals(""))?"''":("'").concat(strPcode).concat("'")).append(",");
		  sql2.append("H_JCODE=");
		  sql2.append((strJcode==null||strJcode.equals(""))?"''":("'").concat(strJcode).concat("'")).append(",");
		  sql2.append("H_TRAND=");
		  sql2.append((strTrand==null||strTrand.equals(""))?null:("'").concat(strTrand).concat("'")).append(",");
		  sql2.append("H_STATUS=");
		  sql2.append((strStatus==null||strStatus.equals(""))?"''":("'").concat(strStatus).concat("'")).append(",");
		  sql2.append("H_NOTE=");
		  sql2.append((strNote==null||strNote.equals(""))?"''":("'").concat(strNote).concat("'")).append(",");
		  sql2.append("H_COP=");
		  sql2.append((strCop==null||strCop.equals(""))?"''":("'").concat(strCop).concat("'")).append(",");
		  sql2.append("H_DOP=");
		  sql2.append((strDop==null||strDop.equals(""))?null:("'").concat(strDop).concat("'")).append(" ");
		  sql2.append("WHERE  H_IDNO='"+strIdno+"' ");
			   
			if(DEBUG) System.out.println("sql2->"+sql2);
			stmt.addBatch(sql2.toString());	
		}
		int[] result=stmt.executeBatch();
		for(int j=0;j<result.length;j++){	
			iResult = result[j]; 
			if(iResult<=0){  
				throw new HbException("修改人事主檔作業失敗");	
			}
		  }
	  }
	  catch (Exception e){
		  e.printStackTrace();
		  throw new HbException("修改人事主檔作業updHbtPersonV失敗->"+e.toString());
	  }
	  finally{
		  closeHDBAConnection();
	  }
	  return iResult;
	}
//	修改HBT_NEWPSN(新進人員檔)作業*	
	public int updHbtNewpsnV(Vector needUpdNewpsnV)throws HbException, SQLException
	{
	int iResult = 0;
	  try{
		  this.initalHDBAConnection();
		Statement stmt = null;
		stmt = conn.createStatement();
		String sql=null;		  
		for(int i=0;i<needUpdNewpsnV.size();i++){
		  HB14000VO vo=(HB14000VO)needUpdNewpsnV.get(i);
			  String strIdno=vo.getH_IDNO();
			String strApproval=vo.getH_APPROVAL();
			String strDeptno="";
			String strCtrno="";
			String strPcode="";
			String strJcode="";	  
			if(strApproval.equals("Y")){
				  strDeptno=vo.getNEWDEPTNO();
				  strCtrno=vo.getNEWCTRNO();
				  strPcode=vo.getNEWPCODE();
				  strJcode=vo.getNEWJCODE();		  	
			}else if(strApproval.equals("N")){
				  strDeptno=vo.getH_DEPTNO();
				  strCtrno=vo.getH_CTRNO();
				  strPcode=vo.getH_PCODE();
				  strJcode=vo.getH_JCODE();		  	
			} 
			  String strCop=vo.getH_COP();
			  String strDop=vo.getH_DOP();
			StringBuffer sql3 = new StringBuffer("UPDATE  badm.dbo.HBT_NEWPSN ");
			sql3.append("SET ");
			sql3.append("H_DEPTNO=");
			sql3.append((strDeptno==null||strDeptno.equals(""))?"''":("'").concat(strDeptno).concat("'")).append(",");
			sql3.append("H_CTRNO=");
			sql3.append((strCtrno==null||strCtrno.equals(""))?"''":("'").concat(strCtrno).concat("'")).append(",");
			sql3.append("H_PCODE=");
			sql3.append((strPcode==null||strPcode.equals(""))?"''":("'").concat(strPcode).concat("'")).append(",");
			sql3.append("H_JCODE=");
			sql3.append((strJcode==null||strJcode.equals(""))?"''":("'").concat(strJcode).concat("'")).append(",");
			sql3.append("H_COP=");
			sql3.append((strCop==null||strCop.equals(""))?"''":("'").concat(strCop).concat("'")).append(",");
			sql3.append("H_DOP=");
			sql3.append((strDop==null||strDop.equals(""))?null:("'").concat(strDop).concat("'")).append(" ");
			sql3.append("WHERE  H_IDNO='"+strIdno+"' ");
		   
			if(DEBUG) System.out.println("updHbtNewpsnV sql3->"+sql3);
			stmt.addBatch(sql3.toString());	
		}
		int[] result=stmt.executeBatch();
		for(int j=0;j<result.length;j++){	
			iResult = result[j]; 
			if(iResult<=0){  
				throw new HbException("修改新進人員檔作業失敗");	
			}
		  }
	  }
	  catch (Exception e){
		  e.printStackTrace();
		  throw new HbException("修改新進人員檔作業updHbtNewpsnV失敗->"+e.toString());
	  }
	  finally{
		  closeHDBAConnection();
	  }
	  return iResult;
	}
//	修改HBT_PERSONINFO(人員資訊檔)作業	*
	public int updHbtPersoninfoV(Vector needUpdPersoninfoV)throws HbException, SQLException
	{

	int iResult = 0;
	  try{
		  this.initalHDBAConnection();
		Statement stmt = null;
		stmt = conn.createStatement();
		String sql=null;		  
		for(int i=0;i<needUpdPersoninfoV.size();i++){
			HB14000VO vo=(HB14000VO)needUpdPersoninfoV.get(i);
				String strIdno=vo.getH_IDNO();	
			String strApproval=vo.getH_APPROVAL();
			String strTransDept2="";
			String strPersontitle="";
			String strAvebonus="";
			String strForebonus="";
			String strMark="";		  
			if(strApproval.equals("Y")){
				strTransDept2=vo.getNEWTRANSDEPT2();
				strPersontitle=vo.getNEWPERSONTITLE();
				strAvebonus=vo.getNEWAVEBONUS();
				strForebonus=vo.getNEWFOREBONUS();
				strMark=vo.getNEWMARK(); 	
			}else if(strApproval.equals("N")){
				strTransDept2=vo.getH_TRANSDEPT2();
				strPersontitle=vo.getH_PERSONTITLE();
				strAvebonus=vo.getH_AVEBONUS();
				strForebonus=vo.getH_FOREBONUS();
				strMark=vo.getH_MARK();		  	
			}		  
			StringBuffer sql3 = new StringBuffer("UPDATE  badm.dbo.HBT_PERSONINFO ");
			sql3.append("SET ");
			sql3.append("H_TRANSDEPT2=");
			sql3.append((strTransDept2==null||strTransDept2.equals(""))?"''":("'").concat(strTransDept2).concat("'")).append(",");
			sql3.append("H_PERSONTITLE=");
			sql3.append((strPersontitle==null||strPersontitle.equals(""))?"''":("'").concat(strPersontitle).concat("'")).append(",");
			sql3.append("H_AVEBONUS=");
			sql3.append((strAvebonus==null||strAvebonus.equals(""))?"''":("'").concat(strAvebonus).concat("'")).append(",");
			sql3.append("H_FOREBONUS=");
			sql3.append((strForebonus==null||strForebonus.equals(""))?"''":("'").concat(strForebonus).concat("'")).append(",");
			sql3.append("H_MARK=");
			sql3.append((strMark==null||strMark.equals(""))?"''":("'").concat(strMark).concat("'")).append(" ");
			sql3.append("WHERE  H_IDNO='"+strIdno+"' ");
		   
			if(DEBUG) System.out.println("updHbtPersoninfoV sql3->"+sql3);
			stmt.addBatch(sql3.toString());	
		}
		int[] result=stmt.executeBatch();
		for(int j=0;j<result.length;j++){	
			iResult = result[j]; 
			if(iResult<=0){  
				throw new HbException("修改人員資訊檔作業失敗");	
			}
		  }
	  }
	  catch (Exception e){
		  e.printStackTrace();
		  throw new HbException("修改人員資訊檔作業updHbtPersoninfoV失敗->"+e.toString());
	  }
	  finally{
		  closeHDBAConnection();
	  }
	  return iResult;
	}
//	新增HBT_PERSONINFO(人員資訊檔)作業*	
	public int addHbtPersoninfoV(Vector needAddPersoninfoV)throws HbException, SQLException
	{

	int iResult = 0;
	  try{
		  this.initalHDBAConnection();
		Statement stmt = null;
		stmt = conn.createStatement();
	  
		for(int i=0;i<needAddPersoninfoV.size();i++){
			HB14000VO vo=(HB14000VO)needAddPersoninfoV.get(i);	
			  String strIdno=vo.getH_IDNO();			  
			  String strNewTransDept2=vo.getNEWTRANSDEPT2();
			  String strNewPersontitle=vo.getNEWPERSONTITLE();
			  String strNewAvebonus=vo.getNEWAVEBONUS();
			  String strNewForebonus=vo.getNEWFOREBONUS();
			  String strNewMark=vo.getNEWMARK();

			StringBuffer sql= new StringBuffer(
				"INSERT INTO " +
				"   badm.dbo.HBT_PERSONINFO " +
				"(H_IDNO,H_TRANSDEPT2,H_PERSONTITLE, H_AVEBONUS, H_FOREBONUS, H_MARK )");
			sql.append("values (");
			sql.append((strIdno==null||strIdno.equals(""))?"''":("'").concat(strIdno).concat("'")).append(",");
			sql.append((strNewTransDept2==null||strNewTransDept2.equals(""))?"''":("'").concat(strNewTransDept2).concat("'")).append(",");
			sql.append((strNewPersontitle==null||strNewPersontitle.equals(""))?"''":("'").concat(strNewPersontitle).concat("'")).append(",");
			sql.append((strNewAvebonus==null||strNewAvebonus.equals(""))?"''":("'").concat(strNewAvebonus).concat("'")).append(",");
			sql.append((strNewForebonus==null||strNewForebonus.equals(""))?"''":("'").concat(strNewForebonus).concat("'")).append(",");
			sql.append((strNewMark==null||strNewMark.equals(""))?"''":("'").concat(strNewMark).concat("'"));
			sql.append(" ) ");	   
			if(DEBUG) System.out.println("addHbtPersoninfoV sql->"+sql);
			stmt.addBatch(sql.toString());	
		}
		int[] result=stmt.executeBatch();
		for(int j=0;j<result.length;j++){	
			iResult = result[j]; 
			if(iResult<=0){  
				throw new HbException("新增人員資訊檔作業失敗");	
			}
		  }
	  }
	  catch (Exception e){
		  e.printStackTrace();
		  throw new HbException("新增人員資訊檔作業addHbtPersoninfoV失敗->"+e.toString());
	  }
	  finally{
		  closeHDBAConnection();
	  }
	  return iResult;
	}
//	修改Hct_Emplatd(出勤主檔)作業*	
	public int updHctEmplatdV(Vector needUpdEmplatdV)throws HbException, SQLException
	{

	int iResult = 0;
	  try{
		  this.initalHDBAConnection();
		Statement stmt = null;
		stmt = conn.createStatement();
		String sql=null;		  
		for(int i=0;i<needUpdEmplatdV.size();i++){
			HB14000VO vo=(HB14000VO)needUpdEmplatdV.get(i);
			  String strIdno=vo.getH_IDNO();
			String strApproval=vo.getH_APPROVAL();
			String strCtrno="";
			String strShift="";
			String strStatus="";
			String strTranfdte="";
			String strSalcyce="";		  
			if(strApproval.equals("Y")){
				strCtrno=vo.getNEWCTRNO();
				strShift=vo.getNEWSHIFT();
				strStatus="0";
				strTranfdte=vo.getNEWTRAND();
				strSalcyce=vo.getNEWSALCYCE(); 	
			}else if(strApproval.equals("N")){
				strCtrno=vo.getH_CTRNO();
				strShift=vo.getH_SHIFT();
				strStatus="0";
				strTranfdte=vo.getH_TRAND();
				strSalcyce=vo.getH_SALCYCE();		  	
			}
			  String strCop=vo.getH_COP();
			  String strDop=vo.getH_DOP();
			StringBuffer sql3 = new StringBuffer("UPDATE  badm.dbo.HCT_EMPLATD ");
			sql3.append("SET ");
			sql3.append("H_CTRNO=");
			sql3.append((strCtrno==null||strCtrno.equals(""))?"''":("'").concat(strCtrno).concat("'")).append(",");
			sql3.append("H_SHIFT=");
			sql3.append((strShift==null||strShift.equals(""))?"''":("'").concat(strShift).concat("'")).append(",");
			sql3.append("H_STATUS=");
			sql3.append((strStatus==null||strStatus.equals(""))?"''":("'").concat(strStatus).concat("'")).append(",");
			sql3.append("H_TRNFDTE=");
			sql3.append((strTranfdte==null||strTranfdte.equals(""))?null:("'").concat(strTranfdte).concat("'")).append(",");
			sql3.append("H_SALCYCE=");
			sql3.append((strSalcyce==null||strSalcyce.equals(""))?"''":("'").concat(strSalcyce).concat("'")).append(",");
			sql3.append("H_COP=");
			sql3.append((strCop==null||strCop.equals(""))?"''":("'").concat(strCop).concat("'")).append(",");
			sql3.append("H_DOP=");
			sql3.append((strDop==null||strDop.equals(""))?"''":("'").concat(strDop).concat("' "));
			sql3.append("WHERE  H_IDNO='"+strIdno+"' ");
			   
			if(DEBUG) System.out.println("updHctEmplatdV sql3->"+sql3);
			stmt.addBatch(sql3.toString());	
		}
		int[] result=stmt.executeBatch();
		for(int j=0;j<result.length;j++){
			iResult = result[j]; 
			if(iResult<=0){
				throw new HbException("修改出勤主檔作業失敗");	
			}
		  }
	  }
	  catch (Exception e){
		  e.printStackTrace();
		  throw new HbException("修改出勤主檔作業updHctEmplatdV失敗->"+e.toString());
	  }
	  finally{
		  closeHDBAConnection();
	  }
	  return iResult;
	}
//	----新增異動歷史檔之資料* 
	  public int addHbtShiftV(Vector needAddShiftV)throws HbException, SQLException{
		  int ibatchValue=0;		
		  try{
			  this.initalHDBAConnection();
			  Statement stmt = null;
			  stmt = conn.createStatement();
			  String sql=null;			
			  for(int i=0;i<needAddShiftV.size();i++){
				HB14000VO vo=(HB14000VO)needAddShiftV.get(i);
					String strIdno=vo.getH_IDNO();
					String strName=vo.getH_NAME();
					String strTrandte=vo.getNEWTRAND();
					String strStatus="0";
					String strNote="00";
					String strTrandto=vo.getH_TRAND();
					String strDeptno=vo.getNEWDEPTNO();
					String strDept=vo.getNEWDEPT();
					String strCclass=vo.getH_CCLASS();
					String strRcode=vo.getH_RCODE();
					String strRdate=vo.getH_RDATE();
					String strPcode=vo.getNEWPCODE();
					String strEcode=vo.getH_ECODE();
					String strEdate=vo.getH_EDATE();
					String strJcode=vo.getNEWJCODE();
					String strCattr=vo.getH_CATTR();
					String strBasepay=vo.getH_BASEPAY();
					String strAddpay=vo.getH_ADDPAY();
					String strMangpay=vo.getH_MANGPAY();
					String strCop=vo.getH_COP();
					String strDop=vo.getH_DOP();
					String strDelmark="";
					
				StringBuffer sql1= new StringBuffer(
				"INSERT INTO " +
				"badm.dbo.HBT_SHIFT " +
				"(H_IDNO, H_NAME, H_TRANDTE , H_STATUS, H_NOTE, H_TRANDTO,H_DEPTNO" +
				",H_DEPT,H_CCLASS,H_RCODE,H_RDATE,H_PCODE,H_ECODE,H_EDATE,H_JCODE" +
				",H_CATTR,H_BASEPAY,H_ADDPAY,H_MANGPAY,H_COP, H_DOP, H_DELMARK) ");
				sql1.append("values (");
				sql1.append("'"+strIdno+"'").append(",");
				sql1.append("'"+strName+"'").append(",");
				sql1.append("'"+strTrandte+"'").append(",");
				sql1.append("'"+strStatus+"'").append(",");
				sql1.append("'"+strNote+"'").append(",");
				sql1.append((strTrandto==null||strTrandto.equals(""))?null:("'").concat(strTrandto).concat("'")).append(",");
				sql1.append((strDeptno==null||strDeptno.equals(""))?"''":("'").concat(strDeptno).concat("'")).append(",");
				sql1.append((strDept==null||strDept.equals(""))?"''":("'").concat(strDept).concat("'")).append(",");
				sql1.append((strCclass==null||strCclass.equals(""))?"''":("'").concat(strCclass).concat("'")).append(",");
				sql1.append((strRcode==null||strRcode.equals(""))?null:("'").concat(strRcode).concat("'")).append(",");
				sql1.append((strRdate==null||strRdate.equals(""))?null:("'").concat(strRdate).concat("'")).append(",");
				sql1.append((strPcode==null||strPcode.equals(""))?null:("'").concat(strPcode).concat("'")).append(",");
				sql1.append((strEcode==null||strEcode.equals(""))?null:("'").concat(strEcode).concat("'")).append(",");
				sql1.append((strEdate==null||strEdate.equals(""))?null:("'").concat(strEdate).concat("'")).append(",");
				sql1.append((strJcode==null||strJcode.equals(""))?null:("'").concat(strJcode).concat("'")).append(",");
				sql1.append((strCattr==null||strCattr.equals(""))?null:("'").concat(strCattr).concat("'")).append(",");
				sql1.append((strBasepay==null||strBasepay.equals(""))?null:("'").concat(strBasepay).concat("'")).append(",");
				sql1.append((strAddpay==null||strAddpay.equals(""))?null:("'").concat(strAddpay).concat("'")).append(",");
				sql1.append((strMangpay==null||strMangpay.equals(""))?null:("'").concat(strMangpay).concat("'")).append(",");
				sql1.append((strCop==null||strCop.equals(""))?"''":("'").concat(strCop).concat("'")).append(",");
				sql1.append((strDop==null||strDop.equals(""))?null:("'").concat(strDop).concat("'")).append(",");
				sql1.append((strDelmark==null||strDelmark.equals(""))?null:("'").concat(strDelmark).concat("'"));
				sql1.append(") ");
				if(DEBUG) System.out.println("sql1->"+sql1);
				stmt.addBatch(sql1.toString());	
			  }
			  int[] result=stmt.executeBatch();
			  for(int j=0;j<result.length;j++){	
				  ibatchValue = result[j]; 
				  if(ibatchValue<=0){  
					  throw new HbException("新增異動歷史檔之資料作業失敗");	
				  }
				}
		  }
		  catch (Exception e){
			  e.printStackTrace();
			  throw new HbException("新增異動歷史檔之資料作業addHbtShiftV失敗"+e.toString());
		  }
		  finally{
			  closeHDBAConnection();	
		  }
		  return ibatchValue;
	  }
//	新增HBT_TRANSINFO(派令檔)作業*	
	public int addHbtTransinfoV(Vector needAddPersoninfoV)throws HbException, SQLException
	{

	int iResult = 0;
	  try{
		  this.initalHDBAConnection();
		Statement stmt = null;
		stmt = conn.createStatement();
	  
		for(int i=0;i<needAddPersoninfoV.size();i++){
			HB14000VO vo=(HB14000VO)needAddPersoninfoV.get(i);			  
			  String strIdno=vo.getH_IDNO();			 			  			  
			  String strTransno=vo.getNEWTRANSNO();			  
			  String strPublishday=vo.getNEWPUBLISHDAY();
			  String strTrand=vo.getNEWTRAND();
			  String strCtrno=vo.getNEWCTRNO();		
			  String strDeptno=vo.getNEWDEPTNO();
			  String strTransdept2=vo.getNEWDEPT2();		  
			  String strPcode=vo.getNEWPCODE();
			  String strPersontitle=vo.getNEWPERSONTITLE();
			  String strJcode=vo.getNEWJCODE();
			  String strSalcyce=vo.getNEWSALCYCE();
			  String strShift=vo.getNEWSHIFT();		  
			  String strAvebonus=vo.getNEWAVEBONUS();
			  String strForebonus=vo.getNEWFOREBONUS();
			  String strMark=vo.getNEWMARK();
			  String strStatus="0";
			  String strNote="00";			  
			  String strOtrand=vo.getH_TRAND();
			  String strOctrno=vo.getH_CTRNO();			  
			  String strOdeptno=vo.getH_DEPTNO();			  
			  String strOtransdept2=vo.getH_DEPT2();
			  String strOpcode=vo.getH_PCODE();
			  String strOpersontitle=vo.getH_PERSONTITLE();
			  String strOjcode=vo.getH_JCODE();			  		  
			  String strOsalcyce=vo.getH_SALCYCE();
			  String strOshift=vo.getH_SHIFT();			  
			  String strOavebonus=vo.getH_AVEBONUS();
			  String strOforebonus=vo.getH_FOREBONUS();
			  String strOmark=vo.getH_MARK();
			  String strOstatus=vo.getH_STATUS();
			  String strOnote=vo.getH_NOTE();
			  String strCop=vo.getH_COP();
			  String strDop=vo.getH_DOP();

			StringBuffer sql= new StringBuffer(
				"INSERT INTO " +
				"   badm.dbo.HBT_TRANSINFO " +
				"(H_IDNO,H_TRANSNO, H_PUBLISHDAY,H_TRAND,H_CTRNO,H_DEPTNO,H_TRANSDEPT2,H_PCODE," +
				"H_PERSONTITLE,H_JCODE,H_SALCYCE,H_SHIFT,H_AVEBONUS,H_FOREBONUS, H_MARK, H_STATUS," +
				"H_NOTE, H_OTRAND, H_OCTRNO ,H_ODEPTNO, H_OTRANSDEPT2, H_OPCODE, H_OPERSONTITLE, H_OJCODE, H_OSALCYCE," +
				"H_OSHIFT, H_OAVEBONUS,H_OFOREBONUS, H_OMARK, H_OSTATUS, H_ONOTE,H_COP, H_DOP )");
			sql.append("values (");
			sql.append("'"+strIdno+"'").append(",");
			sql.append("'"+strTransno+"'").append(",");
			sql.append((strPublishday==null||strPublishday.equals(""))?null:("'").concat(strPublishday).concat("'")).append(",");
			sql.append((strTrand==null||strTrand.equals(""))?null:("'").concat(strTrand).concat("'")).append(",");
			sql.append((strCtrno==null||strCtrno.equals(""))?"''":("'").concat(strCtrno).concat("'")).append(",");
			sql.append((strDeptno==null||strDeptno.equals(""))?"''":("'").concat(strDeptno).concat("'")).append(",");
			sql.append((strTransdept2==null||strTransdept2.equals(""))?"''":("'").concat(strTransdept2).concat("'")).append(",");
			sql.append((strPcode==null||strPcode.equals(""))?"''":("'").concat(strPcode).concat("'")).append(",");
			sql.append((strPersontitle==null||strPersontitle.equals(""))?"''":("'").concat(strPersontitle).concat("'")).append(",");
			sql.append((strJcode==null||strJcode.equals(""))?"''":("'").concat(strJcode).concat("'")).append(",");
			sql.append((strSalcyce==null||strSalcyce.equals(""))?"''":("'").concat(strSalcyce).concat("'")).append(",");
			sql.append((strShift==null||strShift.equals(""))?"''":("'").concat(strShift).concat("'")).append(",");
			sql.append((strAvebonus==null||strAvebonus.equals(""))?"''":("'").concat(strAvebonus).concat("'")).append(",");
			sql.append((strForebonus==null||strForebonus.equals(""))?"''":("'").concat(strForebonus).concat("'")).append(",");
			sql.append((strMark==null||strMark.equals(""))?"''":("'").concat(strMark).concat("'")).append(",");
			sql.append((strStatus==null||strStatus.equals(""))?"''":("'").concat(strStatus).concat("'")).append(",");
			sql.append((strNote==null||strNote.equals(""))?"''":("'").concat(strNote).concat("'")).append(",");
			sql.append((strOtrand==null||strOtrand.equals(""))?null:("'").concat(strOtrand).concat("'")).append(",");
			sql.append((strOctrno==null||strOctrno.equals(""))?"''":("'").concat(strOctrno).concat("'")).append(",");
			sql.append((strOdeptno==null||strOdeptno.equals(""))?"''":("'").concat(strOdeptno).concat("'")).append(",");
			sql.append((strOtransdept2==null||strOtransdept2.equals(""))?"''":("'").concat(strOtransdept2).concat("'")).append(",");
			sql.append((strOpcode==null||strOpcode.equals(""))?"''":("'").concat(strOpcode).concat("'")).append(",");
			sql.append((strOpersontitle==null||strOpersontitle.equals(""))?"''":("'").concat(strOpersontitle).concat("'")).append(",");
			sql.append((strOjcode==null||strOjcode.equals(""))?"''":("'").concat(strOjcode).concat("'")).append(",");
			sql.append((strOsalcyce==null||strOsalcyce.equals(""))?"''":("'").concat(strOsalcyce).concat("'")).append(",");
			sql.append((strOshift==null||strOshift.equals(""))?"''":("'").concat(strOshift).concat("'")).append(",");
			sql.append((strOavebonus==null||strOavebonus.equals(""))?"''":("'").concat(strOavebonus).concat("'")).append(",");
			sql.append((strOforebonus==null||strOforebonus.equals(""))?"''":("'").concat(strOforebonus).concat("'")).append(",");
			sql.append((strOmark==null||strOmark.equals(""))?"''":("'").concat(strOmark).concat("'")).append(",");
			sql.append((strOstatus==null||strOstatus.equals(""))?"''":("'").concat(strOstatus).concat("'")).append(",");
			sql.append((strOnote==null||strOnote.equals(""))?"''":("'").concat(strOnote).concat("'")).append(",");
			sql.append((strCop==null||strCop.equals(""))?"''":("'").concat(strCop).concat("'")).append(",");
			sql.append((strDop==null||strDop.equals(""))?null:("'").concat(strDop).concat("'")).append(" ");
			sql.append(") ");	   
			if(DEBUG) System.out.println("addHbtTransinfoV sql->"+sql);
			stmt.addBatch(sql.toString());	
		}
		int[] result=stmt.executeBatch();
		for(int j=0;j<result.length;j++){	
			iResult = result[j]; 
			if(iResult<=0){  
				throw new HbException("新增派令檔作業失敗");	
			}
		  }
	  }
	  catch (Exception e){
		  e.printStackTrace();
		  throw new HbException("新增派令檔作業addHbtTransinfoV失敗->"+e.toString());
	  }
	  finally{
		  closeHDBAConnection();
	  }
	  return iResult;
	}
//	----新增人事異動檔之資料* 
	  public int addHbtTransV(Vector needAddTransV)throws HbException, SQLException{

		  int ibatchValue=0;		
		  try{

			  this.initalHDBAConnection();
			  Statement stmt = null;
			  stmt = conn.createStatement();
	
			  for(int i=0;i<needAddTransV.size();i++){
				HB14000VO vo=(HB14000VO)needAddTransV.get(i);
					String strIdno=vo.getH_IDNO();
					String strDate=vo.getNEWTRAND();
					String strStatus="0";
					String strNote="00";
					String strName=vo.getH_NAME();
					String strDeptno=vo.getNEWDEPTNO();
					String strWkdis="0";
					String strOdate=vo.getH_TRAND();
					String strOstatus=vo.getH_STATUS();
					String strOnote=vo.getH_NOTE();
					String strOidno=vo.getH_IDNO();
					String strOname=vo.getH_NAME();
					String strOdeptno=vo.getH_DEPTNO();
					String strOctrno=vo.getH_CTRNO();
					String strOcclass=vo.getH_CCLASS();
					String strOrcode=vo.getH_RCODE();
					String strOrdate=vo.getH_RDATE();
					String strOpcode=vo.getH_PCODE();
					String strOecode=vo.getH_ECODE();
					String strOedate=vo.getH_EDATE();
					String strOjcode=vo.getH_JCODE();
					String strOcattr=vo.getH_CATTR();
					String strOreport=vo.getH_REPORT();
					String strOtrand=vo.getH_TRAND();
					String strOregist=vo.getH_REGIST();
					String strOresign=vo.getH_RESIGN();
					String strOreturn=vo.getH_RETURNDAY();
					String strOwkdis=vo.getH_WKDIS();
					String strObasepay=vo.getH_BASEPAY();
					String strOaddpay=vo.getH_ADDPAY();
					String strOmangpay=vo.getH_MANGPAY();
					String strOeducat1=vo.getH_EDUCAT1();
					String strOschool1=vo.getH_SCHOOL1();
					String strOmajor1=vo.getH_MAJOR1();
					String strOgrad1=vo.getH_GRAD1();
					String strCop=vo.getH_COP();
					String strDop=vo.getH_DOP();
					
				StringBuffer sql= new StringBuffer(
						 "INSERT INTO " +
						 "badm.dbo.HBT_TRANS " +
						 "(H_IDNO, H_DATE, H_STATUS, H_NOTE, H_NAME , H_DEPTNO, H_WKDIS, H_ODATE, H_OSTATUS" +
						 ",H_ONOTE,H_OIDNO,H_ONAME,H_ODEPTNO,H_OCTRNO,H_OCCLASS,H_ORCODE,H_ORDATE" +
						 ",H_OPCODE,H_OECODE,H_OEDATE,H_OJCODE,H_OCATTR,H_OREPORT,H_OTRAND, H_OREGIST" +
						 ",H_ORESIGN, H_ORETURN, H_OWKDIS, H_OBASEPAY, H_OADDPAY, H_OMANGPAY, H_OEDUCAT1" +
						 ",H_OSCHOOL1, H_OMAJOR1, H_OGRAD1, H_COP, H_DOP ) ");
				sql.append("values (");
				sql.append("'"+strIdno+"'").append(",");
				sql.append("'"+strDate+"'").append(",");
				sql.append("'"+strStatus+"'").append(",");
				sql.append((strNote==null||strNote.equals(""))?null:("'").concat(strNote).concat("'")).append(",");
				sql.append((strName==null||strName.equals(""))?null:("'").concat(strName).concat("'")).append(",");
				sql.append((strDeptno==null||strDeptno.equals(""))?null:("'").concat(strDeptno).concat("'")).append(",");
				sql.append(("'").concat(strWkdis).concat("'")).append(",");				
				sql.append((strOdate==null||strOdate.equals(""))?null:("'").concat(strOdate).concat("'")).append(",");
				sql.append((strOstatus==null||strOstatus.equals(""))?null:("'").concat(strOstatus).concat("'")).append(",");
				sql.append((strOnote==null||strOnote.equals(""))?null:("'").concat(strOnote).concat("'")).append(",");
				sql.append((strOidno==null||strOidno.equals(""))?null:("'").concat(strOidno).concat("'")).append(",");
				sql.append((strOname==null||strOname.equals(""))?null:("'").concat(strOname).concat("'")).append(",");
				sql.append((strOdeptno==null||strOdeptno.equals(""))?null:("'").concat(strOdeptno).concat("'")).append(",");
				sql.append((strOctrno==null||strOctrno.equals(""))?null:("'").concat(strOctrno).concat("'")).append(",");
				sql.append((strOcclass==null||strOcclass.equals(""))?null:("'").concat(strOcclass).concat("'")).append(",");
				sql.append((strOrcode==null||strOrcode.equals(""))?null:("'").concat(strOrcode).concat("'")).append(",");				
				sql.append((strOrdate==null||strOrdate.equals(""))?null:("'").concat(strOrdate).concat("'")).append(",");
				sql.append((strOpcode==null||strOpcode.equals(""))?null:("'").concat(strOpcode).concat("'")).append(",");
				sql.append((strOecode==null||strOecode.equals(""))?null:("'").concat(strOecode).concat("'")).append(",");
				sql.append((strOedate==null||strOedate.equals(""))?null:("'").concat(strOedate).concat("'")).append(",");
				sql.append((strOjcode==null||strOjcode.equals(""))?null:("'").concat(strOjcode).concat("'")).append(",");
				sql.append((strOcattr==null||strOcattr.equals(""))?null:("'").concat(strOcattr).concat("'")).append(",");
				sql.append((strOreport==null||strOreport.equals(""))?null:("'").concat(strOreport).concat("'")).append(",");
				sql.append((strOtrand==null||strOtrand.equals(""))?null:("'").concat(strOtrand).concat("'")).append(",");
				sql.append((strOregist==null||strOregist.equals(""))?null:("'").concat(strOregist).concat("'")).append(",");
				sql.append((strOresign==null||strOresign.equals(""))?null:("'").concat(strOresign).concat("'")).append(",");
				sql.append((strOreturn==null||strOreturn.equals(""))?null:("'").concat(strOreturn).concat("'")).append(",");
				sql.append((strOwkdis==null||strOwkdis.equals(""))?null:("'").concat(strOwkdis).concat("'")).append(",");
				sql.append((strObasepay==null||strObasepay.equals(""))?null:("'").concat(strObasepay).concat("'")).append(",");
				sql.append((strOaddpay==null||strOaddpay.equals(""))?null:("'").concat(strOaddpay).concat("'")).append(",");
				sql.append((strOmangpay==null||strOmangpay.equals(""))?null:("'").concat(strOmangpay).concat("'")).append(",");
				sql.append((strOeducat1==null||strOeducat1.equals(""))?null:("'").concat(strOeducat1).concat("'")).append(",");
				sql.append((strOschool1==null||strOschool1.equals(""))?null:("'").concat(strOschool1).concat("'")).append(",");
				sql.append((strOmajor1==null||strOmajor1.equals(""))?null:("'").concat(strOmajor1).concat("'")).append(",");
				sql.append((strOgrad1==null||strOgrad1.equals(""))?null:("'").concat(strOgrad1).concat("'")).append(",");
				sql.append((strCop==null||strCop.equals(""))?"''":("'").concat(strCop).concat("'")).append(",");
				sql.append((strDop==null||strDop.equals(""))?null:("'").concat(strDop).concat("'")).append(" ");
				sql.append(") ");			
				if(DEBUG) System.out.println("addHbtTransV sql->"+sql);
				stmt.addBatch(sql.toString());	
			  }
			  int[] result=stmt.executeBatch();
			  for(int j=0;j<result.length;j++){	
				  ibatchValue = result[j]; 
				  if(ibatchValue<=0){  
					  throw new HbException("新增人事異動檔之資料作業失敗");	
				  }
				}
		  }
		  catch (Exception e){
			  throw new HbException("新增人事異動檔之資料作業addHbtTransV失敗"+e.toString());
		  }
		  finally{
			  closeHDBAConnection();	
		  }
		  return ibatchValue;
	  }

//	修改HBT_TRANSINFO(派令檔)作業	*
	public int updHbtTraninfoV(Vector needUpdHbtTraninfoV)throws HbException, SQLException
	{

	int iResult = 0;
	  try{
		  this.initalHDBAConnection();
		Statement stmt = null;
		stmt = conn.createStatement();
  
		for(int i=0;i<needUpdHbtTraninfoV.size();i++){
			HB14000VO vo=(HB14000VO)needUpdHbtTraninfoV.get(i);
				String strIdno=vo.getH_IDNO();			  
				String strNewTransno=vo.getNEWTRANSNO();
				String strApproval=vo.getH_APPROVAL();
				String strCop=vo.getH_COP();
				String strDop=vo.getH_DOP();
				
			StringBuffer sql = new StringBuffer("UPDATE  badm.dbo.HBT_TRANSINFO ");
			sql.append("SET ");
			sql.append("H_APPROVAL=");
			sql.append((strApproval==null||strApproval.equals(""))?"''":("'").concat(strApproval).concat("'")).append(",");
			sql.append("H_COP=");
			sql.append((strCop==null||strCop.equals(""))?"''":("'").concat(strCop).concat("'")).append(",");
			sql.append("H_DOP=");
			sql.append((strDop==null||strDop.equals(""))?null:("'").concat(strDop).concat("'")).append(" ");
			sql.append("WHERE  H_IDNO='"+strIdno+"' AND H_TRANSNO='"+strNewTransno+"' ");				
			   
			if(DEBUG) System.out.println("needUpdHbtTraninfoV sql->"+sql);
			stmt.addBatch(sql.toString());	
		}
		int[] result=stmt.executeBatch();
		for(int j=0;j<result.length;j++){	
			iResult = result[j]; 
			if(iResult<=0){  
				throw new HbException("修改派令檔作業失敗");	
			}
		  }
	  }
	  catch (Exception e){
		  e.printStackTrace();
		  throw new HbException("修改派令檔作業updHbtTraninfoV失敗->"+e.toString());
	  }
	  finally{
		  closeHDBAConnection();
	  }
	  return iResult;
	}

//	刪除HBT_SHIFT(異動歷史檔)作業	*
	public int delHbtShiftV(Vector needDelHbtShiftV)throws HbException, SQLException
	{

	int iResult = 0;
	  try{
		  this.initalHDBAConnection();
		Statement stmt = null;
		stmt = conn.createStatement();
		String sql=null;		  
		for(int i=0;i<needDelHbtShiftV.size();i++){
			HB14000VO vo=(HB14000VO)needDelHbtShiftV.get(i);
			String strIdno=vo.getH_IDNO();
			String strTrandte=vo.getNEWTRAND();
			  
			sql ="DELETE  FROM badm.dbo.HBT_SHIFT " +
				   "WHERE  H_IDNO='"+strIdno+"' AND H_TRANDTE='"+strTrandte+"' AND H_STATUS='0' AND H_NOTE='00' ";				   
			if(DEBUG) System.out.println("delHbtShiftV sql->"+sql);
			stmt.addBatch(sql);	
		}
		int[] result=stmt.executeBatch();
		for(int j=0;j<result.length;j++){	
			iResult = result[j]; 
			if(iResult<=0){  
				throw new HbException("刪除異動歷史檔作業失敗");	
			}
		  }
	  }
	  catch (Exception e){
		  e.printStackTrace();
		  throw new HbException("刪除異動歷史檔delHbtShiftV失敗->"+e.toString());
	  }
	  finally{
		  closeHDBAConnection();
	  }
	  return iResult;
	}
//	刪除HBT_TRANS(人事異動檔)作業	*
	public int delHbtTransV(Vector needDelHbtTransV)throws HbException, SQLException
	{

	int iResult = 0;
	  try{
		  this.initalHDBAConnection();
		Statement stmt = null;
		stmt = conn.createStatement();
		String sql=null;		  
		for(int i=0;i<needDelHbtTransV.size();i++){
			HB14000VO vo=(HB14000VO)needDelHbtTransV.get(i);
			String strIdno=vo.getH_IDNO();
			String strDate=vo.getNEWTRAND();			  
			sql ="DELETE FROM badm.dbo.HBT_TRANS " +
				   "WHERE  H_IDNO='"+strIdno+"' AND H_DATE='"+strDate+"' AND H_STATUS='0' AND H_NOTE='00' ";				   
			if(DEBUG) System.out.println("delHbtTransV sql->"+sql);
			stmt.addBatch(sql);	
		}
		int[] result=stmt.executeBatch();
		for(int j=0;j<result.length;j++){	
			iResult = result[j]; 
			if(iResult<=0){  
				throw new HbException("刪除人事異動檔作業失敗");	
			}
		  }
	  }
	  catch (Exception e){
		  e.printStackTrace();
		  throw new HbException("刪除人事異動檔作業delHbtTransV失敗->"+e.toString());
	  }
	  finally{
		  closeHDBAConnection();
	  }
	  return iResult;
	}
	  	  						  	  	  	  	  				  		
}
