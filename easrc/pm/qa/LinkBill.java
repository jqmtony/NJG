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
     *ȡֵ-System defined method
     *@param pk ȡֵ
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
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@param selector ȡֵ
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
     *ȡֵ-System defined method
     *@param oql ȡֵ
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
     *ȡ����-System defined method
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
     *ȡ����-System defined method
     *@param view ȡ����
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
     *ȡ����-System defined method
     *@param oql ȡ����
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