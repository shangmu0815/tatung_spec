/*
 * �b 2009/5/6 �إ�
 *
 * �Y�n�ܧ�o�Ӳ��ͪ��ɮת��d���A�в���
 * ���� > �ߦn�]�w > Java > �{���X���� > �{���X�M����
 */
package com.tatung.imis.H.HB.model;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.Vector;

import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import com.tatung.imis.Common.helper.ServiceLocator;
import com.tatung.imis.H.HB.dao.HB14000DAO;
import com.tatung.imis.H.HB.data.HB14000VO;
import com.tatung.imis.H.HB.exception.HbException;

/**
 * @author Administrator
 *
 * �Y�n�ܧ�o�Ӳ��ͪ����O���Ѫ��d���A�в���
 * ���� > �ߦn�]�w > Java > �{���X���� > �{���X�M����
 */
public class HB14000Model {
	private boolean DEBUG = false;

//	�d��  �@�~
		  public  Vector qryHbtTransinfoV(String strTrand,String strApproval)throws HbException, SQLException{
				HB14000DAO dao=new HB14000DAO();
			  Vector qryV=dao.qryHbtTransinfoV(strTrand,strApproval);
			  TreeMap tMap=new TreeMap();
			  for(int i=0;i<qryV.size();i++){
				HB14000VO vo=(HB14000VO)qryV.get(i);
				String strChineseNewTrand="";
				String strNewTrand=vo.getNEWTRAND();
				if(DEBUG) System.out.println("strNewTrand->"+strNewTrand+"--");
				if(!(strNewTrand==null||strNewTrand.equals(""))){
					strChineseNewTrand=fillZero(String.valueOf(Integer.parseInt(strNewTrand.substring(0,4))-1911),3).concat(strNewTrand.substring(4,8));  
					vo.setCHINESENEWTRAND(strChineseNewTrand);
				}else{
					vo.setCHINESENEWTRAND("");
				}				
				String strNewTransno=vo.getNEWTRANSNO().trim();
				if(DEBUG) System.out.println("strNewTransno->"+strNewTransno);
				//--�u�����O�r���ۦP���Ĥ@��
				if(tMap.get(strNewTransno)==null){
				   tMap.put(strNewTransno,vo);
				}				
			  }			  
			  Vector dataV=new Vector(); 
			  Iterator it=tMap.keySet().iterator();
			  while(it.hasNext()){
				String key=it.next().toString();
				if(DEBUG) System.out.println("key->"+key);
				HB14000VO vo=(HB14000VO)tMap.get(key);
				dataV.add(vo);
			  }
			  return dataV;
		  }
	//	�d�ߤH�Ʋ����ɧ@�~
			public  Vector qryHbtTransinfo2V(String strTransno)throws HbException, SQLException{
				  HB14000DAO dao=new HB14000DAO();
				Vector qryV=dao.qryHbtTransinfo2V(strTransno);
				return qryV;
			}

	//	�d��
			public  Vector qryHbtPersonHbtTransinfoV(String strTransno)throws HbException, SQLException{
				  HB14000DAO dao=new HB14000DAO();
				Vector qryV=dao.qryHbtPersonHbtTransinfoV(strTransno);
				return qryV;
			}					  

//	�ק�@�~*
  public Vector updData(Vector updDataV) throws HbException, SQLException{
	  HB14000DAO  dao = new HB14000DAO() ;
	  int addResult=0;
	  int updResult=0;
	  int addShiftResult=0;
	  int addTransResult=0;	  
	  int updPersonResult=0;
	  int updNewpsnResult=0;
	  int updPersoninfoResult=0;
	  int addPersoninfoResult=0;
	  int updHbtTransinfoResult=0;
	  int addHbtTransinfoResult=0;
	  int delTransResult=0;
	  int delShiftResult=0;
	  int updEmplatdResult=0;
	 Vector addResultV=new Vector();
	  boolean checkAdd=false;
	  int intNotNeedAddRegist=0;
	  int intNeedAddRegist=0;
	  Vector needUpdPersoninfoV=new Vector();
	  Vector needAddPersoninfoV=new Vector();
	  ServiceLocator sl = ServiceLocator.getInstance(); 
	  UserTransaction ut=null;
	  String strNewTrand="";
	  String strNewTransno="";
	  String strApproval="";
	  //�t�Τ��
	  String strTdop="";
	  try{
			  if(updDataV.size()>0){
				for(int i=0;i<updDataV.size();i++){
					HB14000VO vo=(HB14000VO)updDataV.get(i);
					String strIdno=vo.getH_IDNO();
					strNewTrand=vo.getNEWTRAND();
					strNewTransno=vo.getNEWTRANSNO();
					strTdop=vo.getH_DOP();  //���X�t�Τ��
					strApproval=vo.getH_APPROVAL();
					if(DEBUG) System.out.println("model strApproval->"+strApproval);
					HB14000VO qryVO=dao.qryHatDeptfilVO(vo.getNEWDEPTNO());
//					HB14000VO qryVO=dao.qryHatDeptfilVO(vo.getH_DEPTNO());
					if(DEBUG) System.out.println("model strIdno->"+strIdno);
					if(DEBUG) System.out.println("model strNewTrand->"+strNewTrand);
					vo.setNEWDEPT(qryVO==null?"":qryVO.getH_DEPT());
					checkAdd=dao.checkHbtPersonInfo(strIdno);
					if(DEBUG) System.out.println("1 checkAdd->"+checkAdd);
					if(checkAdd==false){
						throw new HbException("�d�L�����Ҧr��"+strIdno+"���H�ƥD�ɸ��!");
					}
					checkAdd=dao.checkHbtNewpsnInfo(strIdno);
					if(DEBUG) System.out.println("2 checkAdd->"+checkAdd);
					  if(checkAdd==false){
						  throw new HbException("�d�L�����Ҧr��"+strIdno+"���s�i�H���ɸ��!");
					  }
					//check�H����T��
					checkAdd=dao.checkHbtPersoninfo(strIdno);
					if(DEBUG) System.out.println("3 checkAdd->"+checkAdd);
					  if(checkAdd==true){
						  needUpdPersoninfoV.add(vo);
					  }else{
						  needAddPersoninfoV.add(vo);
					  }						
					//check �X�ԥD��
					checkAdd=dao.checkHctEmplatdInfo(strIdno);
					if(DEBUG) System.out.println("4 checkAdd->"+checkAdd);
					if(checkAdd==false){
						throw new HbException("�d�L�����Ҧr��"+strIdno+"���X�ԥD�ɸ��!");
					}						
//					checkAdd=dao.checkHbtShiftInfo(strIdno,strNewTrand,"8","00");
//					if(checkAdd==true){
//						throw new HbException("�w�������Ҧr��"+strIdno+"�����ʾ��v�ɸ��!"); 
//					}
					//check���O��
					checkAdd=dao.checkHbtTransinfo(strIdno,strNewTransno);
					if(DEBUG) System.out.println("5 checkAdd->"+checkAdd);
					if(checkAdd==false){
						throw new HbException("�S�������Ҧr��"+strIdno+"�����O�ɸ��!"); 
					}
					//check�H�Ʋ�����
//					checkAdd=dao.checkHbtTransInfo(strIdno,strNewTrand,"8","00");
//					if(checkAdd==true){
//						throw new HbException("�w�������Ҧr��"+strIdno+"���H�Ʋ����ɸ��!");                           
//					}		
				}
			}					
//		---------UserTransaction����Ҧ��@�~�����\�ɤ~����Commit���@�~,�_�h�N��Rollback			
			if(DEBUG) System.out.println("�ާ@��(�t�Τ�) VS �ͮĤ�***strTdop->"+strTdop+"**strNewTrand->"+strNewTrand);	
			  ut=sl.getUserTransaction();
			  //---�]transaction---- 
			  ut.begin();
			  //�Y�ާ@��(�t�Τ�)>=�ͮĤ�  
			  if(Integer.parseInt(strTdop)>=Integer.parseInt(strNewTrand)){
				  if(DEBUG) System.out.println("--strApproval--->"+strApproval+"--");
				  if(updDataV.size()>0){
					  //�ק�H�ƥD��
					  updPersonResult=dao.updHbtPersonV(updDataV);
					  if(DEBUG) System.out.println("�ާ@��(�t�Τ�)>=�ͮĤ� �ק�H�ƥD�� updPersonResult->"+updPersonResult);
					  if(updPersonResult<=0){
						  throw new HbException("�ק�H�ƥD�ɧ@�~����!");
					  }
					  //�ק�s�i�H����
					  updNewpsnResult=dao.updHbtNewpsnV(updDataV);
					  if(DEBUG) System.out.println("�ާ@��(�t�Τ�)>=�ͮĤ� �ק�s�i�H���� updNewpsnResult->"+updNewpsnResult);
					  if(updNewpsnResult<=0){
						  throw new HbException("�ק�s�i�H���ɧ@�~����!");
					  }
					  //�H����T��
					  if(needUpdPersoninfoV.size()>0){
						//�ק�H����T��
						updPersoninfoResult=dao.updHbtPersoninfoV(needUpdPersoninfoV);
						if(DEBUG) System.out.println("�ާ@��(�t�Τ�)>=�ͮĤ� �ק�H����T�� updPersoninfoResult->"+updPersoninfoResult);
						if(updPersoninfoResult<=0){
							throw new HbException("�ק�H����T�ɧ@�~����!");
						}
					  }
					  ///////////////////////////
					if(needAddPersoninfoV.size()>0){
					  //�s�W�H����T��
					  addPersoninfoResult=dao.addHbtPersoninfoV(needAddPersoninfoV);
					  if(DEBUG) System.out.println("�ާ@��(�t�Τ�)>=�ͮĤ� �s�W�H����T�� addPersoninfoResult->"+addPersoninfoResult);
					  if(addPersoninfoResult<=0){
						  throw new HbException("�s�W�H����T�ɧ@�~����!");
					  }
					}
					//////////////////////////////
					//�ק�X�ԥD��
					updEmplatdResult=dao.updHctEmplatdV(updDataV);
					if(DEBUG) System.out.println("�ާ@��(�t�Τ�)>=�ͮĤ� �ק�X�ԥD�� updEmplatdResult->"+updEmplatdResult);
					if(updEmplatdResult<=0){
						throw new HbException("�ק�X�ԥD�ɧ@�~����!");
					}
					if(DEBUG) System.out.println("-1---strApproval>"+strApproval);
					if(strApproval.equals("Y")){
						//�s�W���ʾ��v��
						  addShiftResult=dao.addHbtShiftV(updDataV);
						  if(DEBUG) System.out.println("�ާ@��(�t�Τ�)>=�ͮĤ� �s�W���ʾ��v�� addShiftResult->"+addShiftResult);					
						  if(addShiftResult<=0){
							  throw new HbException("�s�W���ʾ��v�ɧ@�~����!");
						  }
					}else if(strApproval.equals("N")){
							//�R�����ʾ��v��
							delShiftResult=dao.delHbtShiftV(updDataV);
							if(DEBUG) System.out.println("�ާ@��(�t�Τ�)>=�ͮĤ� �R�����ʾ��v�� delShiftResult->"+delShiftResult);					
							if(delShiftResult<=0){
								throw new HbException("�R�����ʾ��v�ɧ@�~����!");
							}					
					}
					//�קﬣ�O��  
						updHbtTransinfoResult=dao.updHbtTraninfoV(updDataV);
						if(DEBUG) System.out.println("�ާ@��(�t�Τ�)>=�ͮĤ� �קﬣ�O�� updHbtTransinfoResult->"+updHbtTransinfoResult);
						if(updHbtTransinfoResult<=0){
							throw new HbException("�קﬣ�O�ɧ@�~����!");
						}
					if(DEBUG) System.out.println("-2---strApproval>"+strApproval);
					if(strApproval.equals("Y")){		
						//�s�W�H�Ʋ�����		  
						addTransResult=dao.addHbtTransV(updDataV);
						if(DEBUG) System.out.println("�ާ@��(�t�Τ�)>=�ͮĤ� �s�W�H�Ʋ����� addTransResult->"+addTransResult);
						if(addTransResult<=0){
							throw new HbException("�s�W�H�Ʋ����ɧ@�~����!");
						}
					}else if(strApproval.equals("N")){
							//�R���H�Ʋ�����
							delTransResult=dao.delHbtTransV(updDataV);
							if(DEBUG) System.out.println("�ާ@��(�t�Τ�)>=�ͮĤ� �R���H�Ʋ����� delTransResult->"+delTransResult);
							if(delTransResult<=0){
								throw new HbException("�R���H�Ʋ����ɧ@�~����!");
							}
					}	
				 }
			  }else if(Integer.parseInt(strNewTrand)>Integer.parseInt(strTdop))
			  { 
					if(DEBUG) System.out.println("�ͮĤ�>�ާ@��(�t�Τ�)");
					if(updDataV.size()>0){
					   for(int i=0;i<updDataV.size();i++){
							HB14000VO vo=(HB14000VO)updDataV.get(i);
						   String strIdno=vo.getH_IDNO();
						   strNewTrand=vo.getNEWTRAND();
						   strNewTransno=vo.getNEWTRANSNO();
						   strTdop=vo.getH_DOP();  //���X�t�Τ��
						   if(DEBUG) System.out.println("model strIdno->"+strIdno);
						   if(DEBUG) System.out.println("model strNewTrand->"+strNewTrand);
						   //check���O��
							checkAdd=dao.checkHbtTransinfo(strIdno,strNewTransno);
							if(DEBUG) System.out.println("checkAdd->"+checkAdd);						
						   if(checkAdd==false){
							   throw new HbException("���������Ҧr��"+strIdno+"�����O�ɸ��!"); 
						   }
					  }
				   }
				  
				   if(updDataV.size()>0){
					 //�令�קﬣ�O�� 
					 updHbtTransinfoResult=dao.updHbtTraninfoV(updDataV);
					 if(DEBUG) System.out.println("�ͮĤ�>�ާ@��(�t�Τ�) �קﬣ�O��  updHbtTransinfoResult->"+updHbtTransinfoResult);
					 if(updHbtTransinfoResult<=0){
						 throw new HbException("�קﬣ�O�ɧ@�~����!");
					 }
				  }
			} 
			  ut.commit();		
		  }catch(Exception e) {
			  if(ut!=null)
			  try {
				  ut.rollback();				
			  } catch (SystemException e1) {
				  // TODO �۰ʲ��� catch �϶�
				  throw new HbException("RollBack����");
			  }
			  throw new HbException(e.toString());				
		  }
		  return updDataV;                                                                                                       
  }
  
  
	/**
	 * �N�r��(�ƭ�)�����e��n ��0 , �h���I��
	 * @param value  ��
	 * @param number ��� 
	 * @return
	 */
	public  String fillZero(String value , int number){
		if(value==null) value = "";
		StringBuffer valueSB = new StringBuffer("");
		
		//--�Y��Ƥj����Size ���ǤJsize �j�p
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
} 	  		  	

