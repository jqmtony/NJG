package com.kingdee.eas.custom.richbase;

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
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.custom.richbase.app.*;
import com.kingdee.eas.framework.IDataBase;

public class OrgUnit extends DataBase implements IOrgUnit
{
    public OrgUnit()
    {
        super();
        registerInterface(IOrgUnit.class, this);
    }
    public OrgUnit(Context ctx)
    {
        super(ctx);
        registerInterface(IOrgUnit.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("257FF4D2");
    }
    private OrgUnitController getController() throws BOSException
    {
        return (OrgUnitController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public OrgUnitInfo getOrgUnitInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getOrgUnitInfo(getContext(), pk);
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
    public OrgUnitInfo getOrgUnitInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getOrgUnitInfo(getContext(), pk, selector);
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
    public OrgUnitInfo getOrgUnitInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getOrgUnitInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public OrgUnitCollection getOrgUnitCollection() throws BOSException
    {
        try {
            return getController().getOrgUnitCollection(getContext());
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
    public OrgUnitCollection getOrgUnitCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getOrgUnitCollection(getContext(), view);
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
    public OrgUnitCollection getOrgUnitCollection(String oql) throws BOSException
    {
        try {
            return getController().getOrgUnitCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}