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

public class MaterialEnterSumEntry extends CoreBillEntryBase implements IMaterialEnterSumEntry
{
    public MaterialEnterSumEntry()
    {
        super();
        registerInterface(IMaterialEnterSumEntry.class, this);
    }
    public MaterialEnterSumEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IMaterialEnterSumEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("9525BA72");
    }
    private MaterialEnterSumEntryController getController() throws BOSException
    {
        return (MaterialEnterSumEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public MaterialEnterSumEntryInfo getMaterialEnterSumEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getMaterialEnterSumEntryInfo(getContext(), pk);
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
    public MaterialEnterSumEntryInfo getMaterialEnterSumEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getMaterialEnterSumEntryInfo(getContext(), pk, selector);
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
    public MaterialEnterSumEntryInfo getMaterialEnterSumEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getMaterialEnterSumEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public MaterialEnterSumEntryCollection getMaterialEnterSumEntryCollection() throws BOSException
    {
        try {
            return getController().getMaterialEnterSumEntryCollection(getContext());
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
    public MaterialEnterSumEntryCollection getMaterialEnterSumEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getMaterialEnterSumEntryCollection(getContext(), view);
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
    public MaterialEnterSumEntryCollection getMaterialEnterSumEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getMaterialEnterSumEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}