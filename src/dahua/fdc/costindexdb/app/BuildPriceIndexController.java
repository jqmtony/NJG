package com.kingdee.eas.fdc.costindexdb.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.costindexdb.BuildPriceIndexCollection;
import com.kingdee.eas.framework.app.CoreBillBaseController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.costindexdb.BuildPriceIndexInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface BuildPriceIndexController extends CoreBillBaseController
{
    public BuildPriceIndexCollection getBuildPriceIndexCollection(Context ctx) throws BOSException, RemoteException;
    public BuildPriceIndexCollection getBuildPriceIndexCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public BuildPriceIndexCollection getBuildPriceIndexCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public BuildPriceIndexInfo getBuildPriceIndexInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public BuildPriceIndexInfo getBuildPriceIndexInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public BuildPriceIndexInfo getBuildPriceIndexInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
}