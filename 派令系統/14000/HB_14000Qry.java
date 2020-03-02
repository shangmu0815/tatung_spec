/*
 * �b 2009/5/6 �إ�
 *
 * �Y�n�ܧ�o�Ӳ��ͪ��ɮת��d���A�в���
 * ���� > �ߦn�]�w > Java > �{���X���� > �{���X�M����
 */
package com.tatung.imis.H.HB.action;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.ServletException;

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
public class HB_14000Qry implements Command {
	private boolean DEBUG = false;
	public void execute(RequestHelper helper)throws ServletException, IOException {
		String strNewTrand= helper.getRequest().getParameter("newTrand");
		String strQryApproval= helper.getRequest().getParameter("qryApproval");
		Vector dataV=new Vector();
		String sMessage="";
		try{
			HB14000Model model=new HB14000Model();
			dataV=model.qryHbtTransinfoV(strNewTrand,strQryApproval);
			if(dataV.size()==0){
				sMessage="NO�d�L�ɸ��!";
			}else{
				sMessage="OK�d�ߵ���"+dataV.size()+"��";
			}
		}catch(HbException e)
		{
			sMessage="NO"+e.toString();
			e.printStackTrace();
		}catch(SQLException e) {
			sMessage="SQL"+e.toString();
			e.printStackTrace();
		}finally{
			helper.getRequest().setAttribute("newTrand",strNewTrand);
			helper.getRequest().setAttribute("qryApproval",strQryApproval);
			helper.getRequest().setAttribute("ActionResult",sMessage);
			helper.getRequest().setAttribute("dataV",(dataV.size()==0?null:dataV));	
		}	

	}
}
