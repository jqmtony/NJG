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

public class CompensationFacade implements BillBaseSelector{

	public String[] ApproveBack(Context ctx, String strBTID,
			IObjectValue billInfo, String strXML) {
		// TODO Auto-generated method stub
		return null;
	}

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
				String sql = " update T_CON_CompensationBill set fState='"+Info.getState().getValue()+"' where fid='"+Info.getId()+"'";
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
    			xml.append("<OrgName>"+StringUtilBPM.isNULl(Info.getOrgUnit().getName())+"</OrgName>\n");
    			//xml.append("<OrgName>"+StringUtilBPM.isNULl(Info.getLandDeveloper().getName())+"</OrgName>\n");
    			xml.append("<DeptName>"+StringUtilBPM.isNULl(Info.getRespDept().getName())+"</DeptName>\n");
    			xml.append("<respDept>"+StringUtilBPM.isNULl(Info.getRespDept().getName())+"</respDept>\n");
    			xml.append("<ApplyDate>"+dateFormat.format(Info.getCreateTime())+"</ApplyDate>\n");
    			xml.append("<Applicant>"+StringUtilBPM.isNULl(Info.getCreator().getName())+"</Applicant>\n");
    			xml.append("<ApplicantID>"+StringUtilBPM.isNULl(Info.getCreator().getName())+"</ApplicantID>\n");
    			if(false==Info.isIsPartAMaterialCon())
    			xml.append("<isPartAMaterialCon>��</isPartAMaterialCon>\n");
    			else
    			{
    				xml.append("<isPartAMaterialCon>��</isPartAMaterialCon>\n");
    			}
    			xml.append("<creator>"+StringUtilBPM.isNULl(Info.getCreator().getName())+"</creator>\n");
    			xml.append("<createTime>"+dateFormat.format(Info.getCreateTime())+"</createTime>\n");
    			//xml.append("<createTime>"+Info.getCreator()+"</createTime>\n");
    			
    			xml.append("<Topic>"+StringUtilBPM.isNULl(Info.getName())+"</Topic>\n");
    			xml.append("<CompanyName>"+StringUtilBPM.isNULl(Info.getLandDeveloper().getName())+"</CompanyName>\n");
    			xml.append("<Phase>"+StringUtilBPM.isNULl(Info.getCurProject().getName())+"</Phase>\n");
    			xml.append("<OrgCode>"+StringUtilBPM.isNULl(Info.getOrgUnit().getNumber().split("-")[0])+"</OrgCode>\n");
    			
    			xml.append("<contactNumber>"+StringUtilBPM.isNULl(Info.getNumber())+"</contactNumber>\n");
    			xml.append("<contractName>"+StringUtilBPM.isNULl(Info.getName())+"</contractName>\n");
    			//FullOrgUnitInfo costOrg =orgUnitInfo;
    			//costOrg.getDisplayName();
    			//xml.append("<contractName>"+StringUtilBPM.isNULl(Info)+"</contractName>\n");
    			xml.append("<partA>"+StringUtilBPM.isNULl(Info.getLandDeveloper().getName())+"</partA>\n");
    			xml.append("<partB>"+StringUtilBPM.isNULl(Info.getPartB().getName())+"</partB>\n");
    			if(Info.getPartC()!=null)
    				xml.append("<partC>"+StringUtilBPM.isNULl(Info.getPartC().getName())+"</partC>\n");
    			if(Info.getProgrammingContract()!=null)
    				xml.append("<programmingContract>"+StringUtilBPM.isNULl(Info.getProgrammingContract().getName())+"</programmingContract>\n");
    			if(Info.getProgrammingContract()!=null)
    				xml.append("<controlBalance>"+Info.getProgrammingContract().getControlBalance()+"</controlBalance>\n");
    				xml.append("<bizDate>"+Info.getBookedDate()+"</bizDate>\n");
    			if(Info.getGrtRate()!=null)
    				xml.append("<grtRate>"+Info.getGrtRate()+"</grtRate>\n");
    			if(Info.getGrtAmount()!=null)
    				xml.append("<grtAmount>"+Info.getGrtAmount()+"</grtAmount>\n");
    			xml.append("<period>"+Info.getPeriod().getNumber()+"</period>\n");
    			xml.append("<contractSource>"+StringUtilBPM.isNULl(Info.getContractSourceId().getName())+"</contractSource>\n");
    			xml.append("<costProperty>"+StringUtilBPM.isNULl(Info.getCostProperty().getAlias())+"</costProperty>\n");
    			xml.append("<chgPercForWarn>"+Info.getChgPercForWarn()+"</chgPercForWarn>\n");
   				xml.append("<overRate>"+Info.getOverRate()+"</overRate>\n");
    			if(Info.getPaidPartAMatlAmt()!=null)
    				xml.append("<payScale>"+Info.getPaidPartAMatlAmt()+"</payScale>\n");
    			if(Info.getStampTaxAmt()!=null)
    				xml.append("<stampTaxAmt>"+Info.getStampTaxAmt()+"</stampTaxAmt>\n");
    			if(Info.getStampTaxRate()!=null)
    				xml.append("<stampTaxRate>"+Info.getStampTaxRate()+"</stampTaxRate>\n");
    			xml.append("<contractTypeName>"+StringUtilBPM.isNULl(Info.getContractType().getName())+"</contractTypeName>\n");
    			xml.append("<contractTypeNumber>"+StringUtilBPM.isNULl(Info.getContractType().getNumber())+"</contractTypeNumber>\n");
    			xml.append("<contractAmount>"+Info.getAmount()+"</contractAmount>\n");
    			xml.append("<responsiblePerson>"+StringUtilBPM.isNULl(Info.getRespPerson().getName())+"</responsiblePerson>\n");
    			if(Info.getSignDate()!=null)
    				xml.append("<signDate>"+Info.getSignDate()+"</signDate>\n");
    			if(Info.getOriginalAmount()!=null)
    				xml.append("<contractCurrencyAmount>"+Info.getOriginalAmount()+"</contractCurrencyAmount>\n");
    			xml.append("<contractNature>"+StringUtilBPM.isNULl(Info.getContractPropert().getAlias())+"</contractNature>\n");
    			xml.append("<currencyType>"+StringUtilBPM.isNULl(Info.getCurrency().getName())+"</currencyType>\n");
    			xml.append("<rate>"+Info.getExRate()+"</rate>\n");
    			if(false==Info.isIsCoseSplit())
    			xml.append("<isCoseSplit>��</isCoseSplit>\n");
    			else
    			{
    				xml.append("<isCoseSplit>��</isCoseSplit>\n");
    			}
    			
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

	public String[] GetrRelatedBillInfo(Context ctx, String strBTID,
			IObjectValue billInfo, String strRelatedCode) {
		// TODO Auto-generated method stub
		return null;
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
				String sql = " update T_CON_CompensationBill set fState='"+Info.getState().getValue()+"'" +
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
		sic.add(new SelectorItemInfo("isCoseSplit"));
		sic.add(new SelectorItemInfo("isPartAMaterialCon"));
		sic.add(new SelectorItemInfo("contractPropert"));
		sic.add(new SelectorItemInfo("signDate"));
		sic.add(new SelectorItemInfo("state"));
		sic.add(new SelectorItemInfo("orgUnit.id"));
		sic.add(new SelectorItemInfo("orgUnit.number"));
		sic.add(new SelectorItemInfo("orgUnit.name"));
		sic.add(new SelectorItemInfo("creator.id"));
		sic.add(new SelectorItemInfo("creator.number"));
		sic.add(new SelectorItemInfo("creator.name"));
		sic.add(new SelectorItemInfo("curProject.id"));
		sic.add(new SelectorItemInfo("curProject.number"));
		sic.add(new SelectorItemInfo("curProject.name"));
		 sic.add(new SelectorItemInfo("partC.id"));
		 sic.add(new SelectorItemInfo("partC.number"));
		 sic.add(new SelectorItemInfo("partC.name"));
		 sic.add(new SelectorItemInfo("currency.id"));
		 sic.add(new SelectorItemInfo("currency.number"));
		 sic.add(new SelectorItemInfo("currency.name"));
				
		sic.add(new SelectorItemInfo("respDept.id"));
		sic.add(new SelectorItemInfo("respDept.number"));
		sic.add(new SelectorItemInfo("respDept.name"));
		sic.add(new SelectorItemInfo("respPerson.id"));
		sic.add(new SelectorItemInfo("respPerson.number"));
		sic.add(new SelectorItemInfo("respPerson.name"));
		sic.add(new SelectorItemInfo("bookedDate"));
		sic.add(new SelectorItemInfo("period.id"));
		sic.add(new SelectorItemInfo("period.number"));
		sic.add(new SelectorItemInfo("createTime"));
		sic.add(new SelectorItemInfo("stampTaxAmt"));
		sic.add(new SelectorItemInfo("stampTaxRate"));
		sic.add(new SelectorItemInfo("grtRate"));
		sic.add(new SelectorItemInfo("originalAmount"));
		sic.add(new SelectorItemInfo("currency"));
		sic.add(new SelectorItemInfo("costProperty"));
		sic.add(new SelectorItemInfo("contractSourceId.id"));
	    sic.add(new SelectorItemInfo("contractSourceId.number"));
		sic.add(new SelectorItemInfo("contractSourceId.name"));
		sic.add(new SelectorItemInfo("exRate"));
		sic.add(new SelectorItemInfo("amount"));
		sic.add(new SelectorItemInfo("grtAmount"));
		sic.add(new SelectorItemInfo("chgPercForWarn"));
		sic.add(new SelectorItemInfo("payScale"));
		sic.add(new SelectorItemInfo("overRate"));
		sic.add(new SelectorItemInfo("ceremonyb"));
		sic.add(new SelectorItemInfo("ceremonybb"));
		sic.add(new SelectorItemInfo("payPercForWarn"));
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("landDeveloper.id"));
		sic.add(new SelectorItemInfo("landDeveloper.number"));
		sic.add(new SelectorItemInfo("landDeveloper.name"));
		sic.add(new SelectorItemInfo("contractType.id"));
		sic.add(new SelectorItemInfo("contractType.number"));
		sic.add(new SelectorItemInfo("contractType.name"));
		sic.add(new SelectorItemInfo("partB.id"));
		sic.add(new SelectorItemInfo("partB.number"));
		sic.add(new SelectorItemInfo("partB.name"));
		sic.add(new SelectorItemInfo("partC.id"));
		sic.add(new SelectorItemInfo("partC.number"));
		sic.add(new SelectorItemInfo("partC.name"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("isSubContract"));
		sic.add(new SelectorItemInfo("lowestPriceUnit.id"));
		sic.add(new SelectorItemInfo("lowestPriceUnit.number"));
		sic.add(new SelectorItemInfo("lowestPriceUnit.name"));
		sic.add(new SelectorItemInfo("lowerPriceUnit.id"));
		sic.add(new SelectorItemInfo("lowerPriceUnit.number"));
		sic.add(new SelectorItemInfo("lowerPriceUnit.name"));
		sic.add(new SelectorItemInfo("middlePriceUnit.id"));
		sic.add(new SelectorItemInfo("middlePriceUnit.number"));
		sic.add(new SelectorItemInfo("middlePriceUnit.name"));
		sic.add(new SelectorItemInfo("higherPriceUnit.id"));
		sic.add(new SelectorItemInfo("higherPriceUnit.number"));
		sic.add(new SelectorItemInfo("higherPriceUnit.name"));
		sic.add(new SelectorItemInfo("highestPriceUni.id"));
		sic.add(new SelectorItemInfo("highestPriceUni.number"));
		sic.add(new SelectorItemInfo("highestPriceUni.name"));
		sic.add(new SelectorItemInfo("remark"));
		sic.add(new SelectorItemInfo("coopLevel"));
		sic.add(new SelectorItemInfo("priceType"));
		sic.add(new SelectorItemInfo("mainContract.id"));
		sic.add(new SelectorItemInfo("mainContract.number"));
		sic.add(new SelectorItemInfo("mainContract.name"));
		sic.add(new SelectorItemInfo("effectiveStartDate"));
		sic.add(new SelectorItemInfo("effectiveEndDate"));
		sic.add(new SelectorItemInfo("information"));
		sic.add(new SelectorItemInfo("lowestPrice"));
		sic.add(new SelectorItemInfo("lowerPrice"));
		sic.add(new SelectorItemInfo("higherPrice"));
		sic.add(new SelectorItemInfo("middlePrice"));
		sic.add(new SelectorItemInfo("highestPrice"));
		sic.add(new SelectorItemInfo("basePrice"));
		sic.add(new SelectorItemInfo("secondPrice"));
		sic.add(new SelectorItemInfo("inviteType.id"));
		sic.add(new SelectorItemInfo("inviteType.number"));
		sic.add(new SelectorItemInfo("inviteType.name"));
		sic.add(new SelectorItemInfo("winUnit.id"));
		sic.add(new SelectorItemInfo("winUnit.number"));
		sic.add(new SelectorItemInfo("winUnit.name"));
		sic.add(new SelectorItemInfo("fileNo"));
		sic.add(new SelectorItemInfo("quantity"));
		sic.add(new SelectorItemInfo("conChargeType.id"));
		sic.add(new SelectorItemInfo("conChargeType.number"));
		sic.add(new SelectorItemInfo("conChargeType.name"));
		sic.add(new SelectorItemInfo("model.id"));
		sic.add(new SelectorItemInfo("model.number"));
		sic.add(new SelectorItemInfo("model.name"));
		sic.add(new SelectorItemInfo("model.attachID"));
		sic.add(new SelectorItemInfo("programmingContract.id"));
		sic.add(new SelectorItemInfo("programmingContract.number"));
		sic.add(new SelectorItemInfo("programmingContract.name"));
		sic.add(new SelectorItemInfo("programmingContract.controlBalance"));
		sic.add(new SelectorItemInfo("payItems.payItemDate"));
		sic.add(new SelectorItemInfo("payItems.payCondition"));
		sic.add(new SelectorItemInfo("payItems.prop"));
		sic.add(new SelectorItemInfo("payItems.amount"));
		sic.add(new SelectorItemInfo("payItems.desc"));
		sic.add(new SelectorItemInfo("payItems.paymentType.*"));
		sic.add(new SelectorItemInfo("payItems.paymentType.id"));
		sic.add(new SelectorItemInfo("payItems.paymentType.name"));
		sic.add(new SelectorItemInfo("payItems.paymentType.number"));
		sic.add(new SelectorItemInfo("bail.entry.bailDate"));
		sic.add(new SelectorItemInfo("bail.entry.bailConditon"));
		sic.add(new SelectorItemInfo("bail.entry.prop"));
		sic.add(new SelectorItemInfo("bail.entry.amount"));
		sic.add(new SelectorItemInfo("bail.entry.desc"));
		sic.add(new SelectorItemInfo("bail.entry.id"));
		sic.add(new SelectorItemInfo("bail.amount"));
		sic.add(new SelectorItemInfo("bail.prop"));
		return sic;
	}

}
