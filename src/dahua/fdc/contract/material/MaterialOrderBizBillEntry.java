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
     *取值-System defined method
     *@param pk 取值
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
     *取值-System defined method
     *@param pk 取值
     *@param selector 取值
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
     *取值-System defined method
     *@param oql 取值
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
     *取集合-System defined method
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
     *取集合-System defined method
     *@param view 取集合
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
     *取集合-System defined method
     *@param oql 取集合
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