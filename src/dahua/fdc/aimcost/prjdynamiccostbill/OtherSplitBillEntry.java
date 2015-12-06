package com.kingdee.eas.fdc.aimcost.prjdynamiccostbill;

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
import com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.app.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class OtherSplitBillEntry extends CoreBillEntryBase implements IOtherSplitBillEntry
{
    public OtherSplitBillEntry()
    {
        super();
        registerInterface(IOtherSplitBillEntry.class, this);
    }
    public OtherSplitBillEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IOtherSplitBillEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("D4A79BFB");
    }
    private OtherSplitBillEntryController getController() throws BOSException
    {
        return (OtherSplitBillEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public OtherSplitBillEntryInfo getOtherSplitBillEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getOtherSplitBillEntryInfo(getContext(), pk);
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
    public OtherSplitBillEntryInfo getOtherSplitBillEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getOtherSplitBillEntryInfo(getContext(), pk, selector);
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
    public OtherSplitBillEntryInfo getOtherSplitBillEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getOtherSplitBillEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public OtherSplitBillEntryCollection getOtherSplitBillEntryCollection() throws BOSException
    {
        try {
            return getController().getOtherSplitBillEntryCollection(getContext());
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
    public OtherSplitBillEntryCollection getOtherSplitBillEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getOtherSplitBillEntryCollection(getContext(), view);
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
    public OtherSplitBillEntryCollection getOtherSplitBillEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getOtherSplitBillEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}