package com.kingdee.eas.fdc.contract.settle.app;

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
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.contract.settle.SettleDeclarationBillInfo;
import com.kingdee.eas.fdc.contract.settle.SettleDeclarationBillCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface SettleDeclarationBillController extends CoreBillBaseController
{
    public SettleDeclarationBillCollection getSettleDeclarationBillCollection(Context ctx) throws BOSException, RemoteException;
    public SettleDeclarationBillCollection getSettleDeclarationBillCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public SettleDeclarationBillCollection getSettleDeclarationBillCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public SettleDeclarationBillInfo getSettleDeclarationBillInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public SettleDeclarationBillInfo getSettleDeclarationBillInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public SettleDeclarationBillInfo getSettleDeclarationBillInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public void InTrial(Context ctx, IObjectValue model) throws BOSException, RemoteException;
    public void Approved(Context ctx, IObjectValue model) throws BOSException, RemoteException;
    public void Audit(Context ctx, IObjectValue model) throws BOSException, RemoteException;
    public void UnAudit(Context ctx, IObjectValue model) throws BOSException, RemoteException;
}