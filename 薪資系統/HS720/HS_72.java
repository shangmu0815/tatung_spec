/*
 * 在 2010/1/25 建立
 *
 * 若要變更這個產生的檔案的範本，請移至
 * 視窗 > 喜好設定 > Java > 程式碼產生 > 程式碼和註解
 */
package com.tatung.imis.H.HS.HS7.model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Vector;

import com.tatung.imis.H.Common.data.H_tableVO;
import com.tatung.imis.H.Common.model.H_TransactionModel;
import com.tatung.imis.H.Common.util.HS_Source;
import com.tatung.imis.H.HS.HS7.dao.HS_72DAO;

/**
 * @author Administrator
 *
 * 若要變更這個產生的類別註解的範本，請移至
 * 視窗 > 喜好設定 > Java > 程式碼產生 > 程式碼和註解
 */
public class HS_72 {
	//transactionId
	private static String transactionId = null;
	//intChoice=1 測試區
	//intChoice=2 正式區
	private static String  connChoice="1";
	static boolean  isSuccess=false;
	public static void main(String[] args) {
		Vector hstPayrollNowV=new Vector();
		H_tableVO hstReferVO=new H_tableVO();
		//INPUT筆數
		int inputNum=0;  
		System.out.println("INPUT筆數: "+inputNum);
		Vector finalV=new Vector();	
		String strMsg1="",strMsg2="",strMsg3="",strMessage="";
		//**********************************************************		
		
		System.out.println("HS_72   run \n");
		//		薪資計算別1,2
		String salcyce1=null, salcyce2=null;  
		
		//抓取 薪資計算別1, 薪資計算別2
		InputStreamReader isr = new InputStreamReader( System.in );
		BufferedReader br = new BufferedReader( isr );
		try {
			//薪資計算別1
			int iSalcyce1=-1;
			do {
				System.out.print("請輸入 薪資計算別 第1個變數的值: ");
				salcyce1=br.readLine();
				try {
					iSalcyce1=Integer.parseInt(salcyce1);
				} catch(Exception nfe) {
				}
			} while(salcyce1==null || salcyce1.equals("") || (iSalcyce1<1 || iSalcyce1>6));
			System.out.println();
			//薪資計算別2
			int iSalcyce2=-1;
			do {
				System.out.print("請輸入 薪資計算別 第2個變數的值: ");
				salcyce2=br.readLine();
				try {
					iSalcyce2=Integer.parseInt(salcyce2);
				} catch(Exception nfe) {
				}
			} while(salcyce2==null || salcyce2.equals("") || (iSalcyce2<1 || iSalcyce2>6));
		} catch(Exception ioe) {
			ioe.printStackTrace();
			return;
		}		
		System.out.println("\n處理中, 請稍待...\n");
		H_TransactionModel transaction = H_TransactionModel.getInstance();

		try {	
			//connChoice =getMapping
			int intConnChoice=Integer.parseInt(connChoice);
			transactionId = transaction.getNewTransaction(intConnChoice);						
			HS_72DAO hs72DAO = new HS_72DAO(transactionId);
			hstReferVO=hs72DAO.qryHstReferVO();
			if(hstReferVO==null){
				throw new Exception("讀取薪資參數檔作業失敗！");
			}else{
				System.out.println("salcyce1->"+salcyce1);
				System.out.println("salcyce2->"+salcyce2);
				System.out.println("hstReferVO.getFld(1)->"+hstReferVO.getFld(1));
				String [] strSource1={
					salcyce1,							//薪資計算別1
					salcyce2,							//薪資計算別2
					hstReferVO.getFld(1),     //薪資參數檔薪資年月					
				};
				hstPayrollNowV=hs72DAO.qryHstPayrollNowV(strSource1);
				inputNum=hstPayrollNowV.size();
				finalV=hs72DAO.updHstPayrollNowV(hstPayrollNowV);
			  if(finalV.size()==3){
				    strMsg1=(String)(finalV.get(0));
					strMsg2=(String)(finalV.get(1));
					strMsg3=(String)(finalV.get(2));
			  }
			  strMessage="當月結算後薪資明細檔作業"+"\r\n"+
								  "INPUT筆數:"+inputNum+"筆"+"\r\n"+
								  "OUPUT筆數:"+strMsg1+"筆"+"\r\n"+
								  "ERROR筆數:"+strMsg2+"筆"+"\r\n"+
								  "ERROR DATA:"+strMsg3+"\r\n";
			}
			transaction.setCommit(transactionId);			
		} catch (Exception e) {
			transaction.setRollback(transactionId);
			e.printStackTrace();	
			strMessage="領薪序號編製作業失敗"+e.toString();					
		}finally{
			System.out.println("strMessage->"+strMessage);
			String strSysDate=HS_Source.getSysTime();
			String sPath="C:\\iMIS\\THQ\\HRM\\Log\\HS\\7";  //檔案路徑
			String sFileName="HS_72"+"_"+strSysDate+".txt";  //檔案名稱
			try{
				isSuccess=HS_Source.writeNewFile(sPath, sFileName, strMessage);  //產生  檔案, 回傳是否成功
				if(! isSuccess)  //產生[有Ｑ表無出勤異常表]檔案 不成功
					throw new Exception("產生 "+sPath+sFileName+" 檔案作業失敗！");
				else  //產生[有Ｑ表無出勤異常表]檔案 成功
					System.out.println("已經成功產生 "+sPath+sFileName+" 檔案了！");
			}catch (Exception e) {
				System.out.println("作業失敗->"+e.toString());
				e.printStackTrace();	
			}finally{
				System.out.println("finally transactionId->"+transactionId);
				transaction.closeTransaction(transactionId);
				System.out.println("\n處理結束!");
			}
		}
	}
}
