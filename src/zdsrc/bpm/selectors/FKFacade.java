package com.kingdee.eas.bpm.selectors;

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
import com.kingdee.eas.bpm.BPMLogFactory;
import com.kingdee.eas.bpm.BPMLogInfo;
import com.kingdee.eas.bpm.BillBaseSelector;
import com.kingdee.eas.bpm.common.StringUtilBPM;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ChangeAuditBillFactory;
import com.kingdee.eas.fdc.contract.ChangeAuditBillInfo;
import com.kingdee.eas.fdc.contract.ChangeAuditEntryFactory;
import com.kingdee.eas.fdc.contract.ChangeAuditEntryInfo;
import com.kingdee.eas.fdc.contract.ChangeSupplierEntryFactory;
import com.kingdee.eas.fdc.contract.ChangeSupplierEntryInfo;
import com.kingdee.eas.fdc.contract.ContractSettlementBillFactory;
import com.kingdee.eas.fdc.contract.ContractSettlementBillInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillEntryFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillEntryInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fdc.finance.PaymentPrjPayEntryCollection;
import com.kingdee.eas.fdc.finance.PaymentPrjPayEntryFactory;
import com.kingdee.eas.fdc.finance.PaymentPrjPayEntryInfo;
import com.kingdee.eas.fi.cas.PaymentBillEntryFactory;
import com.kingdee.eas.fi.cas.PaymentBillEntryInfo;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.fm.be.ws.PaymentInfo;

public class FKFacade implements BillBaseSelector {

	public String[] ApproveClose(Context ctx, String strBSID,
			IObjectValue billInfo, int procInstID,
			String processInstanceResult, String strComment, Date dtTime) {
		PayRequestBillInfo Info = (PayRequestBillInfo)billInfo;
    	String[] str = new String[3];
    	str[0] = "Y";
		try {
			try{
				Info = PayRequestBillFactory.getLocalInstance(ctx).getPayRequestBillInfo(new ObjectUuidPK(Info.getId()),getSelectors());
			}catch (EASBizException e) {
				str[2] = "���ݵ���getSelectors��ȡ�������ݣ�����getSelectors�����������Ƿ���ȷ,���鿴������log��־��";
				e.printStackTrace();
			}
			try{
				if("1".equals(processInstanceResult)){
					if(FDCBillStateEnum.AUDITTING.equals(Info.getState()))
						Info.setState(FDCBillStateEnum.AUDITTED);
					else{
						str[2] = "����ͨ��ʧ�ܣ��ü�¼״̬���������У�";
						str[0] = "N";
					}
				}
				if("0".equals(processInstanceResult)){
					if(FDCBillStateEnum.AUDITTING.equals(Info.getState()))
						Info.setState(FDCBillStateEnum.INVALID);
					else{
						str[2] = "������ͨ��ʧ�ܣ��ü�¼״̬���������У�";
						str[0] = "N";
					}
				}
				if("2".equals(processInstanceResult)){
					if(FDCBillStateEnum.AUDITTING.equals(Info.getState()))
						Info.setState(FDCBillStateEnum.BACK);
					else{
						str[2] = "�������ʧ�ܣ��ü�¼״̬���������У�";
						str[0] = "N";
					}
				}
				if("3".equals(processInstanceResult)){
					if(FDCBillStateEnum.AUDITTING.equals(Info.getState()))
						Info.setState(FDCBillStateEnum.SAVED);
					else{
						str[2] = "����ʧ�ܣ��ü�¼״̬���������У�";
						str[0] = "N";
					}
				}
				String sql = " update t_con_payrequestbill set fState='"+Info.getState().getValue()+"' where fid='"+Info.getId()+"'";
				FDCSQLBuilder bu = new FDCSQLBuilder(ctx);
				bu.appendSql(sql);
				bu.executeUpdate(ctx);
			}
			catch (BOSException e) {
				str[2] = "���ݵ���stateֵ����״̬sqlʧ�ܣ�����getState�����Ƿ���ֵ,���鿴������log��־��";
				e.printStackTrace();
			}
		}catch (Exception e) {
			str[2] = "�����쳣����鿴������log��־��";
			e.printStackTrace();
		}finally{
			BPMLogInfo log = new BPMLogInfo();
			try {
				log.setLogDate(new Date());
				log.setName("��ͬ����ID��"+Info.getId()+"; ��ţ�"+Info.getNumber());
				log.setDescription("BPM�����"+processInstanceResult+"; EAS���:"+str[0]+"; ������Ϣ"+str[1]+str[2]);
				log.setSimpleName("BPM���̺ţ�"+procInstID+";BPM������Ϣ:"+strComment);
				log.setBeizhu("���ýӿڷ�����ApproveClose");
				BPMLogFactory.getLocalInstance(ctx).save(log);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return str;
	}

	public String[] GetbillInfo(Context ctx, String strBSID,
			IObjectValue billInfo) {
		PayRequestBillInfo Info = (PayRequestBillInfo)billInfo;
    	String[] str = new String[3];
    	str[0] = "Y";
    	StringBuffer xml = new StringBuffer();
		try {
			try{
				Info = PayRequestBillFactory.getLocalInstance(ctx).getPayRequestBillInfo(new ObjectUuidPK(Info.getId()),getSelectors());
			}catch (EASBizException e) {
				str[2] = "���ݵ���getSelectors��ȡ�������ݣ�����getSelectors�����������Ƿ���ȷ,���鿴������log��־��";
				e.printStackTrace();
			}
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			xml.append("<DATA>\n");     			
			xml.append("<bizdate>"+dateFormat.format(Info.getBizDate())+"</bizdate>\n");  //ҵ������
			xml.append("<State>"+Info.getState().getName()+"</State>\n");  //״̬
			xml.append("<Number>"+Info.getNumber()+"</Number>\n");//�������
			xml.append("<SNumber>"+Info.getNumber()+"</SNumber>\n");//���뵥����
			xml.append("<ContractNo>"+Info.getContractNo()+"</ContractNo>\n");//ƾ֤��
			xml.append("<Currency>"+StringUtilBPM.isNULl(Info.getCurrency().getName())+"</Currency>\n"); //�ұ�
			xml.append("<OriginalAmount>"+Info.getOriginalAmount()+"</OriginalAmount>\n");// ԭ��
			xml.append("<Amount>"+Info.getAmount()+"</Amount>\n");//��λ��
			xml.append("<exchangeRate>"+Info.getExchangeRate()+"</exchangeRate>\n");//����
			xml.append("<invoiceAmt>"+Info.getInvoiceAmt()+"</invoiceAmt>\n");//��Ʊ���
			if(Info.getInvoiceNumber()!=null)//��Ʊ��
				xml.append("<invoiceNumber>"+Info.getInvoiceNumber()+"</invoiceNumber>\n");
			xml.append("<PayDate>"+dateFormat.format(Info.getPayDate())+"</PayDate>\n");//��������
			xml.append("<Supplier>"+StringUtilBPM.isNULl(Info.getSupplier().getName())+"</Supplier>\n");//�տλ
			xml.append("<realSupplier>"+StringUtilBPM.isNULl(Info.getRealSupplier().getName())+"</realSupplier>\n");//ʵ���տλ
			xml.append("<Creator>"+StringUtilBPM.isNULl(Info.getCreator().getName())+"</Creator>\n");//�Ƶ���
			xml.append("<CreateTime>"+Info.getCreateTime()+"</CreateTime>\n");//�Ƶ�����
			xml.append("<Usage>"+Info.getUsage()+"</Usage>\n");//����˵��(��;)
			xml.append("<recBank>"+StringUtilBPM.isNULl(Info.getRecBank())+"</recBank>\n");//�տ�����
			xml.append("<recAccount>"+StringUtilBPM.isNULl(Info.getRecAccount())+"</RecAccount>\n");//�տ��˺�
			xml.append("<Auditor>"+StringUtilBPM.isNULl(Info.getAuditor().getName())+"</Auditor>\n");//�����
			xml.append("<AuditTime>"+dateFormat.format(Info.getAuditTime())+"</AuditTime>\n");//�������
			xml.append("</DATA>"); 
			str[1] = xml.toString();
		}catch (Exception e) {
			str[0] = "N";
			str[2] = "�����쳣����鿴������log��־��";
			e.printStackTrace();
		}finally{
			BPMLogInfo log = new BPMLogInfo();
			try {
				log.setLogDate(new Date());
				log.setName("��ͬ����ID��"+Info.getId()+"; ��ţ�"+Info.getNumber());
				log.setDescription("EAS���:"+str[0]+"; ������Ϣ"+str[1]+str[2]);
				log.setBeizhu("���ýӿڷ�����GetbillInfo");
				BPMLogFactory.getLocalInstance(ctx).save(log);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return str;
	}

	public String[] SubmitResult(Context ctx, String strBSID,
			IObjectValue billInfo, boolean success, int procInstID,
			String procURL, String strMessage) {
		PayRequestBillInfo Info = (PayRequestBillInfo)billInfo;
    	String[] str = new String[3];
    	str[0] = "Y";
		try {
			try{
				Info = PayRequestBillFactory.getLocalInstance(ctx).getPayRequestBillInfo(new ObjectUuidPK(Info.getId()),getSelectors());
			}catch (EASBizException e) {
				str[0] = "N";
				str[2] = "���ݵ���getSelectors��ȡ�������ݣ�����getSelectors�����������Ƿ���ȷ,���鿴������log��־��";
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
				str[2] = "���ݵ���stateֵ����״̬sqlʧ�ܣ�����getState�����Ƿ���ֵ,���鿴������log��־��";
				e.printStackTrace();
			}
		}catch (Exception e) {
			str[0] = "N";
			str[2] = "�����쳣����鿴������log��־��";
			e.printStackTrace();
		}finally{
			BPMLogInfo log = new BPMLogInfo();
			try {
				log.setLogDate(new Date());
				log.setName("��ͬ����ID��"+Info.getId()+"; ��ţ�"+Info.getNumber());
				log.setDescription("BPM�����"+success+"; EAS���:"+str[0]+"; ������Ϣ:"+str[1]+str[2]);
				log.setSimpleName("BPM���̺ţ�"+procInstID+";����URL:"+procURL);
				log.setBeizhu("���ýӿڷ�����_SubmitResult");
				BPMLogFactory.getLocalInstance(ctx).save(log);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return str;
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("bizdate"));
		sic.add(new SelectorItemInfo("State.id"));
		sic.add(new SelectorItemInfo("State.name"));
		sic.add(new SelectorItemInfo("State.number"));
		sic.add(new SelectorItemInfo("Number"));
		sic.add(new SelectorItemInfo("ContractNo"));
		sic.add(new SelectorItemInfo("Currency.id"));
		sic.add(new SelectorItemInfo("Currency.name"));
		sic.add(new SelectorItemInfo("Currency.number"));
		sic.add(new SelectorItemInfo("OriginalAmount"));
		sic.add(new SelectorItemInfo("Amount"));
		sic.add(new SelectorItemInfo("exchangeRate"));
		sic.add(new SelectorItemInfo("invoiceAmt"));
		sic.add(new SelectorItemInfo("invoiceNumber"));
		
		sic.add(new SelectorItemInfo("PayDate"));
		sic.add(new SelectorItemInfo("Supplier"));
		sic.add(new SelectorItemInfo("realSupplier.id"));
		sic.add(new SelectorItemInfo("realSupplier.name"));
		sic.add(new SelectorItemInfo("realSupplier.number"));
		sic.add(new SelectorItemInfo("Creator.id"));
		sic.add(new SelectorItemInfo("Creator.name"));
		sic.add(new SelectorItemInfo("Creator.number"));
		sic.add(new SelectorItemInfo("CreateTime"));
		sic.add(new SelectorItemInfo("Usage"));
		sic.add(new SelectorItemInfo("recBank"));
		sic.add(new SelectorItemInfo("recAccount"));
		sic.add(new SelectorItemInfo("Auditor.id"));
		sic.add(new SelectorItemInfo("Auditor.name"));
		sic.add(new SelectorItemInfo("Auditor.number"));
		sic.add(new SelectorItemInfo("AuditTime"));
		return sic;
	}

	public String[] ApproveBack(Context ctx, String strBTID, String strBOID,
			String strXML) {
		// TODO Auto-generated method stub
		return null;
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
