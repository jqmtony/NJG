package com.kingdee.eas.port.pm.base;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.port.pm.base.app.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.TreeBase;

public class QuestionTypeTteeTree extends TreeBase implements IQuestionTypeTteeTree
{
    public QuestionTypeTteeTree()
    {
        super();
        registerInterface(IQuestionTypeTteeTree.class, this);
    }
    public QuestionTypeTteeTree(Context ctx)
    {
        super(ctx);
        registerInterface(IQuestionTypeTteeTree.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("4C61F8FF");
    }
    private QuestionTypeTteeTreeController getController() throws BOSException
    {
        return (QuestionTypeTteeTreeController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public QuestionTypeTteeTreeInfo getQuestionTypeTteeTreeInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getQuestionTypeTteeTreeInfo(getContext(), pk);
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
    public QuestionTypeTteeTreeInfo getQuestionTypeTteeTreeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getQuestionTypeTteeTreeInfo(getContext(), pk, selector);
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
    public QuestionTypeTteeTreeInfo getQuestionTypeTteeTreeInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getQuestionTypeTteeTreeInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public QuestionTypeTteeTreeCollection getQuestionTypeTteeTreeCollection() throws BOSException
    {
        try {
            return getController().getQuestionTypeTteeTreeCollection(getContext());
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
    public QuestionTypeTteeTreeCollection getQuestionTypeTteeTreeCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getQuestionTypeTteeTreeCollection(getContext(), view);
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
    public QuestionTypeTteeTreeCollection getQuestionTypeTteeTreeCollection(String oql) throws BOSException
    {
        try {
            return getController().getQuestionTypeTteeTreeCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}