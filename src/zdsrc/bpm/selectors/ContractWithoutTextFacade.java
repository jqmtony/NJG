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
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;

public class ContractWithoutTextFacade implements BillBaseSelector {
	
	public String[] SubmitResult(Context ctx, String strBSID,
			IObjectValue billInfo, boolean success, int procInstID, String procURL,
			String strMessage) {
		ContractWithoutTextInfo Info = (ContractWithoutTextInfo)billInfo;
    	String[] str = new String[3];
    	str[0] = "Y";
		try {
			try{
				Info = ContractWithoutTextFactory.getLocalInstance(ctx).getContractWithoutTextInfo(new ObjectUuidPK(Info.getId()),getSelectors());
			}catch (EASBizException e) {
				str[0] = "N";
				str[2] = "根据单据getSelectors获取对象数据，请检查getSelectors方法中属性是否正确,并查看服务器log日志！";
				e.printStackTrace();
			}
			try{
				Info.setState(FDCBillStateEnum.AUDITTING);
				String sql = " update t_con_contractwithouttext set fState='"+Info.getState().getValue()+"'" +
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
		try {
			try{
				Info = ContractWithoutTextFactory.getLocalInstance(ctx).getContractWithoutTextInfo(new ObjectUuidPK(Info.getId()),getSelectors());
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
				String sql = " update t_con_contractwithouttext set fState='"+Info.getState().getValue()+"' where fid='"+Info.getId()+"'";
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
    			xml.append("<curProject>"+StringUtilBPM.isNULl(Info.getCurProject().getName())+"</curProject>\n");
    			xml.append("<contractNo>"+StringUtilBPM.isNULl(Info.getContractBaseData().getNumber())+"</contractNo>\n");
    			xml.append("<bizdate>"+dateFormat.format(Info.getBizDate())+"</bizdate>\n");
    			
    			xml.append("<period>"+Info.getPeriod().getNumber()+"</period>\n");//订立期间
    			xml.append("<signDate>"+dateFormat.format(Info.getSignDate())+"</signDate>\n");//订立期间
    			xml.append("<number>"+StringUtilBPM.isNULl(Info.getNumber())+"</number>\n");
    			xml.append("<PaymentType>"+StringUtilBPM.isNULl(Info.getPaymentType().getName())+"</PaymentType>\n");//付款类型
    			xml.append("<contractType>"+StringUtilBPM.isNULl(Info.getContractType().getName())+"</contractType>\n");//合同类型
    			xml.append("<currency>"+StringUtilBPM.isNULl(Info.getCurrency().getName())+"</currency>\n");//币别
    			xml.append("<receiveUnit>"+StringUtilBPM.isNULl(Info.getReceiveUnit().getName())+"</receiveUnit>\n");//收款单位
//    			xml.append("<exchangeRate>"+StringUtilBPM.isNULl(Info.get)+"</exchangeRate>\n");//汇率
    			xml.append("<originalAmount>"+Info.getOriginalAmount()+"</originalAmount>\n");//原币金额
    			xml.append("<invoiceAmt>"+Info.getInvoiceAmt()+"</invoiceAmt>\n");//发票金额
    			xml.append("<allinvoiceAmt>"+Info.getAllInvoiceAmt()+"</allinvoiceAmt>\n");//累计发票金额
    			xml.append("<BcAmount>"+Info.getAmount()+"</BcAmount>\n");//本币金额
//    			xml.append("<capitalAmount>"+StringUtilBPM.isNULl(Info.get)+"</capitalAmount>\n");//大写金额
//    			xml.append("<completePrjAmt>"+StringUtilBPM.isNULl(Info.getcom.getName())+"</completePrjAmt>\n");//本期完成工程量
    			
    			
    			if(Info.getInvoiceNumber()!=null)
    				xml.append("<invoicenumber>"+Info.getInvoiceNumber()+"</invoicenumber>\n");
    			if(Info.getInvoiceDate()!=null)
    				xml.append("<invoiceDate>"+dateFormat.format(Info.getInvoiceDate())+"</invoiceDate>\n");
    			if(Info.getSettlementType()!=null)
    				xml.append("<settlementType>"+Info.getSettlementType().getName()+"</settlementType>\n");
    			if(Info.getBank()!=null)
    				xml.append("<bank>"+Info.getBank()+"</bank>\n");
    			if(Info.getBankAcct()!=null)
    				xml.append("<BankAcct>"+Info.getBankAcct()+"</BankAcct>\n");
//    			if(Info.getPaymentProportion()!=null)
//    				xml.append("<PaymentProportion>"+Info.getPaymentProportion()+"</PaymentProportion>\n");
//    			if(Info.getPlanProject()!=null)
//    				xml.append("<PlanProject>"+Info.getPlanProject()+"</PlanProject>\n");
//    			if(Info.get()!=null)//款项说明
//    				xml.append("<MoneyDesc>"+Info.getMoneyDesc()+"</MoneyDesc>\n");
//    			if(Info.getMoneyDesc()!=null)//加急
//    				xml.append("<Urgency>"+Info.geti+"</Urgency>\n");//
//    			xml.append("<rate>"+Info.getExchangeRate()+"</rate>\n");
    			
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
		sic.add(new SelectorItemInfo("*"));
		sic.add(new SelectorItemInfo("orgUnit.id"));
		sic.add(new SelectorItemInfo("orgUnit.number"));
		sic.add(new SelectorItemInfo("orgUnit.name"));
		sic.add(new SelectorItemInfo("creator.id"));
		sic.add(new SelectorItemInfo("creator.number"));
		sic.add(new SelectorItemInfo("creator.name"));
		sic.add(new SelectorItemInfo("curProject.id"));
		sic.add(new SelectorItemInfo("curProject.number"));
		sic.add(new SelectorItemInfo("curProject.name"));
		 sic.add(new SelectorItemInfo("currency.id"));
		 sic.add(new SelectorItemInfo("currency.number"));
		 sic.add(new SelectorItemInfo("currency.name"));
				
		sic.add(new SelectorItemInfo("useDepartment.id"));
		sic.add(new SelectorItemInfo("useDepartment.number"));
		sic.add(new SelectorItemInfo("useDepartment.name"));
		sic.add(new SelectorItemInfo("period.id"));
		sic.add(new SelectorItemInfo("period.number"));
		sic.add(new SelectorItemInfo("creator.name"));
		sic.add(new SelectorItemInfo("paymentType.id"));
		sic.add(new SelectorItemInfo("paymentType.number"));
		sic.add(new SelectorItemInfo("paymentType.name"));
		sic.add(new SelectorItemInfo("settlementType.id"));
		sic.add(new SelectorItemInfo("settlementType.number"));
		sic.add(new SelectorItemInfo("settlementType.name"));
		sic.add(new SelectorItemInfo("receiveUnit.id"));
		sic.add(new SelectorItemInfo("receiveUnit.number"));
		sic.add(new SelectorItemInfo("receiveUnit.name"));
		sic.add(new SelectorItemInfo("realSupplier.id"));
		sic.add(new SelectorItemInfo("realSupplier.number"));
		sic.add(new SelectorItemInfo("realSupplier.name"));
		
		sic.add(new SelectorItemInfo("contractType.id"));
		sic.add(new SelectorItemInfo("contractType.number"));
		sic.add(new SelectorItemInfo("contractType.name"));
				
		sic.add(new SelectorItemInfo("settlementType.id"));
		sic.add(new SelectorItemInfo("settlementType.number"));
		sic.add(new SelectorItemInfo("settlementType.name"));
		sic.add(new SelectorItemInfo("bankAcct.id"));
		sic.add(new SelectorItemInfo("bankAcct.number"));
		sic.add(new SelectorItemInfo("bankAcct.name"));
		sic.add(new SelectorItemInfo("planProject.id"));
		sic.add(new SelectorItemInfo("planProject.number"));
		sic.add(new SelectorItemInfo("planProject.name"));
		return sic;
    }

	

}
