package com.kingdee.eas.fdc.contract.basedata;

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
import com.kingdee.eas.fdc.contract.basedata.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IDataBase;

public class ProgrammingTemp extends DataBase implements IProgrammingTemp
{
    public ProgrammingTemp()
    {
        super();
        registerInterface(IProgrammingTemp.class, this);
    }
    public ProgrammingTemp(Context ctx)
    {
        super(ctx);
        registerInterface(IProgrammingTemp.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("EE2D5BDB");
    }
    private ProgrammingTempController getController() throws BOSException
    {
        return (ProgrammingTempController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public ProgrammingTempInfo getProgrammingTempInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getProgrammingTempInfo(getContext(), pk);
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
    public ProgrammingTempInfo getProgrammingTempInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getProgrammingTempInfo(getContext(), pk, selector);
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
    public ProgrammingTempInfo getProgrammingTempInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getProgrammingTempInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public ProgrammingTempCollection getProgrammingTempCollection() throws BOSException
    {
        try {
            return getController().getProgrammingTempCollection(getContext());
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
    public ProgrammingTempCollection getProgrammingTempCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getProgrammingTempCollection(getContext(), view);
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
    public ProgrammingTempCollection getProgrammingTempCollection(String oql) throws BOSException
    {
        try {
            return getController().getProgrammingTempCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}