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

public class NoticeInfo extends CoreBase implements INoticeInfo
{
    public NoticeInfo()
    {
        super();
        registerInterface(INoticeInfo.class, this);
    }
    public NoticeInfo(Context ctx)
    {
        super(ctx);
        registerInterface(INoticeInfo.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("DF7CD418");
    }
    private NoticeInfoController getController() throws BOSException
    {
        return (NoticeInfoController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public NoticeInfoInfo getNoticeInfoInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getNoticeInfoInfo(getContext(), pk);
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
    public NoticeInfoInfo getNoticeInfoInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getNoticeInfoInfo(getContext(), pk, selector);
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
    public NoticeInfoInfo getNoticeInfoInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getNoticeInfoInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public NoticeInfoCollection getNoticeInfoCollection() throws BOSException
    {
        try {
            return getController().getNoticeInfoCollection(getContext());
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
    public NoticeInfoCollection getNoticeInfoCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getNoticeInfoCollection(getContext(), view);
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
    public NoticeInfoCollection getNoticeInfoCollection(String oql) throws BOSException
    {
        try {
            return getController().getNoticeInfoCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}