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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBillBase;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.basedata.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBillBase;

public class LandInfomation extends CoreBillBase implements ILandInfomation
{
    public LandInfomation()
    {
        super();
        registerInterface(ILandInfomation.class, this);
    }
    public LandInfomation(Context ctx)
    {
        super(ctx);
        registerInterface(ILandInfomation.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("F47B6C93");
    }
    private LandInfomationController getController() throws BOSException
    {
        return (LandInfomationController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public LandInfomationInfo getLandInfomationInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getLandInfomationInfo(getContext(), pk);
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
    public LandInfomationInfo getLandInfomationInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getLandInfomationInfo(getContext(), pk, selector);
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
    public LandInfomationInfo getLandInfomationInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getLandInfomationInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public LandInfomationCollection getLandInfomationCollection() throws BOSException
    {
        try {
            return getController().getLandInfomationCollection(getContext());
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
    public LandInfomationCollection getLandInfomationCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getLandInfomationCollection(getContext(), view);
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
    public LandInfomationCollection getLandInfomationCollection(String oql) throws BOSException
    {
        try {
            return getController().getLandInfomationCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}