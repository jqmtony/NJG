package com.kingdee.eas.port.equipment.record;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.DataBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.port.equipment.record.app.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IDataBase;

public class Equipment extends DataBase implements IEquipment
{
    public Equipment()
    {
        super();
        registerInterface(IEquipment.class, this);
    }
    public Equipment(Context ctx)
    {
        super(ctx);
        registerInterface(IEquipment.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("3C6AD10C");
    }
    private EquipmentController getController() throws BOSException
    {
        return (EquipmentController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public EquipmentInfo getEquipmentInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getEquipmentInfo(getContext(), pk);
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
    public EquipmentInfo getEquipmentInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getEquipmentInfo(getContext(), pk, selector);
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
    public EquipmentInfo getEquipmentInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getEquipmentInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public EquipmentCollection getEquipmentCollection() throws BOSException
    {
        try {
            return getController().getEquipmentCollection(getContext());
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
    public EquipmentCollection getEquipmentCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getEquipmentCollection(getContext(), view);
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
    public EquipmentCollection getEquipmentCollection(String oql) throws BOSException
    {
        try {
            return getController().getEquipmentCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}