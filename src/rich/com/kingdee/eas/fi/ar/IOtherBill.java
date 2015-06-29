package com.kingdee.eas.fi.ar;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public interface IOtherBill extends IArApBillBase
{
    public OtherBillInfo getOtherBillInfo(IObjectPK pk) throws BOSException, EASBizException;
    public OtherBillInfo getOtherBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public OtherBillInfo getOtherBillInfo(String oql) throws BOSException, EASBizException;
    public OtherBillCollection getOtherBillCollection() throws BOSException;
    public OtherBillCollection getOtherBillCollection(EntityViewInfo view) throws BOSException;
    public OtherBillCollection getOtherBillCollection(String oql) throws BOSException;
    public boolean isAudited(String billId) throws BOSException, EASBizException;
    public boolean isVouchered(String billId) throws BOSException, EASBizException;
    public void audit(IObjectPK billId) throws BOSException, EASBizException;
    public void delVoucher(String billId) throws BOSException, EASBizException;
    public boolean isInitBill(String billId) throws BOSException, EASBizException;
    public boolean hasVerifiedInitBill() throws BOSException, EASBizException;
    public IObjectPK[] createReverseBill(IObjectPK[] sourceBillPK) throws BOSException, EASBizException;
    public int getBillType(String billId) throws BOSException, EASBizException;
    public boolean arIsRelatedAccount(String companyId) throws BOSException, EASBizException;
    public void canReverse(IObjectPK billpk) throws BOSException, EASBizException;
    public IObjectValue creditTransfer(IObjectPK pk) throws BOSException, EASBizException;
    public void generateVoucher(IObjectPK pk) throws BOSException, EASBizException;
    public void createReceivingBill(IObjectPK pk) throws BOSException, EASBizException;
    public boolean doBlankOut(IObjectPK objectPK) throws BOSException, EASBizException;
    public void unpassAudit(IObjectPK[] pks, CoreBillBaseCollection coreBillCollection) throws BOSException, EASBizException;
    public void cancelAppointmentVoucher(IObjectPK billId) throws BOSException, EASBizException;
    public void cancelAppointmentAccount(IObjectPK billId) throws BOSException, EASBizException;
    public void editVouch(String billId) throws BOSException, EASBizException;
}