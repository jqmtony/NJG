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
import com.kingdee.eas.fdc.contract.ContractBailEntryFactory;
import com.kingdee.eas.fdc.contract.ContractBailEntryInfo;
import com.kingdee.eas.fdc.contract.ContractBillEntryFactory;
import com.kingdee.eas.fdc.contract.ContractBillEntryInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractPayItemFactory;
import com.kingdee.eas.fdc.contract.ContractPayItemInfo;

public class CBFacade implements BillBaseSelector {

	public String[] ApproveClose(Context ctx, String strBSID,
			IObjectValue billInfo, int procInstID,
			String processInstanceResult, String strComment, Date dtTime) {
		ContractBillInfo Info = (ContractBillInfo)billInfo;
    	String[] str = new String[3];
    	str[0] = "Y";
		try {
			try{
				Info = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(Info.getId()),getSelectors());
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
				String sql = " update t_con_contractbill set fState='"+Info.getState().getValue()+"' where fid='"+Info.getId()+"'";
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
		ContractBillInfo Info = (ContractBillInfo)billInfo;
    	String[] str = new String[3];
    	str[0] = "Y";
    	StringBuffer xml = new StringBuffer();
		try {
			try{
				Info = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(Info.getId()),getSelectors());
			}catch (EASBizException e) {
				str[2] = "���ݵ���getSelectors��ȡ�������ݣ�����getSelectors�����������Ƿ���ȷ,���鿴������log��־��";
				e.printStackTrace();
			}
			try{
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    			xml.append("<DATA>\n");
    			
    			xml.append("<isCoseSplit>"+Info.isIsCoseSplit()+"</isCoseSplit>\n");
    			xml.append("<billEntries>\n");
    			for(int i=0;i<Info.getEntrys().size();i++){
    				ContractBillEntryInfo entry = Info.getEntrys().get(i);
    				entry = ContractBillEntryFactory.getLocalInstance(ctx).getContractBillEntryInfo(new ObjectUuidPK(entry.getId()));
    				xml.append("<item>\n");
    					xml.append("<detial>"+StringUtilBPM.isNULl(entry.getDetail())+"</detial>\n");
    					xml.append("<content>"+StringUtilBPM.isNULl(entry.getContent())+"</content>\n");
    					xml.append("<remark>"+StringUtilBPM.isNULl(entry.getDesc())+"</remark>\n");
    				xml.append("</item>\n");
    			}
    			xml.append("</billEntries>\n");
    			
    			xml.append("<ContractPayItem>\n");
    			for(int i=0;i<Info.getPayItems().size();i++){
    				ContractPayItemInfo entry = Info.getPayItems().get(i);
    				entry = ContractPayItemFactory.getLocalInstance(ctx).getContractPayItemInfo(new ObjectUuidPK(entry.getId()));
    				xml.append("<item>\n");
    					xml.append("<payItemDate>"+entry.getPayItemDate()+"</payItemDate>\n");
    					if(entry.getPaymentType()!=null){
    						PaymentTypeInfo payINfo = PaymentTypeFactory.getLocalInstance(ctx).getPaymentTypeInfo(new ObjectUuidPK(entry.getPaymentType().getId()));
    						xml.append("<paymentType>"+StringUtilBPM.isNULl(payINfo.getName())+"</paymentType>\n");
    					}
    					xml.append("<payCondition>"+StringUtilBPM.isNULl(entry.getPayCondition())+"</payCondition>\n");
    					xml.append("<prop>"+entry.getProp()+"</prop>\n");
    					xml.append("<amount>"+entry.getAmount()+"</amount>\n");
    					xml.append("<remark>"+StringUtilBPM.isNULl(entry.getDesc())+"</remark>\n");
    				xml.append("</item>\n");
    			}
    			xml.append("</ContractPayItem>\n");
    			
    			xml.append("<ContractBailEntry>\n");
    			for(int i=0;i<Info.getBail().getEntry().size();i++){
    				ContractBailEntryInfo entry = Info.getBail().getEntry().get(i);
    				entry = ContractBailEntryFactory.getLocalInstance(ctx).getContractBailEntryInfo(new ObjectUuidPK(entry.getId()));
    				xml.append("<item>\n");
	    				xml.append("<bailDate>"+entry.getBailDate()+"</bailDate>\n");
	    				xml.append("<bailConditon>"+StringUtilBPM.isNULl(entry.getBailConditon())+"</bailConditon>\n");
						xml.append("<prop>"+entry.getProp()+"</prop>\n");
						xml.append("<amount>"+entry.getAmount()+"</amount>\n");
						xml.append("<remark>"+StringUtilBPM.isNULl(entry.getDesc())+"</remark>\n");
    				xml.append("</item>\n");
    			}
    			xml.append("</ContractBailEntry>\n");
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

	public String[] SubmitResult(Context ctx, String strBSID,
			IObjectValue billInfo, boolean success, int procInstID,
			String procURL, String strMessage) {
		ContractBillInfo Info = (ContractBillInfo)billInfo;
    	String[] str = new String[3];
    	str[0] = "Y";
		try {
			try{
				Info = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(Info.getId()),getSelectors());
			}catch (EASBizException e) {
				str[0] = "N";
				str[2] = "���ݵ���getSelectors��ȡ�������ݣ�����getSelectors�����������Ƿ���ȷ,���鿴������log��־��";
				e.printStackTrace();
			}
			try{
				Info.setState(FDCBillStateEnum.AUDITTING);
				String sql = " update t_con_contractbill set fState='"+Info.getState().getValue()+"'" +
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
		sic.add(new SelectorItemInfo("bail.prop"));
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
