package com.kingdee.eas.fdc.basedata.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
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
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.basedata.FDCDataBaseCollection;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.fdc.basedata.CostTargetInfo;
import com.kingdee.eas.fdc.basedata.CostTargetCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.framework.DataBaseCollection;
import com.kingdee.eas.util.app.DbUtil;

public class CostTargetControllerBean extends AbstractCostTargetControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.basedata.app.CostTargetControllerBean");

	protected boolean _enabled(Context ctx, IObjectPK ctPK) throws BOSException, EASBizException {
	    String sql = "update T_FDC_CostTarget set  FIsEnabled = ?  where FID = ? ";
	      Object[] params = new Object[]{new Integer(1), ctPK.toString()};
	    DbUtil.execute(ctx,sql,params);
		return true;
	}
	protected boolean _disEnabled(Context ctx, IObjectPK ctPK) throws BOSException, EASBizException {
	    String sql = "update T_FDC_CostTarget set  FIsEnabled = ?  where FID = ? ";
	      Object[] params = new Object[]{new Integer(0), ctPK.toString()};
	    DbUtil.execute(ctx,sql,params);
		return true;
	}
}