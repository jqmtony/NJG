package com.kingdee.eas.port.equipment.operate;

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
import com.kingdee.eas.port.equipment.operate.app.*;

public class ComproductionEntry extends CoreBillEntryBase implements IComproductionEntry
{
    public ComproductionEntry()
    {
        super();
        registerInterface(IComproductionEntry.class, this);
    }
    public ComproductionEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IComproductionEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("62F96457");
    }
    private ComproductionEntryController getController() throws BOSException
    {
        return (ComproductionEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public ComproductionEntryInfo getComproductionEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getComproductionEntryInfo(getContext(), pk);
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
    public ComproductionEntryInfo getComproductionEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getComproductionEntryInfo(getContext(), pk, selector);
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
    public ComproductionEntryInfo getComproductionEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getComproductionEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public ComproductionEntryCollection getComproductionEntryCollection() throws BOSException
    {
        try {
            return getController().getComproductionEntryCollection(getContext());
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
    public ComproductionEntryCollection getComproductionEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getComproductionEntryCollection(getContext(), view);
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
    public ComproductionEntryCollection getComproductionEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getComproductionEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}