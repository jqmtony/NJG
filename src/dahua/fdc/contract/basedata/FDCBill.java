package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.BOSException;
import java.util.Map;
import com.kingdee.eas.fdc.basedata.app.*;
import com.kingdee.bos.dao.IObjectPK;
import java.util.List;
import com.kingdee.eas.framework.CoreBillBase;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBillBase;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

public class FDCBill extends CoreBillBase implements IFDCBill
{
    public FDCBill()
    {
        super();
        registerInterface(IFDCBill.class, this);
    }
    public FDCBill(Context ctx)
    {
        super(ctx);
        registerInterface(IFDCBill.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("5A3079DA");
    }
    private FDCBillController getController() throws BOSException
    {
        return (FDCBillController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public FDCBillInfo getFDCBillInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCBillInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@param selector ȡֵ
     *@return
     */
    public FDCBillInfo getFDCBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCBillInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡֵ-System defined method
     *@param oql ȡֵ
     *@return
     */
    public FDCBillInfo getFDCBillInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCBillInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public FDCBillCollection getFDCBillCollection() throws BOSException
    {
        try {
            return getController().getFDCBillCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@param view ȡ����
     *@return
     */
    public FDCBillCollection getFDCBillCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getFDCBillCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@param oql ȡ����
     *@return
     */
    public FDCBillCollection getFDCBillCollection(String oql) throws BOSException
    {
        try {
            return getController().getFDCBillCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����-User defined method
     *@param idList idList
     */
    public void audit(List idList) throws BOSException, EASBizException
    {
        try {
            getController().audit(getContext(), idList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *������-User defined method
     *@param idList idList
     */
    public void unAudit(List idList) throws BOSException, EASBizException
    {
        try {
            getController().unAudit(getContext(), idList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����-User defined method
     *@param billId billId
     */
    public void audit(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().audit(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *������-User defined method
     *@param billId billId
     */
    public void unAudit(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().unAudit(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����������״̬-User defined method
     *@param billId billId
     */
    public void setAudittingStatus(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().setAudittingStatus(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�����ύ״̬-User defined method
     *@param billId billId
     */
    public void setSubmitStatus(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().setSubmitStatus(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��������-User defined method
     *@param pk pk
     */
    public void cancel(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            getController().cancel(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *������Ч-User defined method
     *@param pk ����PK
     */
    public void cancelCancel(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            getController().cancelCancel(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��������-User defined method
     *@param pkArray pkArray
     */
    public void cancel(IObjectPK[] pkArray) throws BOSException, EASBizException
    {
        try {
            getController().cancel(getContext(), pkArray);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *������Ч-User defined method
     *@param pkArray pkArray
     */
    public void cancelCancel(IObjectPK[] pkArray) throws BOSException, EASBizException
    {
        try {
            getController().cancelCancel(getContext(), pkArray);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����ܷ��ύ-User defined method
     *@param id id
     *@return
     */
    public boolean checkCanSubmit(String id) throws BOSException, EASBizException
    {
        try {
            return getController().checkCanSubmit(getContext(), id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��ȡ�����ʼ������-User defined method
     *@param paramMap paramMap
     *@return
     */
    public Map fetchInitData(Map paramMap) throws BOSException, EASBizException
    {
        try {
            return getController().fetchInitData(getContext(), paramMap);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��ȡ��ʱ�������ʼ������-User defined method
     *@param paramMap paramMap
     *@return
     */
    public Map fetchFilterInitData(Map paramMap) throws BOSException, EASBizException
    {
        try {
            return getController().fetchFilterInitData(getContext(), paramMap);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�����Ƿ��ݻ�-User defined method
     *@param billId PK
     *@param value ֵ
     */
    public void setRespite(BOSUuid billId, boolean value) throws BOSException, EASBizException
    {
        try {
            getController().setRespite(getContext(), billId, value);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�����Ƿ��ݻ�-User defined method
     *@param ids ID�б�
     *@param value ֵ
     */
    public void setRespite(List ids, boolean value) throws BOSException, EASBizException
    {
        try {
            getController().setRespite(getContext(), ids, value);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}