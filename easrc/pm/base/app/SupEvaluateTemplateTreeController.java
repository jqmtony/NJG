package com.kingdee.eas.port.pm.base.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.port.pm.base.SupEvaluateTemplateTreeCollection;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.port.pm.base.SupEvaluateTemplateTreeInfo;
import com.kingdee.eas.framework.app.TreeBaseController;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface SupEvaluateTemplateTreeController extends TreeBaseController
{
    public SupEvaluateTemplateTreeInfo getSupEvaluateTemplateTreeInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public SupEvaluateTemplateTreeInfo getSupEvaluateTemplateTreeInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public SupEvaluateTemplateTreeInfo getSupEvaluateTemplateTreeInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public SupEvaluateTemplateTreeCollection getSupEvaluateTemplateTreeCollection(Context ctx) throws BOSException, RemoteException;
    public SupEvaluateTemplateTreeCollection getSupEvaluateTemplateTreeCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public SupEvaluateTemplateTreeCollection getSupEvaluateTemplateTreeCollection(Context ctx, String oql) throws BOSException, RemoteException;
}