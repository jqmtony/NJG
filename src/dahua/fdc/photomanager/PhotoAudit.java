package com.kingdee.eas.fdc.photomanager;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.photomanager.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBillBase;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBillBase;

public class PhotoAudit extends CoreBillBase implements IPhotoAudit
{
    public PhotoAudit()
    {
        super();
        registerInterface(IPhotoAudit.class, this);
    }
    public PhotoAudit(Context ctx)
    {
        super(ctx);
        registerInterface(IPhotoAudit.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("17269CFB");
    }
    private PhotoAuditController getController() throws BOSException
    {
        return (PhotoAuditController)getBizController();
    }
    /**
     *取集合-System defined method
     *@return
     */
    public PhotoAuditCollection getPhotoAuditCollection() throws BOSException
    {
        try {
            return getController().getPhotoAuditCollection(getContext());
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
    public PhotoAuditCollection getPhotoAuditCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getPhotoAuditCollection(getContext(), view);
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
    public PhotoAuditCollection getPhotoAuditCollection(String oql) throws BOSException
    {
        try {
            return getController().getPhotoAuditCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public PhotoAuditInfo getPhotoAuditInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getPhotoAuditInfo(getContext(), pk);
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
    public PhotoAuditInfo getPhotoAuditInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getPhotoAuditInfo(getContext(), pk, selector);
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
    public PhotoAuditInfo getPhotoAuditInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getPhotoAuditInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *审批-User defined method
     *@param model model
     */
    public void audit(PhotoAuditInfo model) throws BOSException
    {
        try {
            getController().audit(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *反审批-User defined method
     *@param model model
     */
    public void unAudit(PhotoAuditInfo model) throws BOSException
    {
        try {
            getController().unAudit(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}