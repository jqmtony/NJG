package com.kingdee.eas.fdc.basedata.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.basedata.VoucherForProjectCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basedata.VoucherForProjectInfo;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface VoucherForProjectController extends FDCBillController
{
    public VoucherForProjectInfo getVoucherForProjectInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public VoucherForProjectInfo getVoucherForProjectInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public VoucherForProjectInfo getVoucherForProjectInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public VoucherForProjectCollection getVoucherForProjectCollection(Context ctx) throws BOSException, RemoteException;
    public VoucherForProjectCollection getVoucherForProjectCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public VoucherForProjectCollection getVoucherForProjectCollection(Context ctx, String oql) throws BOSException, RemoteException;
}