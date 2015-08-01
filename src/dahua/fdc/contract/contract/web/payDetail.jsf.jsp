<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.ArrayList,
		          com.kingdee.bos.Context,
		          com.kingdee.eas.cp.common.web.util.WebContextUtil,
				  java.util.Map,java.util.Iterator,
				  java.lang.Object,java.math.BigDecimal,
				  com.kingdee.eas.fdc.contract.web.PayRequestWebHelper,
				  com.kingdee.eas.fdc.contract.web.WebAmountTest
				  "
%>
<%@page import="com.kingdee.eas.fdc.basedata.FDCHelper"%>
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
<title>累计实付明细</title>
<link href="css/index.css" rel="stylesheet" type="text/css">
<%
	String billID = request.getParameter("billId");
	//billID="sBl/yLkGRq+uT3m8UvXx2MmlqGk=";
	Context ctx = WebContextUtil.getEasContext(request);
	StringBuffer sb = new StringBuffer();
	sb.append("select * , proposerDep.* ");
	sb.append(" where id= '");
	sb.append(billID.trim());
	sb.append("'");

	String payReqId=billID;
	Map detailMap = PayRequestWebHelper.getPayDetail(ctx,payReqId);
%>
</head>
<body>
<br>
<table width="96%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
      <td nowrap style="BORDER-bottom: #D8DEEA 1px solid;"><div class="content" style="cursor:hand;"onClick="expandIt('pay','payDetail')">
	<img src="images/dot_arrow_open.gif" width="9" height="9" id="pay"  align="absmiddle"> 
        <b>累计实付明细</b> 
      </div>
     </td>
  </tr>
</table>
<!--成本信息-->
<table width="96%" border="0" align="center" cellpadding="3" cellspacing="0"  id="payDetail" style="display:block">
  <tr>
  	<td>收付类别</td>
    <td>累计实付</td>
  </tr>
<%
	if(detailMap==null)return;
	BigDecimal detailSum = FDCHelper.ZERO;
	for(Iterator iter=detailMap.keySet().iterator();iter.hasNext();){ 
	String name = (String)iter.next();
	BigDecimal detailAmount = (BigDecimal)detailMap.get(name);
	detailSum = FDCHelper.add(detailSum,detailAmount);
%>
	<tr>
		    <td><%=name%></td>	<!--收付类别-->
		    <td><%=WebAmountTest.getDecimalFormat2(FDCHelper.toBigDecimal(detailAmount,2)) %></td>	<!--累计实付-->

	</tr>
    
<%  } 
%>
	<tr>
		<td>合计：</td>
		<td><%=WebAmountTest.getDecimalFormat2(FDCHelper.toBigDecimal(detailSum,2))%></td>
	</tr>
</table>
<!-- 
<a href="/easweb/winlet/attachment/webAttachment.jsf?bosid=<%=billID%>" onClick="window.open(this.href,'', 'height=350,width=500,toolbar=no,location=no,status=no,menubar=no');return false">合同附件</a>
 -->
</body>
</html>
