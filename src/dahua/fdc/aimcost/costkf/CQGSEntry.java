package com.kingdee.eas.fdc.aimcost.costkf;

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
import com.kingdee.eas.fdc.aimcost.costkf.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class CQGSEntry extends CoreBillEntryBase implements ICQGSEntry
{
    public CQGSEntry()
    {
        super();
        registerInterface(ICQGSEntry.class, this);
    }
    public CQGSEntry(Context ctx)
    {
        super(ctx);
        registerInterface(ICQGSEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("0515C4AF");
    }
    private CQGSEntryController getController() throws BOSException
    {
        return (CQGSEntryController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public CQGSEntryInfo getCQGSEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getCQGSEntryInfo(getContext(), pk);
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
    public CQGSEntryInfo getCQGSEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getCQGSEntryInfo(getContext(), pk, selector);
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
    public CQGSEntryInfo getCQGSEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getCQGSEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public CQGSEntryCollection getCQGSEntryCollection() throws BOSException
    {
        try {
            return getController().getCQGSEntryCollection(getContext());
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
    public CQGSEntryCollection getCQGSEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getCQGSEntryCollection(getContext(), view);
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
    public CQGSEntryCollection getCQGSEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getCQGSEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}