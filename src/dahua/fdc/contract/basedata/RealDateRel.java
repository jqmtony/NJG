package com.kingdee.eas.fdc.contract.basedata;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

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
import com.kingdee.eas.fdc.contract.basedata.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IDataBase;

public class RealDateRel extends DataBase implements IRealDateRel
{
    public RealDateRel()
    {
        super();
        registerInterface(IRealDateRel.class, this);
    }
    public RealDateRel(Context ctx)
    {
        super(ctx);
        registerInterface(IRealDateRel.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("FF80291B");
    }
    private RealDateRelController getController() throws BOSException
    {
        return (RealDateRelController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public RealDateRelInfo getRealDateRelInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getRealDateRelInfo(getContext(), pk);
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
    public RealDateRelInfo getRealDateRelInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getRealDateRelInfo(getContext(), pk, selector);
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
    public RealDateRelInfo getRealDateRelInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getRealDateRelInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public RealDateRelCollection getRealDateRelCollection() throws BOSException
    {
        try {
            return getController().getRealDateRelCollection(getContext());
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
    public RealDateRelCollection getRealDateRelCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getRealDateRelCollection(getContext(), view);
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
    public RealDateRelCollection getRealDateRelCollection(String oql) throws BOSException
    {
        try {
            return getController().getRealDateRelCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *从模板导入-User defined method
     *@param model model
     */
    public void importTemp(RealDateRelInfo model) throws BOSException
    {
        try {
            getController().importTemp(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *从集团导入-User defined method
     *@param model model
     */
    public void importGroup(RealDateRelInfo model) throws BOSException
    {
        try {
            getController().importGroup(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}