package com.kingdee.eas.fdc.material;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.material.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBase;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import java.util.ArrayList;
import com.kingdee.eas.framework.ICoreBase;

public class MaterialIndexValue extends CoreBase implements IMaterialIndexValue
{
    public MaterialIndexValue()
    {
        super();
        registerInterface(IMaterialIndexValue.class, this);
    }
    public MaterialIndexValue(Context ctx)
    {
        super(ctx);
        registerInterface(IMaterialIndexValue.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("6E5BD60C");
    }
    private MaterialIndexValueController getController() throws BOSException
    {
        return (MaterialIndexValueController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public MaterialIndexValueInfo getMaterialIndexValueInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getMaterialIndexValueInfo(getContext(), pk);
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
    public MaterialIndexValueInfo getMaterialIndexValueInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getMaterialIndexValueInfo(getContext(), pk, selector);
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
    public MaterialIndexValueInfo getMaterialIndexValueInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getMaterialIndexValueInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public MaterialIndexValueCollection getMaterialIndexValueCollection() throws BOSException
    {
        try {
            return getController().getMaterialIndexValueCollection(getContext());
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
    public MaterialIndexValueCollection getMaterialIndexValueCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getMaterialIndexValueCollection(getContext(), view);
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
    public MaterialIndexValueCollection getMaterialIndexValueCollection(String oql) throws BOSException
    {
        try {
            return getController().getMaterialIndexValueCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *插入特性指标值-User defined method
     *@param params params
     *@param materialInfoId materialInfoId
     */
    public void insertIndexValue(ArrayList params, IObjectPK materialInfoId) throws BOSException
    {
        try {
            getController().insertIndexValue(getContext(), params, materialInfoId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}