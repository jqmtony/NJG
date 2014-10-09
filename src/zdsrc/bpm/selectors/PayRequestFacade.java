package com.kingdee.eas.bpm.selectors;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.eas.bpm.BPMLogFactory;
import com.kingdee.eas.bpm.BPMLogInfo;
import com.kingdee.eas.bpm.BillBaseSelector;
import com.kingdee.eas.bpm.common.StringUtilBPM;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.PaymentTypeFactory;
import com.kingdee.eas.fdc.basedata.PaymentTypeInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillEntryFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillEntryInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;

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
				String sql = " update t_con_payrequestbill set fState='"+Info.getState().getValue()+"'" +
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
		PayRequestBillInfo Info = (PayRequestBillInfo)billInfo;
    	String[] str = new String[3];
    	str[0] = "Y";
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
						Info.setState(FDCBillStateEnum.AUDITTED);
					else{
						str[2] = "审批通过失败，该记录状态不是审批中！";
						str[0] = "N";
					}
				}
				if("0".equals(processInstanceResult)){
					if(FDCBillStateEnum.AUDITTING.equals(Info.getState()))
						Info.setState(FDCBillStateEnum.INVALID);
					else{
						str[2] = "审批不通过失败，该记录状态不是审批中！";
						str[0] = "N";
					}
				}
				if("2".equals(processInstanceResult)){
					if(FDCBillStateEnum.AUDITTING.equals(Info.getState()))
						Info.setState(FDCBillStateEnum.BACK);
					else{
						str[2] = "审批打回失败，该记录状态不是审批中！";
						str[0] = "N";
					}
				}
				if("3".equals(processInstanceResult)){
					if(FDCBillStateEnum.AUDITTING.equals(Info.getState()))
						Info.setState(FDCBillStateEnum.SAVED);
					else{
						str[2] = "撤销失败，该记录状态不是审批中！";
						str[0] = "N";
					}
				}
				String sql = " update t_con_payrequestbill set fState='"+Info.getState().getValue()+"' where fid='"+Info.getId()+"'";
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
			xml.append("<OrgName>"+StringUtilBPM.isNULl(Info.getOrgUnit().getName())+"</OrgName>\n");
			if(Info.getUseDepartment()!=null)
			xml.append("<Department>"+StringUtilBPM.isNULl(Info.getUseDepartment().getName())+"</Department>\n");
			xml.append("<ApplyDate>"+dateFormat.format(Info.getCreateTime())+"</ApplyDate>\n");
			xml.append("<Applicant>"+StringUtilBPM.isNULl(Info.getCreator().getName())+"</Applicant>\n");
			
			xml.append("<Position>合约部经理</Position>\n");
			xml.append("<Topic>"+StringUtilBPM.isNULl(Info.getName())+"-付款申请审批单"+"</Topic>\n");
		    if(Info.getOrgUnit()!=null)
			xml.append("<orgunit>"+StringUtilBPM.isNULl(Info.getOrgUnit().getName())+"</orgunit>\n");
		    if(Info.getCurProject()!=null)
			xml.append("<curProject>"+StringUtilBPM.isNULl(Info.getCurProject().getName())+"</curProject>\n");
			xml.append("<contractNo>"+StringUtilBPM.isNULl(Info.getContractNo())+"</contractNo>\n");
			if(Info.getUseDepartment()!=null)
			xml.append("<contractNo>"+StringUtilBPM.isNULl(Info.getUseDepartment().getName())+"</contractNo>\n");
			xml.append("<bizdate>"+dateFormat.format(Info.getBizDate())+"</bizdate>\n");
			xml.append("<number>"+StringUtilBPM.isNULl(Info.getNumber())+"</number>\n");
			xml.append("<payDate>"+dateFormat.format(Info.getPayDate())+"</payDate>\n");//付款日期
			xml.append("<period>"+Info.getPeriod().getNumber()+"</period>\n");//申请期间
			if(Info.getIsDifferPlace()!=null)
			xml.append("<isdifferplace>"+StringUtilBPM.isNULl(Info.getIsDifferPlace().getName())+"</isdifferplace>\n");//同城异地
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
			xml.append("<Desc>"+StringUtilBPM.isNULl(Info.getDescription())+"</Desc>\n");//
			xml.append("<usage>"+StringUtilBPM.isNULl(Info.getUsage())+"</usage>\n");//用途
			
   
			xml.append("<currency>"+StringUtilBPM.isNULl(Info.getCurrency().getName())+"</currency>\n");//币别
			xml.append("<exchangeRate>"+Info.getExchangeRate()+"</exchangeRate>\n");//汇率
			xml.append("<originalAmount>"+Info.getOriginalAmount()+"</originalAmount>\n");//原币金额
			xml.append("<invoiceAmt>"+Info.getInvoiceAmt()+"</invoiceAmt>\n");//发票金额
			xml.append("<allinvoiceAmt>"+Info.getAllInvoiceAmt()+"</allinvoiceAmt>\n");//累计发票金额
			xml.append("<amount>"+Info.getAmount()+"</amount>\n");//本币金额
			xml.append("<grtAmount>"+Info.getGrtAmount()+"</grtAmount>\n");//本币金额
			xml.append("<capitalAmount>"+Info.getCapitalAmount()+"</capitalAmount>\n");//大写金额
			xml.append("<isRespite>"+Info.isIsRespite()+"</isRespite>\n");//是否加急
			xml.append("<IsPay>"+Info.isIsPay()+"</IsPay>\n");//是否提交付款
			
			if(Info.getPaymentProportion()!=null)//进度款付款比例
				xml.append("<paymentProportion>"+Info.getPaymentProportion()+"</paymentProportion>\n");
			if(Info.getCompletePrjAmt()!=null)//本期完成工程量
				xml.append("<completePrjAmt>"+Info.getCompletePrjAmt()+"</completePrjAmt>\n");
			if(Info.getTotalSettlePrice()!=null)//已实现产值
				xml.append("<TotalSettlePrice>"+Info.getTotalSettlePrice()+"</TotalSettlePrice>\n");
			if(Info.getInvoiceNumber()!=null)//发票号
				xml.append("<invoiceNumber>"+Info.getInvoiceNumber()+"</invoiceNumber>\n");
			if(Info.getInvoiceOriAmt()!=null)//发票金额原币
				xml.append("<invoiceOriAmt>"+Info.getInvoiceOriAmt()+"</invoiceOriAmt>\n");
			if(Info.getInvoiceDate()!=null)//开票日期
				xml.append("<invoiceDate>"+dateFormat.format(Info.getInvoiceDate())+"</invoiceDate>\n");
			if(Info.getCompletePrjAmt()!=null)//累计已完成工程量
				xml.append("<AllCompletePrjAmt>"+Info.getCompletePrjAmt()+"</AllCompletePrjAmt>\n");
			if(Info.getAllReqPercent()!=null)//累计应付付款比例
				xml.append("<AllPaymentProportion>"+Info.getAllReqPercent()+"</AllPaymentProportion>\n");
			if(Info.getAllInvoiceOriAmt()!=null)//累计发票原币/本币
				xml.append("<allinvoiceOriAmt>"+Info.getAllInvoiceOriAmt()+"</allinvoiceOriAmt>\n");
			if(Info.getPlanHasCon()!=null)
				xml.append("<PlanHasCon>"+Info.getPlanHasCon()+"</PlanHasCon>\n");//预算项目
			if(Info.getMoneyDesc()!=null)
				xml.append("<MoneyDesc>"+Info.getMoneyDesc()+"</MoneyDesc>\n");//备注
			
			
/*    			xml.append("<billEntries>\n");
			for(int i=0;i<Info.getEntrys().size();i++){
				PayRequestBillEntryInfo entry = Info.getEntrys().get(i);
				entry = PayRequestBillEntryFactory.getLocalInstance(ctx).getPayRequestBillEntryInfo(new ObjectUuidPK(entry.getId()));
				xml.append("<item>\n");
					xml.append("<parent>"+StringUtilBPM.isNULl(entry.getParent().getId().toString())+"</parent>\n");
					xml.append("<payTimes>"+entry.getPayTimes()+"</payTimes>\n");
					xml.append("<amount>"+entry.getAmount()+"</amount>\n");
					xml.append("<addProjectAmt>"+entry.getAddProjectAmt()+"</addProjectAmt>\n");
					xml.append("<payPartMatlAmt>"+entry.getPayPartAMatlAmt()+"</payPartMatlAmt>\n");
					xml.append("<projectPriceInContract>"+entry.getProjectPriceInContract()+"</projectPriceInContract>\n");
					xml.append("<originalAmount>"+entry.getOriginalAmount()+"</originalAmount>\n");
					xml.append("<advance>"+entry.getAdvance()+"</advance>\n");
					xml.append("<locAdvance>"+entry.getLocAdvance()+"</locAdvance>\n");
				xml.append("</item>\n");
			}
			xml.append("</billEntries>\n");*/
			
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
		sic.add(new SelectorItemInfo("orgUnit.id"));
		sic.add(new SelectorItemInfo("orgUnit.number"));
		sic.add(new SelectorItemInfo("orgUnit.name"));
		//sic.add(new SelectorItemInfo("curProject.id"));
		//sic.add(new SelectorItemInfo("curProject.number"));
		sic.add(new SelectorItemInfo("curProject.name"));

				
		//sic.add(new SelectorItemInfo("useDepartment.id"));
		//sic.add(new SelectorItemInfo("useDepartment.number"));
		sic.add(new SelectorItemInfo("useDepartment.name"));			
		//sic.add(new SelectorItemInfo("currency.id"));
		//sic.add(new SelectorItemInfo("currency.number"));
		sic.add(new SelectorItemInfo("currency.name"));
		sic.add(new SelectorItemInfo("period"));
		sic.add(new SelectorItemInfo("creator.name"));
		sic.add(new SelectorItemInfo("paymentType.name"));
		sic.add(new SelectorItemInfo("settlementType.name"));
		//sic.add(new SelectorItemInfo("supplier.id"));
		//sic.add(new SelectorItemInfo("supplier.number"));
		sic.add(new SelectorItemInfo("supplier.name"));
		//sic.add(new SelectorItemInfo("realSupplier.id"));
		//sic.add(new SelectorItemInfo("realSupplier.number"));
		sic.add(new SelectorItemInfo("realSupplier.name"));
		sic.add(new SelectorItemInfo("Name"));
		sic.add(new SelectorItemInfo("Number"));
		sic.add(new SelectorItemInfo("IsDifferPlace.name"));
		sic.add(new SelectorItemInfo("ContractNo"));
		sic.add(new SelectorItemInfo("BizDate"));
		sic.add(new SelectorItemInfo("PayDate"));
		sic.add(new SelectorItemInfo("IsPay"));
		sic.add(new SelectorItemInfo("IsRespite"));
		sic.add(new SelectorItemInfo("CreateTime"));
		sic.add(new SelectorItemInfo("recAccount"));
		sic.add(new SelectorItemInfo("RecBank"));
		sic.add(new SelectorItemInfo("Description"));
		sic.add(new SelectorItemInfo("Usage"));
		sic.add(new SelectorItemInfo("ExchangeRate"));
		sic.add(new SelectorItemInfo("OriginalAmount"));
		sic.add(new SelectorItemInfo("InvoiceAmt"));
		sic.add(new SelectorItemInfo("AllInvoiceAmt"));
		sic.add(new SelectorItemInfo("Amount"));
		sic.add(new SelectorItemInfo("GrtAmount"));
		sic.add(new SelectorItemInfo("CapitalAmount"));
		sic.add(new SelectorItemInfo("PaymentProportion"));
		sic.add(new SelectorItemInfo("CompletePrjAmt"));
		sic.add(new SelectorItemInfo("TotalSettlePrice"));
		sic.add(new SelectorItemInfo("InvoiceNumber"));
		sic.add(new SelectorItemInfo("InvoiceOriAmt"));
		sic.add(new SelectorItemInfo("InvoiceDate"));
		sic.add(new SelectorItemInfo("CompletePrjAmt"));
		sic.add(new SelectorItemInfo("AllReqPercent"));
		sic.add(new SelectorItemInfo("AllInvoiceOriAmt"));
		sic.add(new SelectorItemInfo("PlanHasCon.name"));
		sic.add(new SelectorItemInfo("MoneyDesc"));
		
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
