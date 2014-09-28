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
		// TODO Auto-generated method stub
       return null;
	}

	public String[] GetbillInfo(Context ctx, String strBSID,
			IObjectValue billInfo) {
		PaymentBillInfo Info = (PaymentBillInfo) billInfo;
		String[] str = new String[3];
		str[0] = "Y";
		StringBuffer xml = new StringBuffer();
		try {
			try {
				Info = PaymentBillFactory.getLocalInstance(ctx)
						.getPaymentBillInfo(new ObjectUuidPK(Info.getId()),
								getSelectors());
			} catch (EASBizException e) {
				str[2] = "���ݵ���getSelectors��ȡ�������ݣ�����getSelectors�����������Ƿ���ȷ,���鿴������log��־��";
				e.printStackTrace();
			}
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			xml.append("<DATA>\n");
			xml.append("<BillStatus>"
					+ Info.getBillStatus().getName()
					+ "</BillStatus>\n"); // ״̬
			xml.append("<PaymentNumebr>" + Info.getNumber()
					+ "</PaymentNumebr>\n"); // �������
			xml.append("<RequestBillNumber>" + Info.getNumber()
					+ "</RequestBillNumber>\n"); // ���뵥����
			xml.append("<VoucherNumber>" + Info.getVoucherNumber()
					+ "</VoucherNumber>\n"); // ƾ֤��
			xml.append("<Currency>" + Info.getCurrency().getName()
					+ "</Currency>\n"); // �ұ�
			xml.append("<Amount>" + Info.getAmount()
					+ "</Amount>\n"); // ԭ�ҽ��
			xml.append("<ExchangeRate>" + Info.getExchangeRate()
					+ "</ExchangeRate>\n"); // ����
	        xml.append("<LocalAmt>" + Info.getLocalAmt()
			        + "</LocalAmt>\n"); // ��λ�ҽ��
			xml.append("<BizDate>" + Info.getBizDate()
					+ "</BizDate>\n"); // ҵ������
			xml.append("<PayDate>" + Info.getPayDate()
					+ "</PayDate>\n"); // ��������
			xml.append("<Creator>" + Info.getCreator()
					+ "</Creator>\n"); // �Ƶ���
			xml.append("<CreateTime>" + Info.getCreateTime()
					+ "</CreateTime>\n"); // �Ƶ�ʱ��
			xml.append("<PayeeBank>" + Info.getPayeeBank()
					+ "</PayeeBank>\n"); // �տ�����
			xml.append("<PayeeAccountBankO>"
					+ Info.getPayeeAccountBankO().getName()
					+ "</PayeeAccountBankO>\n"); // �տ������˻�
			xml.append("<Auditor>" + Info.getAuditor()
					+ "</Auditor>\n"); // �����
			xml.append("<AuditDate>" + Info.getAuditDate()
					+ "</AuditDate>\n"); // �������
			xml.append("<billEntries>\n");
			for(int i=0;i<Info.getEntries().size();i++){
				PaymentBillEntryInfo entry=Info.getEntries().get(i);
				entry = PaymentBillEntryFactory.getLocalInstance(ctx).getPaymentBillEntryInfo(new ObjectUuidPK(entry.getId()));
				
			}
			
			
			
			
	/*		EntityViewInfo entryavevi = new EntityViewInfo();    
			FilterInfo entryavfilter = new FilterInfo();
			entryavfilter.getFilterItems().add(
					new FilterItemInfo("parent", Info.getEntries()));
			entryavevi.setFilter(entryavfilter);
			PaymentPrjPayEntryCollection entryavc = PaymentPrjPayEntryFactory
					.getRemoteInstance()
					.getPaymentPrjPayEntryCollection(entryavevi);
			for (int j = 0; j < entryavc.size(); j++) {
				PaymentPrjPayEntryInfo entryinfo = entryavc.get(j);
				xml.append("<InvoiceNumber>"
						+ entryinfo.getInvoiceNumber()
						+ "</VoucherNumber>\n"); // ��Ʊ����
				xml.append("<InvoiceAmt >" + entryinfo.getInvoiceAmt()
						+ "</VoucherNumber>\n"); // ��Ʊ��λ��
			}
			xml.append("<ActFdcPayeeName>"
					+ Info.getActFdcPayeeName().getName()
					+ "</ActFdcPayeeName>\n"); // �տλ
			xml.append("<SJActFdcPayeeName>"
					+ Info.getActFdcPayeeName().getName()
					+ "</SJActFdcPayeeName>\n"); // ʵ���տλ
			xml.append("<Description>" + Info.getDescription()
					+ "</Description>\n"); // ˵��
		*/	
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
				log.setName("��ͬ����ID��" + Info.getId() + "; ��ţ�"
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
		// TODO Auto-generated method stub
		return null;
	}

	public SelectorItemCollection getSelectors() {
		// TODO Auto-generated method stub
		return null;
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