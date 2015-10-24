package com.kingdee.eas.fdc.photomanager;

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
import com.kingdee.eas.fdc.photomanager.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class PhotoAuditEntry extends CoreBillEntryBase implements IPhotoAuditEntry
{
    public PhotoAuditEntry()
    {
        super();
        registerInterface(IPhotoAuditEntry.class, this);
    }
    public PhotoAuditEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IPhotoAuditEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("54EB6037");
    }
    private PhotoAuditEntryController getController() throws BOSException
    {
        return (PhotoAuditEntryController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public PhotoAuditEntryInfo getPhotoAuditEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getPhotoAuditEntryInfo(getContext(), pk);
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
    public PhotoAuditEntryInfo getPhotoAuditEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getPhotoAuditEntryInfo(getContext(), pk, selector);
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
    public PhotoAuditEntryInfo getPhotoAuditEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getPhotoAuditEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public PhotoAuditEntryCollection getPhotoAuditEntryCollection() throws BOSException
    {
        try {
            return getController().getPhotoAuditEntryCollection(getContext());
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
    public PhotoAuditEntryCollection getPhotoAuditEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getPhotoAuditEntryCollection(getContext(), view);
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
    public PhotoAuditEntryCollection getPhotoAuditEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getPhotoAuditEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}