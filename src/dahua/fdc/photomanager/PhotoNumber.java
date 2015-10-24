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
import com.kingdee.eas.framework.DataBase;
import com.kingdee.eas.fdc.photomanager.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IDataBase;

public class PhotoNumber extends DataBase implements IPhotoNumber
{
    public PhotoNumber()
    {
        super();
        registerInterface(IPhotoNumber.class, this);
    }
    public PhotoNumber(Context ctx)
    {
        super(ctx);
        registerInterface(IPhotoNumber.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("E3DFFE29");
    }
    private PhotoNumberController getController() throws BOSException
    {
        return (PhotoNumberController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public PhotoNumberInfo getPhotoNumberInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getPhotoNumberInfo(getContext(), pk);
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
    public PhotoNumberInfo getPhotoNumberInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getPhotoNumberInfo(getContext(), pk, selector);
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
    public PhotoNumberInfo getPhotoNumberInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getPhotoNumberInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public PhotoNumberCollection getPhotoNumberCollection() throws BOSException
    {
        try {
            return getController().getPhotoNumberCollection(getContext());
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
    public PhotoNumberCollection getPhotoNumberCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getPhotoNumberCollection(getContext(), view);
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
    public PhotoNumberCollection getPhotoNumberCollection(String oql) throws BOSException
    {
        try {
            return getController().getPhotoNumberCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}