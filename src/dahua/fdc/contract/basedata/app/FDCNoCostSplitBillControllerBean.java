package com.kingdee.eas.fdc.basedata.app;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCNoCostSplitBillEntryInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public class FDCNoCostSplitBillControllerBean extends AbstractFDCNoCostSplitBillControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.basedata.app.FDCNoCostSplitBillControllerBean");
    protected String _getLogInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		return null;
	}
	protected void _audit(Context ctx, BOSUuid billId)throws BOSException, EASBizException
    {
         super._audit(ctx, billId);
    }
    protected void _unAudit(Context ctx, BOSUuid billId)throws BOSException, EASBizException
    {
		FDCBillInfo billInfo = new FDCBillInfo();

		billInfo.setId(billId);
		billInfo.setState(FDCBillStateEnum.SAVED);
		billInfo.setAuditor(null);
		billInfo.setAuditTime(null);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("state");
		selector.add("auditor");
		selector.add("auditTime");

		updatePartial(ctx, billInfo, selector);
    }
    protected void _audit(Context ctx, List idList)throws BOSException, EASBizException
    {
         super._audit(ctx, idList);
    }
    protected void _unAudit(Context ctx, List idList)throws BOSException, EASBizException
    {
         super._unAudit(ctx, idList);
    }

	protected void checkBill(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		// TODO 自动生成方法存根
		//super.checkBill(ctx, model);
	}

	protected void _save(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException
	{
		super._save(ctx, pk, model);
	}
	protected IObjectPK _save(Context ctx, IObjectValue model)
			throws BOSException, EASBizException
	{
		return super._save(ctx, model);
	}
	
	protected void _delete(Context ctx, IObjectPK[] arrayPK)
			throws BOSException, EASBizException
	{
		super._delete(ctx, arrayPK);
	}
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException,
			EASBizException
	{
		super._delete(ctx, pk);
	}
	
	protected void setEntrySeq(FDCNoCostSplitBillEntryInfo entry){
		//index作为seq，actionSave_actionPerformed(ActionEvent e)设置index
		entry.setSeq(entry.getIndex());
		
		//index作为拆分组号，costSplit()设置index，并需要禁用actionSave_actionPerformed(ActionEvent e)中的设置
		//entry.setSeq(entry.getIndex()*100000 + entry.getSeq()%100000);	
	}
	
	/**
	 * 描述：返回单据工程项目财务组织一体化参数值 
	 * 
	 * @param ctx
	 * @throws BOSException
	 * @throws EASBizException
	 * @return map(key,value) key:工程项目+参数编码；value：参数值
	 * 
	 * @author pengwei_hou date:2009-06-29
	 */
	protected Map _fetchInitParam(Context ctx) throws BOSException,
			EASBizException {
		Map initParam = new HashMap();
		Set authorizedOrgs = new HashSet();
		Map orgs = null;
		/**
		 * orgs上面已经为空，所以下面代码不用做非空判断
		 */
		/*if(orgs==null){
			orgs = PermissionFactory.getLocalInstance(ctx).getAuthorizedOrgs(
					 new ObjectUuidPK(ContextUtil.getCurrentUserInfo(ctx).getId()),
		            OrgType.CostCenter,  null,  null, null);
		}*/
		
		//update by renliang
		orgs = PermissionFactory.getLocalInstance(ctx).getAuthorizedOrgs(
				 new ObjectUuidPK(ContextUtil.getCurrentUserInfo(ctx).getId()),
	            OrgType.CostCenter,  null,  null, null);
		
		if(orgs!=null){
			Set orgSet = orgs.keySet();
			Iterator it = orgSet.iterator();
			while(it.hasNext()){
				authorizedOrgs.add(it.next());
			}
		}
		
		List number = new ArrayList();
		number.add(FDCConstants.FDC_PARAM_FINACIAL);
		number.add(FDCConstants.FDC_PARAM_ADJUSTVOURCHER);
		number.add(FDCConstants.FDC_PARAM_SIMPLEFINACIAL);
		number.add(FDCConstants.FDC_PARAM_SIMPLEFINACIALEXTEND);
		number.add(FDCConstants.FDC_PARAM_SEPARATEFROMPAYMENT);
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder
				.appendSql("select item.FOrgUnitID,item.FValue_L1,param.FNumber,p.FID from t_bas_paramitem item ");
		builder
				.appendSql("inner join t_bas_param param on param.fid=item.fkeyid ");
		builder
				.appendSql("inner join t_org_baseunit unit on unit.fid = item.forgunitid ");
		builder
				.appendSql("inner join t_fdc_curproject p on p.ffullorgunit=unit.fid ");
		builder.appendSql("where ");
		builder.appendParam("item.FOrgUnitID", authorizedOrgs.toArray());
		builder.appendSql(" and ");
		builder.appendParam("param.FNumber",number.toArray());
		
		IRowSet rowSet = builder.executeQuery();
		try {
			while(rowSet.next()){
				String orgUnitID = rowSet.getString("FOrgUnitID");
				String projectID = rowSet.getString("FID");
				String key = rowSet.getString("FNumber");
				String value = rowSet.getString("FValue_L1");
				initParam.put(key+projectID, value);
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new BOSException(e);
		}
		
		return initParam;
	}
}