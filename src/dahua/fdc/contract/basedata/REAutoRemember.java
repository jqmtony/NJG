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
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBase;
import com.kingdee.eas.fdc.basedata.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBase;

public class REAutoRemember extends CoreBase implements IREAutoRemember
{
    public REAutoRemember()
    {
        super();
        registerInterface(IREAutoRemember.class, this);
    }
    public REAutoRemember(Context ctx)
    {
        super(ctx);
        registerInterface(IREAutoRemember.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("2088EE81");
    }
    private REAutoRememberController getController() throws BOSException
    {
        return (REAutoRememberController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public REAutoRememberInfo getREAutoRememberInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getREAutoRememberInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@param selector selector
     *@return
     */
    public REAutoRememberInfo getREAutoRememberInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getREAutoRememberInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param oql oql
     *@return
     */
    public REAutoRememberInfo getREAutoRememberInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getREAutoRememberInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public REAutoRememberCollection getREAutoRememberCollection() throws BOSException
    {
        try {
            return getController().getREAutoRememberCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@param view view
     *@return
     */
    public REAutoRememberCollection getREAutoRememberCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getREAutoRememberCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@param oql oql
     *@return
     */
    public REAutoRememberCollection getREAutoRememberCollection(String oql) throws BOSException
    {
        try {
            return getController().getREAutoRememberCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *save-User defined method
     *@param userID userID
     *@param orgUnitID orgUnitID
     *@param function function
     *@param value value
     */
    public void save(String userID, String orgUnitID, String function, String value) throws BOSException
    {
        try {
            getController().save(getContext(), userID, orgUnitID, function, value);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-User defined method
     *@param userID userID
     *@param orgUnitID orgUnitID
     *@param function function
     *@return
     */
    public String getValue(String userID, String orgUnitID, String function) throws BOSException
    {
        try {
            return getController().getValue(getContext(), userID, orgUnitID, function);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}