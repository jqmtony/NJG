package com.kingdee.eas.fdc.basedata.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.eas.fdc.basedata.ProjectIndexDataInfo;
import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import java.util.Map;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.jdbc.rowset.IRowSet;
import java.util.List;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface ProjectIndexDataController extends FDCBillController
{
    public ProjectIndexDataInfo getProjectIndexDataInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public ProjectIndexDataInfo getProjectIndexDataInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public ProjectIndexDataInfo getProjectIndexDataInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public ProjectIndexDataCollection getProjectIndexDataCollection(Context ctx) throws BOSException, RemoteException;
    public ProjectIndexDataCollection getProjectIndexDataCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public ProjectIndexDataCollection getProjectIndexDataCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public IRowSet sum(Context ctx, List projIdList, String productTypeId) throws BOSException, EASBizException, RemoteException;
    public Map submitAreaIndex(Context ctx, CoreBaseCollection colls) throws BOSException, EASBizException, RemoteException;
    public Map idxRefresh(Context ctx, Map param) throws BOSException, EASBizException, RemoteException;
}