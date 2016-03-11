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

public class DecorationEngineeringEntry extends CoreBillEntryBase implements IDecorationEngineeringEntry
{
    public DecorationEngineeringEntry()
    {
        super();
        registerInterface(IDecorationEngineeringEntry.class, this);
    }
    public DecorationEngineeringEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IDecorationEngineeringEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("614539B6");
    }
    private DecorationEngineeringEntryController getController() throws BOSException
    {
        return (DecorationEngineeringEntryController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public DecorationEngineeringEntryInfo getDecorationEngineeringEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getDecorationEngineeringEntryInfo(getContext(), pk);
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
    public DecorationEngineeringEntryInfo getDecorationEngineeringEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getDecorationEngineeringEntryInfo(getContext(), pk, selector);
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
    public DecorationEngineeringEntryInfo getDecorationEngineeringEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getDecorationEngineeringEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public DecorationEngineeringEntryCollection getDecorationEngineeringEntryCollection() throws BOSException
    {
        try {
            return getController().getDecorationEngineeringEntryCollection(getContext());
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
    public DecorationEngineeringEntryCollection getDecorationEngineeringEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getDecorationEngineeringEntryCollection(getContext(), view);
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
    public DecorationEngineeringEntryCollection getDecorationEngineeringEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getDecorationEngineeringEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}