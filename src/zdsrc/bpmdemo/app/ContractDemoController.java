package com.kingdee.eas.bpmdemo.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.app.CoreBillBaseController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.bpmdemo.ContractDemoInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.bpmdemo.ContractDemoCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface ContractDemoController extends CoreBillBaseController
{
    public ContractDemoCollection getContractDemoCollection(Context ctx) throws BOSException, RemoteException;
    public ContractDemoCollection getContractDemoCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public ContractDemoCollection getContractDemoCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public ContractDemoInfo getContractDemoInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public ContractDemoInfo getContractDemoInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public ContractDemoInfo getContractDemoInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
}