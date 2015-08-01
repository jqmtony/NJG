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
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ObjectBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.basedata.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IObjectBase;

public class ProjectIndexDataEntry extends ObjectBase implements IProjectIndexDataEntry
{
    public ProjectIndexDataEntry()
    {
        super();
        registerInterface(IProjectIndexDataEntry.class, this);
    }
    public ProjectIndexDataEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IProjectIndexDataEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("3DCF312B");
    }
    private ProjectIndexDataEntryController getController() throws BOSException
    {
        return (ProjectIndexDataEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public ProjectIndexDataEntryInfo getProjectIndexDataEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getProjectIndexDataEntryInfo(getContext(), pk);
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
    public ProjectIndexDataEntryInfo getProjectIndexDataEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getProjectIndexDataEntryInfo(getContext(), pk, selector);
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
    public ProjectIndexDataEntryInfo getProjectIndexDataEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getProjectIndexDataEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public ProjectIndexDataEntryCollection getProjectIndexDataEntryCollection() throws BOSException
    {
        try {
            return getController().getProjectIndexDataEntryCollection(getContext());
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
    public ProjectIndexDataEntryCollection getProjectIndexDataEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getProjectIndexDataEntryCollection(getContext(), view);
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
    public ProjectIndexDataEntryCollection getProjectIndexDataEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getProjectIndexDataEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}