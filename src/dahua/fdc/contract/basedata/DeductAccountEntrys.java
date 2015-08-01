package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBase;
import com.kingdee.eas.fdc.basedata.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBase;

public class DeductAccountEntrys extends CoreBase implements IDeductAccountEntrys
{
    public DeductAccountEntrys()
    {
        super();
        registerInterface(IDeductAccountEntrys.class, this);
    }
    public DeductAccountEntrys(Context ctx)
    {
        super(ctx);
        registerInterface(IDeductAccountEntrys.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("E5665E99");
    }
    private DeductAccountEntrysController getController() throws BOSException
    {
        return (DeductAccountEntrysController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public DeductAccountEntrysInfo getDeductAccountEntrysInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getDeductAccountEntrysInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@param selector selector
     *@return
     */
    public DeductAccountEntrysInfo getDeductAccountEntrysInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getDeductAccountEntrysInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param oql oql
     *@return
     */
    public DeductAccountEntrysInfo getDeductAccountEntrysInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getDeductAccountEntrysInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public DeductAccountEntrysCollection getDeductAccountEntrysCollection() throws BOSException
    {
        try {
            return getController().getDeductAccountEntrysCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@param view view
     *@return
     */
    public DeductAccountEntrysCollection getDeductAccountEntrysCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getDeductAccountEntrysCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@param oql oql
     *@return
     */
    public DeductAccountEntrysCollection getDeductAccountEntrysCollection(String oql) throws BOSException
    {
        try {
            return getController().getDeductAccountEntrysCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}