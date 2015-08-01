package com.kingdee.eas.fdc.material;

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
import com.kingdee.eas.fdc.material.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class MaterialOrderBizBillEntry extends CoreBillEntryBase implements IMaterialOrderBizBillEntry
{
    public MaterialOrderBizBillEntry()
    {
        super();
        registerInterface(IMaterialOrderBizBillEntry.class, this);
    }
    public MaterialOrderBizBillEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IMaterialOrderBizBillEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("3F1DF459");
    }
    private MaterialOrderBizBillEntryController getController() throws BOSException
    {
        return (MaterialOrderBizBillEntryController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public MaterialOrderBizBillEntryInfo getMaterialOrderBizBillEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getMaterialOrderBizBillEntryInfo(getContext(), pk);
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
    public MaterialOrderBizBillEntryInfo getMaterialOrderBizBillEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getMaterialOrderBizBillEntryInfo(getContext(), pk, selector);
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
    public MaterialOrderBizBillEntryInfo getMaterialOrderBizBillEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getMaterialOrderBizBillEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public MaterialOrderBizBillEntryCollection getMaterialOrderBizBillEntryCollection() throws BOSException
    {
        try {
            return getController().getMaterialOrderBizBillEntryCollection(getContext());
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
    public MaterialOrderBizBillEntryCollection getMaterialOrderBizBillEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getMaterialOrderBizBillEntryCollection(getContext(), view);
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
    public MaterialOrderBizBillEntryCollection getMaterialOrderBizBillEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getMaterialOrderBizBillEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}