package com.kingdee.eas.fi.ar.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fi.ar.OtherBillInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fi.ar.OtherBillCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface OtherBillController extends ArApBillBaseController
{
    public OtherBillInfo getOtherBillInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public OtherBillInfo getOtherBillInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public OtherBillInfo getOtherBillInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public OtherBillCollection getOtherBillCollection(Context ctx) throws BOSException, RemoteException;
    public OtherBillCollection getOtherBillCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public OtherBillCollection getOtherBillCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public boolean isAudited(Context ctx, String billId) throws BOSException, EASBizException, RemoteException;
    public boolean isVouchered(Context ctx, String billId) throws BOSException, EASBizException, RemoteException;
    public void audit(Context ctx, IObjectPK billId) throws BOSException, EASBizException, RemoteException;
    public void delVoucher(Context ctx, String billId) throws BOSException, EASBizException, RemoteException;
    public boolean isInitBill(Context ctx, String billId) throws BOSException, EASBizException, RemoteException;
    public boolean hasVerifiedInitBill(Context ctx) throws BOSException, EASBizException, RemoteException;
    public IObjectPK[] createReverseBill(Context ctx, IObjectPK[] sourceBillPK) throws BOSException, EASBizException, RemoteException;
    public int getBillType(Context ctx, String billId) throws BOSException, EASBizException, RemoteException;
    public boolean arIsRelatedAccount(Context ctx, String companyId) throws BOSException, EASBizException, RemoteException;
    public void canReverse(Context ctx, IObjectPK billpk) throws BOSException, EASBizException, RemoteException;
    public IObjectValue creditTransfer(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public void generateVoucher(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public void createReceivingBill(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public boolean doBlankOut(Context ctx, IObjectPK objectPK) throws BOSException, EASBizException, RemoteException;
    public void unpassAudit(Context ctx, IObjectPK[] pks, CoreBillBaseCollection coreBillCollection) throws BOSException, EASBizException, RemoteException;
    public void cancelAppointmentVoucher(Context ctx, IObjectPK billId) throws BOSException, EASBizException, RemoteException;
    public void cancelAppointmentAccount(Context ctx, IObjectPK billId) throws BOSException, EASBizException, RemoteException;
    public void editVouch(Context ctx, String billId) throws BOSException, EASBizException, RemoteException;
}