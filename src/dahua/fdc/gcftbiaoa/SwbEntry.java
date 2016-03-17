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

public class SwbEntry extends CoreBillEntryBase implements ISwbEntry
{
    public SwbEntry()
    {
        super();
        registerInterface(ISwbEntry.class, this);
    }
    public SwbEntry(Context ctx)
    {
        super(ctx);
        registerInterface(ISwbEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("E1AACC1B");
    }
    private SwbEntryController getController() throws BOSException
    {
        return (SwbEntryController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public SwbEntryInfo getSwbEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSwbEntryInfo(getContext(), pk);
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
    public SwbEntryInfo getSwbEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSwbEntryInfo(getContext(), pk, selector);
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
    public SwbEntryInfo getSwbEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSwbEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public SwbEntryCollection getSwbEntryCollection() throws BOSException
    {
        try {
            return getController().getSwbEntryCollection(getContext());
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
    public SwbEntryCollection getSwbEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSwbEntryCollection(getContext(), view);
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
    public SwbEntryCollection getSwbEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getSwbEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}