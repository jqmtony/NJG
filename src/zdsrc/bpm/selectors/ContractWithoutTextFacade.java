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
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.assistant.ExchangeRateFactory;
import com.kingdee.eas.basedata.assistant.ExchangeRateInfo;
import com.kingdee.eas.basedata.assistant.ExchangeTableInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.bpm.BPMLogFactory;
import com.kingdee.eas.bpm.BPMLogInfo;
import com.kingdee.eas.bpm.BillBaseSelector;
import com.kingdee.eas.bpm.common.ExchangeRate;
import com.kingdee.eas.bpm.common.StringUtilBPM;
import com.kingdee.eas.bpm.common.TransRMB;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.SysContextConstant;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.PaymentTypeFactory;
import com.kingdee.eas.fdc.basedata.PaymentTypeInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.contract.ContractBaseDataCollection;
import com.kingdee.eas.fdc.contract.ContractBaseDataFactory;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;

public class ContractWithoutTextFacade implements BillBaseSelector {
	
	public String[] SubmitResult(Context ctx, String strBSID,
			IObjectValue billInfo, boolean success, int procInstID, String procURL,
			String strMessage) {
		ContractWithoutTextInfo Info = (ContractWithoutTextInfo)billInfo;
    	String[] str = new String[3];
    	str[0] = "Y";
    	String sql="";
		try {
			try{
				Info = ContractWithoutTextFactory.getLocalInstance(ctx).getContractWithoutTextInfo(new ObjectUuidPK(Info.getId()),getSelectors());
			}catch (EASBizException e) {
				str[0] = "N";
				str[2] = "根据单据getSelectors获取对象数据，请检查getSelectors方法中属性是否正确,并查看服务器log日志！";
				e.printStackTrace();
			}
			try{
				Info.setState(FDCBillStateEnum.SUBMITTED);
				sql = " update t_con_contractwithouttext set fState='"+Info.getState().getValue()+"'" +
						", fDescription='"+procURL+"' " +
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
		ContractWithoutTextInfo Info = (ContractWithoutTextInfo)billInfo;
    	String[] str = new String[3];
    	str[0] = "Y";
    	String sql="";
		try {
			try{
				Info = ContractWithoutTextFactory.getLocalInstance(ctx).getContractWithoutTextInfo(new ObjectUuidPK(Info.getId()),getSelectors());
			}catch (EASBizException e) {
				str[2] = "根据单据getSelectors获取对象数据，请检查getSelectors方法中属性是否正确,并查看服务器log日志！";
				e.printStackTrace();
			}
			try{
				if("1".equals(processInstanceResult)){
					if(FDCBillStateEnum.SUBMITTED.equals(Info.getState()))
						Info.setState(FDCBillStateEnum.AUDITTED);
					else{
						str[2] = "审批通过失败，该记录状态不是审批中！";
						str[0] = "N";
					}
				}
				if("0".equals(processInstanceResult)){
					if(FDCBillStateEnum.SUBMITTED.equals(Info.getState()))
						Info.setState(FDCBillStateEnum.SAVED);
					else{
						str[2] = "审批不通过失败，该记录状态不是审批中！";
						str[0] = "N";
					}
				}
				if("2".equals(processInstanceResult)){
					if(FDCBillStateEnum.SUBMITTED.equals(Info.getState()))
						Info.setState(FDCBillStateEnum.SAVED);
					else{
						str[2] = "审批打回失败，该记录状态不是审批中！";
						str[0] = "N";
					}
				}
				if("3".equals(processInstanceResult)){
					if(FDCBillStateEnum.SUBMITTED.equals(Info.getState()))
					{
						Info.setState(FDCBillStateEnum.SAVED);
						sql = " update t_con_contractwithouttext set fDescription='' where fid='"+Info.getId()+"'";
						FDCSQLBuilder bu = new FDCSQLBuilder(ctx);
						bu.appendSql(sql);
						bu.executeUpdate(ctx);
					}
					else{
						str[2] = "撤销失败，该记录状态不是审批中！";
						str[0] = "N";
					}
				}
				 sql = " update t_con_contractwithouttext set fState='"+Info.getState().getValue()+"' where fid='"+Info.getId()+"'";
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
		ContractWithoutTextInfo Info = (ContractWithoutTextInfo)billInfo;
    	String[] str = new String[3];
    	str[0] = "Y";
    	StringBuffer xml = new StringBuffer();
		try {
			try{
				Info = ContractWithoutTextFactory.getLocalInstance(ctx).getContractWithoutTextInfo(new ObjectUuidPK(Info.getId()),getSelectors());
			}catch (EASBizException e) {
				str[2] = "根据单据getSelectors获取对象数据，请检查getSelectors方法中属性是否正确,并查看服务器log日志！";
				e.printStackTrace();
			}
			try{
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    			xml.append("<DATA>\n"); 
    			xml.append("<OrgName>"+StringUtilBPM.isNULl(Info.getOrgUnit().getName())+"</OrgName>\n");
    			xml.append("<useDepartment>"+StringUtilBPM.isNULl(Info.getUseDepartment().getName())+"</useDepartment>\n");
    			xml.append("<ApplyDate>"+dateFormat.format(Info.getCreateTime())+"</ApplyDate>\n");
    			xml.append("<Applicant>"+StringUtilBPM.isNULl(Info.getCreator().getName())+"</Applicant>\n");
    			xml.append("<Position>合约部经理</Position>\n");
    			xml.append("<Topic>"+StringUtilBPM.isNULl(Info.getName())+"-无文本合同审批单"+"</Topic>\n");
    			if(Info.getCurProject()!=null)
    			{
    			xml.append("<curProject>"+StringUtilBPM.isNULl(Info.getCurProject().getName())+"</curProject>\n");
    			}
    			//xml.append("<contractNo>"+StringUtilBPM.isNULl(Info.getContractBaseData().getNumber())+"</contractNo>\n");
    			xml.append("<bizdate>"+dateFormat.format(Info.getBizDate())+"</bizdate>\n");
    			if(Info.getOrgUnit()!=null)
    			{
    			xml.append("<orgunit>"+StringUtilBPM.isNULl(Info.getOrgUnit().getName())+"</orgunit>\n");//组织
    			}
    			xml.append("<name>"+StringUtilBPM.isNULl(Info.getName())+"</name>\n");   //单据名称
    			xml.append("<period>"+Info.getPeriod().getNumber()+"</period>\n");//订立期间
    			xml.append("<signDate>"+dateFormat.format(Info.getSignDate())+"</signDate>\n");//订立期间
    			xml.append("<number>"+StringUtilBPM.isNULl(Info.getNumber())+"</number>\n");
    			if(Info.getPaymentType()!=null)
    			{
    			xml.append("<PaymentType>"+StringUtilBPM.isNULl(Info.getPaymentType().getName())+"</PaymentType>\n");//付款类型
    			}
    			if(Info.getUseDepartment()!=null)
    			{
    			xml.append("<userDepartment>"+StringUtilBPM.isNULl(Info.getUseDepartment().getName())+"</userDepartment>\n");//用款部门
    			}
    			if(Info.getContractType()!=null)
    			{
    			xml.append("<contractType>"+StringUtilBPM.isNULl(Info.getContractType().getName())+"</contractType>\n");//合同类型
    			}
    			xml.append("<currency>"+StringUtilBPM.isNULl(Info.getCurrency().getName())+"</currency>\n");//币别
    			if(Info.getReceiveUnit()!=null)
    			{
    			xml.append("<receiveUnit>"+StringUtilBPM.isNULl(Info.getReceiveUnit().getName())+"</receiveUnit>\n");//收款单位
    			}
    			xml.append("<originalAmount>"+Info.getOriginalAmount()+"</originalAmount>\n");//原币金额
    			xml.append("<invoiceAmt>"+Info.getInvoiceAmt()+"</invoiceAmt>\n");//发票金额
    			xml.append("<allinvoiceAmt>"+Info.getAllInvoiceAmt()+"</allinvoiceAmt>\n");//累计发票金额
    			xml.append("<BcAmount>"+Info.getAmount()+"</BcAmount>\n");//本币金额
    			xml.append("<NoPaidReason>"+Info.getNoPaidReason()+"</NoPaidReason>\n");  //无需付款原因
    			xml.append("<realSupplier>"+StringUtilBPM.isNULl(Info.getReceiveUnit().getName())+"</realSupplier>\n");//实际收款单位
    			
    			
    			BigDecimal rate = ExchangeRate.getRate(ctx,Info.getCurrency().getName(), Info.getBizDate().toString(), "1");
    			if(rate!=null)
    			{
    			xml.append("<exchangeRate>"+rate+"</exchangeRate>\n");//汇率
    			}
    			else
    			{
    				xml.append("<exchangeRate>1</exchangeRate>\n");//汇率
    			}
    			
//    			String srcid = Info.getCurrency().getId().toString();
//    			CompanyOrgUnitInfo currentOrg = (CompanyOrgUnitInfo) ctx.get(SysContextConstant.COMPANYINFO);
//    			ExchangeTableInfo baseExchangeTable = currentOrg.getBaseExchangeTable();
//    			if(baseExchangeTable != null)
//    	        {
//    	            CurrencyInfo baseCurrency = currentOrg.getBaseCurrency();
//    	            if(baseCurrency != null)
//    	            {
//    	                if(srcid.equals(baseCurrency.getId())){
//    	                	xml.append("<exchangeRate>1.00</exchangeRate>\n");//汇率
//    	                }
//    	                ExchangeRateInfo exchangeRate = ExchangeRateFactory.getLocalInstance(ctx).
//    	                	getExchangeRate(new ObjectUuidPK(baseExchangeTable.getId()), new ObjectUuidPK(srcid), new ObjectUuidPK(baseCurrency.getId()), Info.getBizDate());
//    	                if(exchangeRate != null){
//    	                	xml.append("<exchangeRate>"+exchangeRate.getPrecision()+"</exchangeRate>\n");//汇率
//    	                }
//    	            } else
//    	            {
//    	            	xml.append("<exchangeRate>1.00</exchangeRate>\n");//汇率
//    	            }
//    	        }
    			
    			xml.append("<PaymentRequestBillNumber>"+StringUtilBPM.isNULl(Info.getNumber())+"</PaymentRequestBillNumber>\n");//付款申请单编码
    			
    			BigDecimal localamount = Info.getAmount().setScale(2, 4);
    			String s = TransRMB.roundString(localamount.toString());
//    			String s = TransRMB.cleanZero(TransRMB.splitNum(TransRMB.roundString(localamount.toString())));
    			
    			
                String cap = StringUtilBPM.getChineseFormat(localamount, false);
    		    xml.append("<capitalAmount>"+s+"</capitalAmount>\n");//大写金额
    			xml.append("<completePrjAmt>"+Info.getAmount()+"</completePrjAmt>\n");//本期完成工程量
    			
    			 EntityViewInfo Myavevi = new EntityViewInfo();
   		         FilterInfo Myavfilter = new FilterInfo();
   		         Myavfilter.getFilterItems().add(new FilterItemInfo("number",Info.getNumber(),CompareType.EQUALS));
   		         Myavevi.setFilter(Myavfilter);
   		         PayRequestBillCollection myavc=PayRequestBillFactory.getLocalInstance(ctx).getPayRequestBillCollection(Myavevi);
   		          if(myavc.size()>0)
   		           {
   		            for(int i=0;i< myavc.size();i++){  	     
   		            	PayRequestBillInfo info=PayRequestBillFactory.getLocalInstance(ctx).getPayRequestBillInfo(new ObjectUuidPK(myavc.get(i).getId()));
   		            	xml.append("<MoneyDesc>"+info.getMoneyDesc()+"</MoneyDesc>\n");
   		            	xml.append("<PaymentProportion>"+info.getPaymentProportion()+"</PaymentProportion>\n");
   		           }
   		           }     
    			//xml.append("<PaymentProportion>"+Info.getPaymentProportion()+"</PaymentProportion>\n"); 进度款付款比例
    			//xml.append("<MoneyDesc>"+Info.getDescription()+"</MoneyDesc>\n");  款项说明   PayRequestBillInfo
    			//xml.append("<Urgency>"+Info.getNoPaidReason()+"</Urgency>\n");  //加急
    			
    			//if(Info.getFdcDepConPlan()!=null)
    			//xml.append("<PlanProject>"+Info.getFdcDepConPlan()+"</PlanProject>\n");  //计划项目
    			//else
    			//{
    			//	xml.append("<PlanProject></PlanProject>\n");
    			//}
    			if(false==Info.isIsCostSplit())
    		    xml.append("<isCostSplit>否</isCostSplit>\n");  //是否进入动态成本
    			else
    			{
    				xml.append("<isCostSplit>是</isCostSplit>\n");  //是否进入动态成本
    			}
    			if(false==Info.isIsNeedPaid())
    			{
    			xml.append("<isNeedPaid>否</isNeedPaid>\n");
    			}
    			else
    			{
    				xml.append("<isNeedPaid>是</isNeedPaid>\n");
    			}
    			if(Info.getDepPlanState()!=null)
    			{
    			//xml.append("<DepPlanState>"+Info.getDepPlanState().getName()+"</DepPlanState>\n");  //无需付款
    				xml.append("<DepPlanState>"+Info.getDepPlanState().getAlias()+"</DepPlanState>\n");  //无需付款
    			}
       			xml.append("<invoicenumber>"+StringUtilBPM.isNULl(Info.getInvoiceNumber())+"</invoicenumber>\n");  //发票号
    			xml.append("<invoiceDate>"+dateFormat.format(Info.getInvoiceDate())+"</invoiceDate>\n");       //开票日期
    			if(Info.getSettlementType()!=null)
    			{
    			xml.append("<settlementType>"+Info.getSettlementType().getName()+"</settlementType>\n");       //结算方式
    			}
    			xml.append("<bank>"+StringUtilBPM.isNULl(Info.getBank())+"</bank>\n");                         //收款银行
    			if(Info.getBankAcct()!=null)
    			xml.append("<BankAcct>"+Info.getBankAcct()+"</BankAcct>\n");                                   //银行账号
    			else
    			{
    				xml.append("<BankAcct></BankAcct>\n");                                   //银行账号
    			}
                xml.append("</DATA>"); 
                str[1] = xml.toString();
			}
			catch (Exception e) {
				str[0] = "N";
				str[2] = "获取对象属性失败，请检查属性是否有值,并查看服务器log日志!";
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
		sic.add(new SelectorItemInfo("orgUnit.id"));
		sic.add(new SelectorItemInfo("orgUnit.number"));
		sic.add(new SelectorItemInfo("orgUnit.name"));
		sic.add(new SelectorItemInfo("curProject.id"));
		sic.add(new SelectorItemInfo("curProject.number"));
		sic.add(new SelectorItemInfo("curProject.name"));
		sic.add(new SelectorItemInfo("currency.name"));
		sic.add(new SelectorItemInfo("currency.id"));
		sic.add(new SelectorItemInfo("useDepartment.id"));
		sic.add(new SelectorItemInfo("useDepartment.number"));
		sic.add(new SelectorItemInfo("useDepartment.name"));
		sic.add(new SelectorItemInfo("period.number"));
		sic.add(new SelectorItemInfo("creator.name"));
		sic.add(new SelectorItemInfo("paymentType.name"));
		sic.add(new SelectorItemInfo("receiveUnit.name"));
		sic.add(new SelectorItemInfo("contractType.name"));		
		sic.add(new SelectorItemInfo("bizDate"));
		sic.add(new SelectorItemInfo("SignDate"));
		sic.add(new SelectorItemInfo("userDepartment.name"));
		sic.add(new SelectorItemInfo("originalAmount"));
		sic.add(new SelectorItemInfo("invoicenumber"));
		sic.add(new SelectorItemInfo("invoiceAmt"));
		sic.add(new SelectorItemInfo("invoiceDate"));
		sic.add(new SelectorItemInfo("allinvoiceAmt"));
		sic.add(new SelectorItemInfo("settlementType.name"));
		sic.add(new SelectorItemInfo("Amount"));
		sic.add(new SelectorItemInfo("bankAcct"));
		sic.add(new SelectorItemInfo("DepPlanState"));
		sic.add(new SelectorItemInfo("CreateTime"));
		sic.add(new SelectorItemInfo("Name"));
		sic.add(new SelectorItemInfo("Number"));
		sic.add(new SelectorItemInfo("IsCostSplit"));
		sic.add(new SelectorItemInfo("State"));
		sic.add(new SelectorItemInfo("NoPaidReason"));
		sic.add(new SelectorItemInfo("bank"));
		sic.add(new SelectorItemInfo("ContractBaseData"));
		sic.add(new SelectorItemInfo("MoneyDesc"));
		sic.add(new SelectorItemInfo("PaymentProportion"));
		sic.add(new SelectorItemInfo("IsNeedPaid"));
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
