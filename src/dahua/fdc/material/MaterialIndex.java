package com.kingdee.eas.fdc.material;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.fdc.basedata.IFDCDataBase;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.material.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basedata.FDCDataBase;
import java.util.List;

public class MaterialIndex extends FDCDataBase implements IMaterialIndex
{
    public MaterialIndex()
    {
        super();
        registerInterface(IMaterialIndex.class, this);
    }
    public MaterialIndex(Context ctx)
    {
        super(ctx);
        registerInterface(IMaterialIndex.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("09E88265");
    }
    private MaterialIndexController getController() throws BOSException
    {
        return (MaterialIndexController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public MaterialIndexInfo getMaterialIndexInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getMaterialIndexInfo(getContext(), pk);
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
    public MaterialIndexInfo getMaterialIndexInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getMaterialIndexInfo(getContext(), pk, selector);
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
    public MaterialIndexInfo getMaterialIndexInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getMaterialIndexInfo(getContext(), oql);
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
    public MaterialIndexCollection getMaterialIndexCollection(String oql) throws BOSException
    {
        try {
            return getController().getMaterialIndexCollection(getContext(), oql);
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
    public MaterialIndexCollection getMaterialIndexCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getMaterialIndexCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public MaterialIndexCollection getMaterialIndexCollection() throws BOSException
    {
        try {
            return getController().getMaterialIndexCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *²éÑ¯Ìõ¼þ-User defined method
     *@param sql sql
     *@return
     */
    public List getSQLString(String sql) throws BOSException, EASBizException
    {
        try {
            return getController().getSQLString(getContext(), sql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}