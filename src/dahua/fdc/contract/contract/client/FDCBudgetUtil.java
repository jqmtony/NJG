/*
 * @(#)FDCBudgetUtil.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.fdc.contract.client;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * ����:������������뵥�� ���鿴Ԥ�㡰��ؽ���ѯ������
 * @author jian_cao  date:2012-8-13
 * @version EAS6.1
 */
public class FDCBudgetUtil {

	/**
	 * �����Ѹ�ȡ:���ƻ��·ݡ��е��ۼƸ�������ƻ��·ݡ����Ѹ���״̬�ĸ�����ۼ�ֵ��
	 * 
	 * @param firstDay
	 * @param lastDay
	 * @param projectId
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 */
	public static BigDecimal getActPaied(Date firstDay, Date lastDay, String contractBillId) throws BOSException, SQLException {
		BigDecimal actPaied = FDCHelper.ZERO;
		FDCSQLBuilder builderActPaied = new FDCSQLBuilder();
		builderActPaied.appendSql("select sum(FLocalAmount) as actPaied ");
		builderActPaied.appendSql(" from t_cas_paymentbill  as pay where pay.FBillStatus = ");
		builderActPaied.appendParam(new Integer(BillStatusEnum.PAYED_VALUE));
		builderActPaied.appendSql(" and pay.FBizDate >= ");
		builderActPaied.appendParam(firstDay);
		builderActPaied.appendSql(" and pay.FBizDate <= ");
		builderActPaied.appendParam(lastDay);
		builderActPaied.appendSql(" and pay.FContractBillID = ");
		builderActPaied.appendParam(contractBillId);
		IRowSet rowSetActPaied = builderActPaied.executeQuery();
		while (rowSetActPaied.next()) {
			if (rowSetActPaied.getString("actPaied") != null) {
				actPaied = rowSetActPaied.getBigDecimal("actPaied");
			}
		}
		return actPaied;
	}

	
	
	
	
	
	/**
	 * 
	 * ���������㸶�����뵥��;��� <br/>
	 * ȡ���������뵥�ġ�ҵ�����ڡ������·ݵġ����ύ�����������С��������������������Ӧ�����Ϊ���Ѹ��״̬�ĸ������뵥�ġ���λ�ҽ����ۼ�ֵ
	 * @param firstDay
	 * @param lastDay
	 * @param projectId
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 * @Author��jian_cao
	 * @CreateTime��2012-8-13
	 */
	public static BigDecimal getPayRequestBillFloatFund(Date firstDay, Date lastDay, String contractId, BOSUuid payReqID)
			throws BOSException, SQLException {

		BigDecimal floatFund = FDCHelper.ZERO;
		FDCSQLBuilder builderFloatFund = new FDCSQLBuilder();
		
		/* modified by zhaoqin for R131218-0367 on 2013/12/27 start */
		// ȡ��ͬ�ڹ��̿�
		//builderFloatFund.appendSql(" select sum(payReqBill.Famount) as floatFund from (");
		builderFloatFund.appendSql(" select sum(payReqBill.FProjectPriceInContract) as floatFund from (");
		//builderFloatFund.appendSql(" select payReqBill.fid, payReqBill.Famount from T_CON_PayRequestBill as payReqBill");
		builderFloatFund.appendSql(" select payReqBill.fid, payReqBill.FProjectPriceInContract from T_CON_PayRequestBill as payReqBill");
		/* modified by zhaoqin for R131218-0367 on 2013/12/27 end */

		//��ѯָ����ͬ���
		builderFloatFund.appendSql(" where payReqBill.FContractId=");
		builderFloatFund.appendParam(contractId);
		//�������뵥��ҵ������
		builderFloatFund.appendSql(" and payReqBill.FBookedDate >= ");
		builderFloatFund.appendParam(firstDay);
		builderFloatFund.appendSql(" and payReqBill.FBookedDate <= ");
		builderFloatFund.appendParam(lastDay);
		if (payReqID != null) {
			builderFloatFund.appendSql(" and payReqBill.fid !=  ");
			builderFloatFund.appendParam(payReqID.toString());
		}
		builderFloatFund.appendSql(" and payReqBill.FState in('");
		//���ύ
		builderFloatFund.appendSql(FDCBillStateEnum.SUBMITTED_VALUE);
		//������
		builderFloatFund.appendSql("', '" + FDCBillStateEnum.AUDITTING_VALUE);
		//������
		builderFloatFund.appendSql("', '" + FDCBillStateEnum.AUDITTED_VALUE);
		builderFloatFund.appendSql("') )as payReqBill ");
		//�����Ӳ�ѯ��������ύ��������״̬�����뵥��û�в����������Ҫ�����ӣ�
		builderFloatFund.appendSql("left join  T_CAS_PaymentBill as pay  on payReqBill.fid=pay.ffdcPayReqID where pay.FbillStatus <> ");
		//���״̬��Ϊ�Ѹ���
		builderFloatFund.appendParam(new Integer(BillStatusEnum.PAYED_VALUE));
		//���ύ��������״̬�����뵥��û�в���������Ը������뵥�ǿ�
		builderFloatFund.appendSql(" or pay.FbillStatus is null");

		IRowSet rowSetFloatFund = builderFloatFund.executeQuery();
		while (rowSetFloatFund.next()) {
			if (rowSetFloatFund.getString("floatFund") != null) {
				floatFund = rowSetFloatFund.getBigDecimal("floatFund");
			}
		}
		return floatFund;
	}

	/**
	 * ���������㸶���;<br/>
	 * ȡ������ġ�ҵ�����ڡ������·ݵġ����ύδ����ĸ���ġ���λ�ҽ����ۼ�ֵ
	 * @param firstDay
	 * @param lastDay
	 * @param projectId
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 */
	public static BigDecimal getPaymentBillFloatFund(Date firstDay, Date lastDay, String contractBillId) throws BOSException, SQLException {

		BigDecimal floatFund = FDCHelper.ZERO;
		FDCSQLBuilder builderFloatFund = new FDCSQLBuilder();
		builderFloatFund.appendSql(" select sum(FLocalAmount) as floatFund ");
		builderFloatFund.appendSql(" from t_cas_paymentbill ");
		builderFloatFund.appendSql(" where FbillStatus = ");
		builderFloatFund.appendParam(new Integer(BillStatusEnum.SUBMIT_VALUE));
		builderFloatFund.appendSql(" and FBizDate >= ");
		builderFloatFund.appendParam(firstDay);
		builderFloatFund.appendSql(" and FBizDate <= ");
		builderFloatFund.appendParam(lastDay);
		builderFloatFund.appendSql(" and FContractBillID = ");
		builderFloatFund.appendParam(contractBillId);

		IRowSet rowSetFloatFund = builderFloatFund.executeQuery();
		while (rowSetFloatFund.next()) {
			if (rowSetFloatFund.getString("floatFund") != null) {
				floatFund = rowSetFloatFund.getBigDecimal("floatFund");
			}
		}
		return floatFund;
	}
	
	/**
	 * �ۼ��Ѹ����̿�
	 * 
	 * @author RD_zhaoqin
	 * @date 2013/12/25
	 */
	public static BigDecimal getPaid(Date firstDay, Date lastDay, String contractBillId) throws BOSException, SQLException  {
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql(" select isnull(sum(pb.FProjectPriceInContract),0) + isnull(sum(ppe.FLocAdvance),0) ");
		builder.appendSql(" from T_CAS_PaymentBill as pb ");
		builder.appendSql(" left join T_FNC_PaymentPrjPayEntry as ppe ");
		builder.appendSql(" on pb.FPrjPayEntryID = ppe.FID ");
		builder.appendSql(" where pb.FContractbillId = ").appendParam(contractBillId);
		builder.appendSql(" and pb.fbillstatus = 15 ");
		builder.appendSql(" and pb.FBizDate >= ").appendParam(firstDay);
		builder.appendSql(" and pb.FBizDate <= ").appendParam(lastDay);
		
		IRowSet rs = builder.executeQuery();
		BigDecimal paid = FDCHelper.ZERO;
		if (rs.next()) {
			paid = rs.getBigDecimal(1);
		}
		return paid;
	}
	
}
