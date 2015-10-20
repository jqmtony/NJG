<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<%@ page import="java.util.ArrayList,
		          com.kingdee.bos.Context,
		          com.kingdee.eas.cp.common.web.util.WebContextUtil,
				  com.kingdee.eas.base.attachment.BoAttchAssoFactory,
				  com.kingdee.eas.base.attachment.IBoAttchAsso,
				  com.kingdee.eas.base.attachment.AttachmentFactory,
				  com.kingdee.eas.base.attachment.IAttachment,
				  com.kingdee.eas.base.attachment.AttachmentInfo,
				  com.kingdee.jdbc.rowset.IRowSet,
				  com.kingdee.eas.cp.eip.mc.web.WorkFlowUtil,
				  java.text.SimpleDateFormat,
				  com.kingdee.bos.BOSException,
				  java.util.Map,java.util.Iterator,
				  java.lang.Object,java.math.BigDecimal,
					com.kingdee.eas.fdc.basedata.FDCHelper,
				  com.kingdee.bos.dao.ormapping.ObjectUuidPK,
				  com.kingdee.bos.metadata.entity.SelectorItemCollection,
				  com.kingdee.eas.common.EASBizException,
				  com.kingdee.eas.fdc.basedata.DeductTypeInfo,
				  com.kingdee.eas.fdc.basedata.DeductTypeCollection,
				  com.kingdee.eas.fdc.contract.PayRequestBillFactory,
				  com.kingdee.eas.fdc.contract.PayRequestBillInfo,
				  com.kingdee.eas.fdc.contract.PayRequestBillEntryInfo,
				  com.kingdee.eas.fdc.contract.web.PayRequestWebHelper,
				  com.kingdee.eas.fdc.contract.ContractSourceEnum,
				  com.kingdee.eas.fdc.finance.FDCBudgetPeriodInfo,
				  com.kingdee.eas.fdc.contract.web.WebAmountTest
				  "
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>预算信息</title>
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
<link href="css/index.css" rel="stylesheet" type="text/css">
</head>
<%
	String billID = request.getParameter("billId");
	//String billID="+3d+B4PeRU6UkAD/cZPUFcmlqGk=";
	Context ctx = WebContextUtil.getEasContext(request);
	
	StringBuffer sb = new StringBuffer();
	sb.append("select * , proposerDep.* ");
	sb.append(" where id= '");
	sb.append(billID.trim());
	sb.append("'");

	String payReqId=billID;
	Map dataMap = PayRequestWebHelper.fetchInitData(ctx,payReqId);
	PayRequestBillInfo info = (PayRequestBillInfo)dataMap.get("info");
	FDCBudgetPeriodInfo periodInfo= PayRequestWebHelper.getFDCBudgetPeriod(info,ctx);
	Map curMonthMap=PayRequestWebHelper.fetchCurrentMonthConInfo(ctx,info,periodInfo);
%>
<body>
<table width="96%" border="0" align="center" cellpadding="0" cellspacing="0" >
  <tr>
      <td nowrap style="BORDER-bottom: #D8DEEA 1px solid;"><div class="content" style="cursor:hand;"onClick="expandIt('budget','budgetEntrys')">
	<img src="images/dot_arrow_open.gif" width="9" height="9" id="budget"  align="absmiddle"> 
        <b>预算信息</b> 
      </div>
     </td>
  </tr>
</table>
<table width="96%" border="0" align="center" id="budgetEntrys" style="display:block" cellpadding="5" cellspacing="1" bgcolor="CBD9EB">
  <tr bgcolor=#FFFFFF align="center" style="font-weight: bold">
  	<td>合同本月计划付款金额</td>
    <td>合同本月已请款金额</td>
    <td>合同本月计划付款余额</td>
    <td>本次请款</td>
  </tr>
  <tr bgcolor=#FFFFFF>
  	<td><%=curMonthMap.get("planAmount")==null?"0.00":WebAmountTest.getDecimalFormat2(FDCHelper.toBigDecimal(curMonthMap.get("planAmount"),2))%></td>
	<td><%=curMonthMap.get("requestAmt")==null?"0.00":WebAmountTest.getDecimalFormat2(FDCHelper.toBigDecimal(curMonthMap.get("requestAmt"),2))%></td>
	<td><%=curMonthMap.get("bananceAmt")==null?"0.00":WebAmountTest.getDecimalFormat2(FDCHelper.toBigDecimal(curMonthMap.get("bananceAmt"),2))%></td>
    	<td><%=curMonthMap.get("amount")==null?"0.00":WebAmountTest.getDecimalFormat2(FDCHelper.toBigDecimal(curMonthMap.get("amount"),2))%></td>

  </tr>
  </table>
</body>
</html>