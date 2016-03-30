package com.kingdee.eas.fdc.aimcost.prjdynamiccostbill;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.fdc.basedata.IFDCSplitBillEntry;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.app.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntry;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class OtherSplitNewEntry extends FDCSplitBillEntry implements IOtherSplitNewEntry
{
    public OtherSplitNewEntry()
    {
        super();
        registerInterface(IOtherSplitNewEntry.class, this);
    }
    public OtherSplitNewEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IOtherSplitNewEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("09BD8D62");
    }
    private OtherSplitNewEntryController getController() throws BOSException
    {
        return (OtherSplitNewEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public OtherSplitNewEntryInfo getOtherSplitNewEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getOtherSplitNewEntryInfo(getContext(), pk);
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
    public OtherSplitNewEntryInfo getOtherSplitNewEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getOtherSplitNewEntryInfo(getContext(), pk, selector);
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
    public OtherSplitNewEntryInfo getOtherSplitNewEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getOtherSplitNewEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public OtherSplitNewEntryCollection getOtherSplitNewEntryCollection() throws BOSException
    {
        try {
            return getController().getOtherSplitNewEntryCollection(getContext());
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
    public OtherSplitNewEntryCollection getOtherSplitNewEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getOtherSplitNewEntryCollection(getContext(), view);
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
    public OtherSplitNewEntryCollection getOtherSplitNewEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getOtherSplitNewEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}