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
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.CurrencyFactory;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.org.AdminOrgUnitFactory;
import com.kingdee.eas.bpm.BPMLogFactory;
import com.kingdee.eas.bpm.BPMLogInfo;
import com.kingdee.eas.bpm.common.StringUtilBPM;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ChangeAuditBillFactory;
import com.kingdee.eas.fdc.contract.ChangeAuditBillInfo;
import com.kingdee.eas.fdc.contract.ChangeAuditEntryFactory;
import com.kingdee.eas.fdc.contract.ChangeAuditEntryInfo;
import com.kingdee.eas.fdc.contract.ChangeSupplierEntryCollection;
import com.kingdee.eas.fdc.contract.ChangeSupplierEntryFactory;
import com.kingdee.eas.fdc.contract.ChangeSupplierEntryInfo;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractChangeBill;
import com.kingdee.eas.fdc.contract.ContractChangeBillFactory;
import com.kingdee.eas.fdc.contract.ContractChangeBillInfo;
import com.kingdee.eas.fdc.contract.ContractSettlementBillFactory;
import com.kingdee.eas.fdc.contract.ContractSettlementBillInfo;

public class ChangeOfSettlementFacade {
	public String[] ApproveClose(Context ctx, String strBSID,
			IObjectValue billInfo, int procInstID,
			String processInstanceResult, String strComment, Date dtTime) {
		ContractChangeBillInfo Info = (ContractChangeBillInfo) billInfo;
		String[] str = new String[3];
		str[0] = "Y";
		try {
			try {
				Info = ContractChangeBillFactory.getLocalInstance(ctx)
						.getContractChangeBillInfo(
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
						//Info.setState(FDCBillStateEnum.INVALID);
						Info.setState(FDCBillStateEnum.SAVED);
					else {
						str[2] = "������ͨ��ʧ�ܣ��ü�¼״̬���������У�";
						str[0] = "N";
					}
				}
				if ("2".equals(processInstanceResult)) {
					if (FDCBillStateEnum.AUDITTING.equals(Info.getState()))
//						Info.setState(FDCBillStateEnum.BACK);
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
				String sql = " update T_CON_ContractChangeBill set fState='"
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
		ContractChangeBillInfo Info = (ContractChangeBillInfo) billInfo;
		String[] str = new String[3];
		str[0] = "Y";
		StringBuffer xml = new StringBuffer();
		try {
			try {
				Info = ContractChangeBillFactory.getLocalInstance(ctx)
						.getContractChangeBillInfo(
								new ObjectUuidPK(Info.getId()), getSelectors());
			} catch (EASBizException e) {
				str[2] = "���ݵ���getSelectors��ȡ�������ݣ�����getSelectors�����������Ƿ���ȷ,���鿴������log��־��";
				e.printStackTrace();
			}
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			xml.append("<DATA>\n");
			if(Info.getChangeType()!=null)
			xml.append("<name>"+ StringUtilBPM.isNULl(Info.getName())+ "</name>\n"); // ָ�����
			xml.append("<number>"+ Info.getNumber()+ "</number>\n"); // ָ�����
			if(Info.getCurrency()!=null)
			xml.append("<currency>"+ StringUtilBPM.isNULl(Info.getCurrency().getName())+ "</currency>\n"); // �ұ�
			xml.append("<ExRate>"+ Info.getExRate()+ "</ExRate>\n"); // ����
			xml.append("<Topic>"+Info.getName()+"</Topic>\n"); // ����
			xml.append("<OriBalanceAmount>"+Info.getOriBalanceAmount()+ "</OriBalanceAmount>\n"); // ����ҽ��
			xml.append("<BalanceAmount>"+ Info.getBalanceAmount()+ "</BalanceAmount>\n"); // ���㱾λ�ҽ��
            if(Info.getContractBill()!=null)
            {
			xml.append("<contactNumber>"+ Info.getContractBill().getNumber()+ "</contactNumber>\n"); // ��ͬ���
			xml.append("<contractName>"+ Info.getContractBill().getName()+ "</contractName>\n"); // ��ͬ����
            }
			if(Info.getMainSupp()!=null)
		    xml.append("<mainSupp>"+StringUtilBPM.isNULl(Info.getMainSupp().getName())+ "</mainSupp>\n"); // ���͵�λ	
			
				
			xml.append("<OrgName>"+ Info.getOrgUnit().getDisplayName()+ "</OrgName>\n"); // ��֯
			xml.append("<Phase>"+ Info.getCurProject().getDisplayName()+ "</Phase>\n"); // ������Ŀ
				
			
			if(false==Info.isIsDeduct())
			xml.append("<isDeduct>��</isDeduct>\n"); // �Ƿ����οۿ��
			else
			{
				xml.append("<isDeduct>��</isDeduct>\n"); // �Ƿ����οۿ��
			}
			xml.append("<OrgCode>"+StringUtilBPM.isNULl(Info.getOrgUnit().getNumber().split("-")[0])+ "</OrgCode>\n");
			xml.append("<exchangeRate>"+ Info.getExRate()+ "</exchangeRate>\n"); // ����
			xml.append("<originalAmount>"+ Info.getOriginalAmount()+ "</originalAmount>\n"); // Ԥ��ԭ�ҽ��
			xml.append("<unitPrice>"+ Info.getAmount()+ "</unitPrice>\n"); // Ԥ�㱾�ҽ��
			if(false==Info.isIsSureChangeAmt())
			{
			xml.append("<isSureChangeAmt>��</isSureChangeAmt>\n"); // �Ƿ�ȷ��������
			}
			else
			{
			 xml.append("<isSureChangeAmt>��</isSureChangeAmt>\n"); // �Ƿ�ȷ��������
			}
			xml.append("<deductReason>"+ Info.getDeductReason()+ "</deductReason>\n"); // �ۿ�ԭ��
			xml.append("<deductAmount>"+ Info.getDeductAmount()+ "</deductAmount>\n"); // �ۿ���
			xml.append("<changeAuditNumber>"+ Info.getChangeAuditNumber()+ "</changeAuditNumber>\n"); // ��������
			if(Info.getContractBill()!=null)
	        {
			xml.append("<billNumber>"+ Info.getContractBill().getNumber()+ "</billNumber>\n"); // ԭʼ��ϵ����
	        }
			xml.append("<constructPrice>"+ Info.getConstructPrice()+ "</constructPrice>\n"); // ʩ����������
			xml.append("<costNouse>"+ Info.getCostNouse()+ "</costNouse>\n"); // ��Ч�ɱ����
			if(Info.getInvalidCostReason()!=null)
			xml.append("<invalidCostReason>"+ Info.getInvalidCostReason().getName()+ "</invalidCostReason>\n"); // ��Ч�ɱ�ԭ��
			if(Info.getCreator()!=null)
			xml.append("<creator>"+ Info.getCreator().getName()+ "</creator>\n"); // �Ƶ���
			
			xml.append("<createTime>"+ Info.getCreateTime()+ "</createTime>\n"); // �Ƶ�����
			xml.append("<CostAmount>"+ Info.getOriginalAmount()+ "</CostAmount>\n"); // ��������
			
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
		ContractChangeBillInfo Info = (ContractChangeBillInfo) billInfo;
		String[] str = new String[3];
		str[0] = "Y";

		try {
			try {
				Info = ContractChangeBillFactory.getLocalInstance(ctx)
						.getContractChangeBillInfo(
								new ObjectUuidPK(Info.getId()), getSelectors());
			} catch (EASBizException e) {
				str[0] = "N";
				str[2] = "���ݵ���getselector��ȡ�������ݣ�����getselector�����������Ƿ���ȷ,���鿴������log��־��";
				e.printStackTrace();
			}
			try {
				Info.setState(FDCBillStateEnum.AUDITTING);
				String sql = " update T_CON_ContractChangeBill set fState='"
						+ Info.getState().getValue() + "'" + ", fDescription='"
						+ procURL + "' " + ", FSourceFunction='" + procInstID
						+ "' where fid='" + Info.getId() + "'";
//				String sql = " update T_CON_ContractChangeBill set fState='4Auditting', fDescription='"
//					+ procURL + "' " + ", FSourceFunction='" + procInstID
//					+ "' where fid='" + Info.getId() + "'";
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
		sic.add(new SelectorItemInfo("ChangeType.name"));
		sic.add(new SelectorItemInfo("ContractBillNumber"));
		
		sic.add(new SelectorItemInfo("Currency.name"));
		sic.add(new SelectorItemInfo("ExRate"));
		sic.add(new SelectorItemInfo("OriBalanceAmount"));
		sic.add(new SelectorItemInfo("BalanceAmount"));
		sic.add(new SelectorItemInfo("ContractBill.id"));
		sic.add(new SelectorItemInfo("ContractBill.name"));
		sic.add(new SelectorItemInfo("ContractBill.number"));
		sic.add(new SelectorItemInfo("MainSupp.name"));
		sic.add(new SelectorItemInfo("IsDeduct"));
		sic.add(new SelectorItemInfo("OriginalAmount"));
		sic.add(new SelectorItemInfo("Amount"));
		sic.add(new SelectorItemInfo("IsSureChangeAmt"));
		sic.add(new SelectorItemInfo("DeductReason"));
		sic.add(new SelectorItemInfo("DeductAmount"));
		sic.add(new SelectorItemInfo("ChangeAuditNumber"));
		sic.add(new SelectorItemInfo("Number"));
		sic.add(new SelectorItemInfo("Name"));
		sic.add(new SelectorItemInfo("ConstructPrice"));
		sic.add(new SelectorItemInfo("CostNouse"));
		sic.add(new SelectorItemInfo("InvalidCostReason.name"));
		sic.add(new SelectorItemInfo("Creator.name"));
		sic.add(new SelectorItemInfo("CreateTime"));
		sic.add(new SelectorItemInfo("OrgUnit.name"));
		sic.add(new SelectorItemInfo("OrgUnit.number"));
		sic.add(new SelectorItemInfo("OrgUnit.DisplayName"));
		sic.add(new SelectorItemInfo("CurProject.DisplayName"));
		sic.add(new SelectorItemInfo("CurProject.name"));
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
