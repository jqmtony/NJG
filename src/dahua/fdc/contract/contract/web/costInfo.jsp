<%@ page contentType="text/html;charset=UTF-8" %>
<%@page import="com.kingdee.eas.fdc.basedata.FDCHelper"%>
<%@page import="com.kingdee.eas.fdc.contract.web.ContractWebHelper"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="com.kingdee.eas.fdc.basedata.CostAccountCollection"%>
<%@page import="com.kingdee.eas.fdc.basedata.CostAccountInfo"%>
<%@page import="com.kingdee.bos.Context"%>
<%@page import="com.kingdee.eas.cp.common.web.util.WebContextUtil,com.kingdee.eas.fdc.contract.web.WebAmountTest"%>
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
<title>合同成本信息</title>
<link href="css/index.css" rel="stylesheet" type="text/css">
<%
	String billID = request.getParameter("conId");
	//billID="hLXt77bARi+s0vsHa88z9Q1t0fQ=";
	//billID="N1tl6+IESL+pM4p+RsR7HQ1t0fQ=";
	Context ctx = WebContextUtil.getEasContext(request);
	Map costMap=ContractWebHelper.getCostInfo(ctx,billID);
	//SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy 年 MM 月 dd 日");
%>
</head>
<body>
<!--分录<%=billID%>-->
<table width="96%" border="0" align="center" cellpadding="0" cellspacing="0" >
  <tr>
      <td nowrap style="BORDER-bottom: #D8DEEA 1px solid;"><div class="content" style="cursor:hand;"onClick="expandIt('cost','costEntrys')">
	<img src="images/dot_arrow_open.gif" width="9" height="9" id="cost"  align="absmiddle"> 
        <b>成本信息</b> 
      </div>
     </td>
  </tr>
</table>
<!--成本信息-->
<table width="96%" border="0" align="center" id="costEntrys" style="display:block" cellpadding="5" cellspacing="1" bgcolor="CBD9EB">
  <tr bgcolor=#FFFFFF align="center" style="font-weight: bold">
  	<td>工程编码</td>
    <td>工程名称</td>
    <td>科目编码</td>
    <td>科目名称</td>
    <td>目标成本</td>
    <td>目前已发生</td>
    <td>目前待发生</td>
    <td>动态成本</td>
    <td>差异</td>
  </tr>
<%
	CostAccountCollection accts=(CostAccountCollection)costMap.get("CostAccountCollection");
	if(accts==null)return;
	for(Iterator iter=accts.iterator();iter.hasNext();){ 
		CostAccountInfo info=(CostAccountInfo)iter.next();
		String key=info.getId().toString();
		BigDecimal aimCost=(BigDecimal)costMap.get(key+"_aim");
		BigDecimal adjust=(BigDecimal)costMap.get(key+"_adjust");//dif
		BigDecimal happen=(BigDecimal)costMap.get(key+"_happen");
		BigDecimal dyn=(BigDecimal)costMap.get(key+"_dyn");
		BigDecimal intending=(BigDecimal)costMap.get(key+"_intending");
		String url="/easweb/fdc/contract/relationCon.jsp?acctId="+java.net.URLEncoder.encode(key)+"&acctName="+java.net.URLEncoder.encode(info.getName(),"utf-8");
		String clickStr="window.open('"+url+"','"+info.getName()+"', 'height=400,width=700,toolbar=no,location=no,status=no,menubar=no,top=200,left=280,resizable=yes,scrollbars=yes');return false";
%>
	<tr bgcolor=#FFFFFF onClick="<%=clickStr%>">
		  	<td><%=info.getCurProject().getLongNumber()!=null?info.getCurProject().getLongNumber().replace('!','.'):""%></td>	<%--工程编码--%>
		    <td><%=info.getCurProject().getName()%></td>	<%--工程名称--%>
		    <td><%=info.getLongNumber()!=null?info.getLongNumber().replace('!','.'):"" %></td>	<%--科目编码--%>
		    <td><%=info.getName()%></td>	<%--科目名称--%>
		    <td align="right"><%=WebAmountTest.getDecimalFormat2(FDCHelper.toBigDecimal(aimCost,2)) %></td>	<%--目标成本--%>
		    <td align="right"><%=WebAmountTest.getDecimalFormat2(FDCHelper.toBigDecimal(happen,2)) %></td>		<%--目前已发生--%>
		    <td align="right"><%=WebAmountTest.getDecimalFormat2(FDCHelper.toBigDecimal(intending,2)) %></td>	<%--目前待发生--%>
		    <td align="right"><%=WebAmountTest.getDecimalFormat2(FDCHelper.toBigDecimal(dyn,2)) %></td>		<%--动态成本--%>
		    <td align="right"><%=WebAmountTest.getDecimalFormat2(FDCHelper.toBigDecimal(adjust,2)) %></td>		<%--差异,也即待发生调整--%>
	</tr>
    
<%  } 
%>
</table>
</body>
</html>
