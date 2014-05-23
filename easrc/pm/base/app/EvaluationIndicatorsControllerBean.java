package com.kingdee.eas.port.pm.base.app;

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

import com.kingdee.eas.port.pm.base.EvaluationIndicatorsFactory;
import com.kingdee.eas.port.pm.base.EvaluationIndicatorsInfo;
import com.kingdee.eas.port.pm.base.IEvaluationIndicators;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.port.pm.base.EvaluationIndicatorsCollection;
import com.kingdee.eas.framework.app.DataBaseControllerBean;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.DataBaseCollection;

public class EvaluationIndicatorsControllerBean extends AbstractEvaluationIndicatorsControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.port.pm.base.app.EvaluationIndicatorsControllerBean");
    @Override
    protected void _cancel(Context ctx, IObjectPK pk, IObjectValue model)
    		throws BOSException, EASBizException {
    	// TODO Auto-generated method stub
    	super._cancel(ctx, pk, model);
    	EvaluationIndicatorsInfo info = (EvaluationIndicatorsInfo) model;
    	IEvaluationIndicators ieval = EvaluationIndicatorsFactory.getLocalInstance(ctx);
    	info = ieval.getEvaluationIndicatorsInfo(new ObjectUuidPK(info.getId()));
    	info.setUse(false);
    	ieval.update(new ObjectUuidPK(info.getId()), info);
    }
    
    @Override
    protected void _cancelCancel(Context ctx, IObjectPK pk, IObjectValue model)
    		throws BOSException, EASBizException {
    	// TODO Auto-generated method stub
    	super._cancelCancel(ctx, pk, model);
    	EvaluationIndicatorsInfo info = (EvaluationIndicatorsInfo) model;
    	IEvaluationIndicators ieval = EvaluationIndicatorsFactory.getLocalInstance(ctx);
    	info = ieval.getEvaluationIndicatorsInfo(new ObjectUuidPK(info.getId()));
    	info.setUse(true);
    	ieval.update(new ObjectUuidPK(info.getId()), info);
    }
}