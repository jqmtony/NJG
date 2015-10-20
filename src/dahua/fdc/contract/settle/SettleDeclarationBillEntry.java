package com.kingdee.eas.fdc.contract.settle;

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
import com.kingdee.eas.fdc.contract.settle.app.*;

public class SettleDeclarationBillEntry extends CoreBillEntryBase implements ISettleDeclarationBillEntry
{
    public SettleDeclarationBillEntry()
    {
        super();
        registerInterface(ISettleDeclarationBillEntry.class, this);
    }
    public SettleDeclarationBillEntry(Context ctx)
    {
        super(ctx);
        registerInterface(ISettleDeclarationBillEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("7D67211E");
    }
    private SettleDeclarationBillEntryController getController() throws BOSException
    {
        return (SettleDeclarationBillEntryController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public SettleDeclarationBillEntryInfo getSettleDeclarationBillEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSettleDeclarationBillEntryInfo(getContext(), pk);
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
    public SettleDeclarationBillEntryInfo getSettleDeclarationBillEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSettleDeclarationBillEntryInfo(getContext(), pk, selector);
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
    public SettleDeclarationBillEntryInfo getSettleDeclarationBillEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSettleDeclarationBillEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public SettleDeclarationBillEntryCollection getSettleDeclarationBillEntryCollection() throws BOSException
    {
        try {
            return getController().getSettleDeclarationBillEntryCollection(getContext());
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
    public SettleDeclarationBillEntryCollection getSettleDeclarationBillEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSettleDeclarationBillEntryCollection(getContext(), view);
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
    public SettleDeclarationBillEntryCollection getSettleDeclarationBillEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getSettleDeclarationBillEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}