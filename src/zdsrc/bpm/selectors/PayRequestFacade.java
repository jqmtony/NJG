package com.kingdee.eas.bpm.selectors;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.basedata.org.AdminOrgUnitFactory;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.bpm.BPMLogFactory;
import com.kingdee.eas.bpm.BPMLogInfo;
import com.kingdee.eas.bpm.BillBaseSelector;
import com.kingdee.eas.bpm.common.StringUtilBPM;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.ContractTypeCollection;
import com.kingdee.eas.fdc.basedata.ContractTypeFactory;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.PaymentTypeFactory;
import com.kingdee.eas.fdc.basedata.PaymentTypeInfo;
import com.kingdee.eas.fdc.basedata.app.ContractTypeController;
import com.kingdee.eas.fdc.contract.ContractBaseDataCollection;
import com.kingdee.eas.fdc.contract.ContractBaseDataFactory;
import com.kingdee.eas.fdc.contract.ContractBaseDataInfo;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillBgEntryCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillBgEntryFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillBgEntryInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillEntryFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillEntryInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.ma.bg.BgItemCollection;
import com.kingdee.eas.ma.bg.BgItemFactory;
import com.kingdee.eas.ma.bg.BgItemInfo;
import com.kingdee.eas.ma.bg.client.BgItemF7Selector;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public class PayRequestFacade implements BillBaseSelector {
	
	public String[] SubmitResult(Context ctx, String strBSID,
			IObjectValue billInfo, boolean success, int procInstID, String procURL,
			String strMessage) {
		PayRequestBillInfo Info = (PayRequestBillInfo)billInfo;
    	String[] str = new String[3];
    	str[0] = "Y";
		try {
			try{
				Info = PayRequestBillFactory.getLocalInstance(ctx).getPayRequestBillInfo(new ObjectUuidPK(Info.getId()),getSelectors());
			}catch (EASBizException e) {
				str[0] = "N";
				str[2] = "根据单据getSelectors获取对象数据，请检查getSelectors方法中属性是否正确,并查看服务器log日志！";
				e.printStackTrace();
			}
			try{
				Info.setState(FDCBillStateEnum.AUDITTING);
//				String sql = " update t_con_payrequestbill set fState='"+Info.getState().getValue()+"'" +
//						", fDescription='"+procURL+"' " +
//						", FSourceFunction='"+procInstID+"' where fid='"+Info.getId()+"'";
				
				String sql = " update t_con_payrequestbill set fState='"+Info.getState().getValue()+"'" +
				", FProcess='"+procURL+"' " +
				", FSourceFunction='"+procInstID+"' where fid='"+Info.getId()+"'";
				FDCSQLBuilder bu = new FDCSQLBuilder(ctx);
				bu.appendSql(sql);
				bu.executeUpdate(ctx);
			}
			catch (BOSException e) {
				str[0] = "N";
				str[2] = "根据单据state值更新状态sql失败，请检查getState方法是否有值,并查看服务器log日志！";
				e.printStackTrace();
			}
		}catch (Exception e) {
			str[0] = "N";
			str[2] = "其他异常，请查看服务器log日志！";
			e.printStackTrace();
		}finally{
			BPMLogInfo log = new BPMLogInfo();
			try {
				log.setLogDate(new Date());
				log.setName("合同单据ID："+Info.getId()+"; 编号："+Info.getNumber());
				log.setDescription("BPM结果："+success+"; EAS结果:"+str[0]+"; 错误信息:"+str[1]+str[2]);
				log.setSimpleName("BPM流程号："+procInstID+";流程URL:"+procURL);
				log.setBeizhu("调用接口方法：_SubmitResult");
				BPMLogFactory.getLocalInstance(ctx).save(log);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return str;
	}
	
	
	public String[] ApproveClose(Context ctx, String strBSID, IObjectValue billInfo,
			int procInstID, String processInstanceResult, String strComment,
			Date dtTime) {
		PayRequestBillInfo Info = (PayRequestBillInfo)billInfo;
    	String[] str = new String[3];
    	str[0] = "Y";
    	String sql="";
		try {
			try{
				Info = PayRequestBillFactory.getLocalInstance(ctx).getPayRequestBillInfo(new ObjectUuidPK(Info.getId()),getSelectors());
			}catch (EASBizException e) {
				str[2] = "根据单据getSelectors获取对象数据，请检查getSelectors方法中属性是否正确,并查看服务器log日志！";
				e.printStackTrace();
			}
			try{
				if("1".equals(processInstanceResult)){
					if(FDCBillStateEnum.AUDITTING.equals(Info.getState()))
					{
						Info.setState(FDCBillStateEnum.SUBMITTED);
						CompanyOrgUnitInfo company = CompanyOrgUnitFactory.getLocalInstance(ctx).getCompanyOrgUnitInfo(new ObjectUuidPK(Info.getCU().getId()));
						AdminOrgUnitInfo admin=AdminOrgUnitFactory.getLocalInstance(ctx).getAdminOrgUnitInfo(new ObjectUuidPK(Info.getCU().getId()));                            
						ContextUtil.setCurrentFIUnit(ctx, company);
						ContextUtil.setCurrentOrgUnit(ctx, admin);
						PayRequestBillFactory.getLocalInstance(ctx).audit(Info.getId());
						Info.setState(FDCBillStateEnum.AUDITTED); 
						
					}
					else{
						str[2] = "审批通过失败，该记录状态不是审批中！";
						str[0] = "N";
					}
				}
				if("0".equals(processInstanceResult)){
					if(FDCBillStateEnum.AUDITTING.equals(Info.getState()))
						//Info.setState(FDCBillStateEnum.INVALID);
						Info.setState(FDCBillStateEnum.SAVED);
					else{
						str[2] = "审批不通过失败，该记录状态不是审批中！";
						str[0] = "N";
					}
				}
				if("2".equals(processInstanceResult)){
					if(FDCBillStateEnum.AUDITTING.equals(Info.getState()))
						Info.setState(FDCBillStateEnum.SAVED);
					else{
						str[2] = "审批打回失败，该记录状态不是审批中！";
						str[0] = "N";
					}
				}
				if("3".equals(processInstanceResult)){
					if(FDCBillStateEnum.AUDITTING.equals(Info.getState()))
					{
						Info.setState(FDCBillStateEnum.SAVED);
						//sql = " update t_con_payrequestbill set fDescription='' where fid='"+Info.getId()+"'";
						sql = " update t_con_payrequestbill set FProcess='' where fid='"+Info.getId()+"'";
						FDCSQLBuilder bu = new FDCSQLBuilder(ctx);
						bu.appendSql(sql);
						bu.executeUpdate(ctx);
					}
					else{
						str[2] = "撤销失败，该记录状态不是审批中！";
						str[0] = "N";
					}
				}
				sql = " update t_con_payrequestbill set fState='"+Info.getState().getValue()+"' where fid='"+Info.getId()+"'";
				FDCSQLBuilder bu = new FDCSQLBuilder(ctx);
				bu.appendSql(sql);
				bu.executeUpdate(ctx);
			}
			catch (BOSException e) {
				str[2] = "根据单据state值更新状态sql失败，请检查getState方法是否有值,并查看服务器log日志！";
				e.printStackTrace();
			}
		}catch (Exception e) {
			str[2] = "其他异常，请查看服务器log日志！";
			e.printStackTrace();
		}finally{
			BPMLogInfo log = new BPMLogInfo();
			try {
				log.setLogDate(new Date());
				log.setName("合同单据ID："+Info.getId()+"; 编号："+Info.getNumber());
				log.setDescription("BPM结果："+processInstanceResult+"; EAS结果:"+str[0]+"; 错误信息"+str[1]+str[2]);
				log.setSimpleName("BPM流程号："+procInstID+";BPM反馈信息:"+strComment);
				log.setBeizhu("调用接口方法：ApproveClose");
				BPMLogFactory.getLocalInstance(ctx).save(log);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return str;
	}


	public String[] GetbillInfo(Context ctx, String strBSID, IObjectValue billInfo) {
		PayRequestBillInfo Info = (PayRequestBillInfo)billInfo;
    	String[] str = new String[3];
    	str[0] = "Y";
    	StringBuffer xml = new StringBuffer();
		try {
			try{
				Info = PayRequestBillFactory.getLocalInstance(ctx).getPayRequestBillInfo(new ObjectUuidPK(Info.getId()),getSelectors());
			}catch (EASBizException e) {
				str[2] = "根据单据getSelectors获取对象数据，请检查getSelectors方法中属性是否正确,并查看服务器log日志！";
				e.printStackTrace();
			}
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			xml.append("<DATA>\n"); 
			xml.append("<OrgName>"+StringUtilBPM.isNULl(Info.getOrgUnit().getDisplayName())+"</OrgName>\n");
			if(Info.getUseDepartment()!=null)
			xml.append("<Department>"+StringUtilBPM.isNULl(Info.getUseDepartment().getName())+"</Department>\n");
			xml.append("<ApplyDate>"+dateFormat.format(Info.getCreateTime())+"</ApplyDate>\n");
			xml.append("<Applicant>"+StringUtilBPM.isNULl(Info.getCreator().getName())+"</Applicant>\n");
			//xml.append("<OrgCode>"+StringUtilBPM.isNULl(Info.getOrgUnit().getNumber().split("-")[0])+ "</OrgCode>\n");
			xml.append("<Position>合约部经理</Position>\n");
			xml.append("<Topic>"+StringUtilBPM.isNULl(Info.getUsage())+"</Topic>\n");  //标题为空
		    if(Info.getOrgUnit()!=null)
			xml.append("<orgunit>"+StringUtilBPM.isNULl(Info.getOrgUnit().getName())+"</orgunit>\n");
		    if(Info.getCurProject()!=null)
			xml.append("<curProject>"+StringUtilBPM.isNULl(Info.getCurProject().getDisplayName())+"</curProject>\n");
			xml.append("<contractNumber>"+StringUtilBPM.isNULl(Info.getContractNo())+"</contractNumber>\n");
			xml.append("<contractName>"+StringUtilBPM.isNULl(Info.getContractName())+"</contractName>\n");
			if(Info.getUseDepartment()!=null)
			xml.append("<useDepartment>"+StringUtilBPM.isNULl(Info.getUseDepartment().getName())+"</useDepartment>\n");
			xml.append("<bizdate>"+Info.getBookedDate()+"</bizdate>\n");
			xml.append("<number>"+StringUtilBPM.isNULl(Info.getNumber())+"</number>\n");
			xml.append("<payDate>"+dateFormat.format(Info.getPayDate())+"</payDate>\n");//付款日期
			if(Info.getPeriod()!=null)
			xml.append("<period>"+Info.getPeriod().getNumber()+"</period>\n");//申请期间
			if(Info.getIsDifferPlace()!=null)
			xml.append("<isdifferplace>"+StringUtilBPM.isNULl(Info.getIsDifferPlace().getAlias())+"</isdifferplace>\n");//同城异地
			if(Info.getPaymentType()!=null)
			xml.append("<PaymentType>"+StringUtilBPM.isNULl(Info.getPaymentType().getName())+"</PaymentType>\n");//付款类型
			if(Info.getSettlementType()!=null)
			xml.append("<settlementType>"+StringUtilBPM.isNULl(Info.getSettlementType().getName())+"</settlementType>\n");//合同类型
			if(Info.getSupplier()!=null)
			xml.append("<supplier>"+StringUtilBPM.isNULl(Info.getSupplier().getName())+"</supplier>\n");//收款单位
			
			xml.append("<recBank>"+StringUtilBPM.isNULl(Info.getRecBank())+"</recBank>\n");//收款银行
			xml.append("<recAccount>"+StringUtilBPM.isNULl(Info.getRecAccount())+"</recAccount>\n");//收款账号
			if(Info.getRealSupplier()!=null)
			xml.append("<realSupplier>"+StringUtilBPM.isNULl(Info.getRealSupplier().getName())+"</realSupplier>\n");//实际收款单位
			xml.append("<Desc>"+StringUtilBPM.isNULl(Info.getDescription())+"</Desc>\n");// 摘要
			xml.append("<usage>"+StringUtilBPM.isNULl(Info.getUsage())+"</usage>\n");//用途
   
			xml.append("<currency>"+StringUtilBPM.isNULl(Info.getCurrency().getName())+"</currency>\n");//币别
			xml.append("<exchangeRate>"+Info.getExchangeRate()+"</exchangeRate>\n");//汇率
			xml.append("<originalAmount>"+Info.getOriginalAmount()+"</originalAmount>\n");//原币金额--
			xml.append("<amount>"+Info.getAmount()+"</amount>\n");//本币金额
			if(Info.getInvoiceAmt()!=null)
			xml.append("<invoiceAmt>"+Info.getInvoiceAmt()+"</invoiceAmt>\n");//发票金额
			else
			{
				xml.append("<invoiceAmt>0</invoiceAmt>\n");//发票金额
			}
			xml.append("<allinvoiceAmt>"+Info.getAllInvoiceAmt()+"</allinvoiceAmt>\n");//累计发票金额
			//xml.append("<amount>"+Info.getAmount()+"</amount>\n");//本币金额
			if(Info.getGrtAmount()!=null)
			xml.append("<grtAmount>"+Info.getGrtAmount()+"</grtAmount>\n");//本币金额
			else
			{
				xml.append("<grtAmount>0</grtAmount>\n");//本币金额
			}
			xml.append("<capitalAmount>"+Info.getCapitalAmount()+"</capitalAmount>\n");//大写金额
			if(false==Info.isIsRespite())
			xml.append("<isRespite>否</isRespite>\n");//是否加急
			else
			{
				xml.append("<isRespite>是</isRespite>\n");//是否加急
			}
			xml.append("<Process>"+StringUtilBPM.isNULl(Info.getProcess())+"</Process>\n");
			xml.append("<payTimes>"+Info.getPayTimes()+"</payTimes>\n");
			xml.append("<DepPlanState>"+Info.getDepPlanState()+"</DepPlanState>\n");
//			if(Info.getDepPlanState()==null)
//			{
//				xml.append("<DepPlanState>无计划付款</DepPlanState>\n");
//			}
//			else if(Info.getDepPlanState().getName()=="0")
//			{
//				xml.append("<DepPlanState>无计划付款</DepPlanState>\n");	
//			}
//			else if(Info.getDepPlanState().getName()=="1")
//			{
//				xml.append("<DepPlanState>计划内付款</DepPlanState>\n");	
//			}
//			else if(Info.getDepPlanState().getName()=="2")
//			{
//				xml.append("<DepPlanState>超计划付款</DepPlanState>\n");	
//			}
			if(false==Info.isIsPay())
			xml.append("<IsPay>否</IsPay>\n");//是否提交付款
			else
			{
			 xml.append("<IsPay>是</IsPay>\n");//是否提交付款
			}
			if(Info.getPaymentProportion()!=null)//进度款付款比例
				xml.append("<paymentProportion>"+Info.getPaymentProportion()+"</paymentProportion>\n");
			if(Info.getCompletePrjAmt()!=null)//本期完成工程量
				xml.append("<completePrjAmt>"+Info.getCompletePrjAmt()+"</completePrjAmt>\n");
			if(Info.getTotalSettlePrice()!=null)//已实现产值
				xml.append("<TotalSettlePrice>"+Info.getTotalSettlePrice()+"</TotalSettlePrice>\n");
				xml.append("<invoiceNumber>"+StringUtilBPM.isNULl(Info.getInvoiceNumber())+"</invoiceNumber>\n");//发票号
		
			if(Info.getInvoiceOriAmt()!=null)//发票金额原币
				xml.append("<invoiceOriAmt>"+Info.getInvoiceOriAmt()+"</invoiceOriAmt>\n");
			else
			{
				xml.append("<invoiceOriAmt>0</invoiceOriAmt>\n");
			}
			if(Info.getInvoiceDate()!=null)//开票日期
				xml.append("<invoiceDate>"+dateFormat.format(Info.getInvoiceDate())+"</invoiceDate>\n");
			if(Info.getCompletePrjAmt()!=null)//累计已完成工程量
				xml.append("<AllCompletePrjAmt>"+Info.getCompletePrjAmt()+"</AllCompletePrjAmt>\n");
			if(Info.getPaymentProportion()!=null)//累计应付付款比例
				xml.append("<AllPaymentProportion>"+Info.getPaymentProportion()+"</AllPaymentProportion>\n");
			if(Info.getAllInvoiceOriAmt()!=null)//累计发票原币/本币
				xml.append("<allinvoiceOriAmt>"+Info.getAllInvoiceOriAmt()+"</allinvoiceOriAmt>\n");
			if(Info.getPlanHasCon()!=null)
			xml.append("<PlanHasCon>"+Info.getPlanHasCon().getName()+"</PlanHasCon>\n");//预算项目
			if(Info.getMoneyDesc()!=null)
				xml.append("<Remarks>"+Info.getMoneyDesc()+"</Remarks>\n");//备注
			
			if(Info.getContractId()!=null)
			{
				String sql="select sum(pay.FAmount) SFamount  from t_con_payrequestbill pay left join ";
				sql+="t_con_contractbill con on pay.FContractId=con.fid where pay.fstate!='1SAVED' ";
				sql+="and pay.fcontractid='"+Info.getContractId()+"'";
				FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
	    		builder.appendSql(sql);
                IRowSet Rowset=builder.executeQuery();
                if(Rowset.size()==1)
                {
                  Rowset.next();  
                  xml.append("<allAmount>" +FDCHelper.toBigDecimal(Rowset.getBigDecimal("SFamount")) + "</allAmount>\n");//合同已付款金额
                }
                 builder.clear();
			}
			else
			{
				xml.append("<allAmount>0</allAmount>\n");//合同已付款金额
			}
			
			
			
			  EntityViewInfo Myavevi = new EntityViewInfo();
		      FilterInfo Myavfilter = new FilterInfo();
		      Myavfilter.getFilterItems().add(new FilterItemInfo("id",Info.getContractId(),CompareType.EQUALS));
		      Myavevi.setFilter(Myavfilter);
		      ContractBillCollection con=ContractBillFactory.getLocalInstance(ctx).getContractBillCollection(Myavevi);
		      if(con.size()>0)
		      {
		    	  for(int a=0;a<con.size();a++)
		    	  {
		    		  ContractBillInfo conInfo=ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(con.get(a).getId()));
		    		  xml.append("<contractTypeName>"+conInfo.getContractType().getName()+"</contractTypeName>\n");//合同类型
		    		  xml.append("<HTamount>"+conInfo.getAmount()+"</HTamount>\n");//合同金额
		    	  }
		      }
		      
		      xml.append("<CostedName>"+Info.getCostedCompany().getName()+"</CostedName>\n");//合同类型
		      xml.append("<OrgCode>"+Info.getCostedCompany().getNumber()+"</OrgCode>\n");//合同类型
		      xml.append("<CostedDept>"+Info.getCostedDept().getName()+"</CostedDept>\n");//合同类型
		      
		      
			  int control=1;
				  EntityViewInfo Myavevi2 = new EntityViewInfo();
			      FilterInfo Myavfilter2 = new FilterInfo();
			      Myavfilter2.getFilterItems().add(new FilterItemInfo("head",Info.getId(),CompareType.EQUALS));
			      Myavevi2.setFilter(Myavfilter2);
			      PayRequestBillBgEntryCollection contractBasecoll=PayRequestBillBgEntryFactory.getLocalInstance(ctx).getPayRequestBillBgEntryCollection(Myavevi2);
				  if(contractBasecoll.size()>0)
			  {
				  xml.append("<billEntries>\n");
				  for(int ab=0;ab<contractBasecoll.size();ab++)
				  {
					  PayRequestBillBgEntryInfo Conbaseinfo=PayRequestBillBgEntryFactory.getLocalInstance(ctx).getPayRequestBillBgEntryInfo(new ObjectUuidPK(contractBasecoll.get(ab).getId()));
					  xml.append("<item>\n");
					  String sql="select FName_L2 from T_BG_BgItem where fid='"+Conbaseinfo.getBgItem().getId()+"'";
					  FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		    		  builder.appendSql(sql);
	                  IRowSet Rowset=builder.executeQuery();
	                  if(Rowset.size()==1)
	                  {
	                   Rowset.next();  
	                   xml.append("<BgeItem>"+Rowset.getString("FName_L2")+"</BgeItem>\n");//预算费用
	                  }
	                  builder.clear();
					  
	                  String sql2="select FName_l2 from T_BD_Currency where fid='"+Conbaseinfo.getCurrency().getId()+"'";
					  FDCSQLBuilder builders=new FDCSQLBuilder(ctx);
		    		  builders.appendSql(sql2);
	                  IRowSet Rowsets=builders.executeQuery();
	                  if(Rowsets.size()==1)
	                  {
	                   Rowsets.next();  
	                   xml.append("<BgeCurrency>"+Rowsets.getString("FName_L2")+"</BgeCurrency>\n");//币别
	                  }
	                  builders.clear();
					  xml.append("<BgeRate>"+Conbaseinfo.getRate()+"</BgeRate>\n");// 汇率  
					  xml.append("<BgeAmount>"+Conbaseinfo.getAmount()+"</BgeAmount>\n");//  原币金额 
					  xml.append("<BgeRequestAmount>"+Conbaseinfo.getRequestAmount()+"</BgeRequestAmount>\n");//本币金额
					  xml.append("<BgeBalance>"+Conbaseinfo.getBgBalance()+"</BgeBalance>\n");//预算余额 
					  xml.append("<BgeRemark>"+StringUtilBPM.isNULl(Conbaseinfo.getRemark())+"</BgeRemark>\n");//备注
//					  if(Conbaseinfo.getIsHasBill().equals("0"))
//					  {
//						  xml.append("<IsHasBill>否</IsHasBill>\n");//是否有单据  
//					  }
//					  else
//					  {
//						  xml.append("<IsHasBill>是</IsHasBill>\n");//是否有单据
//					  }
					  if(Conbaseinfo.getIsHasBill().equals(""))
					  {
						  xml.append("<IsHasBill></IsHasBill>\n");//是否有单据
					  }
					  xml.append("<IsHasBill>"+Conbaseinfo.getIsHasBill()+"</IsHasBill>\n");//是否有单据  
					  
				      xml.append("</item>\n");
				      BigDecimal balance=Conbaseinfo.getBgBalance()!=null?Conbaseinfo.getBgBalance():FDCHelper.ZERO;
				      if(balance.compareTo(Conbaseinfo.getRequestAmount())<0){
				    	  control=0;
				      }
				  }
				  xml.append("</billEntries>\n");
			  }
			xml.append("<controlType>"+control+"</controlType>\n");//控制类型
			
			xml.append("</DATA>"); 
			str[1] = xml.toString();
		}catch (Exception e) {
			str[0] = "N";
			str[2] = "其他异常，请查看服务器log日志！";
			e.printStackTrace();
		}finally{
			BPMLogInfo log = new BPMLogInfo();
			try {
				log.setLogDate(new Date());
				log.setName("合同单据ID："+Info.getId()+"; 编号："+Info.getNumber());
				log.setDescription("EAS结果:"+str[0]+"; 错误信息"+str[1]+str[2]);
				log.setBeizhu("调用接口方法：GetbillInfo");
				BPMLogFactory.getLocalInstance(ctx).save(log);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return str;
	}


	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("OrgUnit.id"));
		sic.add(new SelectorItemInfo("OrgUnit.number"));
		sic.add(new SelectorItemInfo("OrgUnit.name"));
		sic.add(new SelectorItemInfo("OrgUnit.DisplayName"));
		
		
		sic.add(new SelectorItemInfo("UseDepartment.name"));
		sic.add(new SelectorItemInfo("CreateTime"));
		sic.add(new SelectorItemInfo("Creator.name"));
		sic.add(new SelectorItemInfo("Name"));
		sic.add(new SelectorItemInfo("CurProject.name"));
		sic.add(new SelectorItemInfo("CurProject.DisplayName"));
		sic.add(new SelectorItemInfo("ContractNo"));
		sic.add(new SelectorItemInfo("BizDate"));
		sic.add(new SelectorItemInfo("Number"));
		sic.add(new SelectorItemInfo("PayDate"));
		sic.add(new SelectorItemInfo("Period.number"));
		sic.add(new SelectorItemInfo("IsDifferPlace"));
		sic.add(new SelectorItemInfo("PaymentType.name"));
		sic.add(new SelectorItemInfo("SettlementType.name"));
		sic.add(new SelectorItemInfo("Supplier.name"));
		sic.add(new SelectorItemInfo("RecBank"));
		sic.add(new SelectorItemInfo("RecAccount"));
		sic.add(new SelectorItemInfo("RealSupplier.name"));
		sic.add(new SelectorItemInfo("Description"));
		sic.add(new SelectorItemInfo("Usage"));
		sic.add(new SelectorItemInfo("Currency.name"));
		sic.add(new SelectorItemInfo("ExchangeRate"));
		sic.add(new SelectorItemInfo("ContractId"));
		
		sic.add(new SelectorItemInfo("OriginalAmount"));
		sic.add(new SelectorItemInfo("InvoiceAmt"));
		sic.add(new SelectorItemInfo("AllInvoiceAmt"));
		sic.add(new SelectorItemInfo("Amount"));
		sic.add(new SelectorItemInfo("GrtAmount"));
		sic.add(new SelectorItemInfo("CapitalAmount"));
		sic.add(new SelectorItemInfo("IsRespite"));
		sic.add(new SelectorItemInfo("IsPay"));
		sic.add(new SelectorItemInfo("PaymentProportion"));
		sic.add(new SelectorItemInfo("CompletePrjAmt"));
		sic.add(new SelectorItemInfo("TotalSettlePrice"));
		
		sic.add(new SelectorItemInfo("InvoiceNumber"));
		sic.add(new SelectorItemInfo("InvoiceOriAmt"));
		sic.add(new SelectorItemInfo("InvoiceDate"));
		sic.add(new SelectorItemInfo("AllReqPercent"));
		sic.add(new SelectorItemInfo("AllInvoiceOriAmt"));
		sic.add(new SelectorItemInfo("PlanHasCon.name"));
		sic.add(new SelectorItemInfo("MoneyDesc"));
		sic.add(new SelectorItemInfo("State"));
		
		sic.add(new SelectorItemInfo("CostedCompany.name"));
		sic.add(new SelectorItemInfo("CostedCompany.number"));
		sic.add(new SelectorItemInfo("CostedDept.name"));
		
		sic.add(new SelectorItemInfo("ContractName"));
		sic.add(new SelectorItemInfo("Process"));
		sic.add(new SelectorItemInfo("BookedDate"));
		sic.add(new SelectorItemInfo("PayTimes"));
		sic.add(new SelectorItemInfo("cu.id"));
		sic.add(new SelectorItemInfo("DepPlanState"));
		sic.add(new SelectorItemInfo("ContractBase"));
		sic.add(new SelectorItemInfo("id"));
		
		return sic;
    }


	public String[] GetrRelatedBillInfo(Context ctx, String strBTID,
			IObjectValue billInfo, String strRelatedCode) {
		// TODO Auto-generated method stub
		return null;
	}


	public String[] ApproveBack(Context ctx, String strBTID,
			IObjectValue billInfo, String strXML) {
		// TODO Auto-generated method stub
		return null;
	}
	

	

}
