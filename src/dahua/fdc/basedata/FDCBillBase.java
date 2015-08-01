package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import java.util.Map;
import com.kingdee.eas.framework.IBillBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.basedata.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.framework.BillBase;
import java.util.List;

public class FDCBillBase extends BillBase implements IFDCBillBase
{
    public FDCBillBase()
    {
        super();
        registerInterface(IFDCBillBase.class, this);
    }
    public FDCBillBase(Context ctx)
    {
        super(ctx);
        registerInterface(IFDCBillBase.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("776B77CB");
    }
    private FDCBillBaseController getController() throws BOSException
    {
        return (FDCBillBaseController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public FDCBillBaseInfo getFDCBillBaseInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCBillBaseInfo(getContext(), pk);
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
    public FDCBillBaseInfo getFDCBillBaseInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCBillBaseInfo(getContext(), pk, selector);
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
    public FDCBillBaseInfo getFDCBillBaseInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCBillBaseInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public FDCBillBaseCollection getFDCBillBaseCollection() throws BOSException
    {
        try {
            return getController().getFDCBillBaseCollection(getContext());
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
    public FDCBillBaseCollection getFDCBillBaseCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getFDCBillBaseCollection(getContext(), view);
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
    public FDCBillBaseCollection getFDCBillBaseCollection(String oql) throws BOSException
    {
        try {
            return getController().getFDCBillBaseCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *审批-User defined method
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
     *反审批-User defined method
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
     *审批-User defined method
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
     *反审批-User defined method
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
     *设置审批中状态-User defined method
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
     *设置提交状态-User defined method
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
     *批量作废-User defined method
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
     *批量生效-User defined method
     *@param pk 单据PK
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
     *批量作废-User defined method
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
     *批量生效-User defined method
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
     *检查能否提交-User defined method
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
     *获取界面初始化数据-User defined method
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
     *获取序时簿界面初始化数据-User defined method
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
     *设置是否暂缓-User defined method
     *@param billId PK
     *@param value 值
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
     *设置是否暂缓-User defined method
     *@param ids ID列表
     *@param value 值
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