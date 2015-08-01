package com.kingdee.eas.fdc.material.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.material.MaterialEnterSumInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.app.FDCBillController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.material.MaterialEnterSumCollection;
import java.util.List;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface MaterialEnterSumController extends FDCBillController
{
    public MaterialEnterSumCollection getMaterialEnterSumCollection(Context ctx) throws BOSException, RemoteException;
    public MaterialEnterSumCollection getMaterialEnterSumCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public MaterialEnterSumCollection getMaterialEnterSumCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public MaterialEnterSumInfo getMaterialEnterSumInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public MaterialEnterSumInfo getMaterialEnterSumInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public MaterialEnterSumInfo getMaterialEnterSumInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public boolean refSetPlanSumQty(Context ctx, List refList, FDCBillStateEnum state) throws BOSException, RemoteException;
}