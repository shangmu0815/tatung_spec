/*
 * 在 2009/5/6 建立
 *
 * 若要變更這個產生的檔案的範本，請移至
 * 視窗 > 喜好設定 > Java > 程式碼產生 > 程式碼和註解
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
 * 若要變更這個產生的類別註解的範本，請移至
 * 視窗 > 喜好設定 > Java > 程式碼產生 > 程式碼和註解
 */
public class HB14000Model {
	private boolean DEBUG = false;

//	查詢  作業
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
				//--只取派令字號相同的第一筆
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
	//	查詢人事異動檔作業
			public  Vector qryHbtTransinfo2V(String strTransno)throws HbException, SQLException{
				  HB14000DAO dao=new HB14000DAO();
				Vector qryV=dao.qryHbtTransinfo2V(strTransno);
				return qryV;
			}

	//	查詢
			public  Vector qryHbtPersonHbtTransinfoV(String strTransno)throws HbException, SQLException{
				  HB14000DAO dao=new HB14000DAO();
				Vector qryV=dao.qryHbtPersonHbtTransinfoV(strTransno);
				return qryV;
			}					  

//	修改作業*
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
	  //系統日期
	  String strTdop="";
	  try{
			  if(updDataV.size()>0){
				for(int i=0;i<updDataV.size();i++){
					HB14000VO vo=(HB14000VO)updDataV.get(i);
					String strIdno=vo.getH_IDNO();
					strNewTrand=vo.getNEWTRAND();
					strNewTransno=vo.getNEWTRANSNO();
					strTdop=vo.getH_DOP();  //取出系統日期
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
						throw new HbException("查無身分證字號"+strIdno+"之人事主檔資料!");
					}
					checkAdd=dao.checkHbtNewpsnInfo(strIdno);
					if(DEBUG) System.out.println("2 checkAdd->"+checkAdd);
					  if(checkAdd==false){
						  throw new HbException("查無身分證字號"+strIdno+"之新進人員檔資料!");
					  }
					//check人員資訊檔
					checkAdd=dao.checkHbtPersoninfo(strIdno);
					if(DEBUG) System.out.println("3 checkAdd->"+checkAdd);
					  if(checkAdd==true){
						  needUpdPersoninfoV.add(vo);
					  }else{
						  needAddPersoninfoV.add(vo);
					  }						
					//check 出勤主檔
					checkAdd=dao.checkHctEmplatdInfo(strIdno);
					if(DEBUG) System.out.println("4 checkAdd->"+checkAdd);
					if(checkAdd==false){
						throw new HbException("查無身分證字號"+strIdno+"之出勤主檔資料!");
					}						
//					checkAdd=dao.checkHbtShiftInfo(strIdno,strNewTrand,"8","00");
//					if(checkAdd==true){
//						throw new HbException("已有身分證字號"+strIdno+"之異動歷史檔資料!"); 
//					}
					//check派令檔
					checkAdd=dao.checkHbtTransinfo(strIdno,strNewTransno);
					if(DEBUG) System.out.println("5 checkAdd->"+checkAdd);
					if(checkAdd==false){
						throw new HbException("沒有身分證字號"+strIdno+"之派令檔資料!"); 
					}
					//check人事異動檔
//					checkAdd=dao.checkHbtTransInfo(strIdno,strNewTrand,"8","00");
//					if(checkAdd==true){
//						throw new HbException("已有身分證字號"+strIdno+"之人事異動檔資料!");                           
//					}		
				}
			}					
//		---------UserTransaction為當所有作業都成功時才做其Commit之作業,否則將其Rollback			
			if(DEBUG) System.out.println("操作日(系統日) VS 生效日***strTdop->"+strTdop+"**strNewTrand->"+strNewTrand);	
			  ut=sl.getUserTransaction();
			  //---包transaction---- 
			  ut.begin();
			  //若操作日(系統日)>=生效日  
			  if(Integer.parseInt(strTdop)>=Integer.parseInt(strNewTrand)){
				  if(DEBUG) System.out.println("--strApproval--->"+strApproval+"--");
				  if(updDataV.size()>0){
					  //修改人事主檔
					  updPersonResult=dao.updHbtPersonV(updDataV);
					  if(DEBUG) System.out.println("操作日(系統日)>=生效日 修改人事主檔 updPersonResult->"+updPersonResult);
					  if(updPersonResult<=0){
						  throw new HbException("修改人事主檔作業失敗!");
					  }
					  //修改新進人員檔
					  updNewpsnResult=dao.updHbtNewpsnV(updDataV);
					  if(DEBUG) System.out.println("操作日(系統日)>=生效日 修改新進人員檔 updNewpsnResult->"+updNewpsnResult);
					  if(updNewpsnResult<=0){
						  throw new HbException("修改新進人員檔作業失敗!");
					  }
					  //人員資訊檔
					  if(needUpdPersoninfoV.size()>0){
						//修改人員資訊檔
						updPersoninfoResult=dao.updHbtPersoninfoV(needUpdPersoninfoV);
						if(DEBUG) System.out.println("操作日(系統日)>=生效日 修改人員資訊檔 updPersoninfoResult->"+updPersoninfoResult);
						if(updPersoninfoResult<=0){
							throw new HbException("修改人員資訊檔作業失敗!");
						}
					  }
					  ///////////////////////////
					if(needAddPersoninfoV.size()>0){
					  //新增人員資訊檔
					  addPersoninfoResult=dao.addHbtPersoninfoV(needAddPersoninfoV);
					  if(DEBUG) System.out.println("操作日(系統日)>=生效日 新增人員資訊檔 addPersoninfoResult->"+addPersoninfoResult);
					  if(addPersoninfoResult<=0){
						  throw new HbException("新增人員資訊檔作業失敗!");
					  }
					}
					//////////////////////////////
					//修改出勤主檔
					updEmplatdResult=dao.updHctEmplatdV(updDataV);
					if(DEBUG) System.out.println("操作日(系統日)>=生效日 修改出勤主檔 updEmplatdResult->"+updEmplatdResult);
					if(updEmplatdResult<=0){
						throw new HbException("修改出勤主檔作業失敗!");
					}
					if(DEBUG) System.out.println("-1---strApproval>"+strApproval);
					if(strApproval.equals("Y")){
						//新增異動歷史檔
						  addShiftResult=dao.addHbtShiftV(updDataV);
						  if(DEBUG) System.out.println("操作日(系統日)>=生效日 新增異動歷史檔 addShiftResult->"+addShiftResult);					
						  if(addShiftResult<=0){
							  throw new HbException("新增異動歷史檔作業失敗!");
						  }
					}else if(strApproval.equals("N")){
							//刪除異動歷史檔
							delShiftResult=dao.delHbtShiftV(updDataV);
							if(DEBUG) System.out.println("操作日(系統日)>=生效日 刪除異動歷史檔 delShiftResult->"+delShiftResult);					
							if(delShiftResult<=0){
								throw new HbException("刪除異動歷史檔作業失敗!");
							}					
					}
					//修改派令檔  
						updHbtTransinfoResult=dao.updHbtTraninfoV(updDataV);
						if(DEBUG) System.out.println("操作日(系統日)>=生效日 修改派令檔 updHbtTransinfoResult->"+updHbtTransinfoResult);
						if(updHbtTransinfoResult<=0){
							throw new HbException("修改派令檔作業失敗!");
						}
					if(DEBUG) System.out.println("-2---strApproval>"+strApproval);
					if(strApproval.equals("Y")){		
						//新增人事異動檔		  
						addTransResult=dao.addHbtTransV(updDataV);
						if(DEBUG) System.out.println("操作日(系統日)>=生效日 新增人事異動檔 addTransResult->"+addTransResult);
						if(addTransResult<=0){
							throw new HbException("新增人事異動檔作業失敗!");
						}
					}else if(strApproval.equals("N")){
							//刪除人事異動檔
							delTransResult=dao.delHbtTransV(updDataV);
							if(DEBUG) System.out.println("操作日(系統日)>=生效日 刪除人事異動檔 delTransResult->"+delTransResult);
							if(delTransResult<=0){
								throw new HbException("刪除人事異動檔作業失敗!");
							}
					}	
				 }
			  }else if(Integer.parseInt(strNewTrand)>Integer.parseInt(strTdop))
			  { 
					if(DEBUG) System.out.println("生效日>操作日(系統日)");
					if(updDataV.size()>0){
					   for(int i=0;i<updDataV.size();i++){
							HB14000VO vo=(HB14000VO)updDataV.get(i);
						   String strIdno=vo.getH_IDNO();
						   strNewTrand=vo.getNEWTRAND();
						   strNewTransno=vo.getNEWTRANSNO();
						   strTdop=vo.getH_DOP();  //取出系統日期
						   if(DEBUG) System.out.println("model strIdno->"+strIdno);
						   if(DEBUG) System.out.println("model strNewTrand->"+strNewTrand);
						   //check派令檔
							checkAdd=dao.checkHbtTransinfo(strIdno,strNewTransno);
							if(DEBUG) System.out.println("checkAdd->"+checkAdd);						
						   if(checkAdd==false){
							   throw new HbException("未有身分證字號"+strIdno+"之派令檔資料!"); 
						   }
					  }
				   }
				  
				   if(updDataV.size()>0){
					 //改成修改派令檔 
					 updHbtTransinfoResult=dao.updHbtTraninfoV(updDataV);
					 if(DEBUG) System.out.println("生效日>操作日(系統日) 修改派令檔  updHbtTransinfoResult->"+updHbtTransinfoResult);
					 if(updHbtTransinfoResult<=0){
						 throw new HbException("修改派令檔作業失敗!");
					 }
				  }
			} 
			  ut.commit();		
		  }catch(Exception e) {
			  if(ut!=null)
			  try {
				  ut.rollback();				
			  } catch (SystemException e1) {
				  // TODO 自動產生 catch 區塊
				  throw new HbException("RollBack失敗");
			  }
			  throw new HbException(e.toString());				
		  }
		  return updDataV;                                                                                                       
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
} 	  		  	

