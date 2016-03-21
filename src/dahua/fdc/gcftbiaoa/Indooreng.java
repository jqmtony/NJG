package com.kingdee.eas.fdc.gcftbiaoa;

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
import com.kingdee.eas.fdc.gcftbiaoa.app.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBillBase;
import com.kingdee.util.NumericException;

public class Indooreng extends CoreBillBase implements IIndooreng
{
    public Indooreng()
    {
        super();
        registerInterface(IIndooreng.class, this);
    }
    public Indooreng(Context ctx)
    {
        super(ctx);
        registerInterface(IIndooreng.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("939CCB04");
    }
    private IndoorengController getController() throws BOSException
    {
        return (IndoorengController)getBizController();
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public IndoorengCollection getIndoorengCollection() throws BOSException
    {
        try {
            return getController().getIndoorengCollection(getContext());
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
    public IndoorengCollection getIndoorengCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getIndoorengCollection(getContext(), view);
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
    public IndoorengCollection getIndoorengCollection(String oql) throws BOSException
    {
        try {
            return getController().getIndoorengCollection(getContext(), oql);
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
    public IndoorengInfo getIndoorengInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getIndoorengInfo(getContext(), pk);
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
    public IndoorengInfo getIndoorengInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getIndoorengInfo(getContext(), pk, selector);
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
    public IndoorengInfo getIndoorengInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getIndoorengInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���-User defined method
     *@param model model
     * @throws NumericException 
     */
    public void actionAudit(IndoorengInfo model) throws BOSException, NumericException
    {
        try {
            getController().actionAudit(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�����-User defined method
     *@param model model
     * @throws EASBizException 
     */
    public void actionUnAudit(IndoorengInfo model) throws BOSException, EASBizException
    {
        try {
            getController().actionUnAudit(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}