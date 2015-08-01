package com.kingdee.eas.fdc.basedata.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.eas.fdc.basedata.ContractChargeTypeInfo;
import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.basedata.ContractChargeTypeCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface ContractChargeTypeController extends FDCDataBaseController
{
    public ContractChargeTypeInfo getContractChargeTypeInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public ContractChargeTypeInfo getContractChargeTypeInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public ContractChargeTypeInfo getContractChargeTypeInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public ContractChargeTypeCollection getContractChargeTypeCollection(Context ctx) throws BOSException, RemoteException;
    public ContractChargeTypeCollection getContractChargeTypeCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public ContractChargeTypeCollection getContractChargeTypeCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public boolean enabled(Context ctx, IObjectPK ctPK) throws BOSException, EASBizException, RemoteException;
    public boolean disEnabled(Context ctx, IObjectPK ctPK) throws BOSException, EASBizException, RemoteException;
}