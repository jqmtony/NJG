package com.kingdee.eas.fdc.basedata.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import java.lang.String;

import com.kingdee.eas.basedata.assistant.SettlementTypeInfo;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.eas.fdc.basedata.AdjustReasonInfo;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.basedata.FDCDataBaseCollection;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.fdc.basedata.AdjustReasonCollection;
import com.kingdee.eas.framework.DataBaseCollection;
import com.kingdee.eas.util.app.DbUtil;
/**
 * 描述:调整原因
 * @author jackwang  date:2006-7-7 <p>
 * @version EAS5.1
 */
public class AdjustReasonControllerBean extends AbstractAdjustReasonControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.basedata.app.AdjustReasonControllerBean");
	/**
	 * 新增
	 * @param ctx
	 * @param model
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	protected IObjectPK _addnew(Context ctx, IObjectValue model)
		throws BOSException, EASBizException {
		CtrlUnitInfo rootCU=new CtrlUnitInfo();
		rootCU.setId(BOSUuid.read(OrgConstants.DEF_CU_ID));
		((AdjustReasonInfo)model).setCU(rootCU);
		addnewCheck(ctx, model);
		return super._addnew(ctx, model);
	}
	/**
	 * 新增逻辑检查
	 *   1.检查编码名称是否为空。
	 *   2.检查编码名称是否重复。
	 * @param ctx
	 * @param model
	 * @throws BOSException
	 * @throws EASBizException
	 */
	protected void addnewCheck(Context ctx, IObjectValue model)
		throws BOSException, EASBizException {
//		((SettlementTypeInfo)model).setCU(AssistantCtrlUnitUtils.getRootCU(ctx));
		this._checkNumberBlank(ctx, model);
		this._checkNameBlank(ctx, model);
		this._checkNumberDup(ctx, model);
		this._checkNameDup(ctx, model);
	}
	protected boolean _enabled(Context ctx, IObjectPK ctPK) throws BOSException, EASBizException {
	    String sql = "update T_FDC_AdjustReason set  FIsEnabled = ?  where FID = ? ";
	      Object[] params = new Object[]{new Integer(1), ctPK.toString()};
	    DbUtil.execute(ctx,sql,params);
		return true;
	}
	protected boolean _disEnabled(Context ctx, IObjectPK ctPK) throws BOSException, EASBizException {
	    String sql = "update T_FDC_AdjustReason set  FIsEnabled = ?  where FID = ? ";
	      Object[] params = new Object[]{new Integer(0), ctPK.toString()};
	    DbUtil.execute(ctx,sql,params);
		return true;
	}    
}