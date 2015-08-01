package com.kingdee.eas.fdc.basedata.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CostAccountFacadeFactory;

public class MeasureStageControllerBean extends AbstractMeasureStageControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.basedata.app.MeasureStageControllerBean");
    //记录日志用，不执行任务操作
    protected boolean _disEnabled(Context ctx, IObjectPK ctPK)throws BOSException, EASBizException
    {
        return false;
    }
  //记录日志用，不执行任务操作
    protected boolean _enabled(Context ctx, IObjectPK ctPK)throws BOSException, EASBizException
    {
        return false;
    }
    //新增后，默认设置科目测算阶段值为1
    protected IObjectPK _addnew(Context ctx, IObjectValue model)
    		throws BOSException, EASBizException {
    	IObjectPK pk = super._addnew(ctx, model);
    	//不用传参，方法作了处理
    	CostAccountFacadeFactory.getLocalInstance(ctx).updateAccountStage(null);
    	return pk;
    }
}