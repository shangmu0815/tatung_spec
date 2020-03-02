/*
 * �b 2010/1/25 �إ�
 *
 * �Y�n�ܧ�o�Ӳ��ͪ��ɮת��d���A�в���
 * ���� > �ߦn�]�w > Java > �{���X���� > �{���X�M����
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
 * �Y�n�ܧ�o�Ӳ��ͪ����O���Ѫ��d���A�в���
 * ���� > �ߦn�]�w > Java > �{���X���� > �{���X�M����
 */
public class HS_72 {
	//transactionId
	private static String transactionId = null;
	//intChoice=1 ���հ�
	//intChoice=2 ������
	private static String  connChoice="1";
	static boolean  isSuccess=false;
	public static void main(String[] args) {
		Vector hstPayrollNowV=new Vector();
		H_tableVO hstReferVO=new H_tableVO();
		//INPUT����
		int inputNum=0;  
		System.out.println("INPUT����: "+inputNum);
		Vector finalV=new Vector();	
		String strMsg1="",strMsg2="",strMsg3="",strMessage="";
		//**********************************************************		
		
		System.out.println("HS_72   run \n");
		//		�~��p��O1,2
		String salcyce1=null, salcyce2=null;  
		
		//��� �~��p��O1, �~��p��O2
		InputStreamReader isr = new InputStreamReader( System.in );
		BufferedReader br = new BufferedReader( isr );
		try {
			//�~��p��O1
			int iSalcyce1=-1;
			do {
				System.out.print("�п�J �~��p��O ��1���ܼƪ���: ");
				salcyce1=br.readLine();
				try {
					iSalcyce1=Integer.parseInt(salcyce1);
				} catch(Exception nfe) {
				}
			} while(salcyce1==null || salcyce1.equals("") || (iSalcyce1<1 || iSalcyce1>6));
			System.out.println();
			//�~��p��O2
			int iSalcyce2=-1;
			do {
				System.out.print("�п�J �~��p��O ��2���ܼƪ���: ");
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
		System.out.println("\n�B�z��, �еy��...\n");
		H_TransactionModel transaction = H_TransactionModel.getInstance();

		try {	
			//connChoice =getMapping
			int intConnChoice=Integer.parseInt(connChoice);
			transactionId = transaction.getNewTransaction(intConnChoice);						
			HS_72DAO hs72DAO = new HS_72DAO(transactionId);
			hstReferVO=hs72DAO.qryHstReferVO();
			if(hstReferVO==null){
				throw new Exception("Ū���~��Ѽ��ɧ@�~���ѡI");
			}else{
				System.out.println("salcyce1->"+salcyce1);
				System.out.println("salcyce2->"+salcyce2);
				System.out.println("hstReferVO.getFld(1)->"+hstReferVO.getFld(1));
				String [] strSource1={
					salcyce1,							//�~��p��O1
					salcyce2,							//�~��p��O2
					hstReferVO.getFld(1),     //�~��Ѽ����~��~��					
				};
				hstPayrollNowV=hs72DAO.qryHstPayrollNowV(strSource1);
				inputNum=hstPayrollNowV.size();
				finalV=hs72DAO.updHstPayrollNowV(hstPayrollNowV);
			  if(finalV.size()==3){
				    strMsg1=(String)(finalV.get(0));
					strMsg2=(String)(finalV.get(1));
					strMsg3=(String)(finalV.get(2));
			  }
			  strMessage="��뵲����~������ɧ@�~"+"\r\n"+
								  "INPUT����:"+inputNum+"��"+"\r\n"+
								  "OUPUT����:"+strMsg1+"��"+"\r\n"+
								  "ERROR����:"+strMsg2+"��"+"\r\n"+
								  "ERROR DATA:"+strMsg3+"\r\n";
			}
			transaction.setCommit(transactionId);			
		} catch (Exception e) {
			transaction.setRollback(transactionId);
			e.printStackTrace();	
			strMessage="���~�Ǹ��s�s�@�~����"+e.toString();					
		}finally{
			System.out.println("strMessage->"+strMessage);
			String strSysDate=HS_Source.getSysTime();
			String sPath="C:\\iMIS\\THQ\\HRM\\Log\\HS\\7";  //�ɮ׸��|
			String sFileName="HS_72"+"_"+strSysDate+".txt";  //�ɮצW��
			try{
				isSuccess=HS_Source.writeNewFile(sPath, sFileName, strMessage);  //����  �ɮ�, �^�ǬO�_���\
				if(! isSuccess)  //����[���ߪ�L�X�Բ��`��]�ɮ� �����\
					throw new Exception("���� "+sPath+sFileName+" �ɮק@�~���ѡI");
				else  //����[���ߪ�L�X�Բ��`��]�ɮ� ���\
					System.out.println("�w�g���\���� "+sPath+sFileName+" �ɮפF�I");
			}catch (Exception e) {
				System.out.println("�@�~����->"+e.toString());
				e.printStackTrace();	
			}finally{
				System.out.println("finally transactionId->"+transactionId);
				transaction.closeTransaction(transactionId);
				System.out.println("\n�B�z����!");
			}
		}
	}
}
