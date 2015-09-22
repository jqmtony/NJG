package com.kingdee.eas.fdc.costindexdb;

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
import com.kingdee.eas.fdc.costindexdb.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class BaseAndSinglePointEntry extends CoreBillEntryBase implements IBaseAndSinglePointEntry
{
    public BaseAndSinglePointEntry()
    {
        super();
        registerInterface(IBaseAndSinglePointEntry.class, this);
    }
    public BaseAndSinglePointEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IBaseAndSinglePointEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("DA889AAA");
    }
    private BaseAndSinglePointEntryController getController() throws BOSException
    {
        return (BaseAndSinglePointEntryController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public BaseAndSinglePointEntryInfo getBaseAndSinglePointEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getBaseAndSinglePointEntryInfo(getContext(), pk);
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
    public BaseAndSinglePointEntryInfo getBaseAndSinglePointEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getBaseAndSinglePointEntryInfo(getContext(), pk, selector);
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
    public BaseAndSinglePointEntryInfo getBaseAndSinglePointEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getBaseAndSinglePointEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public BaseAndSinglePointEntryCollection getBaseAndSinglePointEntryCollection() throws BOSException
    {
        try {
            return getController().getBaseAndSinglePointEntryCollection(getContext());
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
    public BaseAndSinglePointEntryCollection getBaseAndSinglePointEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getBaseAndSinglePointEntryCollection(getContext(), view);
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
    public BaseAndSinglePointEntryCollection getBaseAndSinglePointEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getBaseAndSinglePointEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}