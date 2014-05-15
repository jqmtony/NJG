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
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.framework.app.DataBaseControllerBean;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.DataBaseCollection;
import com.kingdee.eas.port.pm.base.EvaluationTemplateCollection;
import com.kingdee.eas.port.pm.base.EvaluationTemplateFactory;
import com.kingdee.eas.port.pm.base.EvaluationTemplateInfo;
import com.kingdee.eas.port.pm.base.IEvaluationTemplate;
import com.kingdee.eas.port.pm.base.IJudges;
import com.kingdee.eas.port.pm.base.JudgesFactory;
import com.kingdee.eas.port.pm.base.JudgesInfo;

public class EvaluationTemplateControllerBean extends AbstractEvaluationTemplateControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.port.pm.base.app.EvaluationTemplateControllerBean");
    @Override
    protected void _cancel(Context ctx, IObjectPK pk, IObjectValue model)
    		throws BOSException, EASBizException {
    	// TODO Auto-generated method stub
    	super._cancel(ctx, pk, model);
    	EvaluationTemplateInfo info = (EvaluationTemplateInfo) model;
    	IEvaluationTemplate ieva = EvaluationTemplateFactory.getLocalInstance(ctx);
    	info = ieva.getEvaluationTemplateInfo(new ObjectUuidPK(info.getId()));
    	info.setUse(false);
    	ieva.update(new ObjectUuidPK(info.getId()), info);
    }
    
    @Override
    protected void _cancelCancel(Context ctx, IObjectPK pk, IObjectValue model)
    		throws BOSException, EASBizException {
    	// TODO Auto-generated method stub
    	super._cancelCancel(ctx, pk, model);
    	EvaluationTemplateInfo info = (EvaluationTemplateInfo) model;
    	IEvaluationTemplate ieva = EvaluationTemplateFactory.getLocalInstance(ctx);
    	info = ieva.getEvaluationTemplateInfo(new ObjectUuidPK(info.getId()));
    	info.setUse(true);
    	ieva.update(new ObjectUuidPK(info.getId()), info);
    }
}