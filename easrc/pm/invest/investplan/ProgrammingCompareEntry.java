package com.kingdee.eas.port.pm.invest.investplan;

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
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.port.pm.invest.investplan.app.*;
import com.kingdee.bos.framework.*;

public class ProgrammingCompareEntry extends CoreBillEntryBase implements IProgrammingCompareEntry
{
    public ProgrammingCompareEntry()
    {
        super();
        registerInterface(IProgrammingCompareEntry.class, this);
    }
    public ProgrammingCompareEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IProgrammingCompareEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("E1960159");
    }
    private ProgrammingCompareEntryController getController() throws BOSException
    {
        return (ProgrammingCompareEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public ProgrammingCompareEntryInfo getProgrammingCompareEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getProgrammingCompareEntryInfo(getContext(), pk);
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
    public ProgrammingCompareEntryInfo getProgrammingCompareEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getProgrammingCompareEntryInfo(getContext(), pk, selector);
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
    public ProgrammingCompareEntryInfo getProgrammingCompareEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getProgrammingCompareEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public ProgrammingCompareEntryCollection getProgrammingCompareEntryCollection() throws BOSException
    {
        try {
            return getController().getProgrammingCompareEntryCollection(getContext());
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
    public ProgrammingCompareEntryCollection getProgrammingCompareEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getProgrammingCompareEntryCollection(getContext(), view);
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
    public ProgrammingCompareEntryCollection getProgrammingCompareEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getProgrammingCompareEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}