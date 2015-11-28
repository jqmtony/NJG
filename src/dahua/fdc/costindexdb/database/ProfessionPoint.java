package com.kingdee.eas.fdc.costindexdb.database;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.eas.fdc.costindexdb.database.app.*;
import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.DataBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IDataBase;

public class ProfessionPoint extends DataBase implements IProfessionPoint
{
    public ProfessionPoint()
    {
        super();
        registerInterface(IProfessionPoint.class, this);
    }
    public ProfessionPoint(Context ctx)
    {
        super(ctx);
        registerInterface(IProfessionPoint.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("8C4A44ED");
    }
    private ProfessionPointController getController() throws BOSException
    {
        return (ProfessionPointController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public ProfessionPointInfo getProfessionPointInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getProfessionPointInfo(getContext(), pk);
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
    public ProfessionPointInfo getProfessionPointInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getProfessionPointInfo(getContext(), pk, selector);
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
    public ProfessionPointInfo getProfessionPointInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getProfessionPointInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public ProfessionPointCollection getProfessionPointCollection() throws BOSException
    {
        try {
            return getController().getProfessionPointCollection(getContext());
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
    public ProfessionPointCollection getProfessionPointCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getProfessionPointCollection(getContext(), view);
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
    public ProfessionPointCollection getProfessionPointCollection(String oql) throws BOSException
    {
        try {
            return getController().getProfessionPointCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}