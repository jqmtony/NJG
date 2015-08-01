package com.kingdee.eas.fdc.basedata.util;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * ���ȡ��
 * @author pengwei_hou
 *
 */
public class FDCSplitAcctHelper {
	
	/**
	 * ��ͬ���
	 * @param ctx
	 * @param prjId
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 */
	public static Map getContractSplitData(Context ctx,String prjId) throws BOSException,SQLException{
		Map conSplitMap=new HashMap();
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql("select con.fid fcontractbillid,FCostAccountId,sum(entry.FAmount) amount \n");
		builder.appendSql("from T_AIM_CostSplitEntry entry   \n ");
		builder.appendSql("inner join T_AIM_CostSplit head on entry.FParentId=head.FId  \n ");
		builder.appendSql("inner join T_CON_ContractBill con on head.FCostBillID=con.FID   \n ");
		builder.appendSql("where FCostBillType='CONTRACTSPLIT' and head.FIsInvalid=0 And  entry.fobjectid=?  \n ");
		builder.appendSql(" and entry.fcostaccountid in (select fid from T_fdc_costAccount where fcurProject=? and fisleaf=1) \n ");
		builder.appendSql("group by con.fid,entry.fcostaccountid \n ");
		builder.addParam(prjId);
		builder.addParam(prjId);
		IRowSet rowSet=builder.executeQuery();
		while(rowSet.next()){
			String costaccountId=rowSet.getString("Fcostaccountid");
			String contractId=rowSet.getString("fcontractbillid");
			BigDecimal amount = rowSet.getBigDecimal("amount");
			String key = costaccountId + contractId;
			conSplitMap.put(key,amount);
		}
		return conSplitMap;
	}
	
	/**
	 * ������
	 * @param ctx
	 * @param prjId
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 */
	public static Map getChangeSplitData(Context ctx,String prjId) throws BOSException,SQLException{
		Map changeSplitMap=new HashMap();
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql("select change.fcontractbillid fcontractbillid,FCostAccountId,sum(entry.FAmount) amount \n");
		builder.appendSql("from T_AIM_CostSplitEntry entry   \n ");
		builder.appendSql("inner join T_AIM_CostSplit head on entry.FParentId=head.FId  \n ");
		builder.appendSql("inner join T_CON_ContractChangeBill change on head.FCostBillID=change.FID \n ");
		builder.appendSql("where FCostBillType='CNTRCHANGESPLIT' and head.FIsInvalid=0 And  entry.fobjectid=?  \n ");
		builder.appendSql(" and entry.fcostaccountid in (select fid from T_fdc_costAccount where fcurProject=? and fisleaf=1) \n ");
		builder.appendSql("group by change.fcontractbillid,entry.fcostaccountid \n ");
		builder.addParam(prjId);
		builder.addParam(prjId);
		IRowSet rowSet=builder.executeQuery();
		while(rowSet.next()){
			String costaccountId=rowSet.getString("Fcostaccountid");
			String contractId=rowSet.getString("fcontractbillid");
			BigDecimal amount = rowSet.getBigDecimal("amount");
			String key = costaccountId + contractId;
			changeSplitMap.put(key,amount);
		}
		return changeSplitMap;
	}
	
	/**
	 * ������<p>
	 * δ���ս��㣬ȡ��ͬ�����н��㵥��ֵ���Ŀ�ĺϼƽ�
	 * ���ս��㣬ȡ���ս��㵥��ֵ���Ŀ�Ľ��
	 * @param ctx
	 * @param prjId
	 * @return settleSplitMap key: ��ĿID+��ͬID
	 * @throws BOSException
	 * @throws SQLException
	 */
	public static Map getSettleSplitData(Context ctx,String prjId) throws BOSException,SQLException{
		Map settleSplitMap=new HashMap();// Key:�ɱ���ĿId     Value:ĳһ���ɱ���Ŀ����غ�ͬ����Ϣ ��Map-  
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql("select FCostAccountId,sum(entry.FAmount) amount,settle.fcontractbillid fcontractbillid from T_AIM_CostSplitEntry entry  \n");
		builder.appendSql("inner join T_AIM_CostSplit head on entry.FParentId=head.FId  \n");
		builder.appendSql("inner join T_CON_ContractSettlementBill settle on head.FCostBillID=settle.FID  \n");
		builder.appendSql("where FCostBillType='SETTLEMENTSPLIT' and head.fcontrolUnitId in (select fcontrolUnitId from T_FDC_CurProject where fid=entry.fobjectid) and head.FIsInvalid=0 And  entry.fobjectid=? and  ((settle.FIsSettled=1 and settle.FIsFinalSettle=1) or (settle.FIsSettled=0 and settle.FIsFinalSettle=0))  \n");
		builder.appendSql("group by fcostaccountid,fcontractbillid\n");//���ɱ���Ŀ���ͬ��Ŀ��Ϸ���
		builder.addParam(prjId);
		IRowSet rowSet=builder.executeQuery();
		while(rowSet.next()){
			String costaccountId=rowSet.getString("Fcostaccountid");
			String contractId=rowSet.getString("fcontractbillid");
			BigDecimal amount = rowSet.getBigDecimal("amount");
			String key = costaccountId + contractId;
			settleSplitMap.put(key,amount);
			
		}
		return settleSplitMap;
	}
	
	/**
	 * ������<p>
	 * δ���ս��㣬ȡ��ͬ�����н��㵥��ֵ���Ŀ�ĺϼƽ�
	 * ���ս��㣬ȡ���ս��㵥��ֵ���Ŀ�Ľ��
	 * @param ctx
	 * @param prjId
	 * @return settleSplitMap key:��ĿID
	 * @throws BOSException
	 * @throws SQLException
	 */
	public static Map getSettleSplits(Context ctx,String prjId) throws BOSException,SQLException{
		Map settleSplitMap=new HashMap();// Key:�ɱ���ĿId     Value:ĳһ���ɱ���Ŀ����غ�ͬ����Ϣ ��Map-  
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql("select FCostAccountId,sum(entry.FAmount) amount from T_AIM_CostSplitEntry entry  \n");
		builder.appendSql("inner join T_AIM_CostSplit head on entry.FParentId=head.FId  \n");
		builder.appendSql("inner join T_CON_ContractSettlementBill settle on head.FCostBillID=settle.FID  \n");
		builder.appendSql("where FCostBillType='SETTLEMENTSPLIT' and head.fcontrolUnitId in (select fcontrolUnitId from T_FDC_CurProject where fid=entry.fobjectid) and head.FIsInvalid=0 And  entry.fobjectid=? and  ((settle.FIsSettled=1 and settle.FIsFinalSettle=1) or (settle.FIsSettled=0 and settle.FIsFinalSettle=0))  \n");
		builder.appendSql("group by fcostaccountid\n");//���ɱ���Ŀ���ͬ��Ŀ��Ϸ���
		builder.addParam(prjId);
		IRowSet rowSet=builder.executeQuery();
		while(rowSet.next()){
			String costaccountId=rowSet.getString("Fcostaccountid");
			BigDecimal amount = rowSet.getBigDecimal("amount");
			settleSplitMap.put(costaccountId,amount);
			
		}
		return settleSplitMap;
	}
	
	/**
	 * ���ı����
	 * @param ctx
	 * @param prjId
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 */
	public static Map getNoTextSplitData(Context ctx,String prjId) throws BOSException,SQLException{
		Map settleSplitMap=new HashMap();// Key:�ɱ���ĿId     Value:ĳһ���ɱ���Ŀ����غ�ͬ����Ϣ ��Map-  
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql("select FCostAccountId,sum(entry.FAmount) amount,bill.fcontractbillid fcontractbillid from T_AIM_CostSplitEntry entry  \n");
		builder.appendSql("inner join T_AIM_CostSplit head on entry.FParentId=head.FId  \n");
		builder.appendSql("inner join T_CAS_PaymentBill bill on head.FCostBillID=bill.FID  \n");
		builder.appendSql("where FCostBillType='NOTEXTCONSPLIT' and head.fcontrolUnitId in (select fcontrolUnitId from T_FDC_CurProject where fid=entry.fobjectid) and head.FIsInvalid=0 And  entry.fobjectid=? \n");
		builder.appendSql("group by fcostaccountid,bill.fcontractbillid\n");//���ɱ���Ŀ���ͬ��Ŀ��Ϸ���
		builder.addParam(prjId);
		IRowSet rowSet=builder.executeQuery();
		while(rowSet.next()){
			String costaccountId=rowSet.getString("Fcostaccountid");
			String contractId=rowSet.getString("fcontractbillid");
			BigDecimal amount = rowSet.getBigDecimal("amount");
			String key = costaccountId + contractId;
			settleSplitMap.put(key,amount);
			
		}
		return settleSplitMap;
	}
}
