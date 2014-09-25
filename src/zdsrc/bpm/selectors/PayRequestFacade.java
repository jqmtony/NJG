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


	public String[] GetbillInfo(Context ctx, String strBSID, IObjectValue billInfo) {
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
			try{
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    			xml.append("<DATA>\n"); 
    			xml.append("<OrgName>"+StringUtilBPM.isNULl(Info.getOrgUnit().getName())+"</OrgName>\n");
    			xml.append("<useDepartment>"+StringUtilBPM.isNULl(Info.getUseDepartment().getName())+"</useDepartment>\n");
    			xml.append("<ApplyDate>"+dateFormat.format(Info.getCreateTime())+"</ApplyDate>\n");
    			xml.append("<Applicant>"+StringUtilBPM.isNULl(Info.getCreator().getName())+"</Applicant>\n");
    			
    			xml.append("<Position>��Լ������</Position>\n");
    			xml.append("<Topic>"+StringUtilBPM.isNULl(Info.getName())+"-��������������"+"</Topic>\n");
    			xml.append("<curProject>"+StringUtilBPM.isNULl(Info.getCurProject().getName())+"</curProject>\n");
    			xml.append("<contractNo>"+StringUtilBPM.isNULl(Info.getContractNo())+"</contractNo>\n");
    			xml.append("<bizdate>"+dateFormat.format(Info.getBizDate())+"</bizdate>\n");
    			xml.append("<payDate>"+dateFormat.format(Info.getPayDate())+"</payDate>\n");//��������
    			xml.append("<period>"+Info.getPeriod().getNumber()+"</period>\n");//�����ڼ�
    			xml.append("<isdifferplace>"+dateFormat.format(Info.getIsDifferPlace().getName())+"</isdifferplace>\n");//ͬ�����
    			xml.append("<number>"+StringUtilBPM.isNULl(Info.getNumber())+"</number>\n");
    			xml.append("<PaymentType>"+StringUtilBPM.isNULl(Info.getPaymentType().getName())+"</PaymentType>\n");//��������
    			xml.append("<settlementType>"+StringUtilBPM.isNULl(Info.getSettlementType().getName())+"</settlementType>\n");//��ͬ����
    			xml.append("<receiveUnit>"+StringUtilBPM.isNULl(Info.getSupplier().getName())+"</receiveUnit>\n");//�տλ
    			xml.append("<recBank>"+StringUtilBPM.isNULl(Info.getRecBank())+"</recBank>\n");//�տ�����
    			xml.append("<recAccount>"+StringUtilBPM.isNULl(Info.getRecAccount())+"</recAccount>\n");//�տ�����
    			xml.append("<realSupplier>"+StringUtilBPM.isNULl(Info.getRealSupplier().getName())+"</realSupplier>\n");//ʵ���տλ
    			xml.append("<Desc>"+StringUtilBPM.isNULl(Info.getDescription())+"</Desc>\n");//�տ�����
    			xml.append("<usage>"+StringUtilBPM.isNULl(Info.getUsage())+"</usage>\n");//��;
    			xml.append("<Desc>"+StringUtilBPM.isNULl(Info.getDescription())+"</Desc>\n");//�տ�����
    			xml.append("<Desc>"+StringUtilBPM.isNULl(Info.getDescription())+"</Desc>\n");//�տ�����
    			
    			
    			
    			xml.append("<currency>"+StringUtilBPM.isNULl(Info.getCurrency().getName())+"</currency>\n");//�ұ�
    			xml.append("<exchangeRate>"+Info.getExchangeRate()+"</exchangeRate>\n");//����
    			xml.append("<originalAmount>"+Info.getOriginalAmount()+"</originalAmount>\n");//ԭ�ҽ��
    			xml.append("<invoiceAmt>"+Info.getInvoiceAmt()+"</invoiceAmt>\n");//��Ʊ���
    			xml.append("<allinvoiceAmt>"+Info.getAllInvoiceAmt()+"</allinvoiceAmt>\n");//�ۼƷ�Ʊ���
    			xml.append("<amount>"+Info.getAmount()+"</amount>\n");//���ҽ��
    			xml.append("<grtAmount>"+Info.getGrtAmount()+"</grtAmount>\n");//���ҽ��
    			xml.append("<capitalAmount>"+Info.getCapitalAmount()+"</capitalAmount>\n");//��д���
    			
    			xml.append("<isRespite>"+dateFormat.format(Info.getUrgentDegree().getName())+"</isRespite>\n");//�Ƿ�Ӽ�
    			xml.append("<IsPay>"+dateFormat.format(Info.isIsPay())+"</IsPay>\n");//�ύ����
    			
    			if(Info.getPaymentProportion()!=null)//���ȿ�����
    				xml.append("<paymentProportion>"+Info.getPaymentProportion()+"</paymentProportion>\n");
    			if(Info.getCompletePrjAmt()!=null)//������ɹ�����
    				xml.append("<completePrjAmt>"+Info.getCompletePrjAmt()+"</completePrjAmt>\n");
    			if(Info.getTotalSettlePrice()!=null)//��ʵ�ֲ�ֵ
    				xml.append("<TotalSettlePrice>"+Info.getTotalSettlePrice()+"</TotalSettlePrice>\n");
    			if(Info.getInvoiceNumber()!=null)//��Ʊ��
    				xml.append("<invoiceNumber>"+Info.getInvoiceNumber()+"</invoiceNumber>\n");
    			if(Info.getInvoiceOriAmt()!=null)//��Ʊ���ԭ��
    				xml.append("<invoiceOriAmt>"+Info.getInvoiceOriAmt()+"</invoiceOriAmt>\n");
    			if(Info.getInvoiceDate()!=null)//��Ʊ����
    				xml.append("<invoiceDate>"+dateFormat.format(Info.getInvoiceDate())+"</invoiceDate>\n");
    			if(Info.getCompletePrjAmt()!=null)//�ۼ�����ɹ�����
    				xml.append("<AllCompletePrjAmt>"+Info.getCompletePrjAmt()+"</AllCompletePrjAmt>\n");
    			if(Info.getAllReqPercent()!=null)//�ۼ�Ӧ���������
    				xml.append("<AllPaymentProportion>"+Info.getAllReqPercent()+"</AllPaymentProportion>\n");
    			if(Info.getAllInvoiceOriAmt()!=null)//�ۼƷ�Ʊԭ��/����
    				xml.append("<allinvoiceOriAmt>"+Info.getAllInvoiceOriAmt()+"</allinvoiceOriAmt>\n");
    			if(Info.getPlanHasCon()!=null)//Ԥ����Ŀ
    				xml.append("<PlanHasCon>"+Info.getPlanHasCon()+"</PlanHasCon>\n");
    			if(Info.getMoneyDesc()!=null)//��ע
    				xml.append("<MoneyDesc>"+Info.getMoneyDesc()+"</MoneyDesc>\n");
    			
    			
    			xml.append("<billEntries>\n");
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
    			xml.append("</billEntries>\n");
    			
                xml.append("</DATA>"); 
                str[1] = xml.toString();
			}
			catch (BOSException e) {
				str[0] = "N";
				str[2] = "��ȡ��������ʧ�ܣ����������Ƿ���ֵ,���鿴������log��־!";
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
				log.setDescription("EAS���:"+str[0]+"; ������Ϣ"+str[1]+str[2]);
				log.setBeizhu("���ýӿڷ�����GetbillInfo");
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
		sic.add(new SelectorItemInfo("supplier.id"));
		sic.add(new SelectorItemInfo("supplier.number"));
		sic.add(new SelectorItemInfo("supplier.name"));
		sic.add(new SelectorItemInfo("realSupplier.id"));
		sic.add(new SelectorItemInfo("realSupplier.number"));
		sic.add(new SelectorItemInfo("realSupplier.name"));
		
		sic.add(new SelectorItemInfo("recAccount.id"));
		sic.add(new SelectorItemInfo("recAccount.number"));
		sic.add(new SelectorItemInfo("recAccount.name"));
		
		sic.add(new SelectorItemInfo("planHasCon.id"));
		sic.add(new SelectorItemInfo("planHasCon.number"));
		sic.add(new SelectorItemInfo("planHasCon.name"));
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
