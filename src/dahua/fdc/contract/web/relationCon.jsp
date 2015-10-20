<%@ page contentType="text/html;charset=UTF-8" %>
<%@page import="com.kingdee.eas.fdc.basedata.FDCHelper"%>
<%@page import="com.kingdee.eas.fdc.contract.web.ContractWebHelper"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="com.kingdee.eas.fdc.basedata.CostAccountCollection"%>
<%@page import="com.kingdee.eas.fdc.basedata.CostAccountInfo"%>
<%@page import="com.kingdee.bos.Context"%>
<%@page import="com.kingdee.eas.cp.common.web.util.WebContextUtil"%>
<%@page import="com.kingdee.eas.fdc.contract.ContractBillCollection"%>
<%@page import="com.kingdee.eas.fdc.contract.ContractBillInfo,com.kingdee.eas.fdc.contract.web.WebAmountTest"%>
<html>
<script language="JavaScript" type="text/javascript">
<!-- 
//name:展开/闭合区段的图标id；name+"Tbl"为对应div/table的id。
function expandIt(name, displayName){ 
	var imgID=eval(name);
	var tblID=eval(displayName);
	if(tblID.style.display == 'block'){
 		tblID.style.display='none';
		imgID.src = "images/dot_arrow_close.gif";
		imgID.width="9";
		imgID.height="9";
	}
	else {
 		tblID.style.display='block';
		imgID.src = "images/dot_arrow_open.gif";
		imgID.width="9";
		imgID.height="9";		
	}
}

// -->
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="css/index.css" rel="stylesheet" type="text/css">
<%
	String acctId = request.getParameter("acctId");
	String acctName = request.getParameter("acctName");
	Context ctx = WebContextUtil.getEasContext(request);
	//SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy 年 MM 月 dd 日");
%>
<title>科目:<%=acctName%></title>
</head>
<body>
<!--分录-->
<table width="96%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
      <td nowrap style="BORDER-bottom: #D8DEEA 1px solid;"><div class="content" style="cursor:hand;"onClick="expandIt('cost','costEntrys')">
	<img src="images/dot_arrow_open.gif" width="9" height="9" id="cost"  align="absmiddle"> 
        <b>成本信息</b> 
      </div>
     </td>
  </tr>
</table>
<!--成本信息-->
<table width="96%" border="0" align="center" cellpadding="5" cellspacing="1" bgcolor="CBD9EB"  id="costEntrys" style="display:block">
  <tr bgcolor=#FFFFFF align="center" style="font-weight: bold">
  	<td>执行状态</td>
    <td>合同编号</td>
    <td>合同名称</td>
    <td>合同金额</td>
    <td>拆分金额</td>
    <td>合同结算金额</td>
    <td>结算拆分到该科目金额</td>
<!-- <td>直接归属产品</td>	 -->
  </tr>
<%
	ContractBillCollection cons=ContractWebHelper.getRelationCon(ctx,acctId);
	if(cons==null)return;
	for(Iterator iter=cons.iterator();iter.hasNext();){ 
		ContractBillInfo info=(ContractBillInfo)iter.next();

%>
	<tr  bgColor=#ffffff>
		  	<td><%=info.isHasSettled()?"已结算":"未结算"%></td>			<%--执行状态--%>
		    <td><%=info.getNumber()%></td>		<%--合同编号--%>
		    <td><%=info.getName()%></td>										<%--合同名称--%>
		    <td align="right"><%=WebAmountTest.getDecimalFormat2(FDCHelper.toBigDecimal(info.getAmount(),2))%></td>						<%--合同金额--%>
		    <td align="right"><%=WebAmountTest.getDecimalFormat2(FDCHelper.toBigDecimal(info.getBigDecimal("splitAmt"),2)) %></td>	<%--拆分金额--%>
		    <td align="right"><%=WebAmountTest.getDecimalFormat2(FDCHelper.toBigDecimal(info.getSettleAmt(),2)) %></td>		<%--合同结算金额--%>
		    <td align="right"><%=WebAmountTest.getDecimalFormat2(FDCHelper.toBigDecimal(info.getBigDecimal("settleSplitAmt"),2))%></td>	<%--结算拆分到该科目金额--%>
<%-- 	    <td><%=%></td>		<!--直接归属产品-->	 --%>
	</tr>
    
<%  
	} 
%>
</table>
</body>
</html>
