package com.kingdee.eas.fdc.gcftbiaoa.app;

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
import com.kingdee.eas.fdc.gcftbiaoa.DecorationEngineeringCollection;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.gcftbiaoa.DecorationEngineeringInfo;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.app.CoreBillBaseControllerBean;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public class DecorationEngineeringControllerBeanEx extends com.kingdee.eas.fdc.gcftbiaoa.app.DecorationEngineeringControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.gcftbiaoa.app.DecorationEngineeringControllerBeanEx");
    protected void _aduit(Context ctx, IObjectValue model)throws BOSException, EASBizException
    {
	     super._aduit(ctx, model);
    }
    protected void _unAudit(Context ctx, IObjectValue model)throws BOSException, EASBizException
    {
	     super._unAudit(ctx, model);
    }
}				
