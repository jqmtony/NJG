<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page errorPage="/common/include/errorPrehandle.jsp" %>
<%@ include file="/html/common/init.jsp"%>
<%@ page import="java.util.ArrayList,
		          com.kingdee.bos.Context,
		          com.kingdee.eas.cp.common.web.util.WebContextUtil,
				  com.kingdee.bos.dao.ormapping.ObjectUuidPK,
				  com.kingdee.eas.base.attachment.BoAttchAssoFactory,
				  com.kingdee.eas.base.attachment.IBoAttchAsso,
				  com.kingdee.eas.base.attachment.AttachmentFactory,
				  com.kingdee.eas.base.attachment.IAttachment,
				  com.kingdee.eas.base.attachment.AttachmentInfo,
				  com.kingdee.jdbc.rowset.IRowSet,
				  com.kingdee.eas.cp.eip.mc.web.WorkFlowUtil,
				  java.text.SimpleDateFormat,
				  com.kingdee.bos.BOSException,
				  com.kingdee.bos.Context,
				  com.kingdee.bos.dao.ormapping.ObjectUuidPK,
				  com.kingdee.bos.metadata.entity.SelectorItemCollection,
				  com.kingdee.eas.common.EASBizException,
				  com.kingdee.eas.fdc.contract.ContractBillFactory,
				  com.kingdee.eas.fdc.contract.ContractBillInfo,
				  com.kingdee.eas.fdc.contract.ContractBillEntryInfo,
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
<title>合同</title>
<link href="css/index.css" rel="stylesheet" type="text/css">
<%
	String billID = request.getParameter("billID");
	billID="hLXt77bARi+s0vsHa88z9Q1t0fQ=";
	Context ctx = WebContextUtil.getEasContext(request);
	StringBuffer sb = new StringBuffer();
	sb.append("select * , proposerDep.* ");
	sb.append(" where id= '");
	sb.append(billID.trim());
	sb.append("'");
	//获取对应的单据信息
	SelectorItemCollection selector=new SelectorItemCollection();
		selector.add("*");
		selector.add("entrys.*");
		selector.add("curProject.displayName");
		selector.add("currency.name");
		selector.add("orgUnit.displayName");
		selector.add("landDeveloper.name");
		selector.add("partB.name");
		selector.add("partC.name");
		selector.add("respDept.name");
		selector.add("respPerson.name");
		selector.add("contractType.name");
		selector.add("partC.name");
		selector.add("creator.name");
		selector.add("lowestPriceUnit.name");
		selector.add("lowerPriceUnit.name");
		selector.add("higherPriceUnit.name");
		selector.add("highestPriceUni.name");
		selector.add("winUnit.name");
		selector.add("middlePriceUnit.name");
		selector.add("inviteType.name");
		
	String contractId=billID;
	ContractBillInfo info = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(contractId), selector);
	//获取审批信息
	IRowSet rowSet_a = WorkFlowUtil.getHostoryData(billID,ctx);
	IRowSet rowSet_a1 = rowSet_a.createCopy();
	SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy 年 MM 月 dd 日");
%>
</head>
<body>
<br>
<div align="center" style="FONT-SIZE:18px; FONT-FAMILY:  "Arial"; color: 000000;">合同</div>
<table >
  <tr>
	<td height="1px"></td>
  </tr>
</table>
<div align="center" ><%= bartDateFormat.format(info.getCreateTime()) %></div>
<table width="96%" border="0" align="center" cellpadding="0" cellspacing="0" style="margin-top:8px;">
  <tr>
    <td bgcolor="BDD2EC"><img name="" src="" width="1" height="1" alt=""></td>
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
          <td width="13%" >合同类型： </td>
          <td width="23%" > 
            <%=info.getContractType().getName()%>
          </td>
        </tr>
        <tr bgcolor="#FFFFFF"> <!--第 2  行-->
          <td width="14%">合同编号：</td>
          <td width="20%"> 
            <%= info.getNumber() %>
            &nbsp; </td>
          <td>合同名称：</td>
          <td> 
            <%= info.getRespDept().getName()%>
          </td>
          <td width="13%" >合同性质： </td>
          <td width="23%" > 
            <%=info.getContractPropert().getAlias()%>
          </td>
        </tr>
        <tr bgcolor="#FFFFFF"> <!--第 3  行-->
          <td width="14%">甲方：</td>
          <td width="20%"> 
            <%= info.getLandDeveloper().getName() %>
            &nbsp; </td>
          <td>乙方：</td>
          <td> 
            <%= info.getPartB().getName()%>
          </td>
          <td width="13%" >丙方： </td>
          <td width="23%" > 
            <%=info.getPartC()==null?"":info.getPartC().getName()%>
          </td>
        </tr>
        <tr bgcolor="#FFFFFF"> <!--第 4  行-->
          <td width="14%">签约日期：</td>
          <td width="20%"> 
            <%= info.getSignDate() %>
            &nbsp; </td>
          <td>币别：</td>
          <td> 
            <%= info.getCurrency().getName()%>
          </td>
          <td width="13%" >汇率： </td>
          <td width="23%" > 
            <%=info.getExRate()%>
          </td>
        </tr>
        <tr bgcolor="#FFFFFF"> <!--第 5  行-->
          <td width="14%">责任部门：</td>
          <td width="20%"> 
            <%= info.getCreator().getName() %>
            &nbsp; </td>
          <td>原币金额：</td>
          <td> 
            <%= info.getOriginalAmount()%>
          </td>
          <td width="13%" >本币金额： </td>
          <td width="23%" > 
            <%=info.getAmount()%>
          </td>
        </tr>
        <tr bgcolor="#FFFFFF"> <!--第 6  行-->
          <td width="14%">责任人：</td>
          <td width="20%"> 
            <%= info.getRespPerson().getName() %>
            &nbsp; </td>
          <td>形成方式：</td>
          <td> 
            <%= info.getContractSource().getAlias()%>
          </td>
          <td width="13%" >造价性质： </td>
          <td width="23%" > 
            <%=info.getCostProperty().getAlias()%>
          </td>
        </tr>
        <tr bgcolor="#FFFFFF"> <!--第 7  行-->
          <td width="14%">制单人：</td>
          <td width="20%"> 
            <%= info.getCreator().getName() %>
            &nbsp; </td>
          <td>制单日期：</td>
          <td> 
            <%= info.getCreateTime()%>
          </td>
          <td width="13%" >是否进入动态成本： </td>
          <td width="23%" > 
            <%=info.isIsCoseSplit()?"是":"否"%>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<br>
<!--分录-->
<table width="96%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td nowrap style="BORDER-bottom: #D8DEEA 1px solid;"><div class="content" style="cursor:hand;"onClick="expandIt('conEntry','conEntrys2')">
	<img src="images/dot_arrow_close.gif" width="9" height="9" id="conEntry"  align="absmiddle"> 
        <b>合同详细信息</b> 
      </div>
	 </td>
  </tr>
</table>
<table width="96%" border="0" align="center" cellpadding="3" cellspacing="0"  id="conEntrys2" style="display:none">
  <tr>
    <td>
		详细信息
	</td>
	<td>
		内容
	</td>
    <td>
		描述
	</td>
  </tr>
  <% for(int i=0;i<info.getEntrys().size();i++){
	  ContractBillEntryInfo entry=info.getEntrys().get(i);
	  %>
	<tr>
	<td>
		<%=entry.getDetail()==null?"":entry.getDetail()%>
	</td>
	<td>
		<%=entry.getContent()==null?"":entry.getContent()%>
	</td>
	<td>
		<%=entry.getDesc()==null?"":entry.getDesc()%>
	</td>
	</tr>
  <%}%>
</table>

<!--分录-->
<table width="96%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
      <td nowrap style="BORDER-bottom: #D8DEEA 1px solid;"><div class="content" style="cursor:hand;"onClick="expandIt('cost','costEntrys')">
	<img src="images/dot_arrow_close.gif" width="9" height="9" id="cost"  align="absmiddle"> 
        <b>成本信息</b> 
      </div>
     </td>
  </tr>
</table>
<!--成本信息-->
<table width="96%" border="0" align="center" cellpadding="3" cellspacing="0"  id="costEntrys" style="display:none">
  <tr>
    <td>
		test1
	</td>
	<td>
		test1
	</td>
  </tr>
    <tr>
    <td>
		test1
	</td>
	<td>
		test1
	</td>
  </tr>
</table>

<!--分录-->
<table width="96%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td nowrap style="BORDER-bottom: #D8DEEA 1px solid;"><div class="content" style="cursor:hand;"onClick="expandIt('otherCon','otherConEntrys')">
	<img src="images/dot_arrow_close.gif" width="9" height="9" id="otherCon"  align="absmiddle"> 
        <b>相关合同</b>  
      </div>
	 </td>
  </tr>
</table>
<!--相关合同信息-->
<table width="96%" border="0" align="center" cellpadding="3" cellspacing="0"  id="otherConEntrys" style="display:none">
  <tr>
    <td>
		test1
	</td>
	<td>
		test1
	</td>
  </tr>
    <tr>
    <td>
		test1
	</td>
	<td>
		test1
	</td>
  </tr>
</table>
<!--分录-->
<table width="96%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td nowrap style="BORDER-bottom: #D8DEEA 1px solid;"><div class="content" style="cursor:hand;"onClick="expandIt('invite','inviteEntrys')">
	<img src="images/dot_arrow_close.gif" width="9" height="9" id="invite"  align="absmiddle"> 
        <b>招标/议标/战略合同信息</b> 
      </div>
	 </td>
  </tr>
</table>
<!--招标/议标/战略合同信息-->
<table width="96%" border="0" align="center" cellpadding="1" cellspacing="0"  id="inviteEntrys" style="display:none">
	<%
			ContractSourceEnum  contractSource=info.getContractSource();
			//

			if (contractSource == ContractSourceEnum.TRUST) {
				//委托
			} else if (contractSource == ContractSourceEnum.INVITE) { 
				//招标
%>		
				 <tr>
					<td>	<!--第0行-->
						
					</td>
					<td>
						投标价
					</td>
					<td>
						投标单位
					</td>
					<td>
						
					</td>
					<td>
						
					</td>
				</tr>
				 <tr>
					<td>	<!--第1行-->
						最低投标单位
					</td>
					<td>
						<%=info.getLowestPrice()%>
					</td>
					<td>
						<%=info.getLowestPriceUnit()==null?"":info.getLowestPriceUnit().getName()%>
					</td>
					<td>
						中标价
					</td>
					<td>
						<%=info.getWinPrice()%>
					</td>
				</tr>
								<tr>
					<td>	<!--第2行-->
						次低投标单位
					</td>
					<td>
						<%=info.getLowerPrice()%>
					</td>
					<td>
						<%=info.getLowerPriceUnit()==null?"":info.getLowerPriceUnit().getName()%>
					</td>
					<td>
						中标单位
					</td>
					<td>
						<%=info.getWinUnit()==null?"":info.getWinUnit().getName()%>
					</td>
				</tr>
				<tr>
					<td>	<!--第3行-->
						中间投标单位
					</td>
					<td>
						<%=info.getMiddlePrice()%>
					</td>
					<td>
						<%=info.getMiddlePriceUnit()==null?"":info.getMiddlePriceUnit().getName()%>
					</td>
					<td>
						单位数量
					</td>
					<td>
						<%=info.getQuantity()%>
					</td>
				</tr>
				<tr>
					<td>	<!--第4行-->
						次高投标单位
					</td>
					<td>
						<%=info.getHigherPrice()%>
					</td>
					<td>
						<%=info.getHigherPriceUnit()==null?"":info.getHigherPriceUnit().getName()%>
					</td>
					<td>
						招标方式
					</td>
					<td>
						<%=info.getInviteType()==null?"":info.getInviteType().getName()%>
					</td>
				</tr>
				<tr>
					<td>	<!--第5行-->
						最高投标单位
					</td>
					<td>
						<%=info.getHighestPrice()%>
					</td>
					<td>
						<%=info.getHighestPriceUni()==null?"":info.getHighestPriceUni().getName()%>
					</td>
					<td>
						招标文件号
					</td>
					<td>
						<%=info.getFileNo()%>
					</td>
				</tr>

<%			}else if (contractSource == ContractSourceEnum.DISCUSS) {//议标%>　
				<tr>
					<td>	<!--第1行-->
						底价
					</td>
					<td>
						<%=info.getBasePrice()%>
					</td>
					<td>
						中标价
					</td>
					<td>
						<%=info.getWinPrice()%>
					</td>
				</tr>
				<tr>
					<td>	<!--第1行-->
						二价标
					</td>
					<td>
						<%=info.getSecondPrice()%>
					</td>
					<td>
						中标单位
					</td>
					<td>
						<%=info.getWinUnit()==null?"":info.getWinUnit().getName()%>
					</td>
				</tr>
				<tr>
					<td>	<!--第1行-->
						备注
					</td>
					<td>
						<%=info.getRemark()%>
					</td>
					<td>
						
					</td>
					<td>
						
					</td>
				</tr>
<%			}else if (contractSource == ContractSourceEnum.COOP) {//战略%>
				<tr>
					<td>	<!--第1行-->
						战备合作级别
					</td>
					<td>
						<%=info.getCoopLevel().getAlias()%>
					</td>
				</tr>
				<tr>
					<td>	<!--第2行-->
						计价方式
					</td>
					<td>
						<%=info.getPriceType().getAlias()%>
					</td>
				</tr>
<%			}%>
</table>
</body>
</html>
