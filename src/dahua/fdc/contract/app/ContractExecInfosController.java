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
import com.kingdee.eas.fdc.contract.ContractExecInfosCollection;
import com.kingdee.eas.fdc.basedata.app.FDCBillController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.contract.ContractExecInfosInfo;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface ContractExecInfosController extends FDCBillController
{
    public ContractExecInfosInfo getContractExecInfosInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public ContractExecInfosInfo getContractExecInfosInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public ContractExecInfosInfo getContractExecInfosInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public ContractExecInfosCollection getContractExecInfosCollection(Context ctx) throws BOSException, RemoteException;
    public ContractExecInfosCollection getContractExecInfosCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public ContractExecInfosCollection getContractExecInfosCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public void updateContract(Context ctx, String type, String contractId) throws BOSException, EASBizException, RemoteException;
    public void updateChange(Context ctx, String type, String contractId) throws BOSException, EASBizException, RemoteException;
    public void updateSettle(Context ctx, String type, String contractId) throws BOSException, EASBizException, RemoteException;
    public void updatePayment(Context ctx, String type, String contractId) throws BOSException, EASBizException, RemoteException;
    public void synOldContract(Context ctx) throws BOSException, EASBizException, RemoteException;
    public void updateSuppliedContract(Context ctx, String type, String contractId) throws BOSException, EASBizException, RemoteException;
}