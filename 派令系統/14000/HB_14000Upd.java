/*
 * �b 2009/11/6 �إ�
 *
 * �Y�n�ܧ�o�Ӳ��ͪ��ɮת��d���A�в���
 * ���� > �ߦn�]�w > Java > �{���X���� > �{���X�M����
 */
package com.tatung.imis.H.HB.action;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.servlet.ServletException;

import com.tatung.imis.A.Common.data.UserInfoVO;
import com.tatung.imis.A.Common.helper.UserInfo;
import com.tatung.imis.A.Common.helper.UserInfoImpl;
import com.tatung.imis.Common.controller.Command;
import com.tatung.imis.Common.controller.RequestHelper;
import com.tatung.imis.H.HB.data.HB14000VO;
import com.tatung.imis.H.HB.exception.HbException;
import com.tatung.imis.H.HB.model.HB14000Model;

/**
 * @author Administrator
 *
 * �Y�n�ܧ�o�Ӳ��ͪ����O���Ѫ��d���A�в���
 * ���� > �ߦn�]�w > Java > �{���X���� > �{���X�M����
 */
public class HB_14000Upd implements Command {
	private boolean DEBUG = false;
	public void execute(RequestHelper helper)throws ServletException, IOException {
		Vector qryResultV=new Vector();
		Vector updResultV=new Vector();
		Vector dataV=new Vector();
		String sMessage ="";
		String strNewTrand="";
//		�ާ@��	
		String strTcop="";
//		��t�Τ��
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		String strTdop = sf.format(date);
//		���o�ާ@��
		UserInfo userinfoSession = UserInfoImpl.getInstance(); 
		UserInfoVO userinfoVO = userinfoSession.getUserInfo(helper.getRequest());
		if(userinfoVO != null) {
			strTcop = userinfoVO.getHb_name();     	
		}
		String strChineseNewTrand= helper.getRequest().getParameter("ChineseNewTrand");
		String strTransno= helper.getRequest().getParameter("Transno");
		String strApproval= helper.getRequest().getParameter("Approval");
		strNewTrand= helper.getRequest().getParameter("newTrand");
		String strQryApproval= helper.getRequest().getParameter("qryApproval");		
		HB14000Model model=new HB14000Model();	
		try {
			Vector data2V=new Vector();
			data2V=model.qryHbtPersonHbtTransinfoV(strTransno);
			for(int i=0;i<data2V.size();i++){
				HB14000VO VO=(HB14000VO)data2V.get(i);
				//���U���s��n�NN�אּY �� Y�אּN		
				VO.setH_APPROVAL(strApproval.equals("N")?"Y":"N");
				VO.setH_COP(strTcop);
				VO.setH_DOP(strTdop);				
			}
			if(DEBUG) System.out.println("data2V size->"+data2V.size());
			if(data2V.size()==0){
				throw new HbException("�d�L�ק蠟�H�����ʸ��!"); 
			}else{
				updResultV=model.updData(data2V);
			}		
					sMessage="OK�i�ק�j���\!�@�ק�"+updResultV.size()+"��";
					}catch(HbException e){
						sMessage = "NO"+e.toString();
						e.printStackTrace();		
					} catch (Exception e) {
							sMessage = "NO"+e.toString();
							e.printStackTrace();
					}finally{
						try {
							dataV=model.qryHbtTransinfoV(strNewTrand,strQryApproval);
						} catch (HbException e1) {
							// TODO �۰ʲ��� catch �϶�
							e1.printStackTrace();
							sMessage=e1.toString();
						} catch (SQLException e1) {
							// TODO �۰ʲ��� catch �϶�
							e1.printStackTrace();
							sMessage=e1.toString();
						}
						helper.getRequest().setAttribute("ActionResult",sMessage);
						helper.getRequest().setAttribute("dataV",(dataV.size()==0?null:dataV));
						helper.getRequest().setAttribute("newTrand",strNewTrand);
						helper.getRequest().setAttribute("qryApproval",strQryApproval);					
					}
	}

}
