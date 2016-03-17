package com.kingdee.eas.fdc.gcftbiaoa;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.framework.CoreBillEntryBase;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.gcftbiaoa.app.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class IndoorEngineeringEntry extends CoreBillEntryBase implements IIndoorEngineeringEntry
{
    public IndoorEngineeringEntry()
    {
        super();
        registerInterface(IIndoorEngineeringEntry.class, this);
    }
    public IndoorEngineeringEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IIndoorEngineeringEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("AFCCECD9");
    }
    private IndoorEngineeringEntryController getController() throws BOSException
    {
        return (IndoorEngineeringEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public IndoorEngineeringEntryInfo getIndoorEngineeringEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getIndoorEngineeringEntryInfo(getContext(), pk);
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
    public IndoorEngineeringEntryInfo getIndoorEngineeringEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getIndoorEngineeringEntryInfo(getContext(), pk, selector);
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
    public IndoorEngineeringEntryInfo getIndoorEngineeringEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getIndoorEngineeringEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public IndoorEngineeringEntryCollection getIndoorEngineeringEntryCollection() throws BOSException
    {
        try {
            return getController().getIndoorEngineeringEntryCollection(getContext());
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
    public IndoorEngineeringEntryCollection getIndoorEngineeringEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getIndoorEngineeringEntryCollection(getContext(), view);
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
    public IndoorEngineeringEntryCollection getIndoorEngineeringEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getIndoorEngineeringEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}