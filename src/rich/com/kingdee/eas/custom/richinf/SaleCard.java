package com.kingdee.eas.custom.richinf;

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
import com.kingdee.eas.framework.CoreBillBase;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBillBase;
import com.kingdee.eas.custom.richinf.app.*;

public class SaleCard extends CoreBillBase implements ISaleCard
{
    public SaleCard()
    {
        super();
        registerInterface(ISaleCard.class, this);
    }
    public SaleCard(Context ctx)
    {
        super(ctx);
        registerInterface(ISaleCard.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("E3B79B05");
    }
    private SaleCardController getController() throws BOSException
    {
        return (SaleCardController)getBizController();
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public SaleCardCollection getSaleCardCollection() throws BOSException
    {
        try {
            return getController().getSaleCardCollection(getContext());
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
    public SaleCardCollection getSaleCardCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSaleCardCollection(getContext(), view);
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
    public SaleCardCollection getSaleCardCollection(String oql) throws BOSException
    {
        try {
            return getController().getSaleCardCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public SaleCardInfo getSaleCardInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSaleCardInfo(getContext(), pk);
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
    public SaleCardInfo getSaleCardInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSaleCardInfo(getContext(), pk, selector);
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
    public SaleCardInfo getSaleCardInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSaleCardInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}