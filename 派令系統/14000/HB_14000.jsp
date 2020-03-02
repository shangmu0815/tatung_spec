<!--**********************

*************************-->
<HTML>
<%@include file="/inc/H_scrollbarColor"%>
<HEAD>
<%@ page language="java" contentType="text/html; charset=Big5" 
         isErrorPage="true"  errorPage="/public/Error.html"  
         import="java.util.Vector,com.tatung.imis.H.Common.util.*" %>
                                                                                                               
<%@ page  import="java.util.*"%> 
<%@ page import ="java.util.StringTokenizer"%>
<%@ page import="com.tatung.imis.A.Common.data.*" %>
<%@ page import="com.tatung.imis.A.Common.helper.*"%>
<%@ page import="com.tatung.imis.H.HB.data.HB14000VO"%>
<%@ page import="java.text.SimpleDateFormat"%> 
<% 
   int HRMID= 0; 
   String strID    = "HB_14000";                 
   String strTitle = java.net.URLEncoder.encode("派令核准作業"); 
   String strTitle1 = "派令核准作業"; 
//  String readonlycolor = dataBean.getReadOnlyColor();
	String readonlycolor = "background-color: #EEEEFF;  border: 1 outset #E7F0F1"; 
%>
<%--@ taglib uri = "HRMtaglib" prefix="Htag"--%>   
<%  
//*****UserInfo and HRMWeb 介面層************//
  boolean bnkey = true;
  H_VarSourceSet obj = new H_VarSourceSet(session,request);
  //userinfo
  boolean userInfoBN = obj.H_getUserInfoIS();
//*****UserInfo and HRMWeb 介面層************//    
%>
<%   
   response.setHeader("Pragma","No-cache");
   response.setHeader("Cache-Contorl","no-cache");
   response.setDateHeader("Expires",0);    
   //deatil

%>

<%
 	String strNewTrand=request.getAttribute("newTrand")==null?"":request.getAttribute("newTrand").toString();
	String strQryApproval="";
	if (request.getAttribute("qryApproval")!=null) {  
		strQryApproval=request.getAttribute("qryApproval")==null?"":request.getAttribute("qryApproval").toString();
	}

%> 	
<META http-equiv="Content-Type" content="text/html; charset=BIG5">
<META name="GENERATOR" content="IBM WebSphere Studio">
<LINK href="/HRMWeb/css/NormalRule.css" rel="stylesheet" type="text/css">
<SCRIPT language="JavaScript" src="/HRMWeb/js/H_checkInputJSHT.js"></SCRIPT>
<SCRIPT language="JavaScript" src="/HRMWeb/js/H_commonJS.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript">

function setParamCurValue(){
	var x="";     
//    x  =  document.getElementById("F1").value +"!";
    for (var i=1;i<=1;i++){
        var str = "F"+String(i);
        if (document.getElementById(str).value==""){svalue = " ";}
        else {svalue = document.getElementById(str).value;}
          x = x +  svalue + "!";
    }    
    x = x ;
//   alert(x); //test
    return x;
} 

function  Maintain(){
	switch(event.srcElement.name){	
		case "query": 
			if(H_ShowSpaceMsg("F1","異動日期")==false){return;}
			if(document.getElementById("F1").value.length<6)
			{
				alert("[HRM 訊息] 異動日期長度至少輸入6位!!");document.getElementById("F1").focus();return;
			}
			document.myform.elements["command"].value = "HB.14000Qry";
			H_setInnerMsg();				
			document.myform.submit();	
		break;
	}
}

function qryAnswer(){ 
	if(event.keyCode==13){
			if(H_ShowSpaceMsg("F1","異動日期")==false){return;}
			if(document.getElementById("F1").value.length<6)
			{
				alert("[HRM 訊息] 異動日期長度至少輸入6位!!");document.getElementById("F1").focus();return;
			}
			document.myform.elements["command"].value = "HB.14000Qry";	
			H_setInnerMsg();	
			document.myform.submit();	
	}		
} 


function getValue(strChineseNewTrand,strTransno,strApproval){
	//alert("strChineseNewTrand->"+strChineseNewTrand+"strTransno->"+strTransno+"strApproval->"+strApproval);
	document.myform.ChineseNewTrand.value=strChineseNewTrand;
	document.myform.Transno.value=strTransno;
	document.myform.Approval.value=strApproval;
	document.myform.command.value = "HB.14000Upd";
	document.myform.submit();
}

</SCRIPT> 
<TITLE>HB_14000.jsp</TITLE>
</HEAD>
<BODY marginwidth="0" marginheight="0" topmargin="0" leftmargin="0"  >
<%if(userInfoBN){%><%@include file="/inc/H_head2"%><%}%>
<FORM name="myform" method="post" action="/HRMWeb/Controller">                                      
<TABLE border="0" width="100%" cellspacing="1">      
      <TR class='MessageWord' >
       		<TD width="100%"class="MessageWord"  height=40 colspan=4>執行結果:
       		<jsp:include page = "/public/ResultView.jsp" flush="true">   
            </jsp:include><label id="InnerMsg"></label>
            </TD>                                      		
       </TR>       	        
</TABLE>

<TABLE align="center" width="100%" border="1" >
	<TR class="TRStyle2">
		<TD align="center">派令核准作業&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</TD>
	</TR>
</TABLE>
<TABLE   width="100%"   border="1" cellpadding="0" cellspacing="0" >
   <TR class="ButtonStyle1" align="left"  >
       <TD width="42%">異動日期
       <INPUT TYPE="text"    ID="F1" tabindex="0" size=10  maxlength="8"  name="newTrand" class="InputStyle1" value="<%=strNewTrand%>" onkeydown="qryAnswer();">(YYYYMMDD)&nbsp;&nbsp;
       核准狀態:
       <SELECT class="InputStyle1"  id= "F2" name="qryApproval" tabindex="11">
	     	<OPTION VALUE = '' <%if(strQryApproval.equals("")) out.println("selected");%>>(請選取)</OPTION>
	        <OPTION VALUE = 'N' <%if(strQryApproval.equals("N")) out.println("selected");%>>未核准</OPTION>
	        <OPTION VALUE = 'Y' <%if(strQryApproval.equals("Y")) out.println("selected");%>>核准</OPTION>            
	    </SELECT>
       <INPUT type="button" id="B4" name="query" value="查詢" class="ButtonStyle3" onclick="Maintain()" ></TD>
   </TR>  
</TABLE>
<DIV  style="overflow-y:scroll;height:0" align="center">
<TABLE     width="100%" border="1" cellpadding="1" cellspacing="2" bordercolor="#999999"> 	
   	<TR  align="center" valign="middle" class="TRStyle1">
	       	<TD  height="20" width="7%">生效日</TD>
			<TD  height="20" width="8%">字號</TD>
			<TD  height="20" width="8%">身分<br>證號</TD>
			<TD  height="20" width="7%" >姓名</TD>	
			<TD  height="20" width="8%" >原任中心<br>代號</TD>
			<TD  height="20" width="12%" >原任中心<br>名稱</TD>
			<TD  height="20" width="10%" >原任職稱</TD>
			<TD  height="20" width="8%" >新任中心<br>代號</TD>
			<TD  height="20" width="12%" >新任中心<br>名稱</TD>
			<TD  height="20" width="10%" >新任職稱</TD>
			<TD  height="20" width="5%">核准與否</TD>
			<TD  height="20" width="5%">核准</TD>
	</TR>    
</TABLE>
</DIV> 
<!-- detail -->
<INPUT type="hidden" name="CurDataValueD" value="">

<DIV  style="overflow-y:scroll;height:202">
<TABLE   align="left" width="100%" border="1"  cellpadding="0" cellspacing="0" id="dataT" frame="box">

<%
	if(request.getAttribute("dataV")!=null){
	String bgcolor="";
	Vector v=(Vector)request.getAttribute("dataV");
		for(int i=0;i<v.size();i++){
			HB14000VO vo=(HB14000VO)v.get(i);
			if((i % 2) == 0)
				bgcolor="TRStyle3";
			else
				bgcolor="TRStyle2";
				String newTrand="";
				String chineseNewTrand="";
				String newTransno="";
				String idno="";
				String name="";
				String ctrno="";
				String ctr="";
				String persontitle="";
				String newCtrno="";
				String newCtr="";
				String newPersontitle="";
				String approval="";								

				chineseNewTrand=vo.getCHINESENEWTRAND();
				newTransno=vo.getNEWTRANSNO();
				idno =vo.getH_IDNO();
				name=vo.getH_NAME();
				ctrno=vo.getH_CTRNO();
				ctr=vo.getH_CTR();
				persontitle=vo.getH_PERSONTITLE();
				newCtrno=vo.getNEWCTRNO();
				newCtr=vo.getNEWCTR();
				newPersontitle=vo.getNEWPERSONTITLE();
				approval=vo.getH_APPROVAL();				
			out.print("<tr align='center'  valign='middle'  class='"+bgcolor+"' onMouseOver='changeTRFontColor()' onMouseOut='changeTRFontColor()'  </td>");		
			out.print("<td width='7%'  vAlign='middle' align='left' >"+chineseNewTrand+"</td>");
			out.print("<td width='8%'  vAlign='middle' align='left' >"+newTransno+"</td>");
			out.print("<td width='8%'  vAlign='middle' align='left' >"+idno+"</td>");
			out.print("<td width='7%'  vAlign='middle' align='left' >"+name+"</td>");	
			out.print("<td width='8%'  vAlign='middle' align='left' >"+ctrno+"</td>");	
			out.print("<td width='12%'  vAlign='middle' align='left' >"+ctr+"</td>");
			out.print("<td width='10%'  vAlign='middle' align='left' >"+persontitle+"</td>");	
			out.print("<td width='8%'  vAlign='middle' align='left' >"+newCtrno+"</td>");	
			out.print("<td width='12%'  vAlign='middle' align='left' >"+newCtr+"</td>");	
			out.print("<td width='10%'  vAlign='middle' align='left' >"+newPersontitle+"</td>");
			out.print("<td width='5%'  vAlign='middle' align='center' >"+approval+"</td>");
			if(approval.equals("Y"))
			out.println("<td width='5%'><input type=button name='Change' onClick=\"getValue('"+chineseNewTrand+"','"+newTransno+"','"+approval+"');\" value='取消' ></td>");
			else
			out.println("<td width='5%'><input type=button name='Change' onClick=\"getValue('"+chineseNewTrand+"','"+newTransno+"','"+approval+"');\" value='核准' ></td>");
			out.print("</tr>");
			}
	}
%>    
            </TABLE>    	   	
</DIV>           

<INPUT TYPE="hidden" name="command" >
<INPUT TYPE="hidden" name="ParamCurValue" >
<INPUT TYPE="hidden" name="Transno" >
<INPUT TYPE="hidden" name="Approval" >
<INPUT TYPE="hidden" name="ChineseNewTrand" >
</FORM>
</BODY>
</HTML>
