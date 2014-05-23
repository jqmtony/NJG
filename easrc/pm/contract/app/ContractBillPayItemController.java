package com.kingdee.eas.port.pm.contract.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.port.pm.contract.ContractBillPayItemInfo;
import com.kingdee.eas.framework.app.CoreBillEntryBaseController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.port.pm.contract.ContractBillPayItemCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface ContractBillPayItemController extends CoreBillEntryBaseController
{
    public ContractBillPayItemInfo getContractBillPayItemInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public ContractBillPayItemInfo getContractBillPayItemInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public ContractBillPayItemInfo getContractBillPayItemInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public ContractBillPayItemCollection getContractBillPayItemCollection(Context ctx) throws BOSException, RemoteException;
    public ContractBillPayItemCollection getContractBillPayItemCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public ContractBillPayItemCollection getContractBillPayItemCollection(Context ctx, String oql) throws BOSException, RemoteException;
}