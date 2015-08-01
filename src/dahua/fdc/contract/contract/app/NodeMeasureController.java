package com.kingdee.eas.fdc.contract.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import java.util.Map;
import java.util.Date;
import com.kingdee.eas.fdc.basedata.app.FDCBillController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.contract.NodeMeasureInfo;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.contract.NodeMeasureCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface NodeMeasureController extends FDCBillController
{
    public NodeMeasureInfo getNodeMeasureInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public NodeMeasureInfo getNodeMeasureInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public NodeMeasureInfo getNodeMeasureInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public NodeMeasureCollection getNodeMeasureCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public NodeMeasureCollection getNodeMeasureCollection(Context ctx) throws BOSException, RemoteException;
    public NodeMeasureCollection getNodeMeasureCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public Map fetchData(Context ctx, String contractId, String nodeMeasureId) throws BOSException, EASBizException, RemoteException;
    public Map fetchExecData(Context ctx, String contractId, Date startDate, Date endDate) throws BOSException, EASBizException, RemoteException;
}