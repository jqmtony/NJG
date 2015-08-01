package com.kingdee.eas.fdc.finance.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.app.DataBaseController;
import com.kingdee.eas.fdc.finance.PayPlanTemplateInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.finance.PayPlanTemplateCollection;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface PayPlanTemplateController extends DataBaseController
{
    public PayPlanTemplateInfo getPayPlanTemplateInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public PayPlanTemplateInfo getPayPlanTemplateInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public PayPlanTemplateInfo getPayPlanTemplateInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public PayPlanTemplateCollection getPayPlanTemplateCollection(Context ctx) throws BOSException, RemoteException;
    public PayPlanTemplateCollection getPayPlanTemplateCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public PayPlanTemplateCollection getPayPlanTemplateCollection(Context ctx, String oql) throws BOSException, RemoteException;
}