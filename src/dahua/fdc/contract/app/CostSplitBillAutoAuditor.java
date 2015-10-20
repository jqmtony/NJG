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
 * ��ֵ��Զ�������, ��ֵ�����Դ��������ͨ����ʱ��,�Զ�����ͨ��(��ȫ���״̬),��Դ���ݷ�������ʱ��,�Զ�������
 * 
 * @author liupd
 *
 */
public class CostSplitBillAutoAuditor {
	
	/**
	 * �Զ�����
	 * @param ctx
	 * @param billPK	Դ����ID(���ͬId)
	 * @param tabName	��ֵ�����(���ͬ��ֵ�����)
	 * @param billFieldName	��ֵ����е�Դ����ID�ֶ�(���ͬ��ֵ��ϵĺ�ͬID�ֶ�)
	 * @throws BOSException
	 */
	public static void autoAudit(Context ctx, IObjectPK billPK, String tabName, String billFieldName) throws BOSException {
		

		processAudit(ctx, billPK, tabName, billFieldName);
		
	}
	
	public static void autoDelete(Context ctx, IObjectPK billPK, String tabName, String billFieldName) throws BOSException {
		

		processDelete(ctx, billPK, tabName, billFieldName);
		
	}
	
	/**
	 * �Զ�������
	 * @param ctx
	 * @param billPK	Դ����ID(���ͬId)
	 * @param tabName	��ֵ�����(���ͬ��ֵ�����)
	 * @param billFieldName	��ֵ����е�Դ����ID�ֶ�(���ͬ��ֵ��ϵĺ�ͬID�ֶ�)
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
		//ֻ��û�����ϵĵ��ݽ��и���	By zhiyuan_tang 2010/07/02
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
		params.add(FDCBillStateEnum.SAVED_VALUE);	//��ԭ������״̬����Ϊ��ֵ�û���ύ״̬
		sql.append(", FAuditorID = null ");
		sql.append(", FAuditTime = null ");
		sql.append(" where ");
		sql.append(billFieldName);
		sql.append(" = ? ");
		params.add(billPK.toString());
		sql.append(" and FState = ? ");
		params.add(FDCBillStateEnum.AUDITTED_VALUE);
		//ֻ��û�����ϵĵ��ݽ��и���	By zhiyuan_tang 2010/07/02
		//ʹ��=0����ʹ��<>1�� ��Ϊ=0��Ч��Ҫ��Ը�һЩ
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
