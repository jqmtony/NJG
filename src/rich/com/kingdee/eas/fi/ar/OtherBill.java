package com.kingdee.eas.fi.ar;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fi.ar.app.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class OtherBill extends ArApBillBase implements IOtherBill
{
    public OtherBill()
    {
        super();
        registerInterface(IOtherBill.class, this);
    }
    public OtherBill(Context ctx)
    {
        super(ctx);
        registerInterface(IOtherBill.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("FC910EF3");
    }
    private OtherBillController getController() throws BOSException
    {
        return (OtherBillController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public OtherBillInfo getOtherBillInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getOtherBillInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@param selector 取值
     *@return
     */
    public OtherBillInfo getOtherBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getOtherBillInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param oql 取值
     *@return
     */
    public OtherBillInfo getOtherBillInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getOtherBillInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public OtherBillCollection getOtherBillCollection() throws BOSException
    {
        try {
            return getController().getOtherBillCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param view 取集合
     *@return
     */
    public OtherBillCollection getOtherBillCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getOtherBillCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param oql 取集合
     *@return
     */
    public OtherBillCollection getOtherBillCollection(String oql) throws BOSException
    {
        try {
            return getController().getOtherBillCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *判断是否审批-User defined method
     *@param billId billId
     *@return
     */
    public boolean isAudited(String billId) throws BOSException, EASBizException
    {
        try {
            return getController().isAudited(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *判断是否生成凭证-User defined method
     *@param billId billId
     *@return
     */
    public boolean isVouchered(String billId) throws BOSException, EASBizException
    {
        try {
            return getController().isVouchered(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *审核-User defined method
     *@param billId billId
     */
    public void audit(IObjectPK billId) throws BOSException, EASBizException
    {
        try {
            getController().audit(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *删除凭证-User defined method
     *@param billId billId
     */
    public void delVoucher(String billId) throws BOSException, EASBizException
    {
        try {
            getController().delVoucher(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *是否为期初单据-User defined method
     *@param billId billId
     *@return
     */
    public boolean isInitBill(String billId) throws BOSException, EASBizException
    {
        try {
            return getController().isInitBill(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *初始单据中是否存在已核销单据-User defined method
     *@return
     */
    public boolean hasVerifiedInitBill() throws BOSException, EASBizException
    {
        try {
            return getController().hasVerifiedInitBill(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *生成冲销应收单-User defined method
     *@param sourceBillPK 被冲销的单据pk
     *@return
     */
    public IObjectPK[] createReverseBill(IObjectPK[] sourceBillPK) throws BOSException, EASBizException
    {
        try {
            return getController().createReverseBill(getContext(), sourceBillPK);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *返回单据种类，1为日常单据，2为期初单据，3为冲销单据-User defined method
     *@param billId billId
     *@return
     */
    public int getBillType(String billId) throws BOSException, EASBizException
    {
        try {
            return getController().getBillType(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *判断应收系统是否与总账联用-User defined method
     *@param companyId 工作流会改变当前ctx中的内容，需要显式的传入当前公司id
     *@return
     */
    public boolean arIsRelatedAccount(String companyId) throws BOSException, EASBizException
    {
        try {
            return getController().arIsRelatedAccount(getContext(), companyId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *是否可以冲销-User defined method
     *@param billpk billpk
     */
    public void canReverse(IObjectPK billpk) throws BOSException, EASBizException
    {
        try {
            getController().canReverse(getContext(), billpk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *债权转移-User defined method
     *@param pk pk
     *@return
     */
    public IObjectValue creditTransfer(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().creditTransfer(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *生成凭证-User defined method
     *@param pk pk
     */
    public void generateVoucher(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            getController().generateVoucher(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *生成付款单-User defined method
     *@param pk pk
     */
    public void createReceivingBill(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            getController().createReceivingBill(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *作废-User defined method
     *@param objectPK objectPK
     *@return
     */
    public boolean doBlankOut(IObjectPK objectPK) throws BOSException, EASBizException
    {
        try {
            return getController().doBlankOut(getContext(), objectPK);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *反审核-User defined method
     *@param pks pks
     *@param coreBillCollection coreBillCollection
     */
    public void unpassAudit(IObjectPK[] pks, CoreBillBaseCollection coreBillCollection) throws BOSException, EASBizException
    {
        try {
            getController().unpassAudit(getContext(), pks, coreBillCollection);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取消凭证指定-User defined method
     *@param billId billId
     */
    public void cancelAppointmentVoucher(IObjectPK billId) throws BOSException, EASBizException
    {
        try {
            getController().cancelAppointmentVoucher(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取消科目指定-User defined method
     *@param billId billId
     */
    public void cancelAppointmentAccount(IObjectPK billId) throws BOSException, EASBizException
    {
        try {
            getController().cancelAppointmentAccount(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *修改凭证-User defined method
     *@param billId billId
     */
    public void editVouch(String billId) throws BOSException, EASBizException
    {
        try {
            getController().editVouch(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}