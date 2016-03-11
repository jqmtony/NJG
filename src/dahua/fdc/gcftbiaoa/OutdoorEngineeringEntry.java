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

public class OutdoorEngineeringEntry extends CoreBillEntryBase implements IOutdoorEngineeringEntry
{
    public OutdoorEngineeringEntry()
    {
        super();
        registerInterface(IOutdoorEngineeringEntry.class, this);
    }
    public OutdoorEngineeringEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IOutdoorEngineeringEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("094E92D4");
    }
    private OutdoorEngineeringEntryController getController() throws BOSException
    {
        return (OutdoorEngineeringEntryController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public OutdoorEngineeringEntryInfo getOutdoorEngineeringEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getOutdoorEngineeringEntryInfo(getContext(), pk);
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
    public OutdoorEngineeringEntryInfo getOutdoorEngineeringEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getOutdoorEngineeringEntryInfo(getContext(), pk, selector);
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
    public OutdoorEngineeringEntryInfo getOutdoorEngineeringEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getOutdoorEngineeringEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public OutdoorEngineeringEntryCollection getOutdoorEngineeringEntryCollection() throws BOSException
    {
        try {
            return getController().getOutdoorEngineeringEntryCollection(getContext());
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
    public OutdoorEngineeringEntryCollection getOutdoorEngineeringEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getOutdoorEngineeringEntryCollection(getContext(), view);
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
    public OutdoorEngineeringEntryCollection getOutdoorEngineeringEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getOutdoorEngineeringEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}