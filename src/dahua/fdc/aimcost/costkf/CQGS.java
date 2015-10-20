package com.kingdee.eas.fdc.aimcost.costkf;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.aimcost.costkf.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBillBase;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBillBase;

public class CQGS extends CoreBillBase implements ICQGS
{
    public CQGS()
    {
        super();
        registerInterface(ICQGS.class, this);
    }
    public CQGS(Context ctx)
    {
        super(ctx);
        registerInterface(ICQGS.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("D1BD4D83");
    }
    private CQGSController getController() throws BOSException
    {
        return (CQGSController)getBizController();
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public CQGSCollection getCQGSCollection() throws BOSException
    {
        try {
            return getController().getCQGSCollection(getContext());
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
    public CQGSCollection getCQGSCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getCQGSCollection(getContext(), view);
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
    public CQGSCollection getCQGSCollection(String oql) throws BOSException
    {
        try {
            return getController().getCQGSCollection(getContext(), oql);
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
    public CQGSInfo getCQGSInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getCQGSInfo(getContext(), pk);
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
    public CQGSInfo getCQGSInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getCQGSInfo(getContext(), pk, selector);
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
    public CQGSInfo getCQGSInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getCQGSInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���-User defined method
     *@param model model
     */
    public void aduit(CQGSInfo model) throws BOSException, EASBizException
    {
        try {
            getController().aduit(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�����-User defined method
     *@param model model
     */
    public void unAudit(CQGSInfo model) throws BOSException, EASBizException
    {
        try {
            getController().unAudit(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}