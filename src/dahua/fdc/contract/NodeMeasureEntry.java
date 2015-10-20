package com.kingdee.eas.fdc.contract;

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
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.contract.app.*;

public class NodeMeasureEntry extends CoreBillEntryBase implements INodeMeasureEntry
{
    public NodeMeasureEntry()
    {
        super();
        registerInterface(INodeMeasureEntry.class, this);
    }
    public NodeMeasureEntry(Context ctx)
    {
        super(ctx);
        registerInterface(INodeMeasureEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("3C97D911");
    }
    private NodeMeasureEntryController getController() throws BOSException
    {
        return (NodeMeasureEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@param selector selector
     *@return
     */
    public NodeMeasureEntryInfo getNodeMeasureEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getNodeMeasureEntryInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public NodeMeasureEntryInfo getNodeMeasureEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getNodeMeasureEntryInfo(getContext(), pk);
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
    public NodeMeasureEntryInfo getNodeMeasureEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getNodeMeasureEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public NodeMeasureEntryCollection getNodeMeasureEntryCollection() throws BOSException
    {
        try {
            return getController().getNodeMeasureEntryCollection(getContext());
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
    public NodeMeasureEntryCollection getNodeMeasureEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getNodeMeasureEntryCollection(getContext(), view);
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
    public NodeMeasureEntryCollection getNodeMeasureEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getNodeMeasureEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}