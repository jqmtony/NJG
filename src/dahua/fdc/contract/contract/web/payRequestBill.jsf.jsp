<%@ page contentType="text/html;charset=UTF-8" %>
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
				  com.kingdee.eas.fdc.contract.web.WebAmountTest
				  "
%>
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
<title>付款申请单</title>
<link href="css/index.css" rel="stylesheet" type="text/css">
<%
	String billID = request.getParameter("billId");
	//String billID="7EdHQY3ESiq9uuolBus1tsmlqGk=";
	//String billID="p9r/u9dnQqi0ztiBn0PdNcmlqGk=";
	//String billID="fIEAmjfmRY+Au4BF6VdVbcmlqGk=";
	Context ctx = WebContextUtil.getEasContext(request);
	
	StringBuffer sb = new StringBuffer();
	sb.append("select * , proposerDep.* ");
	sb.append(" where id= '");
	sb.append(billID.trim());
	sb.append("'");

	String payReqId=billID;
	Map dataMap = PayRequestWebHelper.fetchInitData(ctx,payReqId);
	PayRequestBillInfo info = (PayRequestBillInfo)dataMap.get("info");
	DeductTypeCollection deductTypeColl = (DeductTypeCollection)dataMap.get("typeColl");
	Map deductMap = (Map)dataMap.get("deductMap");
	String prjName = "";
	if(info.getCurProject().getFullOrgUnit()!= null){
		prjName = info.getCurProject().getFullOrgUnit().getName() + "\\"
				+ info.getCurProject().getDisplayName();
	}else{
		prjName = info.getCurProject().getDisplayName();
	}
	prjName = prjName.toString().replace('_', '\\');
%>
</head>
<body>
<br>
<!--
<div align="center" style="FONT-SIZE:18px; FONT-FAMILY:  "Arial"; color: 000000;">合同</div>
<table >
  <tr>
	<td height="1px"></td>
  </tr>
</table>
-->
<table width="96%" border="0" align="center" cellpadding="0" cellspacing="0" style="margin-top:8px;">
  <tr>
    <td bgcolor="BDD2EC"><img name="0.00" src="0.00" width="1" height="1" alt="0.00"></td>
  </tr>
  <tr>
    <td bgcolor="E8EEF6">
	 <table width="100%" border="0" align="center" cellpadding="5" cellspacing="1" bgcolor="CBD9EB">
        <tr bgcolor="#FFFFFF"> <!--第 1  行-->
          <td width="14%">组织：</td>
          <td width="20%"> 
            <%= info.getOrgUnit().getDisplayName() %>
            &nbsp; </td>
          <td>工程项目：</td>
          <td> 
            <%= info.getCurProject().getDisplayName()%>
          </td>
          <td width="13%" >用款部门： </td>
          <td width="23%" > 
            <%=info.getUseDepartment()==null?"":info.getUseDepartment().getName()%>
          </td>
        </tr>
        <tr bgcolor="#FFFFFF"> <!--第 2  行-->
          <td width="14%">合同编号：</td>
          <td width="20%"> 
            <%= info.getContractNo() %>
            &nbsp; </td>
          <td>付款申请单编码：</td>
          <td> 
            <%=info.getNumber()%>
          </td>
          <td width="13%">付款类型：</td>
          <td width="23%">
          		<%=info.getPaymentType()==null?"":info.getPaymentType().getName()%>
          </td>
        </tr>
		<tr bgcolor="#FFFFFF"> <!--第 3  行-->
          <td width="14%">原币金额：</td>
          <td width="20%" align="right"> 
      <%=WebAmountTest.getDecimalFormat2(FDCHelper.toBigDecimal(info.getOriginalAmount(),2))%>

          </td>
          <td>币别：</td>
          <td>
          	<table width="100%">
          		<tr height="100%">
          			<td width="33%"><%=info.getCurrency()==null?"":info.getCurrency().getName()%></td>
          			<td width="33%">汇率：</td>
          			<td width="34%"><%=WebAmountTest.getDecimalFormat2(FDCHelper.toBigDecimal(info.getExchangeRate(),3))%></td>
          		</tr>
          	</table>
          </td>
          <td width="13%" >本币金额： </td>
          <td width="23%" align="right"> 
            <%=WebAmountTest.getDecimalFormat2(FDCHelper.toBigDecimal(info.getAmount(),2))%>
          </td>
        </tr>
        <tr bgcolor="#FFFFFF"> <!--第 4  行-->
          <td width="14%">收款单位全称：</td>
          <td width="20%"> 
            <%=info.getSupplier() == null?"":info.getSupplier().getName() %>
            &nbsp; </td>
          <td>实际收款单位：</td>
          <td> 
            <%=info.getRealSupplier() == null?"":info.getRealSupplier().getName()%>
          </td>
          <td width="13%" >用途： </td>
          <td width="23%" > 
            <%=info.getUsage()==null?"":info.getUsage()%>
          </td>
        </tr>
        <tr bgcolor="#FFFFFF"> <!--第 5  行-->
          <td width="14%">已实现产值：</td>
          <td width="20%" align="right"> 
          	<%=info.getTotalSettlePrice()==null?"0.00":WebAmountTest.getDecimalFormat2(FDCHelper.toBigDecimal(info.getTotalSettlePrice(),2))%>
          </td>
          <td>付款日期：</td>
          <td colspan="3"> 
            <%=info.getPayDate()%>
          </td>
        </tr>

        <tr bgcolor="#FFFFFF"> <!--第 6  行-->
          <td width="14%">摘要：</td>
          <td colspan="5"> 
            <%= info.getDescription() == null?"":info.getDescription()%>
            &nbsp; </td>
        </tr>
        <tr bgcolor="#FFFFFF"> <!--第 7  行-->
          <td width="14%">备注：</td>
          <td colspan="5"> 
            <%= info.getMoneyDesc()==null?"":info.getMoneyDesc()%>
            &nbsp; </td>
        </tr>
      </table>
    </td>
    <td>
    	<Iframe src="/easweb/fdc/contract/payDetail.jsf.jsp?billId=<%=java.net.URLEncoder.encode(billID)%>" width="100%" height="100%" scrolling="auto" frameborder="1" name="payDetail"></iframe>
    </td>
  </tr>
</table>
<hr width="96%"/>
<br/>
<!--辅助信息-->
<table width="96%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td nowrap style="BORDER-bottom: #D8DEEA 1px solid;"><div class="content" style="cursor:hand;"onClick="expandIt('otherInfo','otherInfoEntry')">
	<img src="images/dot_arrow_close.gif" width="9" height="9" id="otherInfo"  align="absmiddle"> 
        <b>辅助信息</b> 
      </div>
	 </td>
  </tr>
</table>
<table width="96%" border="0" align="center" cellpadding="3" cellspacing="0"  id="otherInfoEntry" style="display:none">
	<tr><!-- 第1行 -->
		<td width="13%">业务日期</td>
		<td width="20%"><%=info.getBookedDate()%></td>
		<td width="13%">大写金额</td>
		<td width="20%"><%=info.getCapitalAmount() %></td>
		<td>同城异地</td>
		<td><%=info.getIsDifferPlace()%></td>
	</tr>
	<tr><!-- 第2行 -->
		<td>申请期间</td>
		<td><%=info.getPeriod()==null?"":info.getPeriod().getPeriodYear() +"年"+info.getPeriod().getPeriodNumber()+"期" %></td>
		<td>制单人</td>
		<td><%=info.getCreator().getName()%></td>
		<td>收款银行</td>
		<td><%=info.getRecBank()==null?"":info.getRecBank()%></td>
	</tr>
	<tr><!-- 第3行 -->
		<td>制单日期</td>
		<td><%=info.getCreateTime() %></td>
		<td>审核人</td>
		<td><%=info.getAuditor()==null?"":info.getAuditor().getName()%></td>
		<td>收款账号</td>
		<td><%=info.getRecAccount()==null?"":info.getRecAccount()%></td>
	</tr>
	<tr><!-- 第4行 -->
		<td>审核日期</td>
		<td><%=info.getAuditTime()==null?"":info.getAuditTime()%></td>
		<td>是否加急</td>
		<td><%=info.getUrgentDegree()%></td>
		<td>提交付款</td>
		<td><%=info.isIsPay()?"是":"否"%></td>
	</tr>
</table>
<!--工程付款情况表-->
<table width="96%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td nowrap style="BORDER-bottom: #D8DEEA 1px solid;"><div class="content" style="cursor:hand;"onClick="expandIt('payInfo','payInfoEntry')">
	<img src="images/dot_arrow_close.gif" width="9" height="9" id="payInfo"  align="absmiddle"> 
        <b>工程付款情况表</b> 
      </div>
	 </td>
  </tr>
</table>
<table width="96%" border="0" align="center" cellpadding="5" cellspacing="1" bgcolor="CBD9EB"  id="payInfoEntry" style="display:none">
	<tr bgcolor ="E8E8E3" ><!--第一行-->
		<td  width="13%">项目名称</td>
		<td colspan="3" align="right"><%=prjName%></td>
		<td colspan="2">合同造价</td>
		<td colspan="2" align="right"><%=info.getContractPrice()==null?"":WebAmountTest.getDecimalFormat2(FDCHelper.toBigDecimal(info.getContractPrice(),2))%></td>
	</tr>
	<tr bgcolor ="E8E8E3" ><!--第二行-->
		<td width="13%">合同名称</td>
		<td colspan="3" align="right"><%=info.getContractName()%></td>
		<td colspan="2">最新造价</td>
		<td colspan="2" align="right"><%=WebAmountTest.getDecimalFormat2(FDCHelper.toBigDecimal(info.getLatestPrice(),2))%></td>
	</tr>
	<tr bgcolor ="E8E8E3" ><!--第三行-->
		<td width="13%">变更指令金额</td>
	  <td align="right"><%=info.getChangeAmt()==null?"0.00":WebAmountTest.getDecimalFormat2(FDCHelper.toBigDecimal(info.getChangeAmt(),2))%></td>
	  <td>结算金额</td>
	  <td align="right"><%=info.getSettleAmt()==null?"0.00":WebAmountTest.getDecimalFormat2(FDCHelper.toBigDecimal(info.getSettleAmt(),2))%></td>
	  <td colspan="2">本申请单已付金额</td>
	  <td colspan="2" align="right"><%=info.getPayedAmt()==null?"0.00":WebAmountTest.getDecimalFormat2(FDCHelper.toBigDecimal(info.getPayedAmt(),2))%></td>
	</tr>
	<tr bgcolor ="E8E8E3" ><!--第四行-->
		<td width="13%">合同付款次数</td>
		<td align="right"><%=info.getPayTimes()%></td>
		<td>截止上期累计实付</td>
		<td>截止上期累计申请</td>
		<td>本期申请原币</td>
		<td>本期申请本币</td>
	  <td>截止本期累计申请</td>
	  <td>截止本期累计实付</td>
	</tr>
	<tr bgcolor ="E8E8E3" > <!--第五行-->
		<td rowspan="3" width="13%">进度款项</td>
		<td align="right">合同内工程款</td>
		<td align="right"><%=info.getLstPrjAllPaidAmt()==null?"0.00":WebAmountTest.getDecimalFormat2(FDCHelper.toBigDecimal(info.getLstPrjAllPaidAmt(),2))%></td>  <!--截止上期累计实付-->
		<td align="right"><%=info.getLstPrjAllReqAmt()==null?"0.00":WebAmountTest.getDecimalFormat2(FDCHelper.toBigDecimal(info.getLstPrjAllReqAmt(),2))%></td>   <!--截止上期累计申请-->
		<td align="right"><%=info.getProjectPriceInContractOri()==null?"0.00":WebAmountTest.getDecimalFormat2(FDCHelper.toBigDecimal(info.getProjectPriceInContractOri(),2))%></td>  <!--本期发生原币-->
		<td align="right"><%=info.getProjectPriceInContract()==null?"0.00":WebAmountTest.getDecimalFormat2(FDCHelper.toBigDecimal(info.getProjectPriceInContract(),2))%></td>     <!--本期发生本币-->
		<td align="right"><%=WebAmountTest.getDecimalFormat2(FDCHelper.toBigDecimal(FDCHelper.add(info.getLstPrjAllReqAmt(),info.getProjectPriceInContract()),2))%></td>        <!--截止本期累计申请-->
		<td align="right"><%=info.getLstPrjAllPaidAmt()==null?"0.00":WebAmountTest.getDecimalFormat2(FDCHelper.toBigDecimal(info.getLstPrjAllPaidAmt(),2))%></td>           <!--截止本期累计实付-->
	</tr>
	<tr bgcolor ="E8E8E3" ><!--第六行-->
		<td align="right">奖励</td>
		<td align="right"><%=info.get("lstguerdonPaidAmt")==null?"0.00":WebAmountTest.getDecimalFormat2(FDCHelper.toBigDecimal(info.get("lstguerdonPaidAmt"),2))%></td> 
		<td align="right"><%=info.get("lstGuerdonReqAmt")==null?"0.00":WebAmountTest.getDecimalFormat2(FDCHelper.toBigDecimal(info.get("lstGuerdonReqAmt"),2))%></td> 
		<td align="right"><%=info.get("guerdonOriginalAmt")==null?"0.00":WebAmountTest.getDecimalFormat2(FDCHelper.toBigDecimal(info.get("guerdonOriginalAmt"),2))%></td> 
		<td align="right"><%=info.get("guerdonAmt")==null?"0.00":WebAmountTest.getDecimalFormat2(FDCHelper.toBigDecimal(info.get("guerdonAmt"),2))%></td> 
		<td align="right"><%=WebAmountTest.getDecimalFormat2(FDCHelper.toBigDecimal(FDCHelper.add(info.get("guerdonAmt"),info.get("lstGuerdonReqAmt")),2))%></td> 
		<td align="right"><%=info.get("lstguerdonPaidAmt")==null?"0.00":WebAmountTest.getDecimalFormat2(FDCHelper.toBigDecimal(info.get("lstguerdonPaidAmt"),2))%></td> 
	</tr>
	<tr bgcolor ="E8E8E3" ><!--第七行-->
		<td align="right">违约金</td>
		<td align="right"><%=info.get("lstCompensationPaidAmt")==null?"0.00":WebAmountTest.getDecimalFormat2(FDCHelper.toBigDecimal(info.get("lstCompensationPaidAmt"),2))%></td> 
		<td align="right"><%=info.get("lstCompensationReqAmt")==null?"0.00":WebAmountTest.getDecimalFormat2(FDCHelper.toBigDecimal(info.get("lstCompensationReqAmt"),2))%></td> 
		<td align="right"><%=info.get("compensationOriginalAmt")==null?"0.00":WebAmountTest.getDecimalFormat2(FDCHelper.toBigDecimal(info.get("compensationOriginalAmt"),2))%></td> 
		<td align="right"><%=info.get("compensationAmt")==null?"0.00":WebAmountTest.getDecimalFormat2(FDCHelper.toBigDecimal(info.get("compensationAmt"),2))%></td> 
		<td align="right"><%=WebAmountTest.getDecimalFormat2(FDCHelper.toBigDecimal(FDCHelper.add(info.get("compensationAmt"),info.get("lstCompensationReqAmt")),2))%></td> 
		<td align="right"><%=info.get("lstCompensationPaidAmt")==null?"0.00":WebAmountTest.getDecimalFormat2(FDCHelper.toBigDecimal(info.get("lstCompensationPaidAmt"),2))%></td> 
	</tr>
<%
	Object[] arrays ;
	BigDecimal paidSum = FDCHelper.ZERO;
	BigDecimal reqSum = FDCHelper.ZERO;
	BigDecimal oriSum = FDCHelper.ZERO;
	BigDecimal amtSum = FDCHelper.ZERO;
	BigDecimal allReqSum = FDCHelper.ZERO;
	if(deductTypeColl != null && deductTypeColl.size()>0){
		int num = deductTypeColl.size()+1;
		for(int i = 0 ; i < deductTypeColl.size();i++ ){
			DeductTypeInfo deduct = deductTypeColl.get(i);
			arrays = (Object[])deductMap.get(deduct.getId().toString());
			for (int j = 0; j < 4; j++) {
				if (arrays[j] instanceof BigDecimal && ((BigDecimal) arrays[j]).compareTo(FDCHelper.ZERO) == 0) {
					arrays[j] = FDCHelper.ZERO;
				}
			}
			String deductName = deduct.getName();
			Object deductPaid = arrays[0];
			Object deductReq = arrays[1];
			Object deductOriAmt = arrays[3];
			Object deductAmt = arrays[2];
			paidSum = FDCHelper.add(paidSum,deductPaid);
			reqSum = FDCHelper.add(reqSum,deductReq);
			oriSum = FDCHelper.add(oriSum,deductOriAmt);
			amtSum = FDCHelper.add(amtSum,deductAmt);
			allReqSum = FDCHelper.add(allReqSum,FDCHelper.add(deductReq,deductAmt));
%>
	<tr bgcolor ="E8E8E3" >
		<%
			if(i == 0){
			%>
			<td rowspan=<%=num%>>应扣款项</td>
			<%
			}
		%>
		<td align="right"><%=deductName%></td>
		<td align="right"><%=WebAmountTest.getDecimalFormat2(FDCHelper.toBigDecimal(deductPaid,2))%></td>
		<td align="right"><%=WebAmountTest.getDecimalFormat2(FDCHelper.toBigDecimal(deductReq,2))%></td>
		<td align="right"><%=WebAmountTest.getDecimalFormat2(FDCHelper.toBigDecimal(deductOriAmt,2))%></td>
		<td align="right"><%=WebAmountTest.getDecimalFormat2(FDCHelper.toBigDecimal(deductAmt,2))%></td>
		<td align="right"><%=WebAmountTest.getDecimalFormat2(FDCHelper.toBigDecimal(FDCHelper.add(deductReq,deductAmt),2))%></td>
		<td align="right"><%=WebAmountTest.getDecimalFormat2(FDCHelper.toBigDecimal(deductPaid,2))%></td>
	</tr>
<%
	}
%>
	<tr bgcolor ="E8E8E3" >
		<td align="right">小计</td>
		<td align="right"><%=WebAmountTest.getDecimalFormat2(FDCHelper.toBigDecimal(paidSum,2))%></td>
		<td align="right"><%=WebAmountTest.getDecimalFormat2(FDCHelper.toBigDecimal(reqSum,2))%></td>
		<td align="right"><%=WebAmountTest.getDecimalFormat2(FDCHelper.toBigDecimal(oriSum,2))%></td>
		<td align="right"><%=WebAmountTest.getDecimalFormat2(FDCHelper.toBigDecimal(amtSum,2))%></td>
		<td align="right"><%=WebAmountTest.getDecimalFormat2(FDCHelper.toBigDecimal(allReqSum,2))%></td>
		<td align="right"><%=WebAmountTest.getDecimalFormat2(FDCHelper.toBigDecimal(paidSum,2))%></td>
	</tr>	
<%
	}
%>
	<tr bgcolor ="E8E8E3" >
		<td colspan="2">应扣甲供材款</td>
		<td align="right"><%=info.getLstAMatlAllPaidAmt()==null?"0.00":WebAmountTest.getDecimalFormat2(FDCHelper.toBigDecimal(info.getLstAMatlAllPaidAmt(),2))%></td>
		<td align="right"><%=info.getLstAMatlAllReqAmt()==null?"0.00":WebAmountTest.getDecimalFormat2(FDCHelper.toBigDecimal(info.getLstAMatlAllReqAmt(),2))%></td>
		<td align="right"><%=info.getPayPartAMatlOriAmt()==null?"0.00":WebAmountTest.getDecimalFormat2(FDCHelper.toBigDecimal(info.getPayPartAMatlOriAmt(),2))%></td>
		<td align="right"><%=info.getPayPartAMatlAmt()==null?"0.00":WebAmountTest.getDecimalFormat2(FDCHelper.toBigDecimal(info.getPayPartAMatlAmt(),2))%></td>
		<td align="right"><%=WebAmountTest.getDecimalFormat2(FDCHelper.toBigDecimal(FDCHelper.add(info.getLstAMatlAllReqAmt(),info.getPayPartAMatlAmt()),2))%></td>
		<td align="right"><%=info.getLstAMatlAllPaidAmt()==null?"0.00":WebAmountTest.getDecimalFormat2(FDCHelper.toBigDecimal(info.getLstAMatlAllPaidAmt(),2))%></td>
	</tr>
	<%
		BigDecimal lstPaid = FDCHelper.add(info.getLstPrjAllPaidAmt(),info.get("lstguerdonPaidAmt"));
							 lstPaid = FDCHelper.subtract(FDCHelper.subtract(FDCHelper.subtract(lstPaid,info.get("lstCompensationPaidAmt")),paidSum),info.getLstAMatlAllPaidAmt());
		BigDecimal lstReq = FDCHelper.add(info.getLstPrjAllReqAmt(),info.get("lstGuerdonReqAmt"));
							 lstReq = FDCHelper.subtract(FDCHelper.subtract(FDCHelper.subtract(lstReq,info.get("lstCompensationReqAmt")),reqSum),info.getLstAMatlAllReqAmt());
		BigDecimal lstOriAmt = FDCHelper.add(info.getProjectPriceInContractOri(),info.get("guerdonOriginalAmt"));
							 lstOriAmt = FDCHelper.subtract(FDCHelper.subtract(FDCHelper.subtract(lstOriAmt,info.get("compensationOriginalAmt")),oriSum),info.getPayPartAMatlOriAmt());
		BigDecimal lstAmt = FDCHelper.add(info.getProjectPriceInContract(),info.get("guerdonAmt"));
							 lstAmt = FDCHelper.subtract(FDCHelper.subtract(FDCHelper.subtract(lstAmt,info.get("compensationAmt")),amtSum),info.getPayPartAMatlAmt());
		BigDecimal lstAllReq = FDCHelper.add(info.getLstPrjAllReqAmt(),info.getProjectPriceInContract());
							 lstAllReq = FDCHelper.add(lstAllReq,FDCHelper.add(info.get("guerdonAmt"),info.get("lstGuerdonReqAmt")));
							 lstAllReq = FDCHelper.subtract(lstAllReq,FDCHelper.add(info.get("compensationAmt"),info.get("lstCompensationReqAmt")));
							 lstAllReq = FDCHelper.subtract(lstAllReq,allReqSum);
							 lstAllReq = FDCHelper.subtract(lstAllReq,FDCHelper.add(info.getLstAMatlAllReqAmt(),info.getPayPartAMatlAmt()));

	%>
	<tr bgcolor ="E8E8E3" >
		<td colspan="2">实付款</tr>
		<td align="right"><%=WebAmountTest.getDecimalFormat2(FDCHelper.toBigDecimal(lstPaid,2))%></td>
		<td align="right"><%=WebAmountTest.getDecimalFormat2(FDCHelper.toBigDecimal(lstReq,2))%></td>
		<td align="right"><%=WebAmountTest.getDecimalFormat2(FDCHelper.toBigDecimal(lstOriAmt,2))%></td>
		<td align="right"><%=WebAmountTest.getDecimalFormat2(FDCHelper.toBigDecimal(lstAmt,2))%></td>
		<td align="right"><%=WebAmountTest.getDecimalFormat2(FDCHelper.toBigDecimal(lstAllReq,2))%></td>
		<td align="right"><%=WebAmountTest.getDecimalFormat2(FDCHelper.toBigDecimal(lstPaid,2))%></td>
	</tr>
	<tr bgcolor ="E8E8E3" >
		<td colspan="7">余款</tr>
		<td align="right"><%=WebAmountTest.getDecimalFormat2(FDCHelper.toBigDecimal(FDCHelper.subtract(info.getLatestPrice(),info.getLstPrjAllPaidAmt()),2))%></td>
	</tr>
	<tr bgcolor ="E8E8E3" >
		<td>本期计划付款</td>
		<td align="right"><%=info.getCurPlannedPayment()==null?"0.00":WebAmountTest.getDecimalFormat2(FDCHelper.toBigDecimal(info.getCurPlannedPayment(),2))%></td>
		<td>本期欠付款</td>
		<td align="right"><%=info.getCurBackPay()==null?"0.00":WebAmountTest.getDecimalFormat2(FDCHelper.toBigDecimal(info.getCurBackPay(),2))%></td>
		<td></td>
		<td colspan="3"></td>
	</tr>
	<tr bgcolor ="E8E8E3" >
		<td>本次申请%</td>
		<td align="right"><%=WebAmountTest.getDecimalFormat2(FDCHelper.toBigDecimal(FDCHelper.divide(lstAmt.multiply(new BigDecimal("100")),info.getLatestPrice()),2))%></td>
		<td>累计申请%</td>
		<td align="right"><%=WebAmountTest.getDecimalFormat2(FDCHelper.toBigDecimal(FDCHelper.divide(lstAllReq.multiply(new BigDecimal("100")),info.getLatestPrice()),2))%></td>
		<td>形象进度%</td>
		<td colspan="3" align="right"><%=info.getImageSchedule()==null?"0.00":WebAmountTest.getDecimalFormat2(FDCHelper.toBigDecimal(info.getImageSchedule(),2))%></td>
	</tr>
</table>
<!-- 
<a href="/easweb/winlet/attachment/webAttachment.jsf?bosid=<%=billID%>" onClick="window.open(this.href,'', 'height=350,width=500,toolbar=no,location=no,status=no,menubar=no');return false">合同附件</a>
 -->
  <table width="96%" border="0" align="center" cellpadding="0" cellspacing="0">
 <tr>
 	<td nowrap style="BORDER-bottom: #D8DEEA 1px solid;">
 		 <b>
 		 <a href="/easweb/fdc/contract/viewBudgetInfo.jsp?billId=<%=java.net.URLEncoder.encode(billID)%>" onClick="window.open(this.href,'', 'height=400,width=670,toolbar=no,location=no,status=no,menubar=no,top=200,left=300,scrollbars=yes,resizable=yes');return false">查看预算</a>
 		 </b>
 	</td>
 </tr>
 </table>
 
</body>
</html>
