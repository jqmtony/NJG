package com.kingdee.eas.bpm.selectors;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.derby.iapi.store.raw.Compensation;
import org.omg.CORBA.Object;

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
import com.kingdee.eas.fdc.basedata.PaymentTypeFactory;
import com.kingdee.eas.fdc.basedata.PaymentTypeInfo;
import com.kingdee.eas.fdc.contract.CompensationBill;
import com.kingdee.eas.fdc.contract.CompensationBillCollection;
import com.kingdee.eas.fdc.contract.CompensationBillFactory;
import com.kingdee.eas.fdc.contract.CompensationBillInfo;
import com.kingdee.eas.fdc.contract.ContractBailEntryFactory;
import com.kingdee.eas.fdc.contract.ContractBailEntryInfo;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractPayItemFactory;
import com.kingdee.eas.fdc.contract.ContractPayItemInfo;
import com.kingdee.eas.fdc.contract.ContractSettlementBillFactory;
import com.kingdee.eas.fdc.contract.ContractSettlementBillInfo;
import com.kingdee.eas.fdc.contract.GuerdonBillCollection;
import com.kingdee.eas.fdc.contract.GuerdonBillFactory;
import com.kingdee.eas.fdc.contract.GuerdonBillInfo;
import com.kingdee.eas.fdc.finance.DeductBillCollection;
import com.kingdee.eas.fdc.finance.DeductBillEntryCollection;
import com.kingdee.eas.fdc.finance.DeductBillEntryFactory;
import com.kingdee.eas.fdc.finance.DeductBillEntryInfo;
import com.kingdee.eas.fdc.finance.DeductBillFactory;
import com.kingdee.eas.fdc.finance.DeductBillInfo;
import com.kingdee.eas.fdc.finance.PaymentPrjPayEntryCollection;
import com.kingdee.eas.fdc.finance.PaymentPrjPayEntryFactory;
import com.kingdee.eas.fdc.finance.PaymentPrjPayEntryInfo;
import com.kingdee.eas.fi.cas.PaymentBillCollection;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.fm.be.ws.PaymentInfo;

public class SettleMentFacade implements BillBaseSelector {

	public String[] ApproveClose(Context ctx, String strBSID,
			IObjectValue billInfo, int procInstID,
			String processInstanceResult, String strComment, Date dtTime) {
		ContractSettlementBillInfo Info = (ContractSettlementBillInfo) billInfo;
		String[] str = new String[3];
		str[0] = "Y";
		try {
			try {
				Info = ContractSettlementBillFactory.getLocalInstance(ctx)
						.getContractSettlementBillInfo(
								new ObjectUuidPK(Info.getId()), getSelectors());
			} catch (EASBizException e) {
				str[2] = "���ݵ���Selectors��ȡ�������ݣ�����Selectors�����������Ƿ���ȷ,���鿴������log��־��";
				e.printStackTrace();
			}
			try {
				if ("1".equals(processInstanceResult)) {
					if (FDCBillStateEnum.AUDITTING.equals(Info.getState()))
						Info.setState(FDCBillStateEnum.AUDITTED);
					else {
						str[2] = "����ͨ��ʧ�ܣ��ü�¼״̬���������У�";
						str[0] = "N";
					}
				}
				if ("0".equals(processInstanceResult)) {
					if (FDCBillStateEnum.AUDITTING.equals(Info.getState()))
						Info.setState(FDCBillStateEnum.SAVED);
					else {
						str[2] = "������ͨ��ʧ�ܣ��ü�¼״̬���������У�";
						str[0] = "N";
					}
				}
				if ("2".equals(processInstanceResult)) {
					if (FDCBillStateEnum.AUDITTING.equals(Info.getState()))
						Info.setState(FDCBillStateEnum.SAVED);
					else {
						str[2] = "�������ʧ�ܣ��ü�¼״̬���������У�";
						str[0] = "N";
					}
				}
				if ("3".equals(processInstanceResult)) {
					if (FDCBillStateEnum.AUDITTING.equals(Info.getState()))
						Info.setState(FDCBillStateEnum.SAVED);
					else {
						str[2] = "����ʧ�ܣ��ü�¼״̬���������У�";
						str[0] = "N";
					}
				}
				String sql = " update T_CON_ContractSettlementBill set fState='"
						+ Info.getState().getValue() + "' where fid='"
						+ Info.getId() + "'";
				FDCSQLBuilder bu = new FDCSQLBuilder(ctx);
				bu.appendSql(sql);
				bu.executeUpdate(ctx);
			} catch (BOSException e) {
				str[2] = "���ݵ���stateֵ����״̬sqlʧ�ܣ�����getState�����Ƿ���ֵ,���鿴������log��־��";
				e.printStackTrace();
			}
		} catch (Exception e) {
			str[2] = "�����쳣����鿴������log��־��";
			e.printStackTrace();
		} finally {
			BPMLogInfo log = new BPMLogInfo();
			try {
				log.setLogDate(new Date());
				log.setName("��ͬ���㵥��ID��" + Info.getId() + "; ��ţ�"
						+ Info.getNumber());
				log.setDescription("BPM�����" + processInstanceResult
						+ "; EAS���:" + str[0] + "; ������Ϣ" + str[1] + str[2]);
				log.setSimpleName("BPM���̺ţ�" + procInstID + ";BPM������Ϣ:"
						+ strComment);
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
		ContractSettlementBillInfo Info = (ContractSettlementBillInfo) billInfo;
		String[] str = new String[3];
		str[0] = "Y";
		StringBuffer xml = new StringBuffer();
		try {
			try {
				Info = ContractSettlementBillFactory.getLocalInstance(ctx)
						.getContractSettlementBillInfo(
								new ObjectUuidPK(Info.getId()), getSelectors());
			} catch (EASBizException e) {
				str[2] = "���ݵ���getSelectors��ȡ�������ݣ�����getSelectors�����������Ƿ���ȷ,���鿴������log��־��";
				e.printStackTrace();
			}
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			xml.append("<DATA>\n");
			xml.append("<Topic>"+ StringUtilBPM.isNULl(Info.getName())+ "</Topic>\n");   //����
			xml.append("<OrgName>"+StringUtilBPM.isNULl(Info.getOrgUnit().getDisplayName())+"</OrgName>\n");//��֯
			xml.append("<CurProject>"+ StringUtilBPM.isNULl(Info.getCurProject().getDisplayName())+ "</CurProject>\n"); // ������Ŀ
			xml.append("<ContactNumber>"+Info.getContractBill().getNumber()+ "</ContactNumber>\n"); // ��ͬ����
			xml.append("<ContractName>"+ StringUtilBPM.isNULl(Info.getContractBill().getName())+ "</ContractName>\n");// ��ͬ����
			xml.append("<Number>"+ StringUtilBPM.isNULl(Info.getContractBillNumber())+ "</Number>\n"); // ���㵥����
			xml.append("<Name>"+ StringUtilBPM.isNULl(Info.getName())+ "</Name>\n"); // ���㵥����
			xml.append("<BookedDate>" + dateFormat.format(Info.getBookedDate())+ "</BookedDate>\n"); // ҵ������
			xml.append("<Currency>" + Info.getCurrency().getName()+ "</Currency>\n"); // �ұ�
			xml.append("<OrgCode>"+StringUtilBPM.isNULl(Info.getOrgUnit().getNumber().split("-")[0])+ "</OrgCode>\n");
			xml.append("<Exchangerate>" + Info.getExchangeRate()+ "</Exchangerate>\n");   //����
            String m=dateFormat.format(Info.getBookedDate());
            m=m.toString().substring(0, 4);
            String nz=dateFormat.format(Info.getBookedDate());
            nz=nz.toString().substring(5,7);
			xml.append("<Period>" +m +"��"+nz+"��</Period>\n");   //�ڼ�
			xml.append("<OriginalAmount>" + Info.getOriginalAmount()+ "</OriginalAmount>\n"); // �������ԭ��
			xml.append("<ConstructPrice>" + Info.getConstructPrice()+ "</ConstructPrice>\n"); // ʩ���������
			xml.append("<GetFeeCriteria>" + Info.getGetFeeCriteria()+ "</GetFeeCriteria>\n"); // ȡ�ѱ�׼
			xml.append("<SettlePrice>" + Info.getSettlePrice()+ "</SettlePrice>\n"); // ������۱���
			xml.append("<UnitPrice>" + Info.getUnitPrice()+ "</UnitPrice>\n"); // ��λ���
			xml.append("<InfoPrice>" + Info.getInfoPrice()+ "</InfoPrice>\n"); // ��Ϣ��
			xml.append("<BuildArea>" + Info.getBuildArea()+ "</BuildArea>\n"); // �������
			xml.append("<GuaranteAmt>" + Info.getGuaranteAmt()+ "</GuaranteAmt>\n"); // ���޽�
			xml.append("<QualityTime>" + Info.getQualityTime()+ "</QualityTime>\n"); // ��������
			xml.append("<QulityGuaranterate>"+ Info.getQualityGuaranteRate()+ "</QulityGuaranterate>\n"); // ���޽����
			xml.append("<QualityGuarante>" + Info.getQualityGuarante()+ "</QualityGuarante>\n"); // �ۼƱ��޽�
			xml.append("<TotalOriginalAmount>"+ Info.getTotalOriginalAmount()+ "</TotalOriginalAmount>\n"); // �ۼƽ���ԭ��
			xml.append("<TotalsettlePrice>" + Info.getTotalSettlePrice()+ "</TotalsettlePrice>\n"); // �ۼƽ��㱾��
			xml.append("<Description>" + StringUtilBPM.isNULl(Info.getDescription())+ "</Description>\n"); // ��ע
			xml.append("<IsFinalSettle>" + Info.getIsFinalSettle()+ "</IsFinalSettle>\n");   // �Ƿ����ս���
			xml.append("<Creator>"+ Info.getCreator().getName()+ "</Creator>\n"); // �Ƶ���
			xml.append("<AttenTwo>"+ Info.getOwnID() + "</AttenTwo>\n"); // �鵵��
			xml.append("<CreateTime>"+ Info.getCreateTime()+ "</CreateTime>\n"); // �Ƶ�ʱ��
			//xml.append("<DeptName>"+Info.+"</DeptName>\n");//�Ƶ�����
			xml.append("</DATA>");
			str[1] = xml.toString();
		} catch (Exception e) {
			str[0] = "N";
			str[2] = "�����쳣����鿴������log��־��";
			e.printStackTrace();
		} finally {
			BPMLogInfo log = new BPMLogInfo();
			try {
				log.setLogDate(new Date());
				log.setName("���㵥����ID��" + Info.getId() + "; ��ţ�"
						+ Info.getNumber());
				log.setDescription("EAS���:" + str[0] + "; ������Ϣ" + str[1]
						+ str[2]);
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
		ContractSettlementBillInfo Info = (ContractSettlementBillInfo) billInfo;
		String[] str = new String[3];
		str[0] = "Y";

		try {
			try {
				Info = ContractSettlementBillFactory.getLocalInstance(ctx)
						.getContractSettlementBillInfo(
								new ObjectUuidPK(Info.getId()), getSelectors());
			} catch (EASBizException e) {
				str[0] = "N";
				str[2] = "���ݵ���getselector��ȡ�������ݣ�����getselector�����������Ƿ���ȷ,���鿴������log��־��";
				e.printStackTrace();
			}
			try {
				Info.setState(FDCBillStateEnum.AUDITTING);
				String sql = " update T_CON_ContractSettlementBill set fState='"
						+ Info.getState().getValue() + "'" + ", fDescription='"
						+ procURL + "' " + ", FSourceFunction='" + procInstID
						+ "' where fid='" + Info.getId() + "'";
//				String sql = " update T_CON_ContractSettlementBill set fState='"
//					           + Info.getState().getValue() + "'" + ", FSourceFunction='" + procInstID
//					           + "' where fid='" + Info.getId() + "'";
				FDCSQLBuilder bu = new FDCSQLBuilder(ctx);
				bu.appendSql(sql);
				bu.executeUpdate(ctx);
			} catch (BOSException e) {
				str[0] = "N";
				str[2] = "���ݵ���stateֵ����״̬sqlʧ�ܣ�����getState�����Ƿ���ֵ,���鿴������log��־��";
				e.printStackTrace();
			}
		} catch (Exception e) {
			str[0] = "N";
			str[2] = "�����쳣����鿴������log��־��";
			e.printStackTrace();
		} finally {
			BPMLogInfo log = new BPMLogInfo();
			try {
				log.setLogDate(new Date());
				log.setName("���㵥����ID��" + Info.getId() + "; ��ţ�"
						+ Info.getNumber());
				log.setDescription("BPM�����" + success + "; EAS���:" + str[0]
						+ "; ������Ϣ:" + str[1] + str[2]);
				log.setSimpleName("BPM���̺ţ�" + procInstID + ";����URL:" + procURL);
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
		sic.add(new SelectorItemInfo("Orgunit.DisplayName")); // ��֯
		sic.add(new SelectorItemInfo("Orgunit.Number"));
		sic.add(new SelectorItemInfo("CurProject.DisplayName")); // ������Ŀ
		sic.add(new SelectorItemInfo("ContractBillNumber"));
		sic.add(new SelectorItemInfo("ContractBill.id"));
		sic.add(new SelectorItemInfo("ContractBill.name"));
		sic.add(new SelectorItemInfo("ContractBill.number"));
		sic.add(new SelectorItemInfo("Name"));
		sic.add(new SelectorItemInfo("BookedDate"));
		sic.add(new SelectorItemInfo("Currency.name"));
		sic.add(new SelectorItemInfo("ExchangeRate"));
		sic.add(new SelectorItemInfo("Period"));
		sic.add(new SelectorItemInfo("OriginalAmount"));
		sic.add(new SelectorItemInfo("ConstructPrice"));
		sic.add(new SelectorItemInfo("GetFeeCriteria"));
		sic.add(new SelectorItemInfo("SettlePrice"));
		sic.add(new SelectorItemInfo("UnitPrice"));
		sic.add(new SelectorItemInfo("InfoPrice"));
		sic.add(new SelectorItemInfo("BuildArea"));
		sic.add(new SelectorItemInfo("GuaranteAmt"));
		sic.add(new SelectorItemInfo("QualityTime"));
		sic.add(new SelectorItemInfo("QualityGuaranteRate"));
		sic.add(new SelectorItemInfo("QualityGuarante"));
		sic.add(new SelectorItemInfo("TotalOriginalAmount"));
		sic.add(new SelectorItemInfo("TotalSettlePrice"));
		sic.add(new SelectorItemInfo("Description"));
		sic.add(new SelectorItemInfo("IsFinalSettle"));
		sic.add(new SelectorItemInfo("Creator.name"));
		sic.add(new SelectorItemInfo("State"));
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
