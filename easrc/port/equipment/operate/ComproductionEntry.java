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
     *ȡֵ-System defined method
     *@param pk ȡֵ
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
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@param selector ȡֵ
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
     *ȡֵ-System defined method
     *@param oql ȡֵ
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
     *ȡ����-System defined method
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
     *ȡ����-System defined method
     *@param view ȡ����
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
     *ȡ����-System defined method
     *@param oql ȡ����
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