package com.kingdee.eas.custom.richinf;

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
import com.kingdee.eas.framework.IDataBase;
import com.kingdee.eas.custom.richinf.app.*;

public class RichExamCheckTab extends DataBase implements IRichExamCheckTab
{
    public RichExamCheckTab()
    {
        super();
        registerInterface(IRichExamCheckTab.class, this);
    }
    public RichExamCheckTab(Context ctx)
    {
        super(ctx);
        registerInterface(IRichExamCheckTab.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("26353EB6");
    }
    private RichExamCheckTabController getController() throws BOSException
    {
        return (RichExamCheckTabController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public RichExamCheckTabInfo getRichExamCheckTabInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getRichExamCheckTabInfo(getContext(), pk);
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
    public RichExamCheckTabInfo getRichExamCheckTabInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getRichExamCheckTabInfo(getContext(), pk, selector);
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
    public RichExamCheckTabInfo getRichExamCheckTabInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getRichExamCheckTabInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public RichExamCheckTabCollection getRichExamCheckTabCollection() throws BOSException
    {
        try {
            return getController().getRichExamCheckTabCollection(getContext());
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
    public RichExamCheckTabCollection getRichExamCheckTabCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getRichExamCheckTabCollection(getContext(), view);
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
    public RichExamCheckTabCollection getRichExamCheckTabCollection(String oql) throws BOSException
    {
        try {
            return getController().getRichExamCheckTabCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}