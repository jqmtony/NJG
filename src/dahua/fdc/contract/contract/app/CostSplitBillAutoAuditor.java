package com.kingdee.eas.fdc.contract.app;

import java.util.ArrayList;
import java.util.List;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.app.DbUtil;

/**
 * 拆分单自动审批类, 拆分单据在源单据审批通过的时候,自动审批通过(完全拆分状态),在源单据反审批的时候,自动反审批
 * 
 * @author liupd
 *
 */
public class CostSplitBillAutoAuditor {
	
	/**
	 * 自动审批
	 * @param ctx
	 * @param billPK	源单据ID(如合同Id)
	 * @param tabName	拆分单表名(如合同拆分单表名)
	 * @param billFieldName	拆分单表中的源单据ID字段(如合同拆分单上的合同ID字段)
	 * @throws BOSException
	 */
	public static void autoAudit(Context ctx, IObjectPK billPK, String tabName, String billFieldName) throws BOSException {
		

		processAudit(ctx, billPK, tabName, billFieldName);
		
	}
	
	public static void autoDelete(Context ctx, IObjectPK billPK, String tabName, String billFieldName) throws BOSException {
		

		processDelete(ctx, billPK, tabName, billFieldName);
		
	}
	
	/**
	 * 自动反审批
	 * @param ctx
	 * @param billPK	源单据ID(如合同Id)
	 * @param tabName	拆分单表名(如合同拆分单表名)
	 * @param billFieldName	拆分单表中的源单据ID字段(如合同拆分单上的合同ID字段)
	 * @throws BOSException
	 */
	public static void autoUnAudit(Context ctx, IObjectPK billPK, String tabName, String billFieldName) throws BOSException {
		
		processUnAudit(ctx, billPK, tabName, billFieldName);
		
	}

	private static void processAudit(Context ctx, IObjectPK billPK, String tabName, String billFieldName) throws BOSException {
		
		List params = new ArrayList();
		StringBuffer sql = new StringBuffer();
		
		sql.append("update ");
		sql.append(tabName);
		sql.append(" set FState = ? ");
		params.add(FDCBillStateEnum.AUDITTED_VALUE);
		sql.append(", FAuditorID = ? ");
		params.add(ContextUtil.getCurrentUserInfo(ctx).getId().toString());
		sql.append(", FAuditTime = ? ");
		params.add(FDCHelper.getSqlDate());
		sql.append(" where ");
		sql.append(billFieldName);
		sql.append(" = ? ");
		params.add(billPK.toString());
		sql.append(" and FSplitState = ? ");
		params.add(CostSplitStateEnum.ALLSPLIT_VALUE);
		//只对没有作废的单据进行更新	By zhiyuan_tang 2010/07/02
		sql.append(" and FIsinvalid = ? ");
		params.add(Boolean.FALSE);
		
		DbUtil.execute(ctx, sql.toString(), params.toArray());
	}
	
	private static void processUnAudit(Context ctx, IObjectPK billPK, String tabName, String billFieldName) throws BOSException {
		
		List params = new ArrayList();
		StringBuffer sql = new StringBuffer();
		
		sql.append("update ");
		sql.append(tabName);
		sql.append(" set FState = ? ");
		params.add(FDCBillStateEnum.SAVED_VALUE);	//还原到保存状态，因为拆分单没有提交状态
		sql.append(", FAuditorID = null ");
		sql.append(", FAuditTime = null ");
		sql.append(" where ");
		sql.append(billFieldName);
		sql.append(" = ? ");
		params.add(billPK.toString());
		sql.append(" and FState = ? ");
		params.add(FDCBillStateEnum.AUDITTED_VALUE);
		//只对没有作废的单据进行更新	By zhiyuan_tang 2010/07/02
		//使用=0，不使用<>1， 因为=0的效率要相对高一些
		sql.append(" and FIsinvalid = 0 ");
		
		DbUtil.execute(ctx, sql.toString(), params.toArray());
	}
	
	private static void processDelete(Context ctx, IObjectPK billPK, String tabName, String billFieldName) throws BOSException {
		
		List params = new ArrayList();
		StringBuffer sql = new StringBuffer();
		
		sql.append("delete from ");
		sql.append(tabName);
		sql.append(" where ");
		sql.append(billFieldName);
		sql.append(" = ? ");
		
		params.add(billPK.toString());		
		DbUtil.execute(ctx, sql.toString(), params.toArray());
	}
	
}
