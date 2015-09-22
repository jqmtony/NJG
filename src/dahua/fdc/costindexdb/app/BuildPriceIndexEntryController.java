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
import com.kingdee.eas.fdc.costindexdb.BuildPriceIndexEntryInfo;
import com.kingdee.eas.framework.app.CoreBillEntryBaseController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.costindexdb.BuildPriceIndexEntryCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface BuildPriceIndexEntryController extends CoreBillEntryBaseController
{
    public BuildPriceIndexEntryInfo getBuildPriceIndexEntryInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public BuildPriceIndexEntryInfo getBuildPriceIndexEntryInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public BuildPriceIndexEntryInfo getBuildPriceIndexEntryInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public BuildPriceIndexEntryCollection getBuildPriceIndexEntryCollection(Context ctx) throws BOSException, RemoteException;
    public BuildPriceIndexEntryCollection getBuildPriceIndexEntryCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public BuildPriceIndexEntryCollection getBuildPriceIndexEntryCollection(Context ctx, String oql) throws BOSException, RemoteException;
}