package com.kingdee.eas.custom.richinf;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fi.ar.OtherBillCollection;
import com.kingdee.eas.framework.CoreBillBase;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBillBase;
import com.kingdee.eas.custom.richinf.app.*;

public class RichCustomWriteOff extends CoreBillBase implements IRichCustomWriteOff
{
    public RichCustomWriteOff()
    {
        super();
        registerInterface(IRichCustomWriteOff.class, this);
    }
    public RichCustomWriteOff(Context ctx)
    {
        super(ctx);
        registerInterface(IRichCustomWriteOff.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("C6D7BD2B");
    }
    private RichCustomWriteOffController getController() throws BOSException
    {
        return (RichCustomWriteOffController)getBizController();
    }
    /**
     *取集合-System defined method
     *@return
     */
    public RichCustomWriteOffCollection getRichCustomWriteOffCollection() throws BOSException
    {
        try {
            return getController().getRichCustomWriteOffCollection(getContext());
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
    public RichCustomWriteOffCollection getRichCustomWriteOffCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getRichCustomWriteOffCollection(getContext(), view);
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
    public RichCustomWriteOffCollection getRichCustomWriteOffCollection(String oql) throws BOSException
    {
        try {
            return getController().getRichCustomWriteOffCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public RichCustomWriteOffInfo getRichCustomWriteOffInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getRichCustomWriteOffInfo(getContext(), pk);
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
    public RichCustomWriteOffInfo getRichCustomWriteOffInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getRichCustomWriteOffInfo(getContext(), pk, selector);
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
    public RichCustomWriteOffInfo getRichCustomWriteOffInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getRichCustomWriteOffInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *核销和反核销-User defined method
     *@param fpColl 发票集合
     *@param richExamColl 到检单集合
     *@param hxType 核销类型
     *@param ov 客户核销单
     */
    public void aboutHxAndFanHx(OtherBillCollection fpColl, RichExamedCollection richExamColl, int hxType, RichCustomWriteOffInfo ov) throws BOSException
    {
        try {
            getController().aboutHxAndFanHx(getContext(), fpColl, richExamColl, hxType, ov);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}