package com.kingdee.eas.port.pm.qa;

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
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.port.pm.qa.app.*;
import com.kingdee.eas.framework.IDataBase;

public class LinkBill extends DataBase implements ILinkBill
{
    public LinkBill()
    {
        super();
        registerInterface(ILinkBill.class, this);
    }
    public LinkBill(Context ctx)
    {
        super(ctx);
        registerInterface(ILinkBill.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("4084C343");
    }
    private LinkBillController getController() throws BOSException
    {
        return (LinkBillController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public LinkBillInfo getLinkBillInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getLinkBillInfo(getContext(), pk);
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
    public LinkBillInfo getLinkBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getLinkBillInfo(getContext(), pk, selector);
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
    public LinkBillInfo getLinkBillInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getLinkBillInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public LinkBillCollection getLinkBillCollection() throws BOSException
    {
        try {
            return getController().getLinkBillCollection(getContext());
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
    public LinkBillCollection getLinkBillCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getLinkBillCollection(getContext(), view);
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
    public LinkBillCollection getLinkBillCollection(String oql) throws BOSException
    {
        try {
            return getController().getLinkBillCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}