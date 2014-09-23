package com.kingdee.eas.bpm;

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
import com.kingdee.eas.bpm.app.*;
import com.kingdee.eas.framework.IDataBase;

public class BPMLog extends DataBase implements IBPMLog
{
    public BPMLog()
    {
        super();
        registerInterface(IBPMLog.class, this);
    }
    public BPMLog(Context ctx)
    {
        super(ctx);
        registerInterface(IBPMLog.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("44F49766");
    }
    private BPMLogController getController() throws BOSException
    {
        return (BPMLogController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public BPMLogInfo getBPMLogInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getBPMLogInfo(getContext(), pk);
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
    public BPMLogInfo getBPMLogInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getBPMLogInfo(getContext(), pk, selector);
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
    public BPMLogInfo getBPMLogInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getBPMLogInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public BPMLogCollection getBPMLogCollection() throws BOSException
    {
        try {
            return getController().getBPMLogCollection(getContext());
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
    public BPMLogCollection getBPMLogCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getBPMLogCollection(getContext(), view);
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
    public BPMLogCollection getBPMLogCollection(String oql) throws BOSException
    {
        try {
            return getController().getBPMLogCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}