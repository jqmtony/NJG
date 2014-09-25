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
import com.kingdee.eas.bpmdemo.ContractsettlementAssEntryFactory;
import com.kingdee.eas.bpmdemo.ContractsettlementAssEntryInfo;
import com.kingdee.eas.bpmdemo.ContractsettlementFactory;
import com.kingdee.eas.bpmdemo.ContractsettlementInfo;
import com.kingdee.eas.bpmdemo.ContractsettlementJlEntryFactory;
import com.kingdee.eas.bpmdemo.ContractsettlementJlEntryInfo;
import com.kingdee.eas.bpmdemo.ContractsettlementOtherEntryFactory;
import com.kingdee.eas.bpmdemo.ContractsettlementOtherEntryInfo;
import com.kingdee.eas.bpmdemo.ContractsettlementSettlementEntryFactory;
import com.kingdee.eas.bpmdemo.ContractsettlementSettlementEntryInfo;
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

import elite.lang.Decimal;

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
						Info.setState(FDCBillStateEnum.INVALID);
					else {
						str[2] = "������ͨ��ʧ�ܣ��ü�¼״̬���������У�";
						str[0] = "N";
					}
				}
				if ("2".equals(processInstanceResult)) {
					if (FDCBillStateEnum.AUDITTING.equals(Info.getState()))
						Info.setState(FDCBillStateEnum.BACK);
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
				String sql = " update t_con_contractbill set fState='"
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
			try {
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				xml.append("<DATA>\n");
				xml.append("<Orgunit>"
						+ StringUtilBPM.isNULl(Info.getOrgUnit().getName())
						+ "</Orgunit>\n"); // ��֯
				xml.append("<CurProject>"
						+ StringUtilBPM.isNULl(Info.getCurProject()
								+ "</CurProject>\n")); // ������Ŀ
				// xml.append("<ApplyDate>"
				// + dateFormat.format(Info.getCreateTime())
				// + "</ApplyDate>\n");
				xml.append("<number>��-"
						+ StringUtilBPM.isNULl(Info.getContractBillNumber())
						+ "</number>\n"); // --�Լ������number���㵥����
				xml.append("<ContractBillNumber>"
						+ StringUtilBPM.isNULl(Info.getContractBillNumber())
						+ "</ContractBillNumber>\n"); // ��ͬ����
				xml.append("<ContractBill>"
						+ StringUtilBPM
								.isNULl(Info.getContractBill().getName())
						+ "</ContractBill>\n");// ��ͬ����
				xml.append("<BizDate>" + dateFormat.format(Info.getBizDate())
						+ "</BizDate>\n"); // ҵ������
				xml.append("<Currency>" + Info.getCurrency().getName()
						+ "</Currency>\n"); // �ұ�
				xml.append("<constructPrice>" + Info.getConstructPrice()
						+ "</constructPrice>\n"); // ʩ���������
				xml.append("<exchangerate>" + Info.getExchangeRate()
						+ "</exchangerate>\n");
				xml.append("<settlePrice>" + Info.getSettlePrice()
						+ "</settlePrice>\n"); // �������ԭ��
				xml.append("<unitPrice>" + Info.getUnitPrice()
						+ "</unitPrice>\n"); // ��λ���
				xml.append("<buildArea>" + Info.getBuildArea()
						+ "</buildArea>\n"); // �������
				xml.append("<getFeeCriteria>" + Info.getGetFeeCriteria()
						+ "</getFeeCriteria>\n"); // ȡ�ѱ�׼
				xml.append("<infoPrice>" + Info.getInfoPrice()
						+ "</infoPrice>\n"); // ��Ϣ��
				xml.append("<qualitytime>" + Info.getQualityTime()
						+ "</qualitytime>\n"); // ��������
				xml.append("<qulityguaranterate>"
						+ Info.getQualityGuaranteRate()
						+ "</qulityguaranterate>\n"); // ���޽����
				xml.append("<guaranteAmt>" + Info.getGuaranteAmt()
						+ "</guaranteAmt>\n"); // ���޽�
				xml.append("<totalOriginalAmount>"
						+ Info.getTotalOriginalAmount()
						+ "</totalOriginalAmount>\n"); // �ۼƽ���ԭ��
				xml.append("<totalsettlePrice>" + Info.getTotalSettlePrice()
						+ "</totalsettlePrice>\n"); // �ۼƽ��㱾��
				xml.append("<description>" + Info.getDescription()
						+ "</description>\n"); // ��ע
				xml.append("<isFinalSettle>" + Info.getIsFinalSettle()
						+ "</isFinalSettle>\n");   // �Ƿ����ս���
				xml.append("<Creator>" 
						+ Info.getCreator() + "</Creator>\n"); // �Ƶ���

				xml.append("<PaymentBill>\n"); // ������Ϣ
				EntityViewInfo Paymentavevi = new EntityViewInfo();
				FilterInfo Paymentavfilter = new FilterInfo();
				Paymentavfilter.getFilterItems().add(
						new FilterItemInfo("contractNum", Info
								.getContractBill()));
				Paymentavevi.setFilter(Paymentavfilter);
				PaymentBillCollection Paymentmyavc = PaymentBillFactory
						.getRemoteInstance().getPaymentBillCollection(
								Paymentavevi);
				for (int i = 0; i < Paymentmyavc.size(); i++) {
					PaymentBillInfo payInfo = Paymentmyavc.get(i);
					xml.append("<item>\n");
					xml.append("<BillStatus>"
							+ payInfo.getBillStatus().getName()
							+ "</BillStatus>\n"); // ״̬
					xml.append("<PaymentNumebr>" + payInfo.getNumber()
							+ "</PaymentNumebr>\n"); // �������
					xml.append("<RequestBillNumber>" + payInfo.getNumber()
							+ "</RequestBillNumber>\n"); // ���뵥����
					xml.append("<VoucherNumber>" + payInfo.getVoucherNumber()
							+ "</VoucherNumber>\n"); // ƾ֤��
					xml.append("<Currency>" + payInfo.getCurrency().getName()
							+ "</Currency>\n"); // ƾ֤��
					xml.append("<ExchangeRate>" + payInfo.getExchangeRate()
							+ "</ExchangeRate>\n"); // ����
					xml.append("<BizDate>" + payInfo.getBizDate()
							+ "</BizDate>\n"); // ҵ������
					xml.append("<PayDate>" + payInfo.getPayDate()
							+ "</PayDate>\n"); // ��������
					xml.append("<Creator>" + payInfo.getCreator()
							+ "</Creator>\n"); // �Ƶ���
					xml.append("<CreateTime>" + payInfo.getCreateTime()
							+ "</CreateTime>\n"); // �Ƶ�ʱ��
					xml.append("<PayeeBank>" + payInfo.getPayeeBank()
							+ "</PayeeBank>\n"); // �տ�����
					xml.append("<PayeeAccountBankO>"
							+ payInfo.getPayeeAccountBankO().getName()
							+ "</PayeeAccountBankO>\n"); // �տ������˻�
					xml.append("<Auditor>" + payInfo.getAuditor()
							+ "</Auditor>\n"); // �����
					xml.append("<AuditDate>" + payInfo.getAuditDate()
							+ "</AuditDate>\n"); // �������
					xml.append("<Amount>" + payInfo.getAmount()
									+ "</Amount>\n"); // ԭ�ҽ��
					xml.append("<LocalAmt>" + payInfo.getLocalAmt()
							+ "</LocalAmt>\n"); // ��λ�ҽ��

					EntityViewInfo entryavevi = new EntityViewInfo();    
					FilterInfo entryavfilter = new FilterInfo();
					entryavfilter.getFilterItems().add(
							new FilterItemInfo("parent", payInfo.getEntries()));
					entryavevi.setFilter(entryavfilter);
					PaymentPrjPayEntryCollection entryavc = PaymentPrjPayEntryFactory
							.getRemoteInstance()
							.getPaymentPrjPayEntryCollection(entryavevi);
					for (int j = 0; j < entryavc.size(); j++) {
						PaymentPrjPayEntryInfo entryinfo = entryavc.get(i);
						xml.append("<InvoiceNumber>"
								+ entryinfo.getInvoiceNumber()
								+ "</VoucherNumber>\n"); // ��Ʊ����
						xml.append("<InvoiceAmt >" + entryinfo.getInvoiceAmt()
								+ "</VoucherNumber>\n"); // ��Ʊ��λ��
					}
					xml.append("<ActFdcPayeeName>"
							+ payInfo.getActFdcPayeeName().getName()
							+ "</ActFdcPayeeName>\n"); // �տλ
					xml.append("<SJActFdcPayeeName>"
							+ payInfo.getActFdcPayeeName().getName()
							+ "</SJActFdcPayeeName>\n"); // ʵ���տλ
					xml.append("<Description>" + payInfo.getDescription()
							+ "</Description>\n"); // ˵��
					xml.append("</item>\n");
				}
				xml.append("</PaymentBill>\n");

				xml.append("<ContractBill>\n"); // ��ͬ
				EntityViewInfo Contractavevi = new EntityViewInfo();
				FilterInfo Contractavfilter = new FilterInfo();
				Contractavfilter.getFilterItems().add(
						new FilterItemInfo("CodingNumber", Info
								.getContractBill()));
				Contractavevi.setFilter(Contractavfilter);
				ContractBillCollection Contractmyavc = ContractBillFactory
						.getRemoteInstance().getContractBillCollection(
								Contractavevi);
				for (int i = 0; i < Contractmyavc.size(); i++) {
					ContractBillInfo avInfo = Contractmyavc.get(i);
					xml.append("<item>\n");
					xml.append("<BizDate>" + avInfo.getBizDate()
							+ "</BizDate>\n"); // ҵ������
					xml.append("<PeriodId>" + avInfo.getPeriod()
							+ "</PeriodId>\n"); // �ڼ�
					xml.append("<State>" + avInfo.getState().getName()
							+ "</State>\n"); // ״̬
					xml.append("<HasSettled>" + avInfo.isHasSettled()
							+ "</HasSettled>\n"); // �ѽ���
					xml.append("<ContractType>"
							+ avInfo.getContractType().getName()
							+ "</ContractType>\n"); // ��ͬ����
					xml.append("<CodingNumber>" + avInfo.getCodingNumber()
							+ "</CodingNumber>\n"); // ����
					xml.append("<Name>" + avInfo.getName() + "</Name>\n"); // ����
					xml.append("<Currency>" + avInfo.getCurrency().getName()
							+ "</Currency>\n"); // �ұ�
					xml.append("<OriginalAmount>" + avInfo.getOriginalAmount()
							+ "</OriginalAmount>\n"); // ԭ�ҽ��
					xml.append("<Amount>" + avInfo.getAmount() + "</Amount>\n"); // ��λ�ҽ��
					xml.append("<Developer>"
							+ avInfo.getLandDeveloper().getName()
							+ "</Developer>\n"); // �׷�
					xml.append("<PartB>" + avInfo.getPartB().getName()
							+ "</PartB>\n"); // �ҷ�
					xml.append("<RespDept>" + avInfo.getRespDept().getName()
							+ "</RespDept>\n"); // ���β���
					xml.append("<RespPerson>"
							+ avInfo.getRespPerson().getName()
							+ "</RespPerson>\n"); // ������
					xml.append("<Creator>" + avInfo.getCreator()
							+ "</Creator>\n"); // �Ƶ���
					xml.append("<CreateTime>" + avInfo.getCreateTime()
							+ "</CreateTime>\n"); // �Ƶ�ʱ��
					xml.append("</item>\n");
				}
				xml.append("</ContractBill>\n");

				xml.append("<PaymentPrjPayEntry>\n"); // ���̸��������
				EntityViewInfo PaymentPrjPayavevi = new EntityViewInfo();
				FilterInfo PaymentPrjPayavfilter = new FilterInfo();
				PaymentPrjPayavfilter.getFilterItems().add(
						new FilterItemInfo("contractNum", Info
								.getContractBill()));
				PaymentPrjPayavevi.setFilter(PaymentPrjPayavfilter);
				ContractBillCollection PaymentPrjPayEntryavc = ContractBillFactory
						.getRemoteInstance().getContractBillCollection(
								PaymentPrjPayavevi);
				for (int i = 0; i < PaymentPrjPayEntryavc.size(); i++) {
					ContractBillInfo payInfo = PaymentPrjPayEntryavc.get(i);
					xml.append("<conitem>\n");
					xml.append("<ContractBillName>"+ payInfo.getName()+ "</ContractBillName>\n"); // ��ͬ����
					xml.append("<OriginalAmount>"+ payInfo.getOriginalAmount()+ "</OriginalAmount>\n"); // ԭ�ҽ��
					xml.append("<Amount>"+ payInfo.getAmount()+ "</Amount>\n"); // ��λ�ҽ��
					//xml.append("<PaidPartAMatlAmt>"+ payInfo.getPaidPartAMatlAmt()+ "</PaidPartAMatlAmt>\n"); // Ӧ�ۼ׹��ƿ�
					xml.append("</conitem>\n");
				}
				
				
				
				EntityViewInfo Paymentavevi2 = new EntityViewInfo();
				FilterInfo Paymentavfilter2 = new FilterInfo();
				Paymentavfilter2.getFilterItems().add(
						new FilterItemInfo("contractNum", Info
								.getContractBill()));
				Paymentavevi2.setFilter(Paymentavfilter2);
				PaymentBillCollection Paymentmyavc2 = PaymentBillFactory
						.getRemoteInstance().getPaymentBillCollection(
								Paymentavevi);
				for (int i = 0; i < Paymentmyavc2.size(); i++) {
					PaymentBillInfo payInfo = Paymentmyavc.get(i);
					xml.append("<paymentitem>\n");
					xml.append("<CurPaid>"+ payInfo.getCurPaid()+ "</CurPaid>\n"); //ʵ����
					xml.append("<PayPartAMatlAmt>"+ payInfo.getPayPartAMatlAmt()+ "</PayPartAMatlAmt>\n");// Ӧ�ۼ׹��ƿ�
					xml.append("<CurPlannedPayment>"+ payInfo.getCurPlannedPayment()+ "</CurPlannedPayment>\n");// ���ڼƻ�����
					xml.append("<CurBackPay>"+ payInfo.getCurBackPay()+ "</CurBackPay>\n");// ����Ƿ����					
					xml.append("</paymentitem>\n");
					
					
					EntityViewInfo entryavevi = new EntityViewInfo();
					FilterInfo entryavfilter = new FilterInfo();
					entryavfilter.getFilterItems().add(
							new FilterItemInfo("parent", payInfo.getEntries()));
					entryavevi.setFilter(entryavfilter);
					PaymentPrjPayEntryCollection entryavc = PaymentPrjPayEntryFactory
							.getRemoteInstance()
							.getPaymentPrjPayEntryCollection(entryavevi);
					for (int j = 0; j < entryavc.size(); j++) {
						PaymentPrjPayEntryInfo entryinfo = entryavc.get(j);
						xml.append("<entryitem>\n");
						xml.append("<LstAdvanceAllPaid>"
								+ entryinfo.getLstAdvanceAllPaid()
								+ "</LstAdvanceAllPaid>\n"); // Ԥ�����ֹ�����ۼ�ʵ��
						xml.append("<LstAdvanceAllReq>"
								+ entryinfo.getLstAdvanceAllReq()
								+ "</LstAdvanceAllReq>\n"); // Ԥ�����ֹ�����ۼ�����
						xml.append("<AdvanceAllReq>"
								+ entryinfo.getAdvanceAllReq()
								+ "</AdvanceAllReq>\n"); // Ԥ�����ֹ�����ۼ�����
						xml.append("<AdvanceAllPaid>"
								+ entryinfo.getAdvanceAllPaid()
								+ "</AdvanceAllPaid>\n"); // Ԥ�����ֹ�����ۼ�ʵ��
						xml.append("</entryitem>\n");
					}
					
					
					
					

				}
				

				xml.append("</PaymentPrjPayEntry>\n");

				// ΥԼ��
				xml.append("<CompensationBill>\n");
				EntityViewInfo avevi = new EntityViewInfo();
				FilterInfo avfilter = new FilterInfo();
				avfilter.getFilterItems().add(
						new FilterItemInfo("ContractBill", Info
								.getContractBill()));
				avevi.setFilter(avfilter);
				CompensationBillCollection myavc = CompensationBillFactory
						.getRemoteInstance().getCompensationBillCollection(
								avevi);
				for (int i = 0; i < myavc.size(); i++) {
					CompensationBillInfo avInfo = myavc.get(i);
					xml.append("<item>\n");
					xml.append("<select>" + avInfo.getId() + "</select>\n");
					xml.append("<number>" + avInfo.getNumber() + "</number>\n");
					xml.append("<name>" + avInfo.getName() + "</name>\n");
					xml.append("<amount>" + avInfo.getAmount() + "</amount>\n"); // ���ҽ��
					xml.append("<CompensationType>"
							+ avInfo.getCompensationType().getName()
							+ "</CompensationType>\n");
					xml.append("<DeductMode>" + avInfo.getDeductMode()
							+ "</DeductMode>\n");
					xml.append("<creator>" + avInfo.getCreator()
							+ "</creator>\n");
					xml.append("<CreateTime>" + avInfo.getCreateTime()
							+ "</CreateTime>\n");
					xml.append("</item>\n");
				}
				xml.append("</CompensationBill>\n");

				// �ۿ�
				xml.append("<DeductBillEntry>\n");
				EntityViewInfo Deductavevi = new EntityViewInfo();
				FilterInfo Deductavfilter = new FilterInfo();
				Deductavfilter.getFilterItems()
						.add(
								new FilterItemInfo("contractid", Info
										.getContractBill()));
				Deductavevi.setFilter(Deductavfilter);
				DeductBillEntryCollection Deductmyavc = DeductBillEntryFactory
						.getRemoteInstance().getDeductBillEntryCollection(
								Deductavevi);
				for (int i = 0; i < Deductmyavc.size(); i++) {
					DeductBillEntryInfo deductavInfo = Deductmyavc.get(i);
					xml.append("<item>\n");
					xml.append("<select>" + deductavInfo + "</select>\n");
					xml.append("<Number>" + deductavInfo.getNumber()
							+ "</Number>\n"); // ����
					xml.append("<DeductType>"
							+ deductavInfo.getDeductType().getName()
							+ "</DeductType>\n");
					xml.append("<DeductItem>" + deductavInfo.getDeductItem()
							+ "</DeductItem>\n"); // �ۿ�����
					xml.append("<DeductOriginalAmt>"
							+ deductavInfo.getDeductOriginalAmt()
							+ "</DeductOriginalAmt>\n");
					xml.append("<DeductMode>"
							+ dateFormat.format(deductavInfo.getDeductDate())
							+ "</DeductMode>\n"); // �ۿ�����

					EntityViewInfo Deductavevi2 = new EntityViewInfo();
					FilterInfo Deductavfilter2 = new FilterInfo();
					Deductavfilter2.getFilterItems().add(
							new FilterItemInfo("entry", deductavInfo
									.getParent()));
					Deductavevi2.setFilter(Deductavfilter2);
					DeductBillCollection Deductmyavc2 = DeductBillFactory
							.getRemoteInstance().getDeductBillCollection(
									Deductavevi2);
					for (int j = 0; j < Deductmyavc2.size(); j++) {
						DeductBillInfo deductavInfo2 = Deductmyavc2.get(i);
						xml.append("<creator>" + deductavInfo2.getCreator()
								+ "</creator>\n");
						xml.append("<CreateTime>"
								+ deductavInfo2.getCreateTime()
								+ "</CreateTime>\n");
					}

					xml.append("</item>\n");
				}
				xml.append("</DeductBillEntry>\n");

				// ����
				xml.append("<GuerdonBill>\n");
				EntityViewInfo guerdonavevi = new EntityViewInfo();
				FilterInfo guerdonavfilter = new FilterInfo();
				guerdonavfilter.getFilterItems().add(
						new FilterItemInfo("Contract", Info.getContractBill()));
				guerdonavevi.setFilter(guerdonavfilter);
				GuerdonBillCollection guerdonmyavc = GuerdonBillFactory
						.getRemoteInstance().getGuerdonBillCollection(
								guerdonavevi);
				for (int i = 0; i < guerdonmyavc.size(); i++) {
					GuerdonBillInfo avInfo = guerdonmyavc.get(i);
					xml.append("<item>\n");
					xml.append("<select>" + avInfo.getId() + "</select>\n");
					xml.append("<number>" + avInfo.getNumber() + "</number>\n");
					xml.append("<name>" + avInfo.getName() + "</name>\n");
					xml.append("<amount>" + avInfo.getAmount() + "</amount>\n"); // ���ҽ��
					xml.append("<GuerdonThings>" + avInfo.getGuerdonThings()
							+ "</GuerdonThings>\n");
					xml.append("<PutOutType>"
							+ avInfo.getPutOutType().getName()
							+ "</PutOutType>\n");
					xml.append("<creator>" + avInfo.getCreator()
							+ "</creator>\n");
					xml.append("<CreateTime>" + avInfo.getCreateTime()
							+ "</CreateTime>\n");
					xml.append("</item>\n");
				}
				xml.append("</GuerdonBill>\n");
				xml.append("</DATA>");
				str[1] = xml.toString();
			} catch (BOSException e) {
				str[0] = "N";
				str[2] = "��ȡ��������ʧ�ܣ����������Ƿ���ֵ,���鿴������log��־!";
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
				String sql = " update t_con_contractbill set fState='"
						+ Info.getState().getValue() + "'" + ", fDescription='"
						+ procURL + "' " + ", FSourceFunction='" + procInstID
						+ "' where fid='" + Info.getId() + "'";
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
		sic.add(new SelectorItemInfo("Orgunit.id")); // ��֯
		sic.add(new SelectorItemInfo("Orgunit.name"));
		sic.add(new SelectorItemInfo("Orgunit.number"));
		sic.add(new SelectorItemInfo("CurProject")); // ������Ŀ
		sic.add(new SelectorItemInfo("ContractBillNumber"));
		sic.add(new SelectorItemInfo("ContractBill.id"));
		sic.add(new SelectorItemInfo("ContractBill.name"));
		sic.add(new SelectorItemInfo("ContractBill.number"));
		sic.add(new SelectorItemInfo("BizDate"));
		sic.add(new SelectorItemInfo("Currency.id"));
		sic.add(new SelectorItemInfo("Currency.name"));
		sic.add(new SelectorItemInfo("Currency.number"));
		sic.add(new SelectorItemInfo("constructPrice"));
		sic.add(new SelectorItemInfo("exchangerate"));
		sic.add(new SelectorItemInfo("settlePrice"));
		sic.add(new SelectorItemInfo("unitPrice"));
		sic.add(new SelectorItemInfo("buildArea"));
		sic.add(new SelectorItemInfo("getFeeCriteria"));
		sic.add(new SelectorItemInfo("infoPrice"));
		sic.add(new SelectorItemInfo("qualitytime"));
		sic.add(new SelectorItemInfo("qulityguaranterate"));
		sic.add(new SelectorItemInfo("guaranteAmt"));
		sic.add(new SelectorItemInfo("totalOriginalAmount"));
		sic.add(new SelectorItemInfo("totalsettlePrice"));
		sic.add(new SelectorItemInfo("description"));
		sic.add(new SelectorItemInfo("isFinalSettle"));
		sic.add(new SelectorItemInfo("Creator"));
		return sic;
	}

}
