package com.kingdee.eas.bpm.selectors;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.bpm.BPMLogFactory;
import com.kingdee.eas.bpm.BPMLogInfo;
import com.kingdee.eas.bpm.BillBaseSelector;
import com.kingdee.eas.bpmdemo.ChangeVisaAppFactory;
import com.kingdee.eas.bpmdemo.ChangeVisaAppInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;

public class BGFacade implements BillBaseSelector {

	public String[] ApproveClose(Context ctx, String strBSID,
			IObjectValue billInfo, int procInstID,
			String processInstanceResult, String strComment, Date dtTime) {
		return null;
	}

	public String[] GetbillInfo(Context ctx, String strBSID,
			IObjectValue billInfo) {
		ChangeVisaAppInfo Info = (ChangeVisaAppInfo) billInfo;
		String[] str = new String[3];
		str[0] = "Y";
		StringBuffer xml = new StringBuffer();
		try {
			try {
				Info = ChangeVisaAppFactory.getLocalInstance(ctx)
						.getChangeVisaAppInfo(new ObjectUuidPK(Info.getId()),
								getSelectors());
			} catch (EASBizException e) {
				str[2] = "���ݵ���getSelectors��ȡ�������ݣ�����getSelectors�����������Ƿ���ȷ,���鿴������log��־��";
				e.printStackTrace();
			}
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			xml.append("<DATA>\n");
			xml.append("<Number>" + Info.getNumber()
					+ "</Number>\n"); // ���뵥����
			xml.append("<Name>" + Info.getName()
					+ "</Name>\n"); //����
			xml.append("<Audittype>" + Info.getAudittype().getName()
					+ "</Audittype>\n"); // �������
			xml.append("<Changereason>" + Info.getChangereason().getName()
					+ "</Changereason>\n"); // ���ԭ��
			
			xml.append("<ConductDept>" + Info.getConductDept().getName()
					+ "</ConductDept>\n"); // �������
			
			
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
		return null;
	}

	public SelectorItemCollection getSelectors() {
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
		return null;
	}




}
