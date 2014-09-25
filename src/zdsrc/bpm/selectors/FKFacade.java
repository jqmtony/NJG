package com.kingdee.eas.bpm.selectors;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
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
				str[2] = "根据单据getSelectors获取对象数据，请检查getSelectors方法中属性是否正确,并查看服务器log日志！";
				e.printStackTrace();
			}
			try {
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				xml.append("<DATA>\n");
				xml.append("<BillStatus>"
						+ Info.getBillStatus().getName()
						+ "</BillStatus>\n"); // 状态
				xml.append("<PaymentNumebr>" + Info.getNumber()
						+ "</PaymentNumebr>\n"); // 付款单编码
				xml.append("<RequestBillNumber>" + Info.getNumber()
						+ "</RequestBillNumber>\n"); // 申请单编码
				xml.append("<VoucherNumber>" + Info.getVoucherNumber()
						+ "</VoucherNumber>\n"); // 凭证号
				xml.append("<Currency>" + Info.getCurrency().getName()
						+ "</Currency>\n"); // 凭证号
				xml.append("<ExchangeRate>" + Info.getExchangeRate()
						+ "</ExchangeRate>\n"); // 汇率
				xml.append("<BizDate>" + Info.getBizDate()
						+ "</BizDate>\n"); // 业务日期
				xml.append("<PayDate>" + Info.getPayDate()
						+ "</PayDate>\n"); // 付款日期
				xml.append("<Creator>" + Info.getCreator()
						+ "</Creator>\n"); // 制单人
				xml.append("<CreateTime>" + Info.getCreateTime()
						+ "</CreateTime>\n"); // 制单时间
				xml.append("<PayeeBank>" + Info.getPayeeBank()
						+ "</PayeeBank>\n"); // 收款银行
				xml.append("<PayeeAccountBankO>"
						+ Info.getPayeeAccountBankO().getName()
						+ "</PayeeAccountBankO>\n"); // 收款银行账户
				xml.append("<Auditor>" + Info.getAuditor()
						+ "</Auditor>\n"); // 审核人
				xml.append("<AuditDate>" + Info.getAuditDate()
						+ "</AuditDate>\n"); // 审核日期
				xml.append("<Amount>" + Info.getAmount()
								+ "</Amount>\n"); // 原币金额
				xml.append("<LocalAmt>" + Info.getLocalAmt()
						+ "</LocalAmt>\n"); // 本位币金额

				

				xml.append("</billEntries>\n");
				xml.append("</DATA>");
				str[1] = xml.toString();
			} catch (BOSException e) {
				str[0] = "N";
				str[2] = "获取对象属性失败，请检查属性是否有值,并查看服务器log日志!";
				e.printStackTrace();
			}
		} catch (Exception e) {
			str[0] = "N";
			str[2] = "其他异常，请查看服务器log日志！";
			e.printStackTrace();
		} finally {
			BPMLogInfo log = new BPMLogInfo();
			try {
				log.setLogDate(new Date());
				log.setName("合同单据ID：" + Info.getId() + "; 编号："
						+ Info.getNumber());
				log.setDescription("EAS结果:" + str[0] + "; 错误信息" + str[1]
						+ str[2]);
				log.setBeizhu("调用接口方法：GetbillInfo");
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

}
