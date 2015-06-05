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

public class RichExamTempTab extends DataBase implements IRichExamTempTab
{
    public RichExamTempTab()
    {
        super();
        registerInterface(IRichExamTempTab.class, this);
    }
    public RichExamTempTab(Context ctx)
    {
        super(ctx);
        registerInterface(IRichExamTempTab.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("90615CB8");
    }
    private RichExamTempTabController getController() throws BOSException
    {
        return (RichExamTempTabController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public RichExamTempTabInfo getRichExamTempTabInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getRichExamTempTabInfo(getContext(), pk);
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
    public RichExamTempTabInfo getRichExamTempTabInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getRichExamTempTabInfo(getContext(), pk, selector);
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
    public RichExamTempTabInfo getRichExamTempTabInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getRichExamTempTabInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public RichExamTempTabCollection getRichExamTempTabCollection() throws BOSException
    {
        try {
            return getController().getRichExamTempTabCollection(getContext());
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
    public RichExamTempTabCollection getRichExamTempTabCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getRichExamTempTabCollection(getContext(), view);
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
    public RichExamTempTabCollection getRichExamTempTabCollection(String oql) throws BOSException
    {
        try {
            return getController().getRichExamTempTabCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *同步到检单-User defined method
     *@param model model
     */
    public void syncRichExamed(RichExamTempTabInfo model) throws BOSException
    {
        try {
            getController().syncRichExamed(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}