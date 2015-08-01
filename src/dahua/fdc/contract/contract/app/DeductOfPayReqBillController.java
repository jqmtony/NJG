package com.kingdee.eas.fdc.contract.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.eas.fdc.contract.DeductOfPayReqBillInfo;
import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.app.CoreBaseController;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface DeductOfPayReqBillController extends CoreBaseController
{
    public DeductOfPayReqBillInfo getDeductOfPayReqBillInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public DeductOfPayReqBillInfo getDeductOfPayReqBillInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public DeductOfPayReqBillInfo getDeductOfPayReqBillInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public DeductOfPayReqBillCollection getDeductOfPayReqBillCollection(Context ctx) throws BOSException, RemoteException;
    public DeductOfPayReqBillCollection getDeductOfPayReqBillCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public DeductOfPayReqBillCollection getDeductOfPayReqBillCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public void reCalcAmount(Context ctx, String payReqID) throws BOSException, EASBizException, RemoteException;
}